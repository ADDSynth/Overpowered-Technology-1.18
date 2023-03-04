package addsynth.overpoweredmod.machines.laser.machine;

import java.util.ArrayList;
import java.util.HashSet;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.Node;
import addsynth.core.util.game.data.AdvancementUtil;
import addsynth.core.util.math.block.BlockMath;
import addsynth.core.util.math.block.DirectionUtil;
import addsynth.core.util.network.NetworkUtil;
import addsynth.core.util.player.PlayerUtil;
import addsynth.energy.lib.main.Energy;
import addsynth.energy.lib.main.Receiver;
import addsynth.overpoweredmod.assets.CustomAdvancements;
import addsynth.overpoweredmod.assets.CustomStats;
import addsynth.overpoweredmod.assets.Sounds;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.machines.laser.LaserJobs;
import addsynth.overpoweredmod.machines.laser.cannon.LaserCannon;
import addsynth.overpoweredmod.machines.laser.network_messages.LaserClientSyncMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class LaserNetwork extends BlockNetwork<TileLaserHousing> {

  public static final int max_laser_distance = 1000;

  public boolean changed;
  private final HashSet<BlockPos> lasers = new HashSet<BlockPos>(27);
  private int number_of_lasers;
  private boolean activated;
  private int laser_distance;
  public final Receiver energy = new Receiver(0, MachineValues.laser_max_receive.get()){
    @Override
    public boolean canReceive(){
      return super.canReceive() && running;
    }
  };
  public boolean running;
  public boolean auto_shutoff;

  // https://stackoverflow.com/questions/4963300/which-notnull-java-annotation-should-i-use
  // https://blogs.oracle.com/java-platform-group/java-8s-new-type-annotations
  public LaserNetwork(final Level world, final TileLaserHousing tile){
    super(world, tile);
  }

  @Override
  protected void clear_custom_data(){
    lasers.clear();
  }

  @Override
  protected final void onUpdateNetworkFinished(){
    check_if_lasers_changed();
  }

  @Override
  protected final void customSearch(final Node node){
    final BlockEntity tile = node.getTile();
    if(tile != null){
      if(tile.getClass() == TileLaserHousing.class){
        
        final BlockPos tile_position = tile.getBlockPos();
        BlockPos position;
        for(Direction direction : Direction.values()){
          position = tile_position.relative(direction);
          check_and_add_LaserCannon(position, direction);
        }
      }
    }
  }

  /** Checks position if it is a valid LaserCannon, and adds it. */
  private final void check_and_add_LaserCannon(final BlockPos position, final Direction direction){
    BlockState state = world.getBlockState(position);
    LaserCannon laser_block;
    if(state.getBlock() instanceof LaserCannon){
      laser_block = (LaserCannon)state.getBlock();
      if(laser_block.color >= 0 && state.getValue(LaserCannon.FACING) == direction){
        lasers.add(position);
      }
    }
  }

  @Override
  public void neighbor_was_changed(final BlockPos current_position, final BlockPos position_of_neighbor){
    check_and_add_LaserCannon(position_of_neighbor, DirectionUtil.getDirection(current_position, position_of_neighbor));
    lasers.removeIf((BlockPos pos) -> world.getBlockState(pos).getBlock() instanceof LaserCannon == false);
    // if(world.getBlockState(position_of_neighbor).getBlock() instanceof LaserCannon == false){
    //   lasers.remove(position_of_neighbor);
    // }
    check_if_lasers_changed();
  }

  public final void load_data(Energy energy, boolean power_switch, int laser_distance, boolean auto_shutoff){
    this.energy.set(energy);
    this.running = power_switch;
    this.laser_distance = laser_distance;
    this.auto_shutoff = auto_shutoff;
  }

  public final int getLaserDistance(){
    return laser_distance;
  }

  public final void setLaserDistance(int laser_distance){
    this.laser_distance = laser_distance;
    update_energy_requirements();
  }

  private final void check_if_lasers_changed(){
    if(lasers.size() != number_of_lasers){
      number_of_lasers = lasers.size();
      update_energy_requirements();
      updateClient();
    }
  }

  /**
   *  The energy requirements of the whole LaserHousing network is calculated dynamically depending on how
   *  many lasers there are and what distance the lasers have to travel. It is recalculated whenever one of
   *  these variables change.
   */
  private final void update_energy_requirements(){
    energy.setCapacity(
      (number_of_lasers * MachineValues.required_energy_per_laser.get()) +
      (number_of_lasers * laser_distance * MachineValues.required_energy_per_laser_distance.get())
    );
  }

  /**
   * Only the first Laser Machine in this <code>LaserNetwork</code> calls the tick function,
   * and basically it checks if any {@link TileLaserHousing} is powered with redstone and calls the
   * {@link #fire_lasers()} function, if certain conditions are met.
   * @param tile
   */
  @Override
  public final void tick(final TileLaserHousing tile){
    if(tile == first_tile){
      if(is_redstone_powered()){
        if(activated == false){
          if(lasers.size() > 0 && laser_distance > 0){
            if(energy.isFull()){
              fire_lasers();
            }
          }
        }
        activated = true;
      }
      else{
        activated = false;
      }
      if(energy.tick()){
        changed = true;
      }
      if(changed){
        updateLaserNetwork();
        changed = false;
      }
      energy.updateEnergyIO();
    }
  }

  /** updates server (needs to be saved to world data) */
  private final void updateLaserNetwork(){
    remove_invalid_nodes(blocks);
    TileLaserHousing laser_housing;
    for(final Node node : blocks){
      laser_housing = (TileLaserHousing)node.getTile();
      if(laser_housing != null){
        laser_housing.setDataDirectlyFromNetwork(energy, laser_distance, running, auto_shutoff);
      }
    }
  }
  
  public final void updateClient(){ // (client can determine the information)
    final LaserClientSyncMessage message = new LaserClientSyncMessage(blocks.getBlockPositions(), number_of_lasers);
    NetworkUtil.send_to_clients_in_world(NetworkHandler.INSTANCE, world, message);
  }

  private final void fire_lasers(){
    remove_invalid_nodes(blocks);
    
    LaserJobs.addNew(world, lasers, laser_distance);
    
    final double[] center_position = BlockMath.getExactCenter(blocks.getBlockPositions());
    world.playSound(null, center_position[0], center_position[1], center_position[2], Sounds.laser_fire_sound, SoundSource.AMBIENT, 2.0f, 1.0f);
    awardPlayers();
    
    this.energy.subtract_capacity();
    if(auto_shutoff){
      running = false;
    }
    changed = true;
  }

  private final void awardPlayers(){
    final ArrayList<ServerPlayer> players = new ArrayList<>();
    
    blocks.forAllTileEntities(TileLaserHousing.class, (TileLaserHousing tile) -> {
      // only count each player once, increment Laser Fired stat of each player
      final String player_name = tile.getLastUsedBy();
      final ServerPlayer player = PlayerUtil.getPlayer(world, player_name);
      if(player != null){
        if(players.contains(player) == false){
          players.add(player);
        }
      }
    });
    
    for(final ServerPlayer player : players){
      AdvancementUtil.grantAdvancement(player, CustomAdvancements.FIRE_LASER);
      if(laser_distance >= LaserNetwork.max_laser_distance){
        AdvancementUtil.grantAdvancement(player, CustomAdvancements.FIRE_MAXIMUM_DISTANCE);
      }
      player.awardStat(CustomStats.LASERS_FIRED);
    }
  }

}
