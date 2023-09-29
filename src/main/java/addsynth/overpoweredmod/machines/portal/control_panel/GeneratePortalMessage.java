package addsynth.overpoweredmod.machines.portal.control_panel;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.core.util.game.data.AdvancementUtil;
import addsynth.overpoweredmod.assets.CustomAdvancements;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public final class GeneratePortalMessage {

  private final BlockPos position;

  public GeneratePortalMessage(final BlockPos position){
    this.position = position;
  }

  public static final void encode(final GeneratePortalMessage message, final FriendlyByteBuf buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
  }

  public static final GeneratePortalMessage decode(final FriendlyByteBuf buf){
    return new GeneratePortalMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()));
  }

  public static void handle(final GeneratePortalMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayer player = context.getSender();
    if(player != null){
      context.enqueueWork(() -> {
        @SuppressWarnings("resource")
        final ServerLevel world = player.getLevel();
        if(world.isLoaded(message.position)){
          final TilePortalControlPanel tile = MinecraftUtility.getTileEntity(message.position, world, TilePortalControlPanel.class);
          if(tile != null){
            tile.generate_portal();
            AdvancementUtil.grantAdvancement(player, CustomAdvancements.PORTAL);
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
