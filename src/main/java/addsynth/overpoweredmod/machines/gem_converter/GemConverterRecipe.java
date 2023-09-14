package addsynth.overpoweredmod.machines.gem_converter;

import java.util.ArrayList;
import addsynth.material.Material;
import addsynth.overpoweredmod.game.tags.OverpoweredItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

/** This is only used to display Gem Converter recipes in JEI. */
public final class GemConverterRecipe {

  public final ItemStack result;

  private GemConverterRecipe(final ItemStack output){
    this.result = output;
  }

  public static final ArrayList<Ingredient> getIngredient(){
    final ArrayList<Ingredient> ingredient_list = new ArrayList<Ingredient>();
    ingredient_list.add(Ingredient.of(OverpoweredItemTags.convertable_gems));
    return ingredient_list;
  }

  public static final ArrayList<GemConverterRecipe> getRecipes(){
    final ArrayList<GemConverterRecipe> recipes = new ArrayList<>(8);
    recipes.add(new GemConverterRecipe(new ItemStack(Material.RUBY.getGem())));
    recipes.add(new GemConverterRecipe(new ItemStack(Material.TOPAZ.getGem())));
    recipes.add(new GemConverterRecipe(new ItemStack(Material.CITRINE.getGem())));
    recipes.add(new GemConverterRecipe(new ItemStack(Material.EMERALD.getGem())));
    recipes.add(new GemConverterRecipe(new ItemStack(Material.DIAMOND.getGem())));
    recipes.add(new GemConverterRecipe(new ItemStack(Material.SAPPHIRE.getGem())));
    recipes.add(new GemConverterRecipe(new ItemStack(Material.AMETHYST.getGem())));
    recipes.add(new GemConverterRecipe(new ItemStack(Material.QUARTZ.getGem())));
    return recipes;
  }

}
