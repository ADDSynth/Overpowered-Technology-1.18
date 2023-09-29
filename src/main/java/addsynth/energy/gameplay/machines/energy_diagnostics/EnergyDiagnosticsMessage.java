package addsynth.energy.gameplay.machines.energy_diagnostics;

import java.util.Collection;
import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

public final class EnergyDiagnosticsMessage {

  private final BlockPos position;
  private final int number_of_machine_data;
  private final EnergyDiagnosticData[] diagnostic_data;
  private final EnergyDiagnosticData totals;

  public EnergyDiagnosticsMessage(BlockPos position){
    this.position = position;
    number_of_machine_data = -1;
    diagnostic_data = null;
    totals = null;
  }

  public EnergyDiagnosticsMessage(BlockPos position, Collection<EnergyDiagnosticData> diagnostic_data, EnergyDiagnosticData totals){
    this.position = position;
    number_of_machine_data = diagnostic_data.size();
    this.diagnostic_data = diagnostic_data.toArray(new EnergyDiagnosticData[number_of_machine_data]);
    this.totals = totals;
  }

  private EnergyDiagnosticsMessage(FriendlyByteBuf buf){
    this.position = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    number_of_machine_data = buf.readInt();
    if(number_of_machine_data >= 0){
      diagnostic_data = new EnergyDiagnosticData[number_of_machine_data];
      int i;
      for(i = 0; i < number_of_machine_data; i++){
        diagnostic_data[i] = new EnergyDiagnosticData(buf);
      }
      totals = new EnergyDiagnosticData(buf);
    }
    else{
      diagnostic_data = null;
      totals = null;
    }
  }

  public static final void encode(final EnergyDiagnosticsMessage message, final FriendlyByteBuf buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    if(message.diagnostic_data != null && message.number_of_machine_data >= 0){
      buf.writeInt(message.number_of_machine_data);
      for(EnergyDiagnosticData data : message.diagnostic_data){
        data.save(buf);
      }
      message.totals.save(buf);
    }
    else{
      buf.writeInt(-1);
    }
  }

  public static final EnergyDiagnosticsMessage decode(final FriendlyByteBuf buf){
    return new EnergyDiagnosticsMessage(buf);
  }

  public static void handle(final EnergyDiagnosticsMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    context.enqueueWork(() -> {

      @SuppressWarnings("resource")
      final Minecraft minecraft = Minecraft.getInstance();
      @SuppressWarnings("null")
      final Level world = minecraft.player.level;

      if(world.isLoaded(message.position)){
        final TileEnergyDiagnostics energy_diagnostics_machine = MinecraftUtility.getTileEntity(message.position, world, TileEnergyDiagnostics.class);
        if(energy_diagnostics_machine != null){
          energy_diagnostics_machine.set(message.diagnostic_data, message.totals);
        }
      }
    });
    context.setPacketHandled(true);
  }

}
