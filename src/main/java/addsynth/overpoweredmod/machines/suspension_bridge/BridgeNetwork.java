package addsynth.overpoweredmod.machines.suspension_bridge;

import java.util.ArrayList;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.util.constants.DirectionConstant;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.core.util.game.data.AdvancementUtil;
import addsynth.core.util.game.redstone.RedstoneDetector;
import addsynth.core.util.math.block.BlockArea;
import addsynth.core.util.math.block.DirectionUtil;
import addsynth.core.util.network.NetworkUtil;
import addsynth.energy.lib.main.Receiver;
import addsynth.overpoweredmod.assets.CustomAdvancements;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.game.NetworkHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class BridgeNetwork extends BlockNetwork<TileSuspensionBridge> {

  public final Receiver energy = new Receiver(0, 1000);

  private int lens_index = -1;

  private final RedstoneDetector redstone = new RedstoneDetector();
  /** Whether this Energy Suspension Bridge is active. */
  boolean active;

  private boolean data_changed;

  final BridgeData[] bridge_data = {
    new BridgeData(0), new BridgeData(1), new BridgeData(2),
    new BridgeData(3), new BridgeData(4), new BridgeData(5)
  };

  private BlockArea shape;
  private boolean valid_shape;
  
  private final BlockArea[] check_area = {
    new BlockArea(), new BlockArea(), new BlockArea(),
    new BlockArea(), new BlockArea(), new BlockArea()
  };

  /** This is the status of THIS bridge. */
  private BridgeMessage bridge_message;
  
  private int maximum_length;

  /** Used for the Maximum Bridge Length advancement. */
  private int longest_distance = 0;

  /** This prevents setting opposite bridge data if that bridge is already
   *  in the middle of updating. */
  private boolean updating;

  public BridgeNetwork(final Level world, final TileSuspensionBridge tile){
    super(world, tile);
  }

  public final int get_min_x(){ return shape.min_x; }
  public final int get_min_y(){ return shape.min_y; }
  public final int get_min_z(){ return shape.min_z; }
  public final int get_max_x(){ return shape.max_x; }
  public final int get_max_y(){ return shape.max_y; }
  public final int get_max_z(){ return shape.max_z; }

  @Override
  protected final void onUpdateNetworkFinished(final Level world){
    check_and_update(world);
    check_neighbor_bridges(world);
  }

  /** Main check function. Only runs when needed, such as when you open the gui,
   *  When it is redstone powered, changed lenses, or when the BlockNetwork changes. */
  public final void check_and_update(final Level world){
    updating = true;
    bridge_message = BridgeMessage.PENDING;
    longest_distance = 0;
    
    check_shape(world); // Checks the Bridge Machine itself.

    // Only changes the check area. Doesn't set the area until we activate a bridge.
    maximum_length = Config.energy_bridge_max_distance.get();
    check_down(world);
    check_up(world);
    check_north(world);
    check_south(world);
    check_west(world);
    check_east(world);
    set_active(world, valid_shape && lens_index >= 0 && redstone.isPowered());
    
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
    updating = false;
  }

  /** Sets the {@link shape} and {@link valid_shape} fields. */
  private final void check_shape(final Level world){
    final ArrayList<BlockPos> positions = blocks.getBlockPositions();
    shape = BlockArea.get(positions);
    valid_shape = shape.isFullRectangle(positions);
    if(valid_shape == false){
      bridge_message = BridgeMessage.INVALID_SHAPE;
    }
  }

  // We need a single-directional update and a bi-directional update!
  // The reason we update from both directions is because both networks might have
  // valid shapes, but different shapes, so they think the other is incompatible.
  // TODO: we need a single function that updates both directions, but only if
  // the other network exists, and the other network isn't currently udpating!
  // Move the single check_direction functions into the BridgeData.

  private final void finalize_direction(final int direction, final int distance){
    if(bridge_data[direction].message == BridgeMessage.PENDING){
      bridge_data[direction].message = BridgeMessage.NO_BRIDGE;
      return;
    }
    bridge_data[direction].length = distance;
    if(bridge_data[direction].message == BridgeMessage.OKAY){
      if(distance > longest_distance){
        longest_distance = distance;
      }
    }
  }

  // -Y
  private final void check_down(final Level world){
    final int direction = DirectionConstant.DOWN;
    final BridgeData bridge_data = this.bridge_data[direction];
    bridge_data.clear();
    final BlockArea area = check_area[direction];
    // We want to check 1 more space for TileSuspensionBridges, and the space in between will equal the distance.
    area.setBelow(shape, maximum_length + 1);
    area.setWithinWorldBoundary(world);
    final BlockArea.BlockAreaIterator loop = area.getDirectionalIterator(Direction.DOWN);
    BlockPos position;
    boolean pass;
    do{
      position = loop.next();
      pass = check_position(world, bridge_data, direction, position);
    }
    while(loop.hasNext() && pass);
    area.min_y = position.getY() + 1; // Lower area by 1 space to return to original length
    finalize_direction(direction, pass ? 0 : area.getHeight());
  }
  
  // +Y
  private final void check_up(final Level world){
    final int direction = DirectionConstant.UP;
    final BridgeData bridge_data = this.bridge_data[direction];
    bridge_data.clear();
    final BlockArea area = check_area[direction];
    area.setAbove(shape, maximum_length + 1);
    area.setWithinWorldBoundary(world);
    final BlockArea.BlockAreaIterator loop = area.getDirectionalIterator(Direction.UP);
    BlockPos position;
    boolean pass;
    do{
      position = loop.next();
      pass = check_position(world, bridge_data, direction, position);
    }
    while(loop.hasNext() && pass);
    area.max_y = position.getY() - 1;
    finalize_direction(direction, pass ? 0 : area.getHeight());
  }
  
  // -Z
  private final void check_north(final Level world){
    final int direction = DirectionConstant.NORTH;
    final BridgeData bridge_data = this.bridge_data[direction];
    bridge_data.clear();
    final BlockArea area = check_area[direction];
    area.setNorth(shape, maximum_length + 1);
    final BlockArea.BlockAreaIterator loop = area.getDirectionalIterator(Direction.NORTH);
    BlockPos position;
    boolean pass;
    do{
      position = loop.next();
      pass = check_position(world, bridge_data, direction, position);
    }
    while(loop.hasNext() && pass);
    area.min_z = position.getZ() + 1;
    finalize_direction(direction, pass ? 0 : area.getLength());
  }
  
  // +Z
  private final void check_south(final Level world){
    final int direction = DirectionConstant.SOUTH;
    final BridgeData bridge_data = this.bridge_data[direction];
    bridge_data.clear();
    final BlockArea area = check_area[direction];
    area.setSouth(shape, maximum_length + 1);
    final BlockArea.BlockAreaIterator loop = area.getDirectionalIterator(Direction.SOUTH);
    BlockPos position;
    boolean pass;
    do{
      position = loop.next();
      pass = check_position(world, bridge_data, direction, position);
    }
    while(loop.hasNext() && pass);
    area.max_z = position.getZ() - 1;
    finalize_direction(direction, pass ? 0 : area.getLength());
  }
  
  // -X
  private final void check_west(final Level world){
    final int direction = DirectionConstant.WEST;
    final BridgeData bridge_data = this.bridge_data[direction];
    bridge_data.clear();
    final BlockArea area = check_area[direction];
    area.setWest(shape, maximum_length + 1);
    final BlockArea.BlockAreaIterator loop = area.getDirectionalIterator(Direction.WEST);
    BlockPos position;
    boolean pass;
    do{
      position = loop.next();
      pass = check_position(world, bridge_data, direction, position);
    }
    while(loop.hasNext() && pass);
    area.min_x = position.getX() + 1;
    finalize_direction(direction, pass ? 0 : area.getWidth());
  }
  
  // +X
  private final void check_east(final Level world){
    final int direction = DirectionConstant.EAST;
    final BridgeData bridge_data = this.bridge_data[direction];
    bridge_data.clear();
    final BlockArea area = check_area[direction];
    area.setEast(shape, maximum_length + 1);
    final BlockArea.BlockAreaIterator loop = area.getDirectionalIterator(Direction.EAST);
    BlockPos position;
    boolean pass;
    do{
      position = loop.next();
      pass = check_position(world, bridge_data, direction, position);
    }
    while(loop.hasNext() && pass);
    area.max_x = position.getX() - 1;
    finalize_direction(direction, pass ? 0 : area.getWidth());
  }

  private final boolean check_position(final Level world, final BridgeData bridge_data, final int direction, final BlockPos position){
    final TileSuspensionBridge tile = MinecraftUtility.getTileEntity(position, world, TileSuspensionBridge.class);
    
    // empty space, check if obstructed and return
    if(tile == null){
      if(!bridge_data.obstructed){
        final BlockState state = world.getBlockState(position);
        bridge_data.obstructed = !(state.getMaterial().isReplaceable() || state.getBlock() instanceof EnergyBridge);
      }
      return true;
    }
    
    // found other bridge, assign bridge network
    if(tile.getBlockNetwork() == null){
      BlockNetworkUtil.createBlockNetwork(world, tile, BridgeNetwork::new);
    }
    bridge_data.network = tile.getBlockNetwork();
    
    setBridgeMessage(world, direction, bridge_data);
    
    return false;
  }

  private final void setBridgeMessage(final Level world, final int direction, final BridgeData bridge_data){
    // check bridge shape
    if(bridge_data.network.check(world, direction, shape)){
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
  }

  /** This is an internal method. Only OTHER Bridge Networks should be calling this. */
  private final boolean check(final Level world, final int direction, final BlockArea shape){
    // Other BridgeNetwork should've already been created, and Updated by now.
    // check_shape(world);
    if(valid_shape){
      final boolean length = this.shape.sameLength(shape);
      final boolean width  = this.shape.sameWidth(shape);
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
    BridgeNetwork network;
    for(direction = 0; direction < 6; direction++){
      network = bridge_data[direction].network;
      if(network != null){
        if(!network.updating){
          opposite = DirectionUtil.getOppositeDirection(direction);
          switch(opposite){
          case DirectionConstant.DOWN:  network.check_down(world); break;
          case DirectionConstant.UP:    network.check_up(world); break;
          case DirectionConstant.WEST:  network.check_west(world); break;
          case DirectionConstant.EAST:  network.check_east(world); break;
          case DirectionConstant.NORTH: network.check_north(world); break;
          case DirectionConstant.SOUTH: network.check_south(world); break;
          }
          network.update_direction(world, opposite);
        }
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

  public final void load_data(final int lens_index, final RedstoneDetector redstone, final BridgeData[] bridge_data){
    this.lens_index = lens_index;
    this.redstone.setFrom(redstone);
    this.bridge_data[0].set(bridge_data[0]);
    this.bridge_data[1].set(bridge_data[1]);
    this.bridge_data[2].set(bridge_data[2]);
    this.bridge_data[3].set(bridge_data[3]);
    this.bridge_data[4].set(bridge_data[4]);
    this.bridge_data[5].set(bridge_data[5]);
  }

  /** This updates all TileEntities in the network whenever something changes that must be propogated to the rest of them. */
  private final void syncBridgeNetworkData(final Level world){
    blocks.remove_invalid();
    blocks.forAllTileEntities((TileSuspensionBridge tile) -> {
      tile.save_block_network_data(lens_index, redstone, bridge_data);
    });
    // bridge messages do not need to be saved to the world, they only need to be sent to the client.
    final SyncClientBridgeMessage msg = new SyncClientBridgeMessage(blocks.getBlockPositions(), bridge_message, bridge_data);
    NetworkUtil.send_to_clients_in_world(NetworkHandler.INSTANCE, world, msg);
  }

  @Override
  protected final void tick(final Level world){
    redstone.update(world, blocks.getBlockPositions());
    if(redstone.changed()){
      if(redstone.isPowered()){
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

  private final void set_active(final Level world, final boolean active){
    this.active = active;
    update_direction(world, 0); // down
    update_direction(world, 1); // up
    update_direction(world, 2); // north
    update_direction(world, 3); // south
    update_direction(world, 4); // west
    update_direction(world, 5); // east
    if(active){
      if(longest_distance >= maximum_length){
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
    final BridgeData bridge = this.bridge_data[direction];
    bridge.handleLegacy(check_area[direction]);
    if(bridge.message == BridgeMessage.OKAY){
      // a message of OKAY means we already know WE'RE valid, and valid in that direction,
      // so we're free to manipulate blocks in that area.
      final BridgeNetwork network = bridge.network;
      final BridgeData opposite_data = bridge.getOpposite();
      if(active){
        bridge.turn_on(world, opposite_data, check_area[direction], lens_index);
      }
      else{
        bridge.turn_off(world, opposite_data, check_area[direction], network.lens_index);
      }
      data_changed = true;
      network.data_changed = true;
    }
    else{
      if(bridge.relation == BridgeRelation.MASTER){
        // if we suddenly become invalid, but the bridge is on.
        bridge.turn_off_immediately(world);
      }
    }
  }

  private final void maintain_bridge(final Level world){
    int direction;
    for(direction = 0; direction < 6; direction++){
      bridge_data[direction].maintain(world, lens_index);
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

  @Override
  protected final void clear_custom_data(){
  }

  @Override
  protected final void lastTileWasRemoved(final Level world, final TileSuspensionBridge removed_tile){
    int direction;
    for(direction = 0; direction < 6; direction++){
      if(bridge_data[direction].relation == BridgeRelation.MASTER){
        bridge_data[direction].turn_off_immediately(world);
      }
    }
  }

}
