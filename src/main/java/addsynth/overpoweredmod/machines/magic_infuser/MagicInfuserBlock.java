package addsynth.overpoweredmod.machines.magic_infuser;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.gameplay.reference.EnergyText;
import addsynth.energy.lib.blocks.MachineBlock;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public final class MagicInfuserBlock extends MachineBlock {

  public MagicInfuserBlock(){
    super(MaterialColor.SNOW);
    setRegistryName(Names.MAGIC_INFUSER);
  }

  @Override
  public final void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn){
    tooltip.add(EnergyText.class_3_machine);
  }

  @Override
  @Nullable
  public final BlockEntity newBlockEntity(BlockPos position, BlockState blockstate){
    return new TileMagicInfuser(position, blockstate);
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState blockstate, BlockEntityType<T> type){
    return standardTicker(world, type, Tiles.MAGIC_INFUSER.get());
  }

  @Override
  @SuppressWarnings("deprecation")
  public final InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit){
    if(world.isClientSide == false){
      final TileMagicInfuser tile = MinecraftUtility.getTileEntity(pos, world, TileMagicInfuser.class);
      if(tile != null){
        NetworkHooks.openGui((ServerPlayer)player, tile, pos);
      }
    }
    return InteractionResult.SUCCESS;
  }

}
