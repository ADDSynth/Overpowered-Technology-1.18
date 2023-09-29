package addsynth.overpoweredmod.machines.laser.machine;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.core.game.tiles.TileBase;
import addsynth.core.util.game.tileentity.ITickingTileEntity;
import addsynth.energy.lib.main.Energy;
import addsynth.energy.lib.main.IEnergyConsumer;
import addsynth.energy.lib.main.Receiver;
import addsynth.energy.lib.tiles.machines.IAutoShutoff;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public final class TileLaserHousing extends TileBase implements IBlockNetworkUser<LaserNetwork>,
  ITickingTileEntity, IEnergyConsumer, IAutoShutoff, MenuProvider {

  private final Receiver energy = new Receiver(0, MachineValues.laser_max_receive.get());
  private boolean power_switch = true;

  private LaserNetwork network;
  private int laser_distance = Config.default_laser_distance.get();

  /** Set by {@link LaserNetwork#updateLaserNetwork()} method and used by
   *  {@link addsynth.overpoweredmod.machines.laser.machine.GuiLaserHousing}.
   */
  public int number_of_lasers;
  private boolean auto_shutoff = true;

  public TileLaserHousing(BlockPos position, BlockState blockstate){
    super(Tiles.LASER_MACHINE.get(), position, blockstate);
  }

  @Override
  public final void serverTick(){
    BlockNetwork.tick(network, level, this, LaserNetwork::new);
  }

  @Override
  public final void load(final CompoundTag nbt){
    super.load(nbt);
    energy.loadFromNBT(nbt);
    power_switch = nbt.getBoolean("Power Switch");
    laser_distance = nbt.getInt("Laser Distance");
    auto_shutoff = nbt.getBoolean("Auto Shutoff");
    loadPlayerData(nbt);
  }

  @Override
  protected final void saveAdditional(final CompoundTag nbt){
    super.saveAdditional(nbt);
    energy.saveToNBT(nbt); // save everything just in case we need to save more than just Energy, and maintain backward compatability.
    nbt.putBoolean("Power Switch", power_switch);
    nbt.putInt("Laser Distance", laser_distance);
    nbt.putBoolean("Auto Shutoff", auto_shutoff);
    savePlayerData(nbt);
  }

  @Override
  public final Energy getEnergy(){
    // OPTIMIZE: Standardize getting energy from multi-block structures.
    if(onClientSide()){
      return energy; // only guis should use this.
    }
    if(network == null){
      BlockNetworkUtil.createBlockNetwork(level, this, LaserNetwork::new);
    }
    return network.energy;
  }

  // Only the gui calls these
  public final int getLaserDistance(){     return laser_distance; }
  @Override
  public final boolean getAutoShutoff(){   return auto_shutoff; }
  @Override
  public final boolean get_switch_state(){ return power_switch; }

  public final void setDataDirectlyFromNetwork(final Energy energy, final int laser_distance, final boolean running, final boolean shutoff){
    this.energy.set(energy);
    this.laser_distance = laser_distance;
    this.power_switch = running;
    this.auto_shutoff = shutoff;
    super.update_data();
  }

  @Override
  public final void setBlockNetwork(final LaserNetwork network){
    this.network = network;
  }

  @Override
  @Nullable
  public final LaserNetwork getBlockNetwork(){
    return this.network;
  }

  @Override
  public final void load_block_network_data(){
    network.load_data(energy, power_switch, laser_distance, auto_shutoff);
  }

  /**
   * This is overridden so whatever code calls this function actually updates the whole network.
   */
  @Override
  public final void update_data(){
  }

  @Override
  public final void togglePowerSwitch(){
    network.running = !network.running;
    network.changed = true;
  }

  @Override
  public final void toggle_auto_shutoff(){
    network.auto_shutoff = !network.auto_shutoff;
    network.changed = true;
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    setPlayerAccessed(player);
    return new ContainerLaserHousing(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return getBlockState().getBlock().getName();
  }

}
