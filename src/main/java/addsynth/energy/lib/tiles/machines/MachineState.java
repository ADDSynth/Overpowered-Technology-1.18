package addsynth.energy.lib.tiles.machines;

import addsynth.core.util.color.ColorCode;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

public enum MachineState {

  OFF                 ("gui.addsynth_energy.machine_state.off"),
  POWERING_ON         ("gui.addsynth_energy.machine_state.powering_on"),
  POWERING_OFF        ("gui.addsynth_energy.machine_state.powering_off"),
  IDLE                (ColorCode.GOOD,      "gui.addsynth_energy.machine_state.idle"),
  RUNNING             (                     "gui.addsynth_energy.machine_state.running"),
  OUTPUT_FULL         (ColorCode.ERROR,     "gui.addsynth_energy.machine_state.output_full"),
  NO_ENERGY           (ColorCode.ERROR,     "gui.addsynth_energy.machine_state.no_energy"),
  NOT_RECEIVING_ENERGY(ColorCode.ERROR,     "gui.addsynth_energy.machine_state.not_receiving_energy");

  public static final MachineState[] value = MachineState.values();

  private final MutableComponent status;
  private final ChatFormatting color;

  private MachineState(final String translation_key){
    this.status = new TranslatableComponent(translation_key);
    this.color = null;
  }

  private MachineState(final ChatFormatting color, final String translation_key){
    this.status = new TranslatableComponent(translation_key);
    this.color = color;
  }

  public final String getStatus(){
    return (color != null ? status.withStyle(color) : status).getString();
  }

}
