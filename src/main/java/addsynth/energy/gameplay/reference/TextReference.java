package addsynth.energy.gameplay.reference;

import net.minecraft.network.chat.TranslatableComponent;

public final class TextReference {

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

  // Interface Modes:
  public static final class transfer_mode {
    public static final TranslatableComponent bi_directional = new TranslatableComponent("gui.addsynth_energy.transfer_mode.bi_directional");
    public static final TranslatableComponent receive        = new TranslatableComponent("gui.addsynth_energy.transfer_mode.receive");
    public static final TranslatableComponent extract        = new TranslatableComponent("gui.addsynth_energy.transfer_mode.extract");
    public static final TranslatableComponent external       = new TranslatableComponent("gui.addsynth_energy.transfer_mode.external");
    public static final TranslatableComponent internal       = new TranslatableComponent("gui.addsynth_energy.transfer_mode.internal");
    public static final TranslatableComponent none           = new TranslatableComponent("gui.addsynth_energy.transfer_mode.no_transfer");
  }

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
