package addsynth.core.config;

public class OreGenerationSettings {

  public final int min_height;
  public final int max_height;
  public final int spawn_tries;
  public final int ore_size;
  
  public OreGenerationSettings(final int min_height, final int max_height, final int tries){
    this.min_height = min_height;
    this.max_height = max_height;
    this.spawn_tries = tries;
    this.ore_size = 0;
  }
  
  public OreGenerationSettings(final int min_height, final int max_height, final int tries, final int ore_size){
    this.min_height = min_height;
    this.max_height = max_height;
    this.spawn_tries = tries;
    this.ore_size = ore_size;
  }

}
