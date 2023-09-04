package addsynth.overpoweredmod.machines.portal.rift;

import javax.annotation.Nullable;
import addsynth.core.game.blocks.TileEntityBlock;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class PortalEnergyBlock extends TileEntityBlock {

  // NOTE: well, after seeing a YouTube video, I was going to make this extend from the Vanilla PortalBlock class,
  //       but I want this to have a TileEntity, then just implement ITileProvider?

  public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

  public PortalEnergyBlock(){
    super(Block.Properties.of(Material.PORTAL).noCollission().noDrops());
    setRegistryName(Names.PORTAL_RIFT);
    // Portal Energy Block needs an ItemBlock form to use as an icon for the Achievement.
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
    return Shapes.empty();
  }

  @Override
  public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player){
    return ItemStack.EMPTY;
  }

  @Override
  @SuppressWarnings("deprecation")
  public final void entityInside(BlockState state, Level world, BlockPos pos, Entity entity){
    if(world.isClientSide == false){
      /*
      if(entity.isNonBoss()){
        if(entity instanceof ServerPlayerEntity){
          final MinecraftServer server = entity.getServer();
          if(server != null){
            server.getPlayerList().transferPlayerToDimension((ServerPlayerEntity)entity, WeirdDimension.id, new CustomTeleporter(server.getWorld(WeirdDimension.id)));
          }
        }
        else{
          entity.changeDimension(WeirdDimension.id);
        }
      }
      */
    }
  }

  @Override
  @Nullable
  public final BlockEntity newBlockEntity(BlockPos position, BlockState blockstate){
    return new TilePortal(position, blockstate);
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState blockstate, BlockEntityType<T> type){
    return standardTicker(world, type, Tiles.PORTAL_RIFT.get());
  }

  // Portal Energy block doesn't need it for some reason?
  @Override
  @SuppressWarnings("deprecation")
  public final boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side){
    return adjacentBlockState.getBlock() == this ? true : super.skipRendering(state, adjacentBlockState, side);
  }

}
