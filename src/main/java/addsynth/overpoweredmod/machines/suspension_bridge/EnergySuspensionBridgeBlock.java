package addsynth.overpoweredmod.machines.suspension_bridge;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.core.util.game.tileentity.TileEntityUtil;
import addsynth.energy.gameplay.reference.EnergyText;
import addsynth.energy.lib.blocks.MachineBlock;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
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

public final class EnergySuspensionBridgeBlock extends MachineBlock {

  public EnergySuspensionBridgeBlock(){
    super(MaterialColor.COLOR_GRAY);
    setRegistryName(Names.ENERGY_SUSPENSION_BRIDGE);
  }

  @Override
  @Nullable
  public final BlockEntity newBlockEntity(BlockPos position, BlockState blockstate){
    return new TileSuspensionBridge(position, blockstate);
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState blockstate, BlockEntityType<T> type){
    return standardTicker(world, type, Tiles.ENERGY_SUSPENSION_BRIDGE.get());
  }

  @Override
  public final void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn){
    tooltip.add(EnergyText.class_3_machine);
  }

  @Override
  public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack){
    TileEntityUtil.setOwner(world, placer, pos);
  }

  @Override
  @SuppressWarnings("deprecation")
  public final InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit){
    if(world.isClientSide == false){
      final TileSuspensionBridge tile = MinecraftUtility.getTileEntity(pos, world, TileSuspensionBridge.class);
      if(tile != null){
        final BridgeNetwork network = tile.getBlockNetwork();
        if(network != null){
          network.check_and_update(world);
          NetworkHooks.openGui((ServerPlayer)player, tile, pos);
        }
        else{
          OverpoweredTechnology.log.error(new NullPointerException("Energy Suspension Bridge at "+pos.toString()+" has no BridgeNetwork!"));
        }
      }
    }
    return InteractionResult.SUCCESS;
  }

  @Override
  public final void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving){
    BlockNetworkUtil.onRemove(super::onRemove, TileSuspensionBridge.class, BridgeNetwork::new, state, world, pos, newState, isMoving);
  }

}
