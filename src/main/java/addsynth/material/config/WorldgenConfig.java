package addsynth.material.config;

import org.apache.commons.lang3.tuple.Pair;
import addsynth.core.config.OreGenerationSettings;
import addsynth.core.config.WorldgenOreConfig;
import addsynth.core.config.WorldgenSingleOreConfig;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 *  @see net.minecraft.data.worldgen.BiomeDefaultFeatures
 *  @see net.minecraft.data.worldgen.features.OreFeatures
 */
public final class WorldgenConfig { // TODO: Consider adjusting gen values for new world heights in Minecraft 1.18.

  private static final int MIN_HEIGHT = 5;
  
  private static final OreGenerationSettings gem_settings = new OreGenerationSettings(MIN_HEIGHT,  40, 3);
  private static final OreGenerationSettings rose_quartz_settings  = new OreGenerationSettings(MIN_HEIGHT, 128, 1);
  
  // should be the same as Iron Ore
  private static final OreGenerationSettings common_metal = new OreGenerationSettings(MIN_HEIGHT, 128, 10, 9);
  // 8x  (0 - 120) every 15 levels

  private static final OreGenerationSettings silver_settings = new OreGenerationSettings(MIN_HEIGHT, 64, 3, 9);
  // Gold: 2x (0 - 32) every 16 levels,     Silver: 4x (0 - 64) every 16 levels.
  
  private static final OreGenerationSettings rare_metal = new OreGenerationSettings(MIN_HEIGHT, 96, 2, 7);
  // Rare: 3x (0 - 96) every 32 levels.
  
  // Keep silicon ore size at 4. Good number.
  private static final OreGenerationSettings silicon_settings = new OreGenerationSettings(MIN_HEIGHT, 128, 13, 4);
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

  public static final WorldgenSingleOreConfig ruby     = new WorldgenSingleOreConfig(    "Ruby Ore", gem_settings);
  public static final WorldgenSingleOreConfig topaz    = new WorldgenSingleOreConfig(   "Topaz Ore", gem_settings);
  public static final WorldgenSingleOreConfig citrine  = new WorldgenSingleOreConfig( "Citrine Ore", gem_settings);
  public static final WorldgenSingleOreConfig emerald  = new WorldgenSingleOreConfig( "Emerald Ore", gem_settings);
  public static final WorldgenSingleOreConfig sapphire = new WorldgenSingleOreConfig("Sapphire Ore", gem_settings);
  public static final WorldgenSingleOreConfig amethyst = new WorldgenSingleOreConfig("Amethyst Ore", gem_settings);

  public static final WorldgenOreConfig tin       = new WorldgenOreConfig(     "Tin Ore", common_metal);
  public static final WorldgenOreConfig aluminum  = new WorldgenOreConfig("Aluminum Ore", common_metal);
  public static final WorldgenOreConfig silver    = new WorldgenOreConfig(  "Silver Ore", silver_settings);
  public static final WorldgenOreConfig platinum  = new WorldgenOreConfig("Platinum Ore", rare_metal);
  public static final WorldgenOreConfig titanium  = new WorldgenOreConfig("Titanium Ore", rare_metal);

  public static final WorldgenOreConfig silicon = new WorldgenOreConfig("Silicon Ore", silicon_settings);
  public static final WorldgenSingleOreConfig rose_quartz = new WorldgenSingleOreConfig("Rose Quartz Ore", rose_quartz_settings);

  private static final Pair<WorldgenConfig, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(WorldgenConfig::new);
  public static final WorldgenConfig INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

  public WorldgenConfig(final ForgeConfigSpec.Builder builder){
    ruby.build(builder);
    topaz.build(builder);
    citrine.build(builder);
    emerald.build(builder);
    sapphire.build(builder);
    amethyst.build(builder);

    tin.build(builder);
    aluminum.build(builder);
    silver.build(builder);
    platinum.build(builder);
    titanium.build(builder);

    silicon.build(builder);
    rose_quartz.build(builder);
  }

}
