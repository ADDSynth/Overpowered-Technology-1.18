package addsynth.overpoweredmod.machines.gem_converter;

import java.util.ArrayList;
import addsynth.material.Material;
import addsynth.overpoweredmod.machines.Filters;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public final class GemConverterRecipe {

  public static final ArrayList<GemConverterRecipe> recipes = new ArrayList<>(200);
  
  private static final NonNullList<Ingredient> all_gems = NonNullList.create();
  
  public final NonNullList<Ingredient> ingredients;
  public final ItemStack result;
  
  private GemConverterRecipe(NonNullList<Ingredient> input, final ItemStack output){
    this.ingredients = input;
    this.result = output;
  }
  
  public static final void generate_recipes(){
    recipes.clear();
    generate_all_gems();
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Material.RUBY.gem)));
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Material.TOPAZ.gem)));
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Material.CITRINE.gem)));
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Material.EMERALD.gem)));
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Material.DIAMOND.gem)));
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Material.SAPPHIRE.gem)));
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Material.AMETHYST.gem)));
    recipes.add(new GemConverterRecipe(all_gems, new ItemStack(Material.QUARTZ.gem)));
  }

  private static final void generate_all_gems(){
    all_gems.clear();
    all_gems.add(Ingredient.of(Filters.gem_converter));
  }

}
