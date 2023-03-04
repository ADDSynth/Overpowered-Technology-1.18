package addsynth.energy.gameplay.machines.compressor.recipe;

import addsynth.core.recipe.shapeless.RecipeCollection;
import addsynth.core.recipe.shapeless.ShapelessRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public final class CompressorRecipes {

  public static final class CompressorRecipeType implements RecipeType<CompressorRecipe> {
  }

  public static final RecipeCollection<CompressorRecipe> INSTANCE = new RecipeCollection<CompressorRecipe>(
    new CompressorRecipeType(), new ShapelessRecipeSerializer<CompressorRecipe>(CompressorRecipe.class, 1));

}
