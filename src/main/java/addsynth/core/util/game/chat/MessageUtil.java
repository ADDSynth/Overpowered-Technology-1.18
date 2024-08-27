package addsynth.core.util.game.chat;

import addsynth.core.util.player.PlayerUtil;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class MessageUtil {

  /** Sends a SYSTEM type message to the player.<br>
   *  It's okay to send a message from server to client, or client to client,<br>
   *  Sending a message from client to server will ONLY translate to English. */
  public static final void send_to_player(Player player, Component message){
    player.sendMessage(message, Util.NIL_UUID);
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
      player_list.broadcastMessage(text_component, ChatType.SYSTEM, Util.NIL_UUID);
    }
  }

  public static final void send_to_all_players_in_world(final Level world, final Component text_component){
    @SuppressWarnings("resource")
    final MinecraftServer server = world.getServer();
    if(server != null){
      PlayerUtil.allPlayersInWorld(server, world, (ServerPlayer player) -> {
        player.sendMessage(text_component, Util.NIL_UUID);
      });
    }
  }

}
