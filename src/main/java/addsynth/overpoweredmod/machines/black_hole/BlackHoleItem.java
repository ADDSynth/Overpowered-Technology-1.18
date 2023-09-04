package addsynth.overpoweredmod.machines.black_hole;

import addsynth.core.util.game.MessageUtil;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;

public final class BlackHoleItem extends BlockItem {

  public BlackHoleItem(){
    super(OverpoweredBlocks.black_hole.get(), new Item.Properties().tab(CreativeTabs.creative_tab).rarity(Rarity.EPIC));
    setRegistryName(Names.BLACK_HOLE);
  }

  @Override
  @SuppressWarnings("resource")
  public final InteractionResult place(final BlockPlaceContext context){
    final Level world = context.getLevel();
    if(TileBlackHole.is_black_hole_allowed(world)){
      return super.place(context);
    }
    if(world.isClientSide == false){
      final Player player = context.getPlayer();
      if(player != null){
        MessageUtil.send_to_player(player, "gui.overpowered.black_hole.not_allowed_in_this_dimension");
      }
    }
    return InteractionResult.FAIL;
  }

}
