package addsynth.energy.lib.tiles.machines;

import addsynth.core.util.color.ColorCode;
import addsynth.core.util.java.StringUtil;
import net.minecraft.ChatFormatting;

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

  private final String translation_key;
  private final ChatFormatting color;

  private MachineState(final String translation_key){
    this.translation_key = translation_key;
    this.color = null;
  }

  private MachineState(final ChatFormatting color, final String translation_key){
    this.translation_key = translation_key;
    this.color = color;
  }

  public final String getStatus(){
    if(color != null){
      return color.toString() + StringUtil.translate(translation_key);
    }
    return StringUtil.translate(translation_key);
  }

}
