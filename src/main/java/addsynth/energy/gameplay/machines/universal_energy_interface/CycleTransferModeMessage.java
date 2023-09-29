package addsynth.energy.gameplay.machines.universal_energy_interface;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public final class CycleTransferModeMessage {

  private final BlockPos position;

  public CycleTransferModeMessage(final BlockPos position){
    this.position = position;
  }

  public static final void encode(final CycleTransferModeMessage message, final FriendlyByteBuf buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
  }

  public static final CycleTransferModeMessage decode(final FriendlyByteBuf buf){
    return new CycleTransferModeMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()));
  }

  public static void handle(final CycleTransferModeMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayer player = context.getSender();
    if(player != null){
      @SuppressWarnings("resource")
      final ServerLevel world = player.getLevel();
      context.enqueueWork(() -> {
        if(world.isLoaded(message.position)){
          final TileUniversalEnergyInterface tile = MinecraftUtility.getTileEntity(message.position, world, TileUniversalEnergyInterface.class);
          if(tile != null){
            tile.set_next_transfer_mode();
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
