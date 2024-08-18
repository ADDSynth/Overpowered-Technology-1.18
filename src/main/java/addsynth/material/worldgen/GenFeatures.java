package addsynth.material.worldgen;

import java.util.List;
import addsynth.core.config.WorldgenOreConfig;
import addsynth.core.config.WorldgenSingleOreConfig;
import addsynth.material.Material;
import addsynth.material.config.WorldgenConfig;
import addsynth.material.reference.MaterialBlocks;
import addsynth.material.types.*;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

// New in 2023, Thanks 100% to Nico Kaupenjoe: https://www.youtube.com/watch?v=vuLKGC0_9p4
// https://github.com/Tutorials-By-Kaupenjoe/Forge-Tutorial-1.18.1/tree/34-oreGeneration/src/main/java/net/kaupenjoe/tutorialmod/world
public final class GenFeatures {

  public static final Holder<PlacedFeature>     RUBY_ORE_FEATURE = gen_single_ore(Material.RUBY,     WorldgenConfig.ruby);
  public static final Holder<PlacedFeature>    TOPAZ_ORE_FEATURE = gen_single_ore(Material.TOPAZ,    WorldgenConfig.topaz);
  public static final Holder<PlacedFeature>  CITRINE_ORE_FEATURE = gen_single_ore(Material.CITRINE,  WorldgenConfig.citrine);
  public static final Holder<PlacedFeature>  EMERALD_ORE_FEATURE = gen_single_ore(Material.EMERALD,  WorldgenConfig.emerald);
  public static final Holder<PlacedFeature> SAPPHIRE_ORE_FEATURE = gen_single_ore(Material.SAPPHIRE, WorldgenConfig.sapphire);
  public static final Holder<PlacedFeature> AMETHYST_ORE_FEATURE = gen_single_ore("amethyst", MaterialBlocks.amethyst_ore.get(), WorldgenConfig.amethyst);

  public static final Holder<PlacedFeature>      TIN_ORE_FEATURE = gen_standard_ore(Material.TIN,      WorldgenConfig.tin);
  public static final Holder<PlacedFeature> ALUMINUM_ORE_FEATURE = gen_standard_ore(Material.ALUMINUM, WorldgenConfig.aluminum);
  public static final Holder<PlacedFeature>   SILVER_ORE_FEATURE = gen_standard_ore(Material.SILVER,   WorldgenConfig.silver);
  public static final Holder<PlacedFeature> PLATINUM_ORE_FEATURE = gen_standard_ore(Material.PLATINUM, WorldgenConfig.platinum);
  public static final Holder<PlacedFeature> TITANIUM_ORE_FEATURE = gen_standard_ore(Material.TITANIUM, WorldgenConfig.titanium);

  public static final Holder<PlacedFeature>     SILICON_ORE_FEATURE = gen_standard_ore(Material.SILICON, WorldgenConfig.silicon);
  public static final Holder<PlacedFeature> ROSE_QUARTZ_ORE_FEATURE = gen_single_ore(Material.ROSE_QUARTZ, WorldgenConfig.rose_quartz);

  private static final List<OreConfiguration.TargetBlockState> getReplaceableBlockList(final Block ore){
    return List.of(
      OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ore.defaultBlockState()),
      OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ore.defaultBlockState()) // TODO: replace with Deepslate variant.
    );
  }

  private static final <O extends AbstractMaterial & OreMaterial> Holder<PlacedFeature> gen_single_ore(final O material, final WorldgenSingleOreConfig ore_config){
    return gen_single_ore(material.name, material.getOre(), ore_config);
  }

  private static final Holder<PlacedFeature> gen_single_ore(final String name, final Block ore, final WorldgenSingleOreConfig ore_config){
    // Configured Feature:
    final Holder<ConfiguredFeature<ReplaceBlockConfiguration, ?>> ore_configuration = FeatureUtils.register(ore.getRegistryName().getPath(), Feature.REPLACE_SINGLE_BLOCK, new ReplaceBlockConfiguration(getReplaceableBlockList(ore)));
    // Placement Modifiers:
    // TODO: Change uniform placement to triangle placement. Extend into the new lower depths below Y=0.
    final HeightRangePlacement height_range_placement = HeightRangePlacement.uniform(VerticalAnchor.absolute(ore_config.min_height.get()), VerticalAnchor.absolute(ore_config.max_height.get()));
    final List<PlacementModifier> placement_modifiers = List.of(CountPlacement.of(ore_config.tries.get()), InSquarePlacement.spread(), height_range_placement, BiomeFilter.biome());
    // Placement:
    return PlacementUtils.register(name+"_placement", ore_configuration, placement_modifiers);
  }

  private static final <O extends AbstractMaterial & OreMaterial> Holder<PlacedFeature> gen_standard_ore(final O material, final WorldgenOreConfig ore_config){
    // Configured Feature:
    final Holder<ConfiguredFeature<OreConfiguration, ?>> ore_configuration = FeatureUtils.register(material.getOre().getRegistryName().getPath(), Feature.ORE, new OreConfiguration(getReplaceableBlockList(material.getOre()), ore_config.ore_size.get()));
    // Placement Modifiers:
    final HeightRangePlacement height_range_placement = HeightRangePlacement.uniform(VerticalAnchor.absolute(ore_config.min_height.get()), VerticalAnchor.absolute(ore_config.max_height.get()));
    final List<PlacementModifier> placement_modifiers = List.of(CountPlacement.of(ore_config.tries.get()), InSquarePlacement.spread(), height_range_placement, BiomeFilter.biome());
    // Placement:
    return PlacementUtils.register(material.name+"_placement", ore_configuration, placement_modifiers);
  }

}
