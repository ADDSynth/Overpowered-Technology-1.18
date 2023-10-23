package addsynth.overpoweredmod.machines.advanced_ore_refinery;

import java.util.ArrayList;
import addsynth.core.recipe.FurnaceRecipes;
import addsynth.overpoweredmod.game.tags.OverpoweredItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

// CANNOT use a Tag while responding to a RecipesUpdatedEvent (on Client) because the tag hasn't been
// bounded yet. Even though tag data is sent to the client, the Client DOES NOT receive a Tags Updated
// event. These methods now calculate their data on-demand rather than keeping a cached list updated.
public final class OreRefineryRecipes {

  private static final int output_multiplier = 2;

  // https://github.com/skyboy/MineFactoryReloaded/blob/master/src/main/java/powercrystals/minefactoryreloaded/modhelpers/vanilla/Minecraft.java
  
  /** This will only add an Ore recipe to the Advanced Ore Refinery if, other mods have registered their
   *  ore with the {@code forge:ores} tag, and it has a Furnace Recipe. */
  public static final ArrayList<OreRefineryRecipe> get_recipes(){
    final ArrayList<OreRefineryRecipe> recipes = new ArrayList<>(200);
    final ITag<Item> ores = ForgeRegistries.ITEMS.tags().getTag(OverpoweredItemTags.advanced_ore_refinery);
    for(final Item item : ores){
      final ItemStack result = get_result(new ItemStack(item, 1));
      if(!result.isEmpty()){
        recipes.add(new OreRefineryRecipe(item, result));
      }
    }
    return recipes;
  }

  public static final boolean filter(final ItemStack stack){
    final ITag<Item> recipes = ForgeRegistries.ITEMS.tags().getTag(OverpoweredItemTags.advanced_ore_refinery);
    return FurnaceRecipes.isFurnaceIngredient(stack) && recipes.contains(stack.getItem());
  }

  /** Finds matching input and returns result ItemStack. */
  public static final ItemStack get_result(final ItemStack input){
    final ItemStack result_check = FurnaceRecipes.INSTANCE.getResult(input);
    if(!result_check.isEmpty()){
      final ItemStack result = result_check.copy();
      result.setCount(result.getCount() * output_multiplier);
      return result;
    }
    return ItemStack.EMPTY;
  }

}
