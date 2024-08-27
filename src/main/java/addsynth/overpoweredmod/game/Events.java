package addsynth.overpoweredmod.game;

import addsynth.core.util.game.chat.MessageUtil;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent;

/** Note: functions with the <code>@SubscribeEvent</code> annotation MUST be public! */
@Mod.EventBusSubscriber(modid = OverpoweredTechnology.MOD_ID)
public final class Events {

  @SubscribeEvent
  public static final void pick_up_item(final ItemPickupEvent event){
    final Item item = event.getStack().getItem();
    final Player player = event.getPlayer();
    if(item == OverpoweredItems.void_crystal.get()){
      /*
      if(player.dimension.getId() == WeirdDimension.id){
        final MinecraftServer server = player.getServer();
        if(server != null){
          server.getPlayerList().transferPlayerToDimension((ServerPlayer)player, 0, new CustomTeleporter(server.getWorld(0)));
        }
      }
      */
    }
  }

  /* DELETE when we implement the Unknown Dimension for 1.14+ delete this warning message and all translation keys.
  @SubscribeEvent
  public static final void craft_event(final ItemCraftedEvent event){
    final Item item = event.getCrafting().getItem();
    final PlayerEntity player = event.getPlayer();
    if(item == Item.BLOCK_TO_ITEM.get(Machines.portal_control_panel)){
      if(EffectiveSide.get() == LogicalSide.CLIENT){
        MessageUtil.send_to_player(player, "gui.overpowered.portal_warning_message");
      }
    }
  }
  */

  /** @see net.minecraftforge.common.ForgeHooks#onLivingAttack */
  @SubscribeEvent
  public static final void onLivingEntityAttacked(final LivingAttackEvent event){
  }

  // DELETE these if they don't get used.

  @SubscribeEvent
  public static final void onAttackLivingEntity(final AttackEntityEvent event){
  }

}
