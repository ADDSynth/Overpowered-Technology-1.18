package addsynth.overpoweredmod.machines.advanced_ore_refinery;

import java.util.ArrayList;
import addsynth.core.game.item.ItemUtil;
import addsynth.core.game.resource.ResourceUtil;
import addsynth.core.recipe.FurnaceRecipes;
import addsynth.material.util.MaterialUtil;
import addsynth.overpoweredmod.OverpoweredTechnology;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;

public final class OreRefineryRecipes {

  private static final int output_multiplier = 2;
  private static Item[] valid_ores = new Item[0];
  public static final ArrayList<OreRefineryRecipe> recipes = new ArrayList<>(200);

  // https://github.com/skyboy/MineFactoryReloaded/blob/master/src/main/java/powercrystals/minefactoryreloaded/modhelpers/vanilla/Minecraft.java
  
  public static final void registerResponders(){
    ResourceUtil.addListener(OreRefineryRecipes::rebuild_recipes);
    MinecraftForge.EVENT_BUS.addListener((RecipesUpdatedEvent event) -> rebuild_recipes());
  }
  
  /**
   * <p>This will only add an Ore recipe to the Advanced Ore Refinery if, other mods have registered their
   *    ore with the "ores" tag, and it has a Furnace Recipe.
   */
  private static final void rebuild_recipes(){
    recipes.clear();
    try{
      final ArrayList<Item> list = new ArrayList<Item>(100);
      ItemStack result_check;
      ArrayList<Item> ores = new ArrayList<Item>(MaterialUtil.getOres());
      // Manually Add Raw Iron, Raw Copper, and Raw Gold for the Minecraft 1.17+ versions
      // TODO: Check to see if Mojang or Forge Team added Item tags for these items in MC 1.18.
      ores.add(Items.RAW_IRON);
      ores.add(Items.RAW_COPPER);
      ores.add(Items.RAW_GOLD);
      for(final Item item : ores){
        if(FurnaceRecipes.isFurnaceIngredient(item)){
          result_check = FurnaceRecipes.getResult(item);
          if(result_check.isEmpty() == false){
            list.add(item);
            final ItemStack result = result_check.copy();
            result.setCount(result.getCount()*output_multiplier);
            recipes.add(new OreRefineryRecipe(item, result));
          }
        }
      }
      
      valid_ores = list.toArray(new Item[list.size()]);
    }
    catch(Exception e){
      OverpoweredTechnology.log.error("An exception occured in OreRefineryRecipes.rebuild_recipes().", e);
      valid_ores = new Item[0];
    }
  }

  public static final boolean filter(final ItemStack stack){
    final Item stack_item = stack.getItem();
    for(final Item item : valid_ores){
      if(item == stack_item){
        return true;
      }
    }
    return false;
  }

  @Deprecated // REMOVE in 2026
  public static final boolean matches(final Item item){
    for(Item check_item : valid_ores){
      if(item == check_item){
        return true;
      }
    }
    return false;
  }

  /** Finds matching input and returns result ItemStack. */
  public static final ItemStack get_result(final ItemStack input){
    if(recipes == null){
      OverpoweredTechnology.log.error(new NullPointerException("Ore Refinery recipes list is null."));
      return ItemStack.EMPTY;
    }
    if(ItemUtil.itemStackExists(input)){
      final Item input_item = input.getItem();
      for(OreRefineryRecipe recipe : recipes){
        if(recipe.input == input_item){
          return recipe.output.copy();
        }
      }
    }
    return ItemStack.EMPTY;
  }

}
