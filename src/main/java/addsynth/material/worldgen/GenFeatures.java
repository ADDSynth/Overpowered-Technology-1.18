package addsynth.material.worldgen;

import java.util.List;
import addsynth.material.Material;
import addsynth.material.config.WorldgenConfig;
import addsynth.material.types.OreMaterial;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
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

  // TODO: These should probably be created once and saved instead of BEING RECREATED WHEN GENERATING EVERY CHUNK!!! Downport this fix to previous versions!
  public static final Holder<PlacedFeature> RUBY_ORE_FEATURE = gen_single_ore(
    Material.RUBY,
    WorldgenConfig.ruby_spawn_tries.get(),
    WorldgenConfig.ruby_min_height.get(),
    WorldgenConfig.ruby_max_height.get()
  );

  public static final Holder<PlacedFeature> TOPAZ_ORE_FEATURE = gen_single_ore(
    Material.TOPAZ,
    WorldgenConfig.topaz_spawn_tries.get(),
    WorldgenConfig.topaz_min_height.get(),
    WorldgenConfig.topaz_max_height.get()
  );

  public static final Holder<PlacedFeature> CITRINE_ORE_FEATURE = gen_single_ore(
    Material.CITRINE,
    WorldgenConfig.citrine_spawn_tries.get(),
    WorldgenConfig.citrine_min_height.get(),
    WorldgenConfig.citrine_max_height.get()
  );

  public static final Holder<PlacedFeature> EMERALD_ORE_FEATURE = gen_single_ore(
    Material.EMERALD,
    WorldgenConfig.emerald_spawn_tries.get(),
    WorldgenConfig.emerald_min_height.get(),
    WorldgenConfig.emerald_max_height.get()
  );

  public static final Holder<PlacedFeature> SAPPHIRE_ORE_FEATURE = gen_single_ore(
    Material.SAPPHIRE,
    WorldgenConfig.sapphire_spawn_tries.get(),
    WorldgenConfig.sapphire_min_height.get(),
    WorldgenConfig.sapphire_max_height.get()
  );

  public static final Holder<PlacedFeature> AMETHYST_ORE_FEATURE = gen_single_ore(
    Material.AMETHYST,
    WorldgenConfig.amethyst_spawn_tries.get(),
    WorldgenConfig.amethyst_min_height.get(),
    WorldgenConfig.amethyst_max_height.get()
  );

  public static final Holder<PlacedFeature> TIN_ORE_FEATURE = gen_standard_ore(
    Material.TIN,
    WorldgenConfig.tin_ore_size.get(),
    WorldgenConfig.tin_spawn_tries.get(),
    WorldgenConfig.tin_min_height.get(),
    WorldgenConfig.tin_max_height.get()
  );

  public static final Holder<PlacedFeature> ALUMINUM_ORE_FEATURE = gen_standard_ore(
    Material.ALUMINUM,
    WorldgenConfig.aluminum_ore_size.get(),
    WorldgenConfig.aluminum_spawn_tries.get(),
    WorldgenConfig.aluminum_min_height.get(),
    WorldgenConfig.aluminum_max_height.get()
  );

  public static final Holder<PlacedFeature> SILVER_ORE_FEATURE = gen_standard_ore(
    Material.SILVER,
    WorldgenConfig.silver_ore_size.get(),
    WorldgenConfig.silver_spawn_tries.get(),
    WorldgenConfig.silver_min_height.get(),
    WorldgenConfig.silver_max_height.get()
  );

  public static final Holder<PlacedFeature> PLATINUM_ORE_FEATURE = gen_standard_ore(
    Material.PLATINUM,
    WorldgenConfig.platinum_ore_size.get(),
    WorldgenConfig.platinum_spawn_tries.get(),
    WorldgenConfig.platinum_min_height.get(),
    WorldgenConfig.platinum_max_height.get()
  );

  public static final Holder<PlacedFeature> TITANIUM_ORE_FEATURE = gen_standard_ore(
    Material.TITANIUM,
    WorldgenConfig.platinum_ore_size.get(),
    WorldgenConfig.platinum_spawn_tries.get(),
    WorldgenConfig.platinum_min_height.get(),
    WorldgenConfig.platinum_max_height.get()
  );

  public static final Holder<PlacedFeature> SILICON_ORE_FEATURE = gen_standard_ore(
    Material.SILICON,
    WorldgenConfig.silicon_ore_size.get(),
    WorldgenConfig.silicon_spawn_tries.get(),
    WorldgenConfig.silicon_min_height.get(),
    WorldgenConfig.silicon_max_height.get()
  );

  public static final Holder<PlacedFeature> ROSE_QUARTZ_ORE_FEATURE = gen_single_ore(
    Material.ROSE_QUARTZ,
    WorldgenConfig.rose_quartz_spawn_tries.get(),
    WorldgenConfig.rose_quartz_min_height.get(),
    WorldgenConfig.rose_quartz_max_height.get()
  );

  private static final List<OreConfiguration.TargetBlockState> getReplaceableBlockList(final Block ore){
    return List.of(
      OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ore.defaultBlockState()),
      OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ore.defaultBlockState()) // TODO: replace with Deepslate variant.
    );
  }

  private static final Holder<PlacedFeature> gen_single_ore(final OreMaterial material, int tries, int min_level, int max_level){
    // Configured Feature:
    final Holder<ConfiguredFeature<ReplaceBlockConfiguration, ?>> ore_configuration = FeatureUtils.register(material.ore.getRegistryName().getPath(), Feature.REPLACE_SINGLE_BLOCK, new ReplaceBlockConfiguration(getReplaceableBlockList(material.ore)));
    // Placement Modifiers:
    // TODO: Change uniform placement to triangle placement. Extend into the new lower depths below Y=0.
    final List<PlacementModifier> placement_modifiers = List.of(CountPlacement.of(tries), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(min_level), VerticalAnchor.aboveBottom(max_level)), BiomeFilter.biome());
    // Placement:
    return PlacementUtils.register(material.id_name+"_placement", ore_configuration, placement_modifiers);
  }

  private static final Holder<PlacedFeature> gen_standard_ore(final OreMaterial material, int size, int tries, int min_level, int max_level){
    // Configured Feature:
    final Holder<ConfiguredFeature<OreConfiguration, ?>> ore_configuration = FeatureUtils.register(material.ore.getRegistryName().getPath(), Feature.ORE, new OreConfiguration(getReplaceableBlockList(material.ore), size));
    // Placement Modifiers:
    final List<PlacementModifier> placement_modifiers = List.of(CountPlacement.of(tries), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(min_level), VerticalAnchor.aboveBottom(max_level)), BiomeFilter.biome());
    // Placement:
    return PlacementUtils.register(material.id_name+"_placement", ore_configuration, placement_modifiers);
  }

}
