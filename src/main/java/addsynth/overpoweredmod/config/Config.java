package addsynth.overpoweredmod.config;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import addsynth.core.util.time.TimeConstants;
import addsynth.overpoweredmod.machines.black_hole.TileBlackHole;
import net.minecraftforge.common.ForgeConfigSpec;

public final class Config {

  // 100% Thanks to this guy:
  // https://www.minecraftforge.net/forum/topic/72021-1142-how-does-config-work-in-forge-for-1142/
  // https://github.com/Cadiboo/Example-Mod/commit/43db50e176d758ade2338764d7e2fe1b63aae7dd#diff-cb1dc14ae764daf38ef877fabbe0d72aR37

  public static ForgeConfigSpec.BooleanValue blend_working_item;

  public static ForgeConfigSpec.ConfigValue<Integer> unknown_dimension_id;
  public static ForgeConfigSpec.IntValue weird_biome_id;

  public static ForgeConfigSpec.IntValue     default_laser_distance;
  public static ForgeConfigSpec.BooleanValue lasers_emit_light;
  public static ForgeConfigSpec.IntValue     laser_light_level;
  public static ForgeConfigSpec.BooleanValue lasers_set_entities_on_fire;
  public static ForgeConfigSpec.BooleanValue laser_damage_depends_on_world_difficulty;
  
  public static final byte LASER_DAMAGE_PEACEFUL_DIFFICULTY = 2;
  public static final byte LASER_DAMAGE_EASY_DIFFICULTY     = 4; // 2 hearts
  public static final byte LASER_DAMAGE_NORMAL_DIFFICULTY   = 6; // 3 hearts
  public static final byte LASER_DAMAGE_HARD_DIFFICULTY     = 10; // 5 hearts
  
  public static final int BLACK_HOLE_PEACEFUL_DIFFICULTY_RADIUS = 30;
  public static final int BLACK_HOLE_EASY_DIFFICULTY_RADIUS     = 60;
  public static final int BLACK_HOLE_NORMAL_DIFFICULTY_RADIUS   = 90;
  public static final int BLACK_HOLE_HARD_DIFFICULTY_RADIUS     = 120;
  
  private static final int DEFAULT_BLACK_HOLE_RADIUS = BLACK_HOLE_NORMAL_DIFFICULTY_RADIUS;
  private static final double DEFAULT_BLACK_HOLE_SPEED = 0.2;
  
  public static ForgeConfigSpec.BooleanValue randomize_black_hole_radius;
  public static ForgeConfigSpec.BooleanValue black_hole_radius_depends_on_world_difficulty;
  public static ForgeConfigSpec.IntValue black_hole_radius;
  public static ForgeConfigSpec.IntValue minimum_black_hole_radius;
  public static ForgeConfigSpec.IntValue maximum_black_hole_radius;
  public static ForgeConfigSpec.BooleanValue alert_players_of_black_hole;
  public static ForgeConfigSpec.BooleanValue black_holes_erase_bedrock;
  public static ForgeConfigSpec.ConfigValue<List<String>> black_hole_dimension_blacklist;
  public static ForgeConfigSpec.DoubleValue black_hole_max_tick_time;

  private static final int DEFAULT_ENERGY_BRIDGE_DISTANCE = 250;
  public static ForgeConfigSpec.IntValue energy_bridge_max_distance;

  private static final int DEFAULT_MAX_REQUIRED_MATTER = 64_000;
  public static ForgeConfigSpec.IntValue max_matter;

  public static ForgeConfigSpec.BooleanValue teleport_to_unknown_dimension;

  public static ForgeConfigSpec.BooleanValue rings_have_particle_effects;

  public static ForgeConfigSpec.BooleanValue show_advanced_config;

  private static final Pair<Config, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(Config::new);
  public static final Config INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

