package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.core.util.java.ArrayUtil;
import addsynth.core.util.java.StringUtil;
import addsynth.core.util.math.block.BlockArea;
import addsynth.core.util.world.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class BridgeData {

  private final Direction direction;

  /** This is the status of neighboring bridges. */
  public BridgeMessage message = BridgeMessage.PENDING;

  /** Cache neighboring bridges so we can tell them to
   *  update themselves if THIS bridge becomes invalid. */
  public BridgeNetwork network;

  /** Determines which side of the bridge controls the bridge.
   *  This should only be changed when turning on/off the bridge. */
  public BridgeRelation relation = BridgeRelation.NONE;
  // 6th or 7th problem I have with Java: Enums aren't implicitly initialized to the first value? They're reference types?

  public int length;

  public boolean obstructed;

  public boolean legacy;

  /** This is the recorded area of the space between the
   *  two bridges where we spawn the energy bridge blocks. */
  private final BlockArea area = new BlockArea();
  
  /** Axis of rotation for vertical bridges. */
  private Direction.Axis rotation_axis = Direction.Axis.X;

  public BridgeData(final int direction){
    this.direction = Direction.from3DDataValue(direction);
  }
  
  public final void clear(){
    message = BridgeMessage.PENDING;
    network = null;
    obstructed = false;
  }

  public final boolean exists(){
    if(network != null){
      return network.getCount() > 0;
    }
    return false;
  }

  public final BridgeData getOpposite(){
    if(exists()){
      return network.bridge_data[direction.getOpposite().get3DDataValue()];
    }
    return null;
  }

  /** Called by TileEntity.load_block_network_data() to set the data to the BridgeNetwork. */
  public final void set(final BridgeData data){
    area.set(data.area);
    length = data.length;
    relation = data.relation;
    rotation_axis = data.rotation_axis;
  }
  
  public final void handleLegacy(final BlockArea area){ // REMOVE: BridgeData.handleLegacy in 2028 (because I don't know if it even still works.)
    if(legacy){
      this.area.set(area);
      legacy = false;
    }
  }
  
  /* Next update, something like this:
  And rename bridge_data to bridge.
  public final void update(final boolean active){
    if(exists()){
      if(!network.updating){
        update_internal();
        getOpposite().update_internal();
        update_bridge(active);
      }
      return;
    }
    update_internal();
  }
  */
  
  public final void turn_on(final Level world, final BridgeData opposite_data, final BlockArea new_area, final int lens_index){
    if(relation == BridgeRelation.NONE){
      relation = BridgeRelation.MASTER;
      opposite_data.relation = BridgeRelation.SLAVE;
    }
    if(relation == BridgeRelation.MASTER){
      area.set(new_area);  // update area
      for(final BlockPos position : area){
        world.setBlockAndUpdate(position, EnergyBridge.get(lens_index, direction, rotation_axis));
      }
    }
  }
  
  public final void turn_off(final Level world, final BridgeData opposite_data, final BlockArea new_area, final int lens_index){
    if(relation == BridgeRelation.MASTER){
      if(network.active){ // network on the other side
        // TileSuspension Bridge on other side of bridge becomes the Master.
        relation = BridgeRelation.SLAVE;
        opposite_data.relation = BridgeRelation.MASTER;
        // other bridge is already active, I thought its maintain_bridge() function would
        // put the correct bridge in place, but it doesn't check color or orientation.
        // and we can't check for too many things in a single tick, for performance reasons.
        // it would work if we deleted the energy bridge blocks, which is what we did before.
        // must set blocks from the perspective of the other bridge
        opposite_data.turn_on(world, this, new_area, lens_index);
      }
      else{
        relation = BridgeRelation.NONE;
        opposite_data.relation = BridgeRelation.NONE;
        for(final BlockPos position : area){
          if(world.getBlockState(position).getBlock() instanceof EnergyBridge){
            WorldUtil.delete_block(world, position);
          }
        }
      }
    }
  }

  public final void turn_off_immediately(final Level world){
    for(final BlockPos position : area){
      WorldUtil.delete_block(world, position);
    }
    relation = BridgeRelation.NONE;
    if(exists()){
      getOpposite().relation = BridgeRelation.NONE;
    }
  }

  public final void maintain(final Level world, final int lens_index){
    if(relation == BridgeRelation.MASTER){
      if(!exists()){
        turn_off_immediately(world);
        network = null;
      }
      else{
        Block block;
        for(final BlockPos position : area){
          block = world.getBlockState(position).getBlock();
          if(block instanceof EnergyBridge == false){
            world.setBlockAndUpdate(position, EnergyBridge.get(lens_index, direction, rotation_axis));
          }
        }
      }
    }
  }
  
  /** Called by TileEntity to save this data to the NBT tag. */
  public final CompoundTag save(final CompoundTag nbt){
    final CompoundTag tag = new CompoundTag();
    tag.put("Area", area.save());
    tag.putInt("Length", length);
    tag.putInt("Relation", relation.ordinal());
    tag.putInt("Axis", rotation_axis.ordinal());
    nbt.put(direction.getName(), tag);
    return nbt;
  }

  /** Called by TileEntity to load this data from the NBT tag. */
  public final CompoundTag load(final CompoundTag nbt){
    final CompoundTag tag = nbt.getCompound(direction.getName());
    legacy = !tag.contains("Area");
    area.set(BlockArea.load(tag, "Area"));
    length = tag.getInt("Length");
    relation = ArrayUtil.getArrayValue(BridgeRelation.values(), tag.getInt("Relation"), BridgeRelation.NONE);
    rotation_axis = ArrayUtil.getArrayValue(Direction.Axis.VALUES, tag.getInt("Axis"), Direction.Axis.X);
    return nbt;
  }

  @SuppressWarnings("incomplete-switch")
  public final void rotate(){
    switch(direction){
    case WEST: case EAST:
      switch(rotation_axis){
      case X: rotation_axis = Direction.Axis.Y; break;
      case Y: rotation_axis = Direction.Axis.X; break;
      }
      break;
    case NORTH: case SOUTH:
      switch(rotation_axis){
      case Z: rotation_axis = Direction.Axis.Y; break;
      case Y: rotation_axis = Direction.Axis.Z; break;
      }
      break;
    case UP: case DOWN:
      switch(rotation_axis){
      case X: rotation_axis = Direction.Axis.Z; break;
      case Z: rotation_axis = Direction.Axis.X; break;
      }
      break;
    }
  }

  public final Direction.Axis getRotationAxis(){
    return rotation_axis;
  }

  @Override
  public final String toString(){
    final StringBuilder s = new StringBuilder();
    s.append(BridgeData.class.getSimpleName());
    s.append("{Direction: ");
    s.append(StringUtil.Capitalize(direction.toString()));
    s.append(", Exists: ");
    s.append(exists() ? "True" : "False");
    s.append(", Distance: ");
    s.append(length);
    s.append(", Message: ");
    s.append(message);
    s.append(", Relation: ");
    s.append(relation);
    s.append(", Obstructed: ");
    s.append(obstructed ? "True" : "False");
    s.append(", Rotation Axis: ");
    s.append(StringUtil.Capitalize(rotation_axis.toString()));
    s.append('}');
    return s.toString();
  }

}
