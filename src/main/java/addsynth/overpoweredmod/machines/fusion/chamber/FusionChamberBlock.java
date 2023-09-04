package addsynth.overpoweredmod.machines.fusion.chamber;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.lib.blocks.MachineBlock;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.game.reference.TextReference;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public final class FusionChamberBlock extends MachineBlock {

  public FusionChamberBlock(){
    super(MaterialColor.SNOW);
    setRegistryName(Names.FUSION_CHAMBER);
  }

  @Override
  public final void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn){
    tooltip.add(TextReference.fusion_machine);
  }

  @Override
  @Nullable
  public final BlockEntity newBlockEntity(BlockPos position, BlockState blockstate){
    return new TileFusionChamber(position, blockstate);
  }

  @Override
  @SuppressWarnings("deprecation")
  public final InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit){
    if(world.isClientSide == false){
      final TileFusionChamber tile = MinecraftUtility.getTileEntity(pos, world, TileFusionChamber.class);
      if(tile == null){ return InteractionResult.PASS; }
      if(tile.is_on()){ return InteractionResult.PASS; }
      NetworkHooks.openGui((ServerPlayer)player, tile, pos);
    }
    return InteractionResult.SUCCESS;
  }

  @Override
  public final void playerWillDestroy(final Level worldIn, final BlockPos pos, final BlockState state, final Player player){
    super.playerWillDestroy(worldIn, pos, state, player);
    check_container(worldIn, pos);
  }

  @Override
  public final void wasExploded(final Level world, final BlockPos pos, final Explosion explosion){
    check_container(world, pos);
  }

  private static final void check_container(final Level world, final BlockPos position){
    if(world.isClientSide == false){
      final TileFusionChamber tile = MinecraftUtility.getTileEntity(position, world, TileFusionChamber.class);
      if(tile != null){
        if(tile.is_on()){
          tile.explode();
        }
      }
    }
  }

}
