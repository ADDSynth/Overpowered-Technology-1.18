package addsynth.material.worldgen;

import java.util.List;
import addsynth.core.util.world.WorldUtil;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.Material;
import addsynth.material.config.WorldgenConfig;
import addsynth.material.types.OreMaterial;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
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
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

/**
 *  @see OreFeatures
 */
public final class OreGenerator {

  
  public static final void register(BiomeCategory category, BiomeGenerationSettingsBuilder generation_settings){
    // This is how it's done now apparently.
    if(category != Biome.BiomeCategory.NETHER && category != Biome.BiomeCategory.THEEND){

      if(WorldgenConfig.generate_ruby.get()){
        generate_single_ore(generation_settings, Material.RUBY, WorldgenConfig.ruby_spawn_tries.get(),
                         WorldgenConfig.ruby_min_height.get(), WorldgenConfig.ruby_max_height.get());
      }
      if(WorldgenConfig.generate_topaz.get()){
        generate_single_ore(generation_settings, Material.TOPAZ, WorldgenConfig.topaz_spawn_tries.get(),
                         WorldgenConfig.topaz_min_height.get(), WorldgenConfig.topaz_max_height.get());
      }
      if(WorldgenConfig.generate_citrine.get()){
        generate_single_ore(generation_settings, Material.CITRINE, WorldgenConfig.citrine_spawn_tries.get(),
                         WorldgenConfig.citrine_min_height.get(), WorldgenConfig.citrine_max_height.get());
      }
      if(WorldgenConfig.generate_emerald.get()){
        generate_single_ore(generation_settings, Material.EMERALD, WorldgenConfig.emerald_spawn_tries.get(),
                         WorldgenConfig.emerald_min_height.get(), WorldgenConfig.emerald_max_height.get());
      }
      if(WorldgenConfig.generate_sapphire.get()){
        generate_single_ore(generation_settings, Material.SAPPHIRE, WorldgenConfig.sapphire_spawn_tries.get(),
                         WorldgenConfig.sapphire_min_height.get(), WorldgenConfig.sapphire_max_height.get());
      }
      if(WorldgenConfig.generate_amethyst.get()){
        generate_single_ore(generation_settings, Material.AMETHYST, WorldgenConfig.amethyst_spawn_tries.get(),
                         WorldgenConfig.amethyst_min_height.get(), WorldgenConfig.amethyst_max_height.get());
      }
      
      if(WorldgenConfig.generate_tin.get()){
        generate_ore(generation_settings, Material.TIN, WorldgenConfig.tin_ore_size.get(), WorldgenConfig.tin_spawn_tries.get(),
                           WorldgenConfig.tin_min_height.get(), WorldgenConfig.tin_max_height.get());
      }
      if(WorldgenConfig.generate_aluminum.get()){
        generate_ore(generation_settings, Material.ALUMINUM, WorldgenConfig.aluminum_ore_size.get(), WorldgenConfig.aluminum_spawn_tries.get(),
                           WorldgenConfig.aluminum_min_height.get(), WorldgenConfig.aluminum_max_height.get());
      }
      if(WorldgenConfig.generate_silver.get()){
        generate_ore(generation_settings, Material.SILVER, WorldgenConfig.silver_ore_size.get(), WorldgenConfig.silver_spawn_tries.get(),
                           WorldgenConfig.silver_min_height.get(), WorldgenConfig.silver_max_height.get());
      }
      if(WorldgenConfig.generate_platinum.get()){
        generate_ore(generation_settings, Material.PLATINUM, WorldgenConfig.platinum_ore_size.get(), WorldgenConfig.platinum_spawn_tries.get(),
                           WorldgenConfig.platinum_min_height.get(), WorldgenConfig.platinum_max_height.get());
      }
      if(WorldgenConfig.generate_titanium.get()){
        generate_ore(generation_settings, Material.TITANIUM, WorldgenConfig.titanium_ore_size.get(), WorldgenConfig.titanium_spawn_tries.get(),
                           WorldgenConfig.titanium_min_height.get(), WorldgenConfig.titanium_max_height.get());
      }
      
      if(WorldgenConfig.generate_silicon.get()){
        generate_ore(generation_settings, Material.SILICON, WorldgenConfig.silicon_ore_size.get(), WorldgenConfig.silicon_spawn_tries.get(),
                           WorldgenConfig.silicon_min_height.get(), WorldgenConfig.silicon_max_height.get());
      }
      
      // Rose Quartz is the most rare. Make sure it is the last one generated
      if(WorldgenConfig.generate_rose_quartz.get()){
        generate_single_ore(generation_settings, Material.ROSE_QUARTZ, WorldgenConfig.rose_quartz_spawn_tries.get(),
                         WorldgenConfig.rose_quartz_min_height.get(), WorldgenConfig.rose_quartz_max_height.get());
      }
    }
  }

