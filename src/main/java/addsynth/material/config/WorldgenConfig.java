package addsynth.material.config;

import org.apache.commons.lang3.tuple.Pair;
import addsynth.core.util.world.WorldConstants;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 *  @see net.minecraft.data.worldgen.BiomeDefaultFeatures
 *  @see net.minecraft.data.worldgen.features.OreFeatures
 */
public final class WorldgenConfig { // TODO: Consider adjusting gen values for new world heights in Minecraft 1.18.

  private static final int DEFAULT_MIN_HEIGHT = 5;
  
  private static final int DEFAULT_GEM_MAX_HEIGHT = 40;
  private static final int DEFAULT_GEM_TRIES = 3;
  
  private static final int DEFAULT_ROSE_QUARTZ_MAX_HEIGHT = 128;
  
  // should be the same as Iron Ore
  private static final int DEFAULT_COMMON_METAL_MAX_HEIGHT = 128;
  private static final int DEFAULT_COMMON_METAL_TRIES = 10;
  private static final int DEFAULT_COMMON_METAL_ORE_SIZE = 9;
  // 8x  (0 - 120) every 15 levels

  private static final int DEFAULT_SILVER_ORE_MAX_HEIGHT = 64;
  private static final int DEFAULT_SILVER_ORE_TRIES = 3;
  private static final int DEFAULT_SILVER_ORE_SIZE = 9;
  // Gold: 2x (0 - 32) every 16 levels,     Silver: 4x (0 - 64) every 16 levels.
  
  private static final int DEFAULT_RARE_METAL_MAX_HEIGHT = 96;
  private static final int DEFAULT_RARE_METAL_TRIES = 2;
  private static final int DEFAULT_RARE_METAL_ORE_SIZE = 7;
  // Rare: 3x (0 - 96) every 32 levels.
  
  private static final int DEFAULT_SILICON_MAX_HEIGHT = 128;
  private static final int DEFAULT_SILICON_TRIES = 13;
  private static final int DEFAULT_SILICON_ORE_SIZE = 4; // Keep this at 4. Good number.
  /* December 2021 Silicon tests. All these use a Range of 5 to 128.
  Tested in Minecraft 1.16.5   World seed: -6884434917911476714
  Size: Tries: Count:  Density:       Notes:
    3      6    [265]    1.778    Definitely not enough! 1.4 Test 1
    3     12     530     2.356    Not enough! 1.4 Test 3 (final settings used for 1.4 release)
    3     40    1839     8.173
    3     50    2340    10.400
  Size: Tries: Count:  Density:       Notes: Linear Regression for Count: y=209.286*x-58
                                             Linear Regression for Density: y=0.930036*x-0.25675
    4      6    1185     5.267    Just barely enough. Need more for future use.
    4      7   [1408]    6.258
    4      8    1631     7.249    *Perfect-ish amount, but still need more for future use.
    4      9   [1832]    8.142    
    4     10    2033     9.035    
    4     11   [2241]    9.960    *versions 1.4.1 and 1.4.2 uses these values.
    4     12    2449    10.884
    4     13   [2663]  [11.384]    (Calculated using linear regression of existing data points.)
    4     14   [2872]  [12.764]   *(Calculated using linear regression of existing data points.) Now set to these values.
  Size: Tries: Count:  Density:       Notes:
    5      6    1846     8.204
    5      7   [2206]   [9.804]
    5      8    2566    11.404    Might be too much. (untested)
  Size: Tries: Count:  Density:       Notes:
    6      9    3518    15.636    Way too much! 1.4 Test 2
  */

  public static ForgeConfigSpec.BooleanValue generate_ruby;
  public static ForgeConfigSpec.IntValue ruby_min_height;
  public static ForgeConfigSpec.IntValue ruby_max_height;
  public static ForgeConfigSpec.IntValue ruby_spawn_tries;

  public static ForgeConfigSpec.BooleanValue generate_topaz;
  public static ForgeConfigSpec.IntValue topaz_min_height;
  public static ForgeConfigSpec.IntValue topaz_max_height;
  public static ForgeConfigSpec.IntValue topaz_spawn_tries;

  public static ForgeConfigSpec.BooleanValue generate_citrine;
  public static ForgeConfigSpec.IntValue citrine_min_height;
  public static ForgeConfigSpec.IntValue citrine_max_height;
  public static ForgeConfigSpec.IntValue citrine_spawn_tries;

