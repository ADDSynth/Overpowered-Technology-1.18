package addsynth.overpoweredmod.game.core;

import addsynth.material.Material;
import addsynth.material.types.gem.Gem;
import addsynth.material.util.MaterialTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

public final class Gems {

  private static final Gem[] index = {
    Material.RUBY,    Material.TOPAZ,    Material.CITRINE,  Material.EMERALD,
    Material.DIAMOND, Material.SAPPHIRE, Material.AMETHYST, Material.QUARTZ
  };

  public static final ItemStack getGem(final int gem_id){
    return new ItemStack(index[gem_id].getGem());
  }

  public static final ItemStack getShard(final int gem_id){
    return new ItemStack(index[gem_id].getGemShard());
  }

  public static final int getGemIndex(final ItemStack gem){
    return getGemIndex(gem.getItem());
  }
  
  public static final int getGemIndex(final Item gem){
    final ITagManager<Item> tag_manager = ForgeRegistries.ITEMS.tags();
    if(tag_manager != null){
      if(tag_manager.getTag(MaterialTag.RUBY.GEMS).contains(gem)    ){return 0;}
      if(tag_manager.getTag(MaterialTag.TOPAZ.GEMS).contains(gem)   ){return 1;}
      if(tag_manager.getTag(MaterialTag.CITRINE.GEMS).contains(gem) ){return 2;}
      if(tag_manager.getTag(Tags.Items.GEMS_EMERALD).contains(gem)  ){return 3;}
      if(tag_manager.getTag(Tags.Items.GEMS_DIAMOND).contains(gem)  ){return 4;}
      if(tag_manager.getTag(MaterialTag.SAPPHIRE.GEMS).contains(gem)){return 5;}
      if(tag_manager.getTag(Tags.Items.GEMS_AMETHYST).contains(gem) ){return 6;}
      if(tag_manager.getTag(Tags.Items.GEMS_QUARTZ).contains(gem)   ){return 7;}
    }
    return -1;
  }

}