  private static final boolean valid_min_max_values(final String name, final int min, final int max){
    final boolean pass = WorldUtil.validYLevel(min) && WorldUtil.validYLevel(max);
    if(pass == false){ // TODO: Only warn, and continue anyway
      ADDSynthMaterials.log.error("Invalid Worldgen Min/Max values: Min: "+min+", Max: "+max+", while generating Ores for "+name+".");
    }
    return pass;
  }

  private static final List<OreConfiguration.TargetBlockState> getReplaceableBlockList(final Block ore){
    return List.of(
      OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ore.defaultBlockState()),
      OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ore.defaultBlockState()) // TODO: replace with Deepslate variant.
    );
  }

  // New in 2023, Thanks 100% to Nico Kaupenjoe: https://www.youtube.com/watch?v=vuLKGC0_9p4
  // https://github.com/Tutorials-By-Kaupenjoe/Forge-Tutorial-1.18.1/tree/34-oreGeneration/src/main/java/net/kaupenjoe/tutorialmod/world
  private static final void generate_single_ore(BiomeGenerationSettings.Builder builder, OreMaterial material, int tries, int min, int max){
    if(valid_min_max_values(material.name, min, max)){
      final int min_depth = Math.min(min, max);
      final int max_depth = Math.max(min, max);
      // TODO: I think all these gen feature types should finally be in their own class, with their own defined names
      // Configured Feature:
      final Holder<ConfiguredFeature<ReplaceBlockConfiguration, ?>> ore_configuration = FeatureUtils.register(material.ore.getRegistryName().getPath(), Feature.REPLACE_SINGLE_BLOCK, new ReplaceBlockConfiguration(getReplaceableBlockList(material.ore)));
      // Placement Modifiers:
      // TODO: Change uniform placement to triangle placement. Extend into the new lower depths below Y=0.
      final List<PlacementModifier> placement_modifiers = List.of(CountPlacement.of(tries), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(min_depth), VerticalAnchor.aboveBottom(max_depth)), BiomeFilter.biome());
      // Placement:
      final Holder<PlacedFeature> placed_feature = PlacementUtils.register(material.id_name+"_placement", ore_configuration, placement_modifiers);
      
      builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, placed_feature);
      /* DELETE
      final ConfiguredFeature   configured_feature = new ConfiguredFeature<>(Feature.EMERALD_ORE, new ReplaceBlockConfig(Blocks.STONE.defaultBlockState(), material.ore.defaultBlockState()));
      final ConfiguredPlacement configured_placement = new ConfiguredPlacement<>(Placement.COUNT_RANGE, new CountRangeConfig(tries, min_depth, min_depth, max_depth));
      final DecoratedFeatureConfig decorated_feature = new DecoratedFeatureConfig(configured_feature, configured_placement);
      biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new ConfiguredFeature<>(Feature.DECORATED, decorated_feature));
      */
    }
  }

  private static final void generate_ore(BiomeGenerationSettings.Builder builder, OreMaterial material, int size, int tries, int min, int max){
    if(valid_min_max_values(material.name, min, max)){
      final int min_depth = Math.min(min, max);
      final int max_depth = Math.max(min, max);
      // Configured Feature:
      final Holder<ConfiguredFeature<OreConfiguration, ?>> ore_configuration = FeatureUtils.register(material.ore.getRegistryName().getPath(), Feature.ORE, new OreConfiguration(getReplaceableBlockList(material.ore), size));
      // Placement Modifiers:
      final List<PlacementModifier> placement_modifiers = List.of(CountPlacement.of(tries), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(min_depth), VerticalAnchor.aboveBottom(max_depth)), BiomeFilter.biome());
      // Placement:
      final Holder<PlacedFeature> placed_feature = PlacementUtils.register(material.id_name+"_placement", ore_configuration, placement_modifiers);
      
      builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, placed_feature);
      /*
      final ConfiguredFeature   configured_feature = new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, material.ore.defaultBlockState(), size));
      final ConfiguredPlacement configured_placement = new ConfiguredPlacement<>(Placement.COUNT_RANGE, new CountRangeConfig(tries, min_depth, min_depth, max_depth));
      final DecoratedFeatureConfig decorated_feature = new DecoratedFeatureConfig(configured_feature, configured_placement);
      biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new ConfiguredFeature<>(Feature.DECORATED, decorated_feature));
      */
    }
  }

}
