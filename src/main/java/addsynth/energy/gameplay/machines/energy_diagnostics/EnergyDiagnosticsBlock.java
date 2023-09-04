package addsynth.energy.gameplay.machines.energy_diagnostics;

import javax.annotation.Nullable;
import addsynth.core.game.blocks.TileEntityBlock;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.gameplay.client.GuiProvider;
import addsynth.energy.gameplay.reference.Names;
import addsynth.energy.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;

public final class EnergyDiagnosticsBlock extends TileEntityBlock {

  public EnergyDiagnosticsBlock(){
    super(Properties.of(Material.METAL, MaterialColor.WOOL).strength(1.5f, 6.0f));
    setRegistryName(Names.ENERGY_DIAGNOSTICS_BLOCK);
  }

  @Override
  @Nullable
  public BlockEntity newBlockEntity(BlockPos position, BlockState blockstate){
    return new TileEnergyDiagnostics(position, blockstate);
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState blockstate, BlockEntityType<T> type){
    return standardTicker(world, type, Tiles.ENERGY_DIAGNOSTICS_BLOCK.get());
  }

  @Deprecated
  @Override
  public InteractionResult use(BlockState blockstate, Level world, BlockPos position, Player player, InteractionHand hand, BlockHitResult hit_result){
    if(world.isClientSide){
      final TileEnergyDiagnostics tile = MinecraftUtility.getTileEntity(position, world, TileEnergyDiagnostics.class);
      if(tile != null){
        GuiProvider.openEnergyDiagnostics(tile, getDescriptionId());
      }
    }
    return InteractionResult.SUCCESS;
  }

}
