package addsynth.energy.gameplay.machines.circuit_fabricator.recipe;

import addsynth.core.recipe.shapeless.AbstractRecipe;
import addsynth.energy.gameplay.EnergyBlocks;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public final class CircuitFabricatorRecipe extends AbstractRecipe {

  public CircuitFabricatorRecipe(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> input){
    super(id, group, output, input);
    CircuitFabricatorRecipes.INSTANCE.addRecipe(this);
  }

  @Override
  public ItemStack getToastSymbol(){
    return new ItemStack(EnergyBlocks.circuit_fabricator, 1);
  }

  @Override
  public RecipeSerializer<?> getSerializer(){
    return CircuitFabricatorRecipes.INSTANCE.serializer;
  }

  @Override
  public RecipeType<?> getType(){
    return CircuitFabricatorRecipes.INSTANCE.type;
  }

}
