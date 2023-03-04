package addsynth.core.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import addsynth.core.ADDSynthCore;
import addsynth.core.game.inventory.InventoryUtil;
import addsynth.core.game.item.ItemUtil;
import addsynth.core.util.server.ServerUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.items.ItemStackHandler;

@EventBusSubscriber(modid = ADDSynthCore.MOD_ID, bus = Bus.FORGE)
public final class RecipeUtil {

  @Deprecated
  private static HashMap<Item, ItemStack> furnace_recipes;

  public static final Item[] getFurnaceIngredients(){
    check_furnace_recipes();
    if(furnace_recipes == null){ return null;}
    final Set<Item> furnace_input = furnace_recipes.keySet();
    return furnace_input.toArray(new Item[furnace_input.size()]);
  }

  public static final boolean isFurnaceIngredient(final Item item){
    if(check_furnace_recipes()){
      for(final Item item2 : furnace_recipes.keySet()){
        if(item == item2){
          return true;
        }
      }
    }
    return false;
  }

  public static final ItemStack getFurnaceResult(final ItemStack stack){
    return ItemUtil.itemStackExists(stack) ? getFurnaceRecipeResult(stack.getItem()) : ItemStack.EMPTY;
  }

  public static final ItemStack getFurnaceRecipeResult(final Item item){
    if(check_furnace_recipes()){
      for(final Entry<Item, ItemStack> entry : furnace_recipes.entrySet()){
        if(entry.getKey() == item){
          return entry.getValue();
        }
      }
    }
    return ItemStack.EMPTY;
  }

  /** This only returns whether furnace recipes are loaded. For a more robust check,
   *  call the check_furnace_recipes() function.
   */
  public static final boolean furnace_recipes_loaded(){
    if(furnace_recipes == null){ return false; }
    if(furnace_recipes.size() == 0){ return false; }
    return true;
  }

  /** Checks if Furnace Recipes are loaded, and attempts to load them if they aren't.
   *  Returns true if they are loaded.
   */
  @Deprecated
  public static final boolean check_furnace_recipes(){
    if(furnace_recipes_loaded() == true){ return true;}
    update_furnace_recipes();
    if(furnace_recipes_loaded() == false){
      // FEATURE: In the future, when I figure out how to determine if we're in a dev environment,
      //          probably by calling JavaUtils.classExists("Minecraft"), throw this error ONLY if
      //          we're in a development environment. Add this boolean method to the ForgeUtils class.
      if(ADDSynthCore.DEV_STAGE.isDevelopment){
        ADDSynthCore.log.error(new RuntimeException(
          "Attempted to access Furnace Recipes at an inappropiate time. Recipes should "+
          "automatically update when recipes are reloaded, such as when joining worlds."));
        Thread.dumpStack();
      }
      return false;
    }
    return true;
  }

  public static final boolean match(final Recipe<Container> recipe, final ItemStackHandler inventory, final Level world){
    return recipe.matches(InventoryUtil.toInventory(inventory), world);
  }

  private static final ArrayList<Runnable> responders = new ArrayList<>();

  /** Register functions to call when Recipes are reloaded. Your functions are probably used to
   *  generate internal recipes for your machines. These depend on Item Tags.
   *  The Reload Recipes event ALWAYS occurrs after Tags are reloaed.
   * @param executable
   */
  public static final void registerResponder(final Runnable executable){
    if(responders.contains(executable)){
      ADDSynthCore.log.warn("That function is already registered as an event responder.");
      // Thread.dumpStack();
    }
    else{
      responders.add(executable);
    }
  }

  private static final void dispatchEvent(){
    for(final Runnable responder : responders){
      responder.run();
    }
  }

  // This event should only fire when Clients connect to Server Worlds.
  @SubscribeEvent
  public static final void onRecipesUpdated(final RecipesUpdatedEvent event){
    ADDSynthCore.log.info("Recipes were reloaded. Sending update events...");
  
    update_furnace_recipes(event.getRecipeManager());
    dispatchEvent();

    ADDSynthCore.log.info("Done responding to Recipe reload.");
  }

  public static final ArrayList<Recipe> getRecipesofType(final RecipeManager recipe_manager, final RecipeType type){
    final ArrayList<Recipe> list = new ArrayList<>(200);
    for(Recipe recipe : recipe_manager.getRecipes()){
      if(recipe.getType() == type){
        list.add(recipe);
      }
    }
    return list;
  }

  @Deprecated
  private static final void update_furnace_recipes(){
    @SuppressWarnings("resource")
    final MinecraftServer server = ServerUtils.getServer();
    if(server != null){
      update_furnace_recipes(server.getRecipeManager());
    }
  }

  private static final void update_furnace_recipes(final RecipeManager recipe_manager){
    if(furnace_recipes == null){
      furnace_recipes = new HashMap<>(300);
    }
    else{
      furnace_recipes.clear();
    }
    SmeltingRecipe furnace_recipe;
    Ingredient ingredient;
    ItemStack result;
    for(final Recipe recipe : getRecipesofType(recipe_manager, RecipeType.SMELTING)){
      if(recipe instanceof SmeltingRecipe){ // only for safety reasons
        furnace_recipe = (SmeltingRecipe)recipe;
        ingredient = furnace_recipe.getIngredients().get(0); // furnace recipes only have 1 ingredient
        result = furnace_recipe.getResultItem();
        for(final ItemStack stack : ingredient.getItems()){
          furnace_recipes.put(stack.getItem(), result);
        }
      }
      else{
        ADDSynthCore.log.warn("RecipeUtil.update_furnace_recipes() found a Recipe of type SMELTING but it is not a Furnace Recipe?");
      }
    }
  }

}
