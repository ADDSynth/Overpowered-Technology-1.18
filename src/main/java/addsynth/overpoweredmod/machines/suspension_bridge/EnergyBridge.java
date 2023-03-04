package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.core.util.constants.Constants;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.core.Lens;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
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

  public EnergyBridge(final String name, final Lens lens){
    super(Block.Properties.of(
      new Material(lens.color, false, true, true, false, false, false, PushReaction.BLOCK)
    ).lightLevel((blockstate)->{return 11;}).strength(-1.0f, Constants.infinite_resistance).noOcclusion().noDrops());
    setRegistryName(new ResourceLocation(OverpoweredTechnology.MOD_ID, name));
  }

  public final BlockState getRotated(final Direction.Axis axis){
    return defaultBlockState().setValue(AXIS, axis);
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
