package addsynth.overpoweredmod.machines.data_cable;

import java.util.ArrayList;
import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.util.block.BlockShape;
import addsynth.energy.lib.blocks.Wire;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class DataCable extends Wire {

  private static final double min_wire_size =  5.5 / 16;
  private static final double max_wire_size = 10.5 / 16;

  private static final ArrayList<Block> valid_blocks = new ArrayList<>(6);

  /** Call this during the constructor of blocks to add that block to the list of blocks the
   *  Data Cable can connect to. This way they can be initialized in any order.
   *  @see addsynth.overpoweredmod.machines.portal.frame.PortalFrameBlock
   *  @see addsynth.overpoweredmod.machines.portal.control_panel.PortalControlPanelBlock
   *  @see addsynth.overpoweredmod.machines.fusion.control.FusionControlUnit
   *  @see addsynth.overpoweredmod.machines.fusion.converter.FusionEnergyConverterBlock
   *  @see addsynth.overpoweredmod.blocks.IronFrameBlock
   */
  public static final void addAttachableBlock(final Block block){
    if(valid_blocks.contains(block) == false){
      valid_blocks.add(block);
    }
  }

  public DataCable(){
    super(Block.Properties.of(Material.VEGETABLE, MaterialColor.WOOL).strength(0.1f, 0.0f));
    setRegistryName(Names.DATA_CABLE);
    valid_blocks.add(this);
  }

  @Override
  protected VoxelShape[] makeShapes(){
    return BlockShape.create_six_sided_binary_voxel_shapes(min_wire_size, max_wire_size);
  }

  // The world may contain multiple BLOCK Objects, but those are internal, and they all reference the
  // SAME Block Type! That's what we're checking with the == operator below, not if they are the same
  // Object, but if the Block Type that block is using is the same one we register in Init class!
  @Override
  protected final boolean[] get_valid_sides(final BlockGetter world, final BlockPos pos){
    final boolean[] valid_sides = new boolean[6];
    Block block;
    for(Direction side : Direction.values()){
      block = world.getBlockState(pos.relative(side)).getBlock();
      valid_sides[side.ordinal()] = valid_blocks.contains(block);
    }
    return valid_sides;
  }

  @Override
  @Nullable
  public final BlockEntity newBlockEntity(BlockPos position, BlockState blockstate){
    return new TileDataCable(position, blockstate);
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState blockstate, BlockEntityType<T> type){
    return standardTicker(world, type, Tiles.DATA_CABLE.get());
  }

  @Override
  @SuppressWarnings("deprecation")
  public final void neighborChanged(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos neighbor, boolean isMoving){
    BlockNetworkUtil.neighbor_changed(world, pos, neighbor);
  }

}
