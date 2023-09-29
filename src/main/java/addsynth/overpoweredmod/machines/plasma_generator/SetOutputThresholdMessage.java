package addsynth.overpoweredmod.machines.plasma_generator;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public final class SetOutputThresholdMessage {

  private final BlockPos position;
  private final int output_threshold;

  public SetOutputThresholdMessage(final BlockPos position, final int output_threshold){
    this.position = position;
    this.output_threshold = output_threshold;
  }

  public static final void encode(final SetOutputThresholdMessage message, final FriendlyByteBuf buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeInt(message.output_threshold);
  }

  public static final SetOutputThresholdMessage decode(final FriendlyByteBuf buf){
    return new SetOutputThresholdMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()),buf.readInt());
  }

  public static void handle(final SetOutputThresholdMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayer player = context.getSender();
    if(player != null){
      context.enqueueWork(() -> {
        @SuppressWarnings("resource")
        final ServerLevel world = player.getLevel();
        if(world.isLoaded(message.position)){
          final TilePlasmaGenerator tile = MinecraftUtility.getTileEntity(message.position, world, TilePlasmaGenerator.class);
          if(tile != null){
            tile.set_output_number(message.output_threshold);
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
