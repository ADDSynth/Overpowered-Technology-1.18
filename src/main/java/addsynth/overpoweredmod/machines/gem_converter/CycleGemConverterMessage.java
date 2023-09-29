package addsynth.overpoweredmod.machines.gem_converter;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public final class CycleGemConverterMessage {

  private final BlockPos position;
  private final boolean cycle_direction;

  public CycleGemConverterMessage(final BlockPos position, final boolean cycle_direction){
    this.position = position;
    this.cycle_direction = cycle_direction;
  }

  public static final void encode(final CycleGemConverterMessage message, final FriendlyByteBuf buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeBoolean(message.cycle_direction);
  }

  public static final CycleGemConverterMessage decode(final FriendlyByteBuf buf){
    return new CycleGemConverterMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()),buf.readBoolean());
  }

  public static void handle(final CycleGemConverterMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayer player = context.getSender();
    if(player != null){
      context.enqueueWork(() -> {
        @SuppressWarnings("resource")
        final ServerLevel world = player.getLevel();
        if(world.isLoaded(message.position)){
          final TileGemConverter tile = MinecraftUtility.getTileEntity(message.position, world, TileGemConverter.class);
          if(tile != null){
            tile.cycle(message.cycle_direction);
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
