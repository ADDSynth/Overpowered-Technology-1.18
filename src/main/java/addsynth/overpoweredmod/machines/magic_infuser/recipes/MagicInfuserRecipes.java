package addsynth.overpoweredmod.machines.magic_infuser.recipes;

import java.util.ArrayList;
import java.util.Random;
import addsynth.core.recipe.RecipeCollection;
import addsynth.overpoweredmod.OverpoweredTechnology;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public final class MagicInfuserRecipes {

  public static final class MagicInfuserRecipeType implements RecipeType<MagicInfuserRecipe> {
  }

  public static final RecipeCollection<MagicInfuserRecipe> INSTANCE =
    new RecipeCollection<MagicInfuserRecipe>(new MagicInfuserRecipeType(), 2);

  /** The standard function {@link RecipeCollection#getResult(ItemStack)} only returns
   *  the first recipe we find that matches the input. Here, we want to find all recipes
   *  that match the input, and return a random one.
   *  For Magic Infuser recipes, a single input item can return different Enchanted Books.
   * @param input
   */
  public static final ItemStack getResult(final ItemStack input){
    final ArrayList<ItemStack> output = new ArrayList<>(10);
    for(final MagicInfuserRecipe recipe : INSTANCE.getRecipes()){
      if(recipe.getIngredients().get(1).test(input)){
        output.add(recipe.getResultItem());
      }
    }
    final int size = output.size();
    if(size == 0){
      OverpoweredTechnology.log.error("Cannot find a Magic Infuser recipe that matches "+input.toString()+".");
      return ItemStack.EMPTY;
    }
    final Random random = new Random();
    return output.get(random.nextInt(size));
  }

}
