package addsynth.energy.gameplay.client;

import addsynth.energy.gameplay.machines.energy_diagnostics.GuiEnergyDiagnostics;
import addsynth.energy.gameplay.machines.energy_diagnostics.TileEnergyDiagnostics;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;

public final class GuiProvider {

  @SuppressWarnings("resource")
  public static final void openEnergyDiagnostics(final TileEnergyDiagnostics tile, final String title){
    Minecraft.getInstance().setScreen(new GuiEnergyDiagnostics(tile, new TranslatableComponent(title)));
  }

}
