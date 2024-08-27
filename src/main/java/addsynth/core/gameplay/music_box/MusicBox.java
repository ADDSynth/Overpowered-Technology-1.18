package addsynth.core.gameplay.music_box;

import javax.annotation.Nullable;
import addsynth.core.game.blocks.TileEntityBlock;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.client.GuiProvider;
import addsynth.core.gameplay.reference.Names;
import addsynth.core.gameplay.registers.Tiles;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.core.util.player.PlayerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;

public final class MusicBox extends TileEntityBlock {

  public MusicBox(){
    super(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(0.8f));
    setRegistryName(Names.MUSIC_BOX);
  }

  @Override
  @SuppressWarnings("deprecation")
  public final int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction side){
    return 0;
  }

  @Override
  @Nullable
  public final BlockEntity newBlockEntity(BlockPos position, BlockState blockstate){
    return new TileMusicBox(position, blockstate);
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState blockstate, BlockEntityType<T> type){
    return standardTicker(world, type, Tiles.MUSIC_BOX.get());
  }

  @Override
  @SuppressWarnings({ "deprecation" })
  public final InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit){
    if(PlayerUtil.isPlayerHoldingItem(player, Core.music_sheet.get())){
      return InteractionResult.PASS; // let the music sheet item handle it.
    }
    if(world.isClientSide){
      final TileMusicBox tile = MinecraftUtility.getTileEntity(pos, world, TileMusicBox.class);
      if(tile != null){
        GuiProvider.openMusicBoxGui(tile, getName());
      }
    }
    return InteractionResult.SUCCESS;
  }

}