  public static ForgeConfigSpec.BooleanValue generate_emerald;
  public static ForgeConfigSpec.IntValue emerald_min_height;
  public static ForgeConfigSpec.IntValue emerald_max_height;
  public static ForgeConfigSpec.IntValue emerald_spawn_tries;

  public static ForgeConfigSpec.BooleanValue generate_sapphire;
  public static ForgeConfigSpec.IntValue sapphire_min_height;
  public static ForgeConfigSpec.IntValue sapphire_max_height;
  public static ForgeConfigSpec.IntValue sapphire_spawn_tries;

  public static ForgeConfigSpec.BooleanValue generate_amethyst;
  public static ForgeConfigSpec.IntValue amethyst_min_height;
  public static ForgeConfigSpec.IntValue amethyst_max_height;
  public static ForgeConfigSpec.IntValue amethyst_spawn_tries;

  public static ForgeConfigSpec.BooleanValue generate_silicon;
  public static ForgeConfigSpec.IntValue silicon_min_height;
  public static ForgeConfigSpec.IntValue silicon_max_height;
  public static ForgeConfigSpec.IntValue silicon_spawn_tries;
  public static ForgeConfigSpec.IntValue silicon_ore_size;
  
  public static ForgeConfigSpec.BooleanValue generate_rose_quartz;
  public static ForgeConfigSpec.IntValue rose_quartz_min_height;
  public static ForgeConfigSpec.IntValue rose_quartz_max_height;
  public static ForgeConfigSpec.IntValue rose_quartz_spawn_tries;

  public static ForgeConfigSpec.BooleanValue generate_tin;
  public static ForgeConfigSpec.IntValue tin_min_height;
  public static ForgeConfigSpec.IntValue tin_max_height;
  public static ForgeConfigSpec.IntValue tin_spawn_tries;
  public static ForgeConfigSpec.IntValue tin_ore_size;

  public static ForgeConfigSpec.BooleanValue generate_aluminum;
  public static ForgeConfigSpec.IntValue aluminum_min_height;
  public static ForgeConfigSpec.IntValue aluminum_max_height;
  public static ForgeConfigSpec.IntValue aluminum_spawn_tries;
  public static ForgeConfigSpec.IntValue aluminum_ore_size;

  public static ForgeConfigSpec.BooleanValue generate_silver;
  public static ForgeConfigSpec.IntValue silver_min_height;
  public static ForgeConfigSpec.IntValue silver_max_height;
  public static ForgeConfigSpec.IntValue silver_spawn_tries;
  public static ForgeConfigSpec.IntValue silver_ore_size;

  public static ForgeConfigSpec.BooleanValue generate_platinum;
  public static ForgeConfigSpec.IntValue platinum_min_height;
  public static ForgeConfigSpec.IntValue platinum_max_height;
  public static ForgeConfigSpec.IntValue platinum_spawn_tries;
  public static ForgeConfigSpec.IntValue platinum_ore_size;

  public static ForgeConfigSpec.BooleanValue generate_titanium;
  public static ForgeConfigSpec.IntValue titanium_min_height;
  public static ForgeConfigSpec.IntValue titanium_max_height;
  public static ForgeConfigSpec.IntValue titanium_spawn_tries;
  public static ForgeConfigSpec.IntValue titanium_ore_size;

  private static final Pair<WorldgenConfig, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(WorldgenConfig::new);
  public static final WorldgenConfig INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

