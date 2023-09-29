package addsynth.overpoweredmod.machines.suspension_bridge;

import java.util.List;
import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.core.util.network.NetworkUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

public final class SyncClientBridgeMessage {

  private final BlockPos[] positions;
  private BridgeMessage bridge_message;
  private final BridgeMessage[] messages = new BridgeMessage[6];

  public SyncClientBridgeMessage(final BlockPos[] positions, final BridgeMessage bridge_message, final BridgeData[] bridge_data){
    this.positions = positions;
    this.bridge_message = bridge_message;
    messages[0] = bridge_data[0].message;
    messages[1] = bridge_data[1].message;
    messages[2] = bridge_data[2].message;
    messages[3] = bridge_data[3].message;
    messages[4] = bridge_data[4].message;
    messages[5] = bridge_data[5].message;
  }

  public SyncClientBridgeMessage(final List<BlockPos> positions, final BridgeMessage bridge_message, final BridgeData[] bridge_data){
    this(positions.toArray(new BlockPos[positions.size()]), bridge_message, bridge_data);
  }

  public SyncClientBridgeMessage(final BlockPos[] positions, final BridgeMessage bridge_message, final BridgeMessage[] messages){
    this.positions = positions;
    this.bridge_message = bridge_message;
    this.messages[0] = messages[0];
    this.messages[1] = messages[1];
    this.messages[2] = messages[2];
    this.messages[3] = messages[3];
    this.messages[4] = messages[4];
    this.messages[5] = messages[5];
  }

  public static final void encode(final SyncClientBridgeMessage message, final FriendlyByteBuf buf){
    NetworkUtil.writeBlockPositions(buf, message.positions);
    buf.writeInt(message.bridge_message.ordinal());
    buf.writeInt(message.messages[0].ordinal());
    buf.writeInt(message.messages[1].ordinal());
    buf.writeInt(message.messages[2].ordinal());
    buf.writeInt(message.messages[3].ordinal());
    buf.writeInt(message.messages[4].ordinal());
    buf.writeInt(message.messages[5].ordinal());
  }

  public static final SyncClientBridgeMessage decode(final FriendlyByteBuf buf){
    final BlockPos[] positions = NetworkUtil.readBlockPositions(buf);
    final BridgeMessage[] v = BridgeMessage.values();
    final BridgeMessage bridge_message = v[buf.readInt()];
    final BridgeMessage[] messages = {
      v[buf.readInt()], v[buf.readInt()], v[buf.readInt()],
      v[buf.readInt()], v[buf.readInt()], v[buf.readInt()]};
    return new SyncClientBridgeMessage(positions, bridge_message, messages);
  }

  public static final void handle(final SyncClientBridgeMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    context.enqueueWork(() -> {
      
      @SuppressWarnings("resource")
      final Minecraft minecraft = Minecraft.getInstance();
      @SuppressWarnings("null")
      final Level world = minecraft.player.level;
      
      TileSuspensionBridge tile;
      for(final BlockPos pos : message.positions){
        if(world.isLoaded(pos)){
          tile = MinecraftUtility.getTileEntity(pos, world, TileSuspensionBridge.class);
          if(tile != null){
            tile.setMessages(message.bridge_message, message.messages);
          }
        }
      }
    });
    context.setPacketHandled(true);
  }

}
