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

      if(WorldgenConfig.ruby.generate.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.RUBY_ORE_FEATURE);
      }
      if(WorldgenConfig.topaz.generate.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.TOPAZ_ORE_FEATURE);
      }
      if(WorldgenConfig.citrine.generate.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.CITRINE_ORE_FEATURE);
      }
      if(WorldgenConfig.emerald.generate.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.EMERALD_ORE_FEATURE);
      }
      if(WorldgenConfig.sapphire.generate.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.SAPPHIRE_ORE_FEATURE);
      }
      if(WorldgenConfig.amethyst.generate.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.AMETHYST_ORE_FEATURE);
      }
      
      if(WorldgenConfig.tin.generate.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.TIN_ORE_FEATURE);
      }
      if(WorldgenConfig.aluminum.generate.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.ALUMINUM_ORE_FEATURE);
      }
      if(WorldgenConfig.silver.generate.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.SILVER_ORE_FEATURE);
      }
      if(WorldgenConfig.platinum.generate.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.PLATINUM_ORE_FEATURE);
      }
      if(WorldgenConfig.titanium.generate.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.TITANIUM_ORE_FEATURE);
      }
      
      if(WorldgenConfig.silicon.generate.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.SILICON_ORE_FEATURE);
      }
      
      // Rose Quartz is the most rare. Make sure it is the last one generated
      if(WorldgenConfig.rose_quartz.generate.get()){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GenFeatures.ROSE_QUARTZ_ORE_FEATURE);
      }
    }
  }

}