  public WorldgenConfig(final ForgeConfigSpec.Builder builder){
    builder.push("Ruby Ore");
    generate_ruby    = builder.define("generate", true);
    ruby_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    ruby_max_height  = worldgen(builder, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    ruby_spawn_tries = tries(builder, DEFAULT_GEM_TRIES);
    builder.pop();

    builder.push("Topaz Ore");
    generate_topaz    = builder.define("generate", true);
    topaz_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    topaz_max_height  = worldgen(builder, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    topaz_spawn_tries = tries(builder, DEFAULT_GEM_TRIES);
    builder.pop();

    builder.push("Citrine Ore");
    generate_citrine    = builder.define("generate", true);
    citrine_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    citrine_max_height  = worldgen(builder, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    citrine_spawn_tries = tries(builder, DEFAULT_GEM_TRIES);
    builder.pop();

    builder.push("Emerald Ore");
    generate_emerald    = builder.comment(
      "Set this to false to revert back to Vanilla generation, which only generates\n"+
      "Emerald Ore in the Extreme Hills biomes.").define("generate", true);
    emerald_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    emerald_max_height  = worldgen(builder, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    emerald_spawn_tries = tries(builder, DEFAULT_GEM_TRIES);
    builder.pop();

    builder.push("Sapphire Ore");
    generate_sapphire    = builder.define("generate", true);
    sapphire_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    sapphire_max_height  = worldgen(builder, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    sapphire_spawn_tries = tries(builder, DEFAULT_GEM_TRIES);
    builder.pop();

    builder.push("Amethyst Ore");
    generate_amethyst    = builder.define("generate", true);
    amethyst_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    amethyst_max_height  = worldgen(builder, "maximum height", DEFAULT_GEM_MAX_HEIGHT);
    amethyst_spawn_tries = tries(builder, DEFAULT_GEM_TRIES);
    builder.pop();
    
    builder.push("Tin Ore");
    generate_tin    = builder.define("generate", true);
    tin_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    tin_max_height  = worldgen(builder, "maximum height", DEFAULT_COMMON_METAL_MAX_HEIGHT);
    tin_spawn_tries = tries(builder, DEFAULT_COMMON_METAL_TRIES);
    tin_ore_size    = size(builder, DEFAULT_COMMON_METAL_ORE_SIZE);
    builder.pop();

    builder.push("Aluminum Ore");
    generate_aluminum    = builder.define("generate", true);
    aluminum_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    aluminum_max_height  = worldgen(builder, "maximum height", DEFAULT_COMMON_METAL_MAX_HEIGHT);
    aluminum_spawn_tries = tries(builder, DEFAULT_COMMON_METAL_TRIES);
    aluminum_ore_size    = size(builder, DEFAULT_COMMON_METAL_ORE_SIZE);
    builder.pop();

    builder.push("Silver Ore");
    generate_silver    = builder.define("generate", true);
    silver_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    silver_max_height  = worldgen(builder, "maximum height", DEFAULT_SILVER_ORE_MAX_HEIGHT);
    silver_spawn_tries = tries(builder, DEFAULT_SILVER_ORE_TRIES);
    silver_ore_size    = size(builder, DEFAULT_SILVER_ORE_SIZE);
    builder.pop();

    builder.push("Platinum Ore");
    generate_platinum    = builder.define("generate", true);
    platinum_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    platinum_max_height  = worldgen(builder, "maximum height", DEFAULT_RARE_METAL_MAX_HEIGHT);
    platinum_spawn_tries = tries(builder, DEFAULT_RARE_METAL_TRIES);
    platinum_ore_size    = size(builder, DEFAULT_RARE_METAL_ORE_SIZE);
    builder.pop();

    builder.push("Titanium Ore");
    generate_titanium    = builder.define("generate", true);
    titanium_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    titanium_max_height  = worldgen(builder, "maximum height", DEFAULT_RARE_METAL_MAX_HEIGHT);
    titanium_spawn_tries = tries(builder, 3);
    titanium_ore_size    = size(builder, DEFAULT_RARE_METAL_ORE_SIZE);
    builder.pop();

    builder.push("Silicon Ore");
    generate_silicon    = builder.define("generate", true);
    silicon_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    silicon_max_height  = worldgen(builder, "maximum height", DEFAULT_SILICON_MAX_HEIGHT);
    silicon_spawn_tries = tries(builder, DEFAULT_SILICON_TRIES);
    silicon_ore_size    = size(builder, DEFAULT_SILICON_ORE_SIZE);
    builder.pop();
    
    builder.push("Rose Quartz Ore");
    generate_rose_quartz    = builder.define("generate", true);
    rose_quartz_min_height  = worldgen(builder, "minimum height", DEFAULT_MIN_HEIGHT);
    rose_quartz_max_height  = worldgen(builder, "maximum height", DEFAULT_ROSE_QUARTZ_MAX_HEIGHT);
    rose_quartz_spawn_tries = tries(builder, 1);
    builder.pop();
  }

  private static final ForgeConfigSpec.IntValue worldgen(final ForgeConfigSpec.Builder builder, final String name, final int default_value){
    return builder.defineInRange(name, default_value, WorldConstants.bottom_level, WorldConstants.world_height - 1);
  }

  private static final ForgeConfigSpec.IntValue tries(final ForgeConfigSpec.Builder builder, final int default_value){
    return builder.defineInRange("tries", default_value, 1, 40); // same as Vanilla Minecraft custom world gen settings.
  }

  private static final ForgeConfigSpec.IntValue size(final ForgeConfigSpec.Builder builder, final int default_value){
    return builder.defineInRange("size", default_value, 1, 20); // vanilla custom world settings has this set as 50.
  }

}
