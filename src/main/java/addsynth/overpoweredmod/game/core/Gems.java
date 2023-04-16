package addsynth.overpoweredmod.game.core;

import addsynth.material.Material;
import addsynth.material.types.gem.Gem;
import net.minecraft.world.item.ItemStack;

public final class Gems {

  private static final Gem[] index = {
    Material.RUBY,    Material.TOPAZ,    Material.CITRINE,  Material.EMERALD,
    Material.DIAMOND, Material.SAPPHIRE, Material.AMETHYST, Material.QUARTZ
  };

  public static final ItemStack getItemStack(final int gem_id){
    return new ItemStack(index[gem_id].getGem());
  }

  public static final ItemStack getShard(final int gem_id){
    return new ItemStack(index[gem_id].getGemShard());
  }

}
