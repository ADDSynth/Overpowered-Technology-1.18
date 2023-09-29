package addsynth.core.gameplay.music_box.network_messages;

import java.util.function.Supplier;
import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public final class MusicBoxMessage {

  private BlockPos position;
  private TileMusicBox.Command command;
  private byte info;

  public MusicBoxMessage(final BlockPos position, final TileMusicBox.Command command){
    this(position, command, 0);
  }

  public MusicBoxMessage(final BlockPos position, final TileMusicBox.Command command, final int data){
    this.position = position;
    this.command = command;
    this.info = (byte)data;
  }

  public static final void encode(final MusicBoxMessage message, final FriendlyByteBuf buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeInt(message.command.ordinal());
    buf.writeByte(message.info);
  }

  public static final MusicBoxMessage decode(final FriendlyByteBuf buf){
    final BlockPos position = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
    final TileMusicBox.Command command = TileMusicBox.Command.value[buf.readInt()];
    return new MusicBoxMessage(position, command, buf.readByte());
  }

  public static void handle(final MusicBoxMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayer player = context.getSender();
    if(player != null){
      @SuppressWarnings("resource")
      final ServerLevel world = player.getLevel();
      context.enqueueWork(() -> {
        if(world.isLoaded(message.position)){
          final TileMusicBox music_box = MinecraftUtility.getTileEntity(message.position, world, TileMusicBox.class);
          if(music_box != null){
            switch(message.command){
            case PLAY:                    music_box.play(false); break;
            case CHANGE_TEMPO:            music_box.change_tempo(message.info > 0); break;
            case CYCLE_NEXT_DIRECTION:    music_box.increment_next_direction(); break;
            case TOGGLE_MUTE:             music_box.toggle_mute(message.info); break;
            case SWAP_TRACK:              music_box.swap_track(message.info, message.info + 1); break;
            }
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
