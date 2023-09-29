package addsynth.core.gameplay.music_box.network_messages;

import java.util.function.Supplier;
import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public final class ChangeInstrumentMessage {

  private BlockPos position;
  private byte track;
  private byte instrument;

  public ChangeInstrumentMessage(final BlockPos position, final byte track, final byte instrument){
    this.position = position;
    this.track = track;
    this.instrument = instrument;
  }

  public static final void encode(final ChangeInstrumentMessage message, final FriendlyByteBuf buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeByte(message.track);
    buf.writeByte(message.instrument);
  }

  public static final ChangeInstrumentMessage decode(final FriendlyByteBuf buf){
    final BlockPos position = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
    return new ChangeInstrumentMessage(position, buf.readByte(), buf.readByte());
  }

  public static void handle(final ChangeInstrumentMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayer player = context.getSender();
    if(player != null){
      @SuppressWarnings("resource")
      final ServerLevel world = player.getLevel();
      context.enqueueWork(() -> {
        if(world.isLoaded(message.position)){
          final TileMusicBox music_box = MinecraftUtility.getTileEntity(message.position, world, TileMusicBox.class);
          if(music_box != null){
            music_box.change_track_instrument(message.track, message.instrument);
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
