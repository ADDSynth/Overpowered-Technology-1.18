package addsynth.overpoweredmod.machines.laser.network_messages;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.overpoweredmod.machines.laser.machine.LaserNetwork;
import addsynth.overpoweredmod.machines.laser.machine.TileLaserHousing;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public final class SetLaserDistanceMessage {

  private final BlockPos position;
  private final int laser_distance;

  public SetLaserDistanceMessage(final BlockPos position, final int laser_distance){
    this.position = position;
    this.laser_distance = laser_distance;
  }

  public static final void encode(final SetLaserDistanceMessage message, final FriendlyByteBuf buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeInt(message.laser_distance);
  }

  public static final SetLaserDistanceMessage decode(final FriendlyByteBuf buf){
    return new SetLaserDistanceMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()),buf.readInt());
  }

  public static void handle(final SetLaserDistanceMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayer player = context.getSender();
    if(player != null){
      context.enqueueWork(() -> {
        @SuppressWarnings("resource")
        final ServerLevel world = player.getLevel();
        if(world.isLoaded(message.position)){
          final TileLaserHousing tile = MinecraftUtility.getTileEntity(message.position, world, TileLaserHousing.class);
          if(tile != null){
            final LaserNetwork network = tile.getBlockNetwork();
            if(network != null){
              network.setLaserDistance(message.laser_distance);
            }
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
