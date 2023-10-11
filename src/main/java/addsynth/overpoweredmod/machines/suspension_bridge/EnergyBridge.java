package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.core.util.constants.Constants;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.core.DeviceColor;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class EnergyBridge extends RotatedPillarBlock {

  private static final VoxelShape[] shapes = {
    Shapes.box(7.0 / 16, 0, 0, 9.0 / 16, 1.0, 1.0), // X = flat going forward and back, missing left and right
    Shapes.box(0, 14.0 / 16, 0, 1.0, 1.0, 1.0),
    Shapes.box(0, 0, 7.0 / 16, 1.0, 1.0, 9.0 / 16)  // Z = flat going left and right, missing front and back
  };

  public EnergyBridge(final DeviceColor device_color){
    super(Block.Properties.of(
      new Material(device_color.color, false, true, true, false, false, false, PushReaction.BLOCK)
    ).lightLevel((blockstate)->{return 11;}).strength(-1.0f, Constants.infinite_resistance).noOcclusion().noDrops());
    setRegistryName(device_color.energy_bridge);
  }

  public static final BlockState get(final int index, final Direction direction, final Direction.Axis axis){
    final Block block = switch(index){
      case 0 -> OverpoweredBlocks.white_energy_bridge.get();
      case 1 -> OverpoweredBlocks.red_energy_bridge.get();
      case 2 -> OverpoweredBlocks.orange_energy_bridge.get();
      case 3 -> OverpoweredBlocks.yellow_energy_bridge.get();
      case 4 -> OverpoweredBlocks.green_energy_bridge.get();
      case 5 -> OverpoweredBlocks.cyan_energy_bridge.get();
      case 6 -> OverpoweredBlocks.blue_energy_bridge.get();
      case 7 -> OverpoweredBlocks.magenta_energy_bridge.get();
      default -> null;
    };
    if(block != null){
      final BlockState block_state = block.defaultBlockState();
      if(direction.getAxis() == Direction.Axis.Y){
        block_state.setValue(AXIS, axis);
      }
      return block_state;
    }
    OverpoweredTechnology.log.error("Device color index ("+index+") is out-of-range for the getEnergyBridge() function.", new IllegalArgumentException());
    return Blocks.AIR.defaultBlockState();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
    return shapes[state.getValue(AXIS).ordinal()];
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
    return shapes[state.getValue(AXIS).ordinal()];
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side){
    // Minecraft 1.15 already renders the Energy Suspension Bridge correctly.
    // TODO: Still have problem with vertical bridges however.
    // If I ever need to implement this function, copy the code from the 1.14 version.
    return super.skipRendering(state, adjacentBlockState, side);
  }

}
