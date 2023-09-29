package addsynth.overpoweredmod.machines.suspension_bridge;

import java.util.ArrayList;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.util.constants.DirectionConstant;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.core.util.game.data.AdvancementUtil;
import addsynth.core.util.math.block.BlockMath;
import addsynth.core.util.math.block.DirectionUtil;
import addsynth.core.util.network.NetworkUtil;
import addsynth.core.util.world.WorldUtil;
import addsynth.energy.lib.main.Receiver;
import addsynth.overpoweredmod.assets.CustomAdvancements;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class BridgeNetwork extends BlockNetwork<TileSuspensionBridge> {

  public final Receiver energy = new Receiver(0, 1000);

  private int lens_index = -1;

  private boolean valid_shape;
  private boolean powered;
  /** Whether this Energy Suspension Bridge is active. */
  private boolean active;
  private boolean[] bridge_active = new boolean[6]; // Still doesn't keep Master/Slave relationship between saves because of this. This needs to be saved.

  private boolean data_changed;

  private final BridgeData[] bridge_data = {
    new BridgeData(0), new BridgeData(1), new BridgeData(2),
    new BridgeData(3), new BridgeData(4), new BridgeData(5)
  };

  private int min_x;
  private int min_y;
  private int min_z;
  private int max_x;
  private int max_y;
  private int max_z;

  /** This is the status of THIS bridge. */
  private BridgeMessage bridge_message;
  
  /** Cached maximum bridge length. Must be loaded from TileEntity. */
  private int maximum_length;

  /** Used for the Maximum Bridge Length advancement. */
  private int longest_distance = 0;

  public BridgeNetwork(final Level world, final TileSuspensionBridge tile){
    super(world, tile);
  }

  public final int get_min_x(){ return min_x; }
  public final int get_min_y(){ return min_y; }
  public final int get_min_z(){ return min_z; }
  public final int get_max_x(){ return max_x; }
  public final int get_max_y(){ return max_y; }
  public final int get_max_z(){ return max_z; }

  @Override
  protected final void onUpdateNetworkFinished(final Level world){
    // most likely the shape changed, which invalidates the bridge, so turn off
    // the CURRENT area of blocks first, then reevaluate.
    set_active(world, false); // on world load, the bridge data will have no blocks in their area, so this will do nothing?
    check_and_update(world);
    check_neighbor_bridges(world);
  }

  /** Main check function. Only run when needed, such as when you open the gui,
   *  When it is redstone powered, or when the BlockNetwork changes. */
  public final void check_and_update(final Level world){
    bridge_message = BridgeMessage.PENDING;
    longest_distance = 0;
    check_shape(world);
    check_all_directions(world);
    update_active_state(world);
    if(maximum_length != Config.energy_bridge_max_distance.get()){
      maximum_length = Config.energy_bridge_max_distance.get();
      check_all_directions(world);
    }
    if(bridge_message == BridgeMessage.PENDING){
      if(lens_index == -1){
        bridge_message = BridgeMessage.NO_LENS;
      }
      else{
        if(active){
          bridge_message = BridgeMessage.ACTIVE;
        }
        else{
          bridge_message = BridgeMessage.OFF;
        }
      }
    }
    data_changed = true;
  }

  /** Sets the <code>valid_shape</code> variable.
   *  Also sets all the <code>min</code> and <code>max</code> dimensions. */
  private final void check_shape(final Level world){
    // This is a copy of the BlockMath.is_full_rectangle() function, but
    // keep this as is because we set important variables for this class.
    valid_shape = true;
    final BlockPos[] positions = BlockMath.get_min_max_positions(blocks.getBlockPositions());
    int x;
    int y;
    int z;
    min_x = positions[0].getX();
    min_y = positions[0].getY();
    min_z = positions[0].getZ();
    max_x = positions[1].getX();
    max_y = positions[1].getY();
    max_z = positions[1].getZ();
    final MutableBlockPos position = new MutableBlockPos();
    for(z = min_z; z <= max_z && valid_shape; z++){
      position.setZ(z);
      for(y = min_y; y <= max_y && valid_shape; y++){
        position.setY(y);
        for(x = min_x; x <= max_x && valid_shape; x++){
          position.setX(x);
          if(world.getBlockState(position).getBlock() != OverpoweredBlocks.energy_suspension_bridge.get()){
            valid_shape = false;
            bridge_message = BridgeMessage.INVALID_SHAPE;
          }
        }
      }
    }
  }

  private final void check_all_directions(final Level world){
    if(valid_shape){ // because checking directions requires the dimensions of this bridge, which is determined by check_shape()
      check_down(world);
      check_up(world);
      check_north(world);
      check_south(world);
      check_west(world);
      check_east(world);
    }
  }

  private final void check_direction(final Level world, final int direction){
    if(direction == DirectionConstant.DOWN ){ check_down(world);  return; }
    if(direction == DirectionConstant.UP   ){ check_up(world);    return; }
    if(direction == DirectionConstant.NORTH){ check_north(world); return; }
    if(direction == DirectionConstant.SOUTH){ check_south(world); return; }
    if(direction == DirectionConstant.WEST ){ check_west(world);  return; }
    if(direction == DirectionConstant.EAST ){ check_east(world);  return; }
  }

  private final void finalize(final int direction, final int distance){
    bridge_data[direction].finish(distance);
    if(bridge_data[direction].message == BridgeMessage.OKAY){
      if(distance > longest_distance){
        longest_distance = distance;
      }
    }
  }

  // -Y
  private final void check_down(final Level world){
    final int direction = DirectionConstant.DOWN;
    bridge_data[direction].clear();
    int x;
    int y;
    int z;
    final int start_x = min_x;
    final int end_x   = max_x;
    final int start_y = min_y - 1;
    final int end_y   = Math.max(min_y - 1 - maximum_length, world.getMinBuildHeight());
    final int start_z = min_z;
    final int end_z   = max_z;
    int distance = -1;
    boolean pass = true;
    for(y = start_y; y >= end_y && pass; y--){
      for(z = start_z; z <= end_z && pass; z++){
        for(x = start_x; x <= end_x && pass; x++){
          pass = check_position(world, direction, new BlockPos(x, y, z));
        }
      }
      distance++;
    }
    finalize(direction, distance);
  }
  
  // +Y
  private final void check_up(final Level world){
    final int direction = DirectionConstant.UP;
    bridge_data[direction].clear();
    int x;
    int y;
    int z;
    final int start_x = min_x;
    final int end_x   = max_x;
    final int start_y = max_y + 1;
    final int end_y   = Math.min(max_y + 1 + maximum_length, world.getMaxBuildHeight() - 1);
    final int start_z = min_z;
    final int end_z   = max_z;
    int distance = -1;
    boolean pass = true;
    for(y = start_y; y <= end_y && pass; y++){
      for(z = start_z; z <= end_z && pass; z++){
        for(x = start_x; x <= end_x && pass; x++){
          pass = check_position(world, direction, new BlockPos(x, y, z));
        }
      }
      distance++;
    }
    finalize(direction, distance);
  }
  
  // -Z
  private final void check_north(final Level world){
    final int direction = DirectionConstant.NORTH;
    bridge_data[direction].clear();
    int x;
    int z;
    final int start_x = min_x;
    final int end_x   = max_x;
    final int start_z = min_z - 1;
    final int end_z   = min_z - 1 - maximum_length;
    int distance = -1;
    boolean pass = true;
    for(z = start_z; z >= end_z && pass; z--){
      for(x = start_x; x <= end_x && pass; x++){
        pass = check_position(world, direction, new BlockPos(x, max_y, z));
      }
      distance++;
    }
    finalize(direction, distance);
  }
  
  // +Z
  private final void check_south(final Level world){
    final int direction = DirectionConstant.SOUTH;
    bridge_data[direction].clear();
    int x;
    int z;
    final int start_x = min_x;
    final int end_x   = max_x;
    final int start_z = max_z + 1;
    final int end_z   = max_z + 1 + maximum_length;
    int distance = -1;
    boolean pass = true;
    for(z = start_z; z <= end_z && pass; z++){
      for(x = start_x; x <= end_x && pass; x++){
        pass = check_position(world, direction, new BlockPos(x, max_y, z));
      }
      distance++;
    }
    finalize(direction, distance);
  }
  
  // -X
  private final void check_west(final Level world){
    final int direction = DirectionConstant.WEST;
    bridge_data[direction].clear();
    int x;
    int z;
    final int start_x = min_x - 1;
    final int end_x   = min_x - 1 - maximum_length;
    final int start_z = min_z;
    final int end_z   = max_z;
    int distance = -1;
    boolean pass = true;
    for(x = start_x; x >= end_x && pass; x--){
      for(z = start_z; z <= end_z && pass; z++){
        pass = check_position(world, direction, new BlockPos(x, max_y, z));
      }
      distance++;
    }
    finalize(direction, distance);
  }
  
  // +X
  private final void check_east(final Level world){
    final int direction = DirectionConstant.EAST;
    bridge_data[direction].clear();
    int x;
    int z;
    final int start_x = max_x + 1;
    final int end_x   = max_x + 1 + maximum_length;
    final int start_z = min_z;
    final int end_z   = max_z;
    int distance = -1;
    boolean pass = true;
    for(x = start_x; x <= end_x && pass; x++){
      for(z = start_z; z <= end_z && pass; z++){
        pass = check_position(world, direction, new BlockPos(x, max_y, z));
      }
      distance++;
    }
    finalize(direction, distance);
  }

  private final boolean check_position(final Level world, final int direction, final BlockPos position){
    final BridgeData bridge_data = this.bridge_data[direction];
    final TileSuspensionBridge tile = MinecraftUtility.getTileEntity(position, world, TileSuspensionBridge.class);
    
    // empty space, add to area and return
    if(tile == null){
      final BlockState state = world.getBlockState(position);
      if(state.getMaterial().isReplaceable() || state.getBlock() instanceof EnergyBridge){
        bridge_data.area.add(position);
      }
      else{
        bridge_data.obstructed = true;
      }
      return true;
    }
    
    // found other bridge, assign bridge network
    if(tile.getBlockNetwork() == null){
      BlockNetworkUtil.createBlockNetwork(world, tile, BridgeNetwork::new);
    }
    bridge_data.network = tile.getBlockNetwork();
    
    // check bridge shape
    if(bridge_data.network.check(world, direction, min_x, max_x, min_z, max_z)){
      if(bridge_data.obstructed){
        bridge_data.message = BridgeMessage.OBSTRUCTED;
      }
      else{
        bridge_data.message = BridgeMessage.OKAY;
      }
    }
    else{
      bridge_data.message = BridgeMessage.INVALID_BRIDGE;
    }
    return false;
  }

  /** This is an internal method. Only OTHER Bridge Networks should be calling this. */
  private final boolean check(final Level world, final int direction, final int min_x, final int max_x, final int min_z, final int max_z){
    check_shape(world);
    if(valid_shape){
      final boolean length = this.min_z == min_z && this.max_z == max_z;
      final boolean width  = this.min_x == min_x && this.max_x == max_x;
      if(direction == DirectionConstant.DOWN || direction == DirectionConstant.UP){
        return width && length;
      }
      if(direction == DirectionConstant.WEST || direction == DirectionConstant.EAST){
        return length;
      }
      if(direction == DirectionConstant.NORTH || direction == DirectionConstant.SOUTH){
        return width;
      }
    }
    return false;
  }

  private final void check_neighbor_bridges(final Level world){
    int direction;
    int opposite;
    BridgeNetwork bridge;
    for(direction = 0; direction < 6; direction++){
      bridge = bridge_data[direction].network;
      if(bridge != null){
        opposite = DirectionUtil.getOppositeDirection(direction);
        bridge.check_direction(world, opposite); // updates messages, and bridge area.
        bridge.update_direction(world, opposite);
      }
    }
  }

  /** Called whenever a player inserts or removes a Lens to/from the TileEntity. */
  public final void update_lens(final Level world, final int index){
    if(index != lens_index){
      this.lens_index = index;
      check_and_update(world);
    }
  }

  public final void load_data(final int lens_index, final boolean active, final BridgeData[] bridge_data, final int maximum_length){
    this.lens_index = lens_index;
    // this.active = active;
    this.bridge_data[0].set(bridge_data[0]);
    this.bridge_data[1].set(bridge_data[1]);
    this.bridge_data[2].set(bridge_data[2]);
    this.bridge_data[3].set(bridge_data[3]);
    this.bridge_data[4].set(bridge_data[4]);
    this.bridge_data[5].set(bridge_data[5]);
    this.maximum_length = maximum_length;
  }

  /** This updates all TileEntities in the network whenever something changes that must be propogated to the rest of them. */
  private final void syncBridgeNetworkData(final Level world){
    blocks.remove_invalid();
    blocks.forAllTileEntities((TileSuspensionBridge tile) -> {
      tile.save_block_network_data(lens_index, active, bridge_data, maximum_length);
    });
    // bridge messages do not need to be saved to the world, they only need to be sent to the client.
    final SyncClientBridgeMessage msg = new SyncClientBridgeMessage(blocks.getBlockPositions(), bridge_message, bridge_data);
    NetworkUtil.send_to_clients_in_world(NetworkHandler.INSTANCE, world, msg);
  }

  @Override
  protected final void tick(final Level world){
    final boolean power_check = is_redstone_powered(world);
    if(power_check != powered){
      powered = power_check;
      if(powered){
        check_and_update(world);
      }
      else{
        set_active(world, false);
        if(bridge_message == BridgeMessage.ACTIVE){
          bridge_message = BridgeMessage.OFF;
          data_changed = true;
        }
      }
    }
    if(active){
      maintain_bridge(world); // for every tick the bridge is powered and active, ensure that NOTHING erases the bridge.
    }
    if(data_changed){
      syncBridgeNetworkData(world);
      data_changed = false;
    }
  }

  private final void update_active_state(final Level world){
    final boolean valid = valid_shape && lens_index >= 0 && maximum_length >= Config.energy_bridge_max_distance.get();
    set_active(world, valid && powered);
  }

  public final void set_active(final Level world, final boolean active){
    this.active = active;
    update_direction(world, 0);
    update_direction(world, 1);
    update_direction(world, 2);
    update_direction(world, 3);
    update_direction(world, 4);
    update_direction(world, 5);
    if(active){
      if(longest_distance >= Config.energy_bridge_max_distance.get()){
        award_players(world);
      }
    }
  }

  private final void award_players(final Level world){
    final ArrayList<String> players = new ArrayList<>();
    String player;
    for(BlockEntity tile : blocks.getTileEntities()){
      player = ((TileSuspensionBridge)tile).getOwner();
      if(players.contains(player) == false){
        players.add(player);
      }
    }
    for(String player_name : players){
      AdvancementUtil.grantAdvancement(player_name, world, CustomAdvancements.MAXIMUM_BRIDGE_LENGTH);
    }
  }

  /** This is the function that actually turns on/off the bridge depending on the active state. */
  private final void update_direction(final Level world, final int direction){
    final BridgeData bridge_data = this.bridge_data[direction];
    if(bridge_data.message == BridgeMessage.OKAY){
      // a message of OKAY means we already know WE'RE valid, and valid in that direction,
      // so we're free to manipulate blocks in that area.
      final int opposite = DirectionUtil.getOppositeDirection(direction);
      final BridgeNetwork other_bridge_network = bridge_data.network;
      final BridgeData opposite_bridge_data = other_bridge_network.bridge_data[opposite];
      if(active){
        if(bridge_data.relation == BridgeRelation.NONE){
          bridge_data.relation = BridgeRelation.MASTER;
          opposite_bridge_data.relation = BridgeRelation.SLAVE;
        }
        if(bridge_data.relation == BridgeRelation.MASTER){
          bridge_active[direction] = true;
          for(final BlockPos position : bridge_data.area){
            set_energy_block(world, direction, position);
          }
        }
      }
      else{
        // If we turn off
        bridge_active[direction] = false;
        if(other_bridge_network.active){
          // TileSuspension Bridge on other side of bridge becomes the Master.
          other_bridge_network.bridge_active[opposite] = true;
          bridge_data.relation = BridgeRelation.SLAVE;
          opposite_bridge_data.relation = BridgeRelation.MASTER;
          // other bridge is already active, I thought its maintain_bridge() function would
          // put the correct bridge in place, but it doesn't check color or orientation.
          // and we can't check for too many things in a single tick, for performance reasons.
          // it would work if we deleted the energy bridge blocks, which is what we did before.
          // must set blocks from the perspective of the other bridge
          for(final BlockPos position : opposite_bridge_data.area){
            other_bridge_network.set_energy_block(world, opposite, position);
          }
        }
        else{
          bridge_data.relation = BridgeRelation.NONE;
          opposite_bridge_data.relation = BridgeRelation.NONE;
          for(final BlockPos position : bridge_data.area){
            if(world.getBlockState(position).getBlock() instanceof EnergyBridge){
              WorldUtil.delete_block(world, position);
            }
          }
        }
      }
      data_changed = true;
      other_bridge_network.data_changed = true;
    }
    else{
      if(bridge_active[direction]){ // if we suddenly become invalid, but the bridge is on.
        for(final BlockPos position : bridge_data.area){
          WorldUtil.delete_block(world, position);
        }
        bridge_active[direction] = false;
      }
    }
  }

  private final void set_energy_block(final Level world, final int direction, final BlockPos position){
    final boolean vertical = direction == DirectionConstant.DOWN || direction == DirectionConstant.UP;
    final Direction.Axis axis = vertical ? bridge_data[direction].getRotationAxis() : null;
    world.setBlockAndUpdate(position, EnergyBridge.get(lens_index, axis));
  }

  private final void maintain_bridge(final Level world){
    int direction;
    Block block;
    for(direction = 0; direction < 6; direction++){
      if(bridge_data[direction].relation == BridgeRelation.MASTER){
        for(final BlockPos position : bridge_data[direction].area){
          block = world.getBlockState(position).getBlock();
          if(block instanceof EnergyBridge == false){
            set_energy_block(world, direction, position);
          }
        }
      }
    }
  }

  public final void rotate(final Level world){
    // still only rotates the top and bottom bridges for now
    rotate(world, DirectionConstant.DOWN);
    rotate(world, DirectionConstant.UP);
    data_changed = true;
  }

  private final void rotate(final Level world, final int direction){
    // only rotate bridgees if we're active
    if(bridge_data[direction].relation == BridgeRelation.MASTER){
      bridge_data[direction].rotate();
      update_direction(world, direction);
      return;
    }
    if(bridge_data[direction].relation == BridgeRelation.SLAVE){
      final int opposite = DirectionUtil.getOppositeDirection(direction);
      bridge_data[direction].network.rotate(world, opposite);
    }
  }

  // FIX: Bridge doesn't disappear when a neighbor bridge of a single block
  // wide is destroyed. We need to check if neighbor bridges still exist.
  // Each bridge needs to have their own active and valid boolean states.
  // This needs to wait until after the BlockNetwork rewrite.

  @Override
  protected final void clear_custom_data(){
  }

}
