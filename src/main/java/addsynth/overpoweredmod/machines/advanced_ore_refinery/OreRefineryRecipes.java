package addsynth.overpoweredmod.machines.advanced_ore_refinery;

import java.util.ArrayList;
import addsynth.core.game.item.ItemUtil;
import addsynth.core.recipe.FurnaceRecipes;
import addsynth.core.recipe.RecipeUtil;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.tags.OverpoweredItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

public final class OreRefineryRecipes {

  private static final int output_multiplier = 2;
  private static Item[] valid_ores = new Item[0];
  public static final ArrayList<OreRefineryRecipe> recipes = new ArrayList<>(200);

  // https://github.com/skyboy/MineFactoryReloaded/blob/master/src/main/java/powercrystals/minefactoryreloaded/modhelpers/vanilla/Minecraft.java
  
  public static final void register(){
    RecipeUtil.addResponder(OreRefineryRecipes::rebuild_recipes);
    // Still need to rebuild recipes on Resource reload for JEI.
  }
  
  /**
   * <p>This will only add an Ore recipe to the Advanced Ore Refinery if, other mods have registered their
   *    ore with the "ores" tag, and it has a Furnace Recipe.
   */
  private static final void rebuild_recipes(final RecipeManager recipe_manager){
    recipes.clear();
    try{
      FurnaceRecipes.INSTANCE.rebuild(recipe_manager); // force rebuild Furnace recipes since we depend on them.
      final ArrayList<Item> list = new ArrayList<Item>(100);
      ItemStack result_check;
      final ITag<Item> ores = ForgeRegistries.ITEMS.tags().getTag(OverpoweredItemTags.advanced_ore_refinery);
      for(final Item item : ores){
        if(FurnaceRecipes.isFurnaceIngredient(item)){
          result_check = FurnaceRecipes.getResult(item);
          if(result_check.isEmpty() == false){
            list.add(item);
            final ItemStack result = result_check.copy();
            result.setCount(result.getCount()*output_multiplier);
            recipes.add(new OreRefineryRecipe(item, result));
          }
        }
      }
      
      valid_ores = list.toArray(new Item[list.size()]);
      OverpoweredTechnology.log.info("Advanced Ore Refinery recipes were rebuilt.");
    }
    catch(Exception e){
      OverpoweredTechnology.log.error("An exception occured in OreRefineryRecipes.rebuild_recipes().", e);
      valid_ores = new Item[0];
    }
  }

  public static final boolean filter(final ItemStack stack){
    final Item stack_item = stack.getItem();
    for(final Item item : valid_ores){
      if(item == stack_item){
        return true;
      }
    }
    return false;
  }

  @Deprecated // REMOVE in 2026
  public static final boolean matches(final Item item){
    for(Item check_item : valid_ores){
      if(item == check_item){
        return true;
      }
    }
    return false;
  }

  /** Finds matching input and returns result ItemStack. */
  public static final ItemStack get_result(final ItemStack input){
    if(recipes == null){
      OverpoweredTechnology.log.error(new NullPointerException("Ore Refinery recipes list is null."));
      return ItemStack.EMPTY;
    }
    if(ItemUtil.itemStackExists(input)){
      final Item input_item = input.getItem();
      for(OreRefineryRecipe recipe : recipes){
        if(recipe.input == input_item){
          return recipe.output.copy();
        }
      }
    }
    return ItemStack.EMPTY;
  }

}
