package addsynth.core.recipe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;

/** Use this to query Furnace recipes, or to get a filter that accepts
 *  only smelting recipe ingredients such as ores, sand, and raw meat.
 *  This is automatically kept up-to-date.
 */
public final class FurnaceRecipes extends RecipeCollection<SmeltingRecipe> {

  public static final FurnaceRecipes INSTANCE = new FurnaceRecipes();

  public FurnaceRecipes(){
    super(RecipeType.SMELTING, 1);
  }

  public static final boolean isFurnaceIngredient(final Item item){
    for(final SmeltingRecipe recipe : INSTANCE.getRecipes()){
      for(final ItemStack stack : recipe.getIngredients().get(0).getItems()){
        if(stack.getItem() == item){
          return true;
        }
      }
    }
    return false;
  }

  public static final ItemStack getResult(final Item item){
    return INSTANCE.getResult(new ItemStack(item, 1));
  }

}
