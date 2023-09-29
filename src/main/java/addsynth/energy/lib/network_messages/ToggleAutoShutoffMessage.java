package addsynth.energy.lib.network_messages;

import java.util.function.Supplier;
import addsynth.energy.lib.tiles.machines.IAutoShutoff;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

public final class ToggleAutoShutoffMessage {

  private final BlockPos position;

  public ToggleAutoShutoffMessage(final BlockPos position){
    this.position = position;
  }

  public static final void encode(final ToggleAutoShutoffMessage message, final FriendlyByteBuf buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
  }

  public static final ToggleAutoShutoffMessage decode(final FriendlyByteBuf buf){
    return new ToggleAutoShutoffMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()));
  }

  public static void handle(final ToggleAutoShutoffMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayer player = context.getSender();
    if(player != null){
      @SuppressWarnings("resource")
      final ServerLevel world = player.getLevel();
      context.enqueueWork(() -> {
        if(world.isLoaded(message.position)){
          final BlockEntity tile = world.getBlockEntity(message.position);
          if(tile != null){
            if(tile instanceof IAutoShutoff){
              ((IAutoShutoff)tile).toggle_auto_shutoff();
            }
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
