package addsynth.core.gameplay;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;

public final class Config {

  private static final Pair<Config, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(Config::new);
  public static final Config INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

  // Debug
  public static ForgeConfigSpec.BooleanValue debug_mod_detection;
  public static ForgeConfigSpec.BooleanValue dump_tags;
  public static ForgeConfigSpec.BooleanValue dump_map_colors;

  // Music Box
  public static ForgeConfigSpec.BooleanValue enable_left_hand;

  public static ForgeConfigSpec.BooleanValue item_explosion_command;
  public static ForgeConfigSpec.BooleanValue zombie_raid_command;
  public static ForgeConfigSpec.BooleanValue blackout_command;
  public static ForgeConfigSpec.BooleanValue lightning_storm_command;

  // Other Mods
  public enum EMCValueDefinition {DEVELOPER_DEFINED, ACCURATE}
  public static ForgeConfigSpec.EnumValue<EMCValueDefinition> emc_definition;
  public static final boolean emcDeveloperDefined(){
    return emc_definition.get() == EMCValueDefinition.DEVELOPER_DEFINED;
  }

  public static ForgeConfigSpec.BooleanValue show_advanced_config;

  public Config(final ForgeConfigSpec.Builder builder){

    builder.push("Debug");
    debug_mod_detection = builder.define("Print Mod Detection Results", false);
    dump_tags           = builder.define("Dump Block & Item Tags", false);
    dump_map_colors     = builder.define("Dump Map Colors", false);
    builder.pop();

    builder.push("Music Box");
    enable_left_hand = builder.comment(
      "By default, the Music Box uses Right-Hand controls (Left-click adds notes, Right-click deletes notes.)\n"+
      "Set this to true to enable Left-Hand controls, which will swap these functions.")
      .define("Enable Left Hand", false);
    builder.pop();

    builder.push("Compatibility");
      builder.push("Project E");
        emc_definition = builder.defineEnum("How Should EMC Values be Calculated", EMCValueDefinition.DEVELOPER_DEFINED);
      builder.pop();
    builder.pop();

    builder.push("Commands");
    item_explosion_command  = builder.define("Item Explosion",  true);
    zombie_raid_command     = builder.define("Zombie Raid",     true);
    blackout_command        = builder.define("Blackout",        true);
    lightning_storm_command = builder.define("Lightning Storm", true);
    builder.pop();

    builder.push("Advanced");
    show_advanced_config = builder.comment(
      "Enabling this will grant you access to advanced configuration options in the Mod's Configuration screen.\n"+
      "Advanced configuration options such as those in the worldgen.toml file allow you access to internal game values,\n"+
      "and adjusting them will vastly alter gameplay. They are only intended to be used for debug, testing, or\n"+
      "experimental purposes. In order to maintain a standard gameplay experience (the way the author intended)\n"+
      "we encourage you to leave these values at their defaults. (However, modpack authors may want to adjust these\n"+
      "values in order to create a balanced gameplay.)")
      .define("Show Advanced Config in Client Gui", false);
    builder.pop();
  }

}
