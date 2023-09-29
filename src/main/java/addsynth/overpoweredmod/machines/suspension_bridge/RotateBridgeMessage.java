package addsynth.overpoweredmod.machines.suspension_bridge;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public final class RotateBridgeMessage {

  private final BlockPos position;
  
  public RotateBridgeMessage(final BlockPos position){
    this.position = position;
  }
  
  public static final void encode(final RotateBridgeMessage message, final FriendlyByteBuf buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
  }
  
  public static final RotateBridgeMessage decode(final FriendlyByteBuf buf){
    return new RotateBridgeMessage(new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
  }

  public static final void handle(final RotateBridgeMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayer player = context.getSender();
    if(player != null){
      context.enqueueWork(() -> {
        @SuppressWarnings("resource")
        final ServerLevel world = player.getLevel();
        if(world.isLoaded(message.position)){
          final TileSuspensionBridge tile = MinecraftUtility.getTileEntity(message.position, world, TileSuspensionBridge.class);
          if(tile != null){
            final BridgeNetwork bridge_network = tile.getBlockNetwork();
            if(bridge_network != null){
              bridge_network.rotate(world);
            }
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
