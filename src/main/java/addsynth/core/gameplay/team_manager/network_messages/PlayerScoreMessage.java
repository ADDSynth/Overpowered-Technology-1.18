package addsynth.core.gameplay.team_manager.network_messages;

import java.util.function.Supplier;
import addsynth.core.gameplay.team_manager.gui.TeamManagerGui;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public final class PlayerScoreMessage {

  private final int score;

  public PlayerScoreMessage(int score){
    this.score = score;
  }

  public static final void encode(final PlayerScoreMessage message, final FriendlyByteBuf buf){
    buf.writeInt(message.score);
  }

  public static final PlayerScoreMessage decode(final FriendlyByteBuf buf){
    return new PlayerScoreMessage(buf.readInt());
  }

  public static void handle(final PlayerScoreMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    context.enqueueWork(() -> {
      TeamManagerGui.player_score = message.score;
    });
    context.setPacketHandled(true);
  }

}
