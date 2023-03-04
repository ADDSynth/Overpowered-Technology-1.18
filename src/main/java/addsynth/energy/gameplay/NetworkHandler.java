package addsynth.energy.gameplay;

import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.machines.circuit_fabricator.ChangeCircuitFabricatorRecipe;
import addsynth.energy.gameplay.machines.energy_diagnostics.EnergyDiagnosticsMessage;
import addsynth.energy.gameplay.machines.universal_energy_interface.CycleTransferModeMessage;
import addsynth.energy.lib.network_messages.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class NetworkHandler {

  private static final String PROTOCAL_VERSION = "1";

  public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
    new ResourceLocation(ADDSynthEnergy.MOD_ID, "main_network_channel"),
    () -> PROTOCAL_VERSION, PROTOCAL_VERSION::equals, PROTOCAL_VERSION::equals
  );

  public static final void registerMessages(){
    INSTANCE.registerMessage(0,
      SwitchMachineMessage.class,
      SwitchMachineMessage::encode,
      SwitchMachineMessage::decode,
      SwitchMachineMessage::handle
    );
    INSTANCE.registerMessage(1,
      CycleTransferModeMessage.class,
      CycleTransferModeMessage::encode,
      CycleTransferModeMessage::decode,
      CycleTransferModeMessage::handle
    );
    INSTANCE.registerMessage(2,
      ToggleAutoShutoffMessage.class,
      ToggleAutoShutoffMessage::encode,
      ToggleAutoShutoffMessage::decode,
      ToggleAutoShutoffMessage::handle
    );
    INSTANCE.registerMessage(3,
      ChangeCircuitFabricatorRecipe.class,
      ChangeCircuitFabricatorRecipe::encode,
      ChangeCircuitFabricatorRecipe::decode,
      ChangeCircuitFabricatorRecipe::handle
    );
    INSTANCE.registerMessage(4,
      EnergyDiagnosticsMessage.class,
      EnergyDiagnosticsMessage::encode,
      EnergyDiagnosticsMessage::decode,
      EnergyDiagnosticsMessage::handle
    );
  }

}
