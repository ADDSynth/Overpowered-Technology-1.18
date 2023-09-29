package addsynth.energy.lib.network_messages;

import java.util.function.Supplier;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.lib.gui.widgets.OnOffSwitch;
import addsynth.energy.lib.tiles.machines.ISwitchableMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

/** After a player clicks on a {@link OnOffSwitch} this message gets sent to a
 *  TileEntity that implements the {@link ISwitchableMachine} and calls the
 *  {@link ISwitchableMachine#togglePowerSwitch() togglePowerSwitch()} method.
 */
public final class SwitchMachineMessage {

  private final BlockPos position;

  public SwitchMachineMessage(final BlockPos position){
    this.position = position;
  }

  public static final void encode(final SwitchMachineMessage message, final FriendlyByteBuf buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
  }

  public static final SwitchMachineMessage decode(final FriendlyByteBuf buf){
    return new SwitchMachineMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()));
  }

  public static void handle(final SwitchMachineMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayer player = context.getSender();
    if(player != null){
      @SuppressWarnings("resource")
      final ServerLevel world = player.getLevel();
      context.enqueueWork(() -> {
        if(world.isLoaded(message.position)){
          final BlockEntity tile = world.getBlockEntity(message.position);
          if(tile != null){
            if(tile instanceof ISwitchableMachine){
              ((ISwitchableMachine)tile).togglePowerSwitch();
            }
            else{
              ADDSynthEnergy.log.warn(
                "A "+SwitchMachineMessage.class.getSimpleName()+" network message was sent to the Server."+
                "Cannot toggle the Power Switch for TileEntity '"+tile.getClass().getSimpleName()+"' at "+
                "position "+message.position+" because"+
                "it is does not implement the "+ISwitchableMachine.class.getSimpleName()+" interface."
              );
            }
          }
          else{
            ADDSynthEnergy.log.warn(new NullPointerException("No TileEntity exists at location: "+message.position+"."));
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
