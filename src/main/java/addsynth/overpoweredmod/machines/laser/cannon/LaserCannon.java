package addsynth.overpoweredmod.machines.laser.cannon;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.game.RegistryUtil;
import addsynth.core.util.block.BlockShape;
import addsynth.core.util.constants.DirectionConstant;
import addsynth.core.util.world.WorldUtil;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.core.Laser;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.game.reference.TextReference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class LaserCannon extends Block implements SimpleWaterloggedBlock {

  public static final DirectionProperty FACING = BlockStateProperties.FACING; // Data Cable uses this block property.
  private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
  
  public final int color;

  private static final VoxelShape[] shapes = create_laser_cannon_shapes();

  private static final VoxelShape[] create_laser_cannon_shapes(){
    final VoxelShape[] shape = new VoxelShape[6];
    final double[] calc = { 0.0,    1.0/16,  2.0/16,  3.0/16,  4.0/16,  5.0/16,  6.0/16,  7.0/16,
                            8.0/16, 9.0/16, 10.0/16, 11.0/16, 12.0/16, 13.0/16, 14.0/16, 15.0/16, 1.0};
    // NORTH
    VoxelShape box0 = Shapes.box(calc[1], calc[1], calc[14], calc[15], calc[15], calc[16]);
    VoxelShape box1 = Shapes.box(calc[3], calc[3], calc[12], calc[13], calc[13], calc[14]);
    VoxelShape box2 = Shapes.box(calc[4], calc[4], calc[10], calc[12], calc[12], calc[12]);
    VoxelShape box3 = Shapes.box(calc[5], calc[5], calc[8], calc[11], calc[11], calc[10]);
    VoxelShape box4 = Shapes.box(calc[6], calc[6], calc[6], calc[10], calc[10], calc[8]);
    VoxelShape box5 = Shapes.box(calc[7], calc[7], calc[2], calc[9], calc[9], calc[6]);
    shape[DirectionConstant.NORTH] = BlockShape.combine(box0, box1, box2, box3, box4, box5);
    
    // SOUTH
    box0 = Shapes.box(calc[1], calc[1], calc[0], calc[15], calc[15], calc[2]);
    box1 = Shapes.box(calc[3], calc[3], calc[2], calc[13], calc[13], calc[4]);
    box2 = Shapes.box(calc[4], calc[4], calc[4], calc[12], calc[12], calc[6]);
    box3 = Shapes.box(calc[5], calc[5], calc[6], calc[11], calc[11], calc[8]);
    box4 = Shapes.box(calc[6], calc[6], calc[8], calc[10], calc[10], calc[10]);
    box5 = Shapes.box(calc[7], calc[7], calc[10], calc[9], calc[9], calc[14]);
    shape[DirectionConstant.SOUTH] = BlockShape.combine(box0, box1, box2, box3, box4, box5);
    
    // EAST
    box0 = Shapes.box(calc[0], calc[1], calc[1], calc[2], calc[15], calc[15]);
    box1 = Shapes.box(calc[2], calc[3], calc[3], calc[4], calc[13], calc[13]);
    box2 = Shapes.box(calc[4], calc[4], calc[4], calc[6], calc[12], calc[12]);
    box3 = Shapes.box(calc[6], calc[5], calc[5], calc[8], calc[11], calc[11]);
    box4 = Shapes.box(calc[8], calc[6], calc[6], calc[10], calc[10], calc[10]);
    box5 = Shapes.box(calc[10], calc[7], calc[7], calc[14], calc[9], calc[9]);
    shape[DirectionConstant.EAST] = BlockShape.combine(box0, box1, box2, box3, box4, box5);
    
    // WEST
    box0 = Shapes.box(calc[14], calc[1], calc[1], calc[16], calc[15], calc[15]);
    box1 = Shapes.box(calc[12], calc[3], calc[3], calc[14], calc[13], calc[13]);
    box2 = Shapes.box(calc[10], calc[4], calc[4], calc[12], calc[12], calc[12]);
    box3 = Shapes.box(calc[8], calc[5], calc[5], calc[10], calc[11], calc[11]);
    box4 = Shapes.box(calc[6], calc[6], calc[6], calc[8], calc[10], calc[10]);
    box5 = Shapes.box(calc[2], calc[7], calc[7], calc[6], calc[9], calc[9]);
    shape[DirectionConstant.WEST] = BlockShape.combine(box0, box1, box2, box3, box4, box5);
    
    // UP
    box0 = Shapes.box(calc[1], calc[0], calc[1], calc[15], calc[2], calc[15]);
    box1 = Shapes.box(calc[3], calc[2], calc[3], calc[13], calc[4], calc[13]);
    box2 = Shapes.box(calc[4], calc[4], calc[4], calc[12], calc[6], calc[12]);
    box3 = Shapes.box(calc[5], calc[6], calc[5], calc[11], calc[8], calc[11]);
    box4 = Shapes.box(calc[6], calc[8], calc[6], calc[10], calc[10], calc[10]);
    box5 = Shapes.box(calc[7], calc[10], calc[7], calc[9], calc[14], calc[9]);
    shape[DirectionConstant.UP] = BlockShape.combine(box0, box1, box2, box3, box4, box5);
    
    // DOWN
    box0 = Shapes.box(calc[1], calc[14], calc[1], calc[15], calc[16], calc[15]);
    box1 = Shapes.box(calc[3], calc[12], calc[3], calc[13], calc[14], calc[13]);
    box2 = Shapes.box(calc[4], calc[10], calc[4], calc[12], calc[12], calc[12]);
    box3 = Shapes.box(calc[5], calc[8], calc[5], calc[11], calc[10], calc[11]);
    box4 = Shapes.box(calc[6], calc[6], calc[6], calc[10], calc[8], calc[10]);
    box5 = Shapes.box(calc[7], calc[2], calc[7], calc[9], calc[6], calc[9]);
    shape[DirectionConstant.DOWN] = BlockShape.combine(box0, box1, box2, box3, box4, box5);

    return shape;
  }

  public LaserCannon(final ResourceLocation name, final int color){
    super(Block.Properties.of(Material.METAL, color >= 0 ? MaterialColor.STONE : MaterialColor.COLOR_GRAY)
      .sound(SoundType.METAL).strength(3.5f, 6.0f).dynamicShape());
    RegistryUtil.register_block(this, name, CreativeTabs.creative_tab);
    this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    this.color = color;
  }

  @Override
  public final void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn){
    tooltip.add(color == -1 ? TextReference.fusion_machine : TextReference.laser_machine);
  }

  @Override
  @Nullable
  @SuppressWarnings("resource")
  public BlockState getStateForPlacement(BlockPlaceContext context){
    final Level world = context.getLevel();
    final BlockPos position  = context.getClickedPos();
    // TODO: replace with the following:   for LaserCannon, TrophyBlock, and Wire
    // final FluidState fluidstate = context.getLevel().getFluidState(position);
    return this.defaultBlockState()
      .setValue(FACING, context.getClickedFace())
      .setValue(WATERLOGGED, world.getFluidState(position).getType() == Fluids.WATER);
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos){
    final Block block = world.getBlockState(pos.relative(state.getValue(FACING).getOpposite())).getBlock();
    if(color == -1){
      return block == OverpoweredBlocks.fusion_control_unit;
    }
    return block == OverpoweredBlocks.laser_housing;
  }

  @Override
  @SuppressWarnings("deprecation")
  public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
    return shapes[state.getValue(FACING).ordinal()];
  }

  @Override
  @SuppressWarnings("deprecation")
  public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
    return shapes[state.getValue(FACING).ordinal()];
  }

  @Override
  @SuppressWarnings("deprecation")
  public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos){
    if(state.getValue(WATERLOGGED)){
      world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
    }
    return state;
  }

  @Override
  public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos){
    return !state.getValue(WATERLOGGED);
  }

  @Override
  @SuppressWarnings("deprecation")
  public FluidState getFluidState(BlockState state){
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
    builder.add(FACING, WATERLOGGED);
  }

  @Override
  @SuppressWarnings("deprecation")
  public final void neighborChanged(BlockState state, Level world, BlockPos position, Block block, BlockPos neighbor, boolean isMoving){
    if(world.isClientSide == false){
      if(canSurvive(state, world, position) == false){
        final ItemStack stack = color >= 0 ? new ItemStack(Laser.index[color].cannon, 1) : new ItemStack(OverpoweredBlocks.fusion_control_laser, 1);
        WorldUtil.spawnItemStack(world, position, stack);
        world.removeBlock(position, isMoving);
      }
    }
  }

}
