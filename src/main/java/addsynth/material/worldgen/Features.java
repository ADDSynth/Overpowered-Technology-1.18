package addsynth.material.worldgen;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.ReplaceBlockFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;

public final class Features { // UNUSED, here if I ever need it. But I need to register it in the CoreRegisters class.

  public static final Feature<ReplaceBlockConfiguration> GEM_ORE = create("gem_ore", new ReplaceBlockFeature(ReplaceBlockConfiguration.CODEC));
  
  private static final <T extends FeatureConfiguration, C extends Feature<T>> Feature<T> create(String id, C feature_config){
    final C feature = feature_config;
    feature.setRegistryName(id);
    return feature;
  }

}
