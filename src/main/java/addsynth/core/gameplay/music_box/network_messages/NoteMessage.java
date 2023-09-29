package addsynth.core.gameplay.music_box.network_messages;

import java.util.function.Supplier;
import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public final class NoteMessage {

  private BlockPos position;
  private byte frame;
  private byte track;
  private boolean on;
  private byte note;
  private float volume;

  public NoteMessage(BlockPos position, byte frame, byte track, byte note, float volume){
    this.position = position;
    this.frame = frame;
    this.track = track;
    this.on = true;
    this.note = note;
    this.volume = volume;
  }

  public NoteMessage(BlockPos position, byte frame, byte track){
    this.position = position;
    this.frame = frame;
    this.track = track;
    this.on = false;
  }

  public static final void encode(final NoteMessage message, final FriendlyByteBuf buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeBoolean(message.on);
    buf.writeByte(message.frame);
    buf.writeByte(message.track);
    buf.writeByte(message.note);
    buf.writeFloat(message.volume);
  }

  public static final NoteMessage decode(final FriendlyByteBuf buf){
    final BlockPos position = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
    if(buf.readBoolean()){
      return new NoteMessage(position, buf.readByte(), buf.readByte(), buf.readByte(), buf.readFloat());
    }
    return new NoteMessage(position, buf.readByte(), buf.readByte());
  }

  public static void handle(final NoteMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayer player = context.getSender();
    if(player != null){
      @SuppressWarnings("resource")
      final ServerLevel world = player.getLevel();
      context.enqueueWork(() -> {
        if(world.isLoaded(message.position)){
          final TileMusicBox tile = MinecraftUtility.getTileEntity(message.position,world, TileMusicBox.class);
          if(tile != null){
            if(message.on){
              tile.set_note(message.track, message.frame, message.note);
            }
            else{
              tile.disable_note(message.track, message.frame);
            }
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
