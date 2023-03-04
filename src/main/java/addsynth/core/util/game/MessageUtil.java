package addsynth.core.util.game;

import javax.annotation.Nonnull;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.player.PlayerUtil;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.server.command.TextComponentHelper;

public final class MessageUtil {

  /** Sends a SYSTEM type message to the player.<br>
   *  This must be called on the server side. Calling on the client side will only translate to English.
   * @param player
   * @param translation_key
   */
  public static final void send_to_player(final Player player, final String translation_key, final Object ... arguments){
    @SuppressWarnings("resource")
    final MinecraftServer server = player.getServer();
    if(server != null){
      send_to_player(server, player, translation_key, arguments);
    }
  }

  /** Sends a SYSTEM type message to the player.<br>
   *  This must be called on the server side. Calling on the client side will only translate to English.
   * @param player
   * @param translation_key
   */
  public static final void send_to_player(@Nonnull MinecraftServer server, Player player, String translation_key, Object ... arguments){
    if(Language.getInstance().has(translation_key) == false){
      ADDSynthCore.log.warn("Missing translated text for: "+translation_key);
    }
    player.sendMessage(TextComponentHelper.createComponentTranslation(server, translation_key, arguments), null);
  }

  public static final void send_to_all_players(final Level world, final String translation_key, final Object ... arguments){
    @SuppressWarnings("resource")
    final MinecraftServer server = world.getServer();
    if(server != null){
      send_to_all_players(server, TextComponentHelper.createComponentTranslation(server, translation_key, arguments));
    }
  }

  public static final void send_to_all_players(final Level world, final Component text_component){
    @SuppressWarnings("resource")
    final MinecraftServer server = world.getServer();
    if(server != null){
      send_to_all_players(server, text_component);
    }
  }
  
  private static final void send_to_all_players(final MinecraftServer server, final Component text_component){
    final PlayerList player_list = server.getPlayerList();
    if(player_list != null){
      player_list.broadcastMessage(text_component, ChatType.SYSTEM, null);
    }
  }

  public static final void send_to_all_players_in_world(final Level world, final String translation_key, final Object ... arguments){
    @SuppressWarnings("resource")
    final MinecraftServer server = world.getServer();
    if(server != null){
      PlayerUtil.allPlayersInWorld(server, world, (ServerPlayer player) -> {
        player.sendMessage(TextComponentHelper.createComponentTranslation(server, translation_key, arguments), null);
      });
    }
  }

  public static final void send_to_all_players_in_world(final Level world, final Component text_component){
    @SuppressWarnings("resource")
    final MinecraftServer server = world.getServer();
    if(server != null){
      PlayerUtil.allPlayersInWorld(server, world, (ServerPlayer player) -> {
        player.sendMessage(text_component, null);
      });
    }
  }

}
