package addsynth.core.util.game;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

public final class Game {

  /** @see Stats#makeCustomStat(String, StatFormatter) */
  public static final void registerCustomStat(final ResourceLocation stat){
    Registry.register(Registry.CUSTOM_STAT, stat.getPath(), stat);
    Stats.CUSTOM.get(stat, StatFormatter.DEFAULT);
  }

}
