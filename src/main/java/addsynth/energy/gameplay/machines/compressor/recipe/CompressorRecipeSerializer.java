package addsynth.energy.gameplay.machines.compressor.recipe;

import addsynth.core.recipe.shapeless.ShapelessRecipeSerializer;
import addsynth.energy.gameplay.reference.Names;

public final class CompressorRecipeSerializer extends ShapelessRecipeSerializer<CompressorRecipe> {

  public CompressorRecipeSerializer(){
    super(CompressorRecipe.class, 1);
    setRegistryName(Names.COMPRESSOR);
  }

}