  public Config(final ForgeConfigSpec.Builder builder){

    builder.push("Client");
    blend_working_item = builder.define("Blend Center Item in Guis", true);
    builder.pop();

    // IDs
    builder.push("IDs");
    unknown_dimension_id = builder.worldRestart().define("Unknown Dimension ID", -20189);
    // weird_biome_id       = get("Unknion Biome ID", IDS, 3072).getInt();
    builder.pop();

    // Laser Config
    builder.push("Lasers");
    default_laser_distance      = builder.defineInRange("Default Laser Distance", 30, 0, 1000);
    lasers_emit_light           = builder.comment("You can turn this off if you feel like Laser Beams are giving you too much lag.")
                                         .define("Lasers Beams emit light", true);
    laser_light_level           = builder.comment("The Light level that Laser Beams emit.")
                                         .defineInRange("Laser Light Amount", 15, 0, 15);
    lasers_set_entities_on_fire = builder.comment("If set to true, Living Entities that are inside Laser Beams will receive fire damage.")
                                         .define("Lasers Set Entities On Fire", true);
    laser_damage_depends_on_world_difficulty = builder.define("Laser Damage depends on World Difficulty", true);
    builder.pop();
    
    // Black Hole Config
    builder.push("Black Hole");
    randomize_black_hole_radius    = builder.comment(
      "If set to true, Black Holes will set their radius to a random integer between the Min and Max values.\n"+
      "If 'Black Hole Radius depends on World Difficulty' is set to true, then Black Holes will deviate from\n"+
      "the predefined value chosen for that difficulty.")
                                            .define("Randomize Black Hole Radius", false);

    black_hole_radius_depends_on_world_difficulty = builder.define("Black Hole Radius depends on World Difficulty", false);

    black_hole_radius              = builder.defineInRange("Black Hole Radius", DEFAULT_BLACK_HOLE_RADIUS, TileBlackHole.MIN_RADIUS, TileBlackHole.MAX_RADIUS);

    minimum_black_hole_radius      = builder.comment("This value is used if 'Randomize Black Hole Radius' is set to true.")
                                            .defineInRange("Minimum Black Hole Radius", 20, TileBlackHole.MIN_RADIUS, TileBlackHole.MAX_RADIUS);

    maximum_black_hole_radius      = builder.comment("This value is used if 'Randomize Black Hole Radius' is set to true.")
                                            .defineInRange("Maximum Black Hole Radius", DEFAULT_BLACK_HOLE_RADIUS, TileBlackHole.MIN_RADIUS, TileBlackHole.MAX_RADIUS);

    alert_players_of_black_hole    = builder.comment("If set to true, displays a chat message for all players in that world when a player sets down a Black Hole.")
                                            .define("Alert Players of Black Hole Occurrence", true);

    black_holes_erase_bedrock      = builder.comment("Warning: If used in Survival-Only worlds, you will not be able to replace the Bedrock.")
                                            .define("Black Holes erase Bedrock", false);

    black_hole_dimension_blacklist = builder.comment(
      "Specify a list of Dimension String IDs you don't want the Black Hole to destroy. Placing a Black Hole\n"+
      "in these dimensions will not do anything. By default, the Black Hole is allowed in all dimensions.")
                                            .define("Black Hole Dimension ID Blacklist", new ArrayList<>());

    black_hole_max_tick_time       = builder.comment(
      "The algorithm the Black Hole uses to erase the world is now spread across multiple ticks. A tick in\n"+
      "Minecraft occurs every 50 milliseconds. However, the amount of time it takes for your computer to\n"+
      "process the tick varies depending on your computer and all the stuff that occurs in a tick. This value\n"+
      "is a percentage of how much of a tick is allocated to the Black Hole algorithm. The algorithm is\n"+
      "self balancing, meaning it will slow down if it detects the algorithm is taking too long to process in\n"+
      "a single tick. One block per tick will always be deleted, no matter how low you specify this value.")
      .defineInRange("Black Hole Max Tick Time", DEFAULT_BLACK_HOLE_SPEED, 1.0 / TimeConstants.tick_time_in_nanoseconds, 1.0);
    builder.pop();

    builder.push("Energy Suspension Bridge");
    energy_bridge_max_distance = builder.comment(
      "This determines the maximum distance Energy Suspension Bridges can reach. Energy Suspension Bridges only\n"+
      "connect to other Energy Suspension Bridges if they are within range. Increasing this may increase load on\n"+
      "your computer's processor.").defineInRange("Maximum Distance", DEFAULT_ENERGY_BRIDGE_DISTANCE, 20, 500);
    builder.pop();

    builder.push("Matter Compressor");
    max_matter = builder.comment("How many items are required to create 1 Unimatter in the Matter Compressor?")
                        .defineInRange("Matter Required", DEFAULT_MAX_REQUIRED_MATTER, 1, 1_280_000);
    builder.pop();

    builder.push("Portal");
    teleport_to_unknown_dimension = builder.comment(
      "If this is disabled, Portals will not transport you to the Unknown Dimension.\n"+
      "Instead the Portal will just spawn a Void Crystal inside the Portal frame.")
                                        .define("Allow Players to Enter the Unknown Dimension", true);
    builder.pop();

    builder.push("Other");
      rings_have_particle_effects = builder.define("Rings Have Particle Effects", false);
    builder.pop();

    builder.push("Advanced");
    show_advanced_config = builder.comment(
      "Enabling this will grant you access to advanced configuration options in the Mod's Configuration screen.\n"+
      "Advanced configuration options such as those in the values.toml file allow you access to internal game values,\n"+
      "and adjusting them will vastly alter gameplay. They are only intended to be used for debug, testing, or\n"+
      "experimental purposes. In order to maintain a standard gameplay experience (the way the author intended)\n"+
      "we encourage you to leave these values at their defaults. (However, modpack authors may want to adjust these\n"+
      "values in order to create a balanced gameplay.)")
                                  .define("Show Advanced Config in Client Gui", false);
    builder.pop();
  }

}
