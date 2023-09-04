package addsynth.energy.gameplay.machines.circuit_fabricator.recipe;

import addsynth.core.recipe.shapeless.ShapelessRecipeSerializer;
import addsynth.energy.gameplay.reference.Names;

public final class CircuitFabricatorRecipeSerializer extends ShapelessRecipeSerializer<CircuitFabricatorRecipe> {

  public CircuitFabricatorRecipeSerializer(){
    super(CircuitFabricatorRecipe.class, 8);
    setRegistryName(Names.CIRCUIT_FABRICATOR);
  }

}
