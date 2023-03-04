package addsynth.core.gameplay.team_manager.network_messages;

import java.util.function.Supplier;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.util.game.data.ScoreUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.Score;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

/** Created on the client side whenever the player selects a Player or Objective,
 *  and gets sent to the Server. Determines player's score given the objective and
 *  returns the score back to the client that requested the score. */
public final class RequestPlayerScoreMessage {

  private String player;
  private String objective;

  public RequestPlayerScoreMessage(final String player, String objective){
    this.player = player;
    this.objective = objective;
  }

  public static final void encode(final RequestPlayerScoreMessage message, final FriendlyByteBuf buf){
    buf.writeUtf(message.player);
    buf.writeUtf(message.objective);
  }

  public static final RequestPlayerScoreMessage decode(final FriendlyByteBuf buf){
    return new RequestPlayerScoreMessage(buf.readUtf(), buf.readUtf());
  }

  public static void handle(final RequestPlayerScoreMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayer source = context.getSender();
    if(source != null){
      context.enqueueWork(() -> {
        // server only gets sent this message if player name and objective name strings exist. 
        try{
          @SuppressWarnings("resource")
          final MinecraftServer server = source.getServer();
          if(server != null){
            final Score score = ScoreUtil.getScore(server, message.player, message.objective);
            final int player_score = score.getScore();
            NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> source), new PlayerScoreMessage(player_score));
          }
        }
        catch(Exception e){}
      });
    }
    context.setPacketHandled(true);
  }

}
