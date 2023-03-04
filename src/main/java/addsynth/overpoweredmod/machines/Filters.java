package addsynth.overpoweredmod.machines;

import addsynth.material.MaterialTag;
import addsynth.material.MaterialsUtil;
import addsynth.overpoweredmod.machines.gem_converter.GemConverterRecipe;
import addsynth.overpoweredmod.machines.magic_infuser.recipes.MagicInfuserRecipes;
import net.minecraft.world.item.Item;

public final class Filters {

  // In Minecraft versions 1.14+, We now use the Tag system, which is also part of the Data Pack system.
  // Machine filters need to be regenerated whenever Tags are reloaded, and possibly when Recipes are
  // reloaded as well. These events occur when joining worlds, and when players do the Reload Resource
  // Packs command. But what about on a dedicated server? Both server and client sides use these filters.
  // We need to ensure these filters are generated on both the client and server side, otherwise there
  // will be a desync issue, where one side accepts items into the machine while the other does not.

  // TODO: When we do the WorkSystem upgrade, have TileEntities construct their own item filters.
  //       Keep the variable private, and have the get() method first check if it is null and if it is,
  //       then construct it first. Always keep the constructed filter as a cached variable.
  // PRIORITY: Update Machine filters. Have machine override a getFilter() method, only if necessary.
  public static Item[] gem_converter;

  public static Item[] portal_frame;

  public static Item[] magic_infuser;

  public static final void regenerate_machine_filters(){
  
    gem_converter = MaterialsUtil.getFilter(
      MaterialTag.RUBY.GEMS,     MaterialTag.TOPAZ.GEMS,
      MaterialTag.CITRINE.GEMS,  MaterialTag.EMERALD.GEMS,
      MaterialTag.DIAMOND.GEMS,  MaterialTag.SAPPHIRE.GEMS,
      MaterialTag.AMETHYST.GEMS, MaterialTag.QUARTZ.GEMS
    );
    
    portal_frame = MaterialsUtil.getFilter(
      MaterialTag.RUBY.BLOCKS,     MaterialTag.TOPAZ.BLOCKS,
      MaterialTag.CITRINE.BLOCKS,  MaterialTag.EMERALD.BLOCKS,
      MaterialTag.DIAMOND.BLOCKS,  MaterialTag.SAPPHIRE.BLOCKS,
      MaterialTag.AMETHYST.BLOCKS, MaterialTag.QUARTZ.BLOCKS
    );
    
    magic_infuser = MagicInfuserRecipes.getFilter();
    
    GemConverterRecipe.generate_recipes();
  }

}
