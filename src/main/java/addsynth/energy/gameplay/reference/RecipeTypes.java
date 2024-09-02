package addsynth.energy.gameplay.reference;

import addsynth.core.game.registry.RegistryUtil;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipe;
import addsynth.energy.gameplay.machines.compressor.recipe.CompressorRecipe;
import net.minecraft.world.item.crafting.RecipeType;

public final class RecipeTypes {

  public static final RecipeType<CircuitFabricatorRecipe> CIRCUIT_FABRICATOR = RegistryUtil.createRecipeType(Names.CIRCUIT_FABRICATOR);
  public static final RecipeType<CompressorRecipe> COMPRESSOR = RegistryUtil.createRecipeType(Names.COMPRESSOR);

}
