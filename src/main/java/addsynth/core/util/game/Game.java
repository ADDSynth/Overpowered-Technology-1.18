package addsynth.core.util.game;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

public final class Game {

  public static final SoundEvent newSound(final String mod_id, final String name){
    SoundEvent sound = new SoundEvent(new ResourceLocation(mod_id,name));
    sound.setRegistryName(name);
    return sound;
  }

  /** @see Stats#makeCustomStat(String, StatFormatter) */
  public static final void registerCustomStat(final ResourceLocation stat){
    Registry.register(Registry.CUSTOM_STAT, stat.getPath(), stat);
    Stats.CUSTOM.get(stat, StatFormatter.DEFAULT);
  }

}
