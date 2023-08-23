package addsynth.overpoweredmod.machines.suspension_bridge;

import java.util.ArrayList;
import addsynth.core.util.java.ArrayUtil;
import addsynth.overpoweredmod.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

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

  /** Actual length of the energy bridge. Saved and loaded from TileEntities. */
  public int length;

  // Should probably save area dimensions?
  // OPTIMIZE: absolutely do this next update. Saving a few integers is a lot less memory than saving a list of BlockPositions. And easier to modify the bridge between saves.
  // private BlockPos minimum_position;
  // private BlockPos maximum_position;

  public boolean obstructed;

  /** This is the recorded area of the space between the
   *  two bridges where we spawn the energy bridge blocks. */
  public final ArrayList<BlockPos> area = new ArrayList<BlockPos>(Config.energy_bridge_max_distance.get() * 2);
  
  /** Axis of rotation for vertical bridges. */
  private Direction.Axis rotation_axis = Direction.Axis.X;

  public BridgeData(final int direction){
    this.direction = Direction.from3DDataValue(direction);
  }
  
  public final void clear(){
    message = BridgeMessage.PENDING;
    network = null;
    // relation = BridgeRelation.NONE;
    obstructed = false;
    area.clear();
  }
  
  public final void set(final BridgeData data){
    length = data.length;
    relation = data.relation;
    rotation_axis = data.rotation_axis;
  }
  
  public final void finish(final int distance){
    if(message == BridgeMessage.PENDING){
      length = 0;
      message = BridgeMessage.NO_BRIDGE;
      return;
    }
    length = distance;
  }
  
  public final CompoundTag save(final CompoundTag nbt){
    final CompoundTag tag = new CompoundTag();
    tag.putInt("Length", length);
    tag.putInt("Relation", relation.ordinal());
    tag.putInt("Axis", rotation_axis.ordinal());
    nbt.put(direction.getName(), tag);
    return nbt;
  }

  public final CompoundTag load(final CompoundTag nbt){
    final CompoundTag tag = nbt.getCompound(direction.getName());
    length = tag.getInt("Length");
    relation = ArrayUtil.getArrayValue(BridgeRelation.values(), tag.getInt("Relation"), BridgeRelation.NONE);
    rotation_axis = ArrayUtil.getArrayValue(Direction.Axis.VALUES, tag.getInt("Axis"), Direction.Axis.X);
    return nbt;
  }

  public final boolean is_valid(){
    return network != null && message == BridgeMessage.OKAY;
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

}
