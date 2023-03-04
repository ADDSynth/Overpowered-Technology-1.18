package addsynth.core.util.server;

import java.util.ArrayList;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.server.ServerLifecycleHooks;

public final class ServerUtils {

  // DO NOT CACHE THE SERVER! Because the actively running server could change during the course
  // of the game, such as when a player quits a multiplayer world and starts a singleplayer world.

  /** DO NOT CACHE THE SERVER!!!
   *  Please Use {@link Level#getServer()} whenever possible. */
  @Nullable
  public static final MinecraftServer getServer(){
    final MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
    if(server == null){
      ADDSynthCore.log.fatal(new NullPointerException(ServerUtils.class.getName()+".getServer() was unable to retrieve the current running server! Maybe there is no server running?"));
    }
    return server;
  }

  @SuppressWarnings("resource")
  public static ArrayList<ServerPlayer> get_players_in_world(final Level world){
    final ArrayList<ServerPlayer> player_list = new ArrayList<>(20);
    final MinecraftServer server = world.getServer();
    if(server != null){
      for(ServerPlayer player : server.getPlayerList().getPlayers()){
        if(player.level == world){
          player_list.add(player);
        }
      }
    }
    return player_list;
  }

  /** Allows any Entity and not EntityLiving? */
  public static void teleport_to_dimension(final Entity entity, final int dimension_id){
    // entity.changeDimension(dimension_id);
  }

  //public static void teleport_to_dimension(final Entity entity, final int dimension_id, final Teleporter teleporter){
  //   if(entity instanceof EntityPlayerMP){
  //     getServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP)entity, dimension_id, teleporter);
  //   }
  // }

  public static void teleport_to_dimension(final ServerPlayer player, final int dimension_id, final ITeleporter teleporter){
    // getServer().getPlayerList().transferPlayerToDimension(player, dimension_id, teleporter);
  }

}
