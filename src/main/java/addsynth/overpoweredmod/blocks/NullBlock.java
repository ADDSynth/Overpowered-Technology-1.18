package addsynth.overpoweredmod.blocks;

import addsynth.overpoweredmod.game.reference.Names;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class NullBlock extends Block {

  public NullBlock(){
    super(Block.Properties.of(Material.AIR).noCollission());
    setRegistryName(Names.NULL_BLOCK);
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
     return Shapes.empty();
  }

  // The Null Block is also translucent, so logically it should choose NOT to
  // render sides adjacent to other Null Blocks, but I kind of like this aesthetic.
  // See Portal Energy Block, Energy Storage Container, Laser Beam, and Energy Bridge
  // blocks for the implementation.
  @Override
  @SuppressWarnings("deprecation")
  public final boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side){
    return super.skipRendering(state, adjacentBlockState, side);
  }

}
