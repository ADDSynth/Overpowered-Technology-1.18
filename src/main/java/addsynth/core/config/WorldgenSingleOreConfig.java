package addsynth.core.config;

import addsynth.core.util.world.WorldConstants;
import net.minecraftforge.common.ForgeConfigSpec;

public class WorldgenSingleOreConfig {

  private final String section;
  private final int DEFAULT_MIN_HEIGHT;
  private final int DEFAULT_MAX_HEIGHT;
  private final int DEFAULT_SPAWN_TRIES;
  public ForgeConfigSpec.BooleanValue generate;
  public ForgeConfigSpec.IntValue min_height;
  public ForgeConfigSpec.IntValue max_height;
  public ForgeConfigSpec.IntValue tries;

  public WorldgenSingleOreConfig(final String section_name, final OreGenerationSettings generation_settings){
    this.section = section_name;
    DEFAULT_MIN_HEIGHT = generation_settings.min_height;
    DEFAULT_MAX_HEIGHT = generation_settings.max_height;
    DEFAULT_SPAWN_TRIES = generation_settings.spawn_tries;
  }

  public WorldgenSingleOreConfig(final String section_name, final int min_height, final int max_height, final int spawn_tries){
    this.section = section_name;
    DEFAULT_MIN_HEIGHT = min_height;
    DEFAULT_MAX_HEIGHT = max_height;
    DEFAULT_SPAWN_TRIES = spawn_tries;
  }

  @SuppressWarnings("deprecation")
  public final void build(final ForgeConfigSpec.Builder builder){
    builder.push(section);
    generate   = builder.define("generate", true);
    min_height = builder.defineInRange("minimum height", DEFAULT_MIN_HEIGHT, WorldConstants.bottom_level, WorldConstants.world_height - 1);
    max_height = builder.defineInRange("maximum height", DEFAULT_MAX_HEIGHT, WorldConstants.bottom_level, WorldConstants.world_height - 1);
    tries      = builder.defineInRange("tries", DEFAULT_SPAWN_TRIES, 1, 40); // same as Vanilla Minecraft custom world gen settings.
    builder.pop();
  }

}
