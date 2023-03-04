package addsynth.material.worldgen;

import addsynth.material.config.WorldgenConfig;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

/**
 *  @see OreFeatures
 */
public final class OreGenerator {

  public static final void register(BiomeCategory category, BiomeGenerationSettingsBuilder builder){
    // This is how it's done now apparently.
    if(category != Biome.BiomeCategory.NETHER && category != Biome.BiomeCategory.THEEND){

      if(WorldgenConfig.generate_ruby.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.RUBY_ORE_FEATURE);
      }
      if(WorldgenConfig.generate_topaz.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.TOPAZ_ORE_FEATURE);
      }
      if(WorldgenConfig.generate_citrine.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.CITRINE_ORE_FEATURE);
      }
      if(WorldgenConfig.generate_emerald.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.EMERALD_ORE_FEATURE);
      }
      if(WorldgenConfig.generate_sapphire.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.SAPPHIRE_ORE_FEATURE);
      }
      if(WorldgenConfig.generate_amethyst.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.AMETHYST_ORE_FEATURE);
      }
      
      if(WorldgenConfig.generate_tin.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.TIN_ORE_FEATURE);
      }
      if(WorldgenConfig.generate_aluminum.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.ALUMINUM_ORE_FEATURE);
      }
      if(WorldgenConfig.generate_silver.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.SILVER_ORE_FEATURE);
      }
      if(WorldgenConfig.generate_platinum.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.PLATINUM_ORE_FEATURE);
      }
      if(WorldgenConfig.generate_titanium.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.TITANIUM_ORE_FEATURE);
      }
      
      if(WorldgenConfig.generate_silicon.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.SILICON_ORE_FEATURE);
      }
      
      // Rose Quartz is the most rare. Make sure it is the last one generated
      if(WorldgenConfig.generate_rose_quartz.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.ROSE_QUARTZ_ORE_FEATURE);
      }
    }
  }

}
