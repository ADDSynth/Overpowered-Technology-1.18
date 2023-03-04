package addsynth.energy.gameplay.machines.circuit_fabricator.recipe;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import addsynth.core.recipe.shapeless.RecipeCollection;
import addsynth.core.recipe.shapeless.ShapelessRecipeSerializer;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.EnergyItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public final class CircuitFabricatorRecipes {

  public static final class CircuitFabricatorRecipeType implements RecipeType<CircuitFabricatorRecipe> {
  }
  
  public static final RecipeCollection<CircuitFabricatorRecipe> INSTANCE = new RecipeCollection<CircuitFabricatorRecipe>(
    new CircuitFabricatorRecipeType(), new ShapelessRecipeSerializer<CircuitFabricatorRecipe>(CircuitFabricatorRecipe.class, 8)
  );

  /** This is used by the CircuitFabricatorGui to populate the Item list. */
  public static final ItemStack[] getRecipes(){
    // add circuits first
    final ArrayList<ItemStack> output = new ArrayList<>(30);
    for(Item circuit : EnergyItems.circuit){
      output.add(new ItemStack(circuit));
    }
    // add all other items
    final ArrayList<CircuitFabricatorRecipe> recipes = new ArrayList<>(INSTANCE.recipes);
    recipes.removeIf(
      (CircuitFabricatorRecipe r) -> {
        return r.getId().getNamespace().equals(ADDSynthEnergy.MOD_ID);
      }
    );
    
    final BiConsumer<ArrayList<ItemStack>, ItemStack> add = (ArrayList<ItemStack> list, ItemStack result) -> {
      final Item result_item = result.getItem();
      Item item;
      for(ItemStack i : list){
        item = i.getItem();
        if(item == result_item){
          return; // item already exists in list, so return and don't do anything
        }
      }
      list.add(result);
    };
    
    for(CircuitFabricatorRecipe recipe : recipes){
      add.accept(output, recipe.getResultItem());
    }
    return output.toArray(new ItemStack[output.size()]);
  }

}
