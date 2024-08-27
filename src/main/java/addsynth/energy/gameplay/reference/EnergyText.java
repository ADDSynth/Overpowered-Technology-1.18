package addsynth.energy.gameplay.reference;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

/** The TextReference class should only be used for common words
 *  or commonly used text that can be used in multiple projects.
 *  Text that is only used in one place, should be defined there.
 *  
 *  Also, keep in mind that just because a word has multiple
 *  meanings in my language, does not mean it has the same meanings
 *  in other languages. */
public final class EnergyText {

  public static final TextComponent null_energy_reference = new TextComponent("[Error: Null Energy Reference]");

  // Common
  public static final TranslatableComponent energy_text           = new TranslatableComponent("gui.addsynth_energy.common.energy");
  public static final TranslatableComponent energy_usage_text     = new TranslatableComponent("gui.addsynth_energy.common.energy_usage");
  public static final TranslatableComponent tick_text             = new TranslatableComponent("gui.addsynth_energy.common.tick");
  public static final TranslatableComponent efficiency_text       = new TranslatableComponent("gui.addsynth_energy.common.efficiency");
  public static final TranslatableComponent max_extract_text      = new TranslatableComponent("gui.addsynth_energy.common.max_extract");
  public static final TranslatableComponent extraction_text       = new TranslatableComponent("gui.addsynth_energy.common.extraction");
  public static final TranslatableComponent status_text           = new TranslatableComponent("gui.addsynth_energy.common.status");
  public static final TranslatableComponent time_left_text        = new TranslatableComponent("gui.addsynth_energy.common.time_remaining");
  public static final TranslatableComponent charge_remaining_text = new TranslatableComponent("gui.addsynth_energy.common.charge_time_remaining");
  public static final TranslatableComponent full_charge_time_text = new TranslatableComponent("gui.addsynth_energy.common.time_to_full_charge");
  public static final TranslatableComponent no_energy_change_text = new TranslatableComponent("gui.addsynth_energy.common.no_energy_change");
  public static final TranslatableComponent energy_stored_text    = new TranslatableComponent("gui.addsynth_energy.common.energy_stored");
  public static final TranslatableComponent input_text            = new TranslatableComponent("gui.addsynth_energy.generator.input");
  public static final TranslatableComponent mode_text             = new TranslatableComponent("gui.addsynth_energy.common.mode");

  // Tooltip Subtitles:
  public static final TranslatableComponent energy_machine     = new TranslatableComponent("gui.addsynth_energy.tooltip.energy_machine");
  public static final TranslatableComponent energy_source      = new TranslatableComponent("gui.addsynth_energy.tooltip.energy_source");
  public static final TranslatableComponent generator_subtitle = new TranslatableComponent("gui.addsynth_energy.tooltip.generator");
  public static final TranslatableComponent battery_subtitle   = new TranslatableComponent("gui.addsynth_energy.tooltip.battery");
  public static final TranslatableComponent class_1_machine    = new TranslatableComponent("gui.addsynth_energy.tooltip.class_1_machine");
  public static final TranslatableComponent class_2_machine    = new TranslatableComponent("gui.addsynth_energy.tooltip.class_2_machine");
  public static final TranslatableComponent class_3_machine    = new TranslatableComponent("gui.addsynth_energy.tooltip.class_3_machine");
  public static final TranslatableComponent class_4_machine    = new TranslatableComponent("gui.addsynth_energy.tooltip.class_4_machine");
  public static final TranslatableComponent class_5_machine    = new TranslatableComponent("gui.addsynth_energy.tooltip.class_5_machine");

  // Machines
  public static final TranslatableComponent selected_text = new TranslatableComponent("gui.addsynth_energy.common.selected");
  public static final TranslatableComponent total         = new TranslatableComponent("gui.addsynth_energy.common.total");

  // Descriptions:
  public static final TranslatableComponent wire_description               = new TranslatableComponent("gui.addsynth_energy.jei_description.wire");
  public static final TranslatableComponent generator_description          = new TranslatableComponent("gui.addsynth_energy.jei_description.generator");
  public static final TranslatableComponent energy_storage_description     = new TranslatableComponent("gui.addsynth_energy.jei_description.energy_storage");
  public static final TranslatableComponent electric_furnace_description   = new TranslatableComponent("gui.addsynth_energy.jei_description.electric_furnace");
  public static final TranslatableComponent compressor_description         = new TranslatableComponent("gui.addsynth_energy.jei_description.compressor");
  public static final TranslatableComponent circuit_fabricator_description = new TranslatableComponent("gui.addsynth_energy.jei_description.circuit_fabricator");
  public static final TranslatableComponent energy_interface_description   = new TranslatableComponent("gui.addsynth_energy.jei_description.universal_energy_interface");
  public static final TranslatableComponent energy_diagnostics_description = new TranslatableComponent("gui.addsynth_energy.jei_description.energy_diagnostics_block");
  public static final TranslatableComponent power_regulator_description    = new TranslatableComponent("gui.addsynth_energy.jei_description.power_regulator");

}
