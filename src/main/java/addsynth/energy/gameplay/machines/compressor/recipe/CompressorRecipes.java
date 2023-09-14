package addsynth.energy.gameplay.machines.compressor.recipe;

import addsynth.core.recipe.RecipeCollection;
import net.minecraft.world.item.crafting.RecipeType;

public final class CompressorRecipes {

  public static final class CompressorRecipeType implements RecipeType<CompressorRecipe> {
  }

  public static final RecipeCollection<CompressorRecipe> INSTANCE =
    new RecipeCollection<CompressorRecipe>(new CompressorRecipeType(), 1);

}
