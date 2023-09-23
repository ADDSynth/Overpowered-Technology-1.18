package addsynth.core.recipe;

import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import addsynth.core.ADDSynthCore;
import addsynth.core.game.inventory.InventoryUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.server.ServerLifecycleHooks;

/** The Recipe Utility class is now only used for simple / generic recipe queries.
 *  For more specific control over recipes use the {@link RecipeManager} or better
 *  yet, keep an instance of a {@link RecipeCollection} of your {@link RecipeType}.
 *  @see Level#getRecipeManager()
 */
public final class RecipeUtil {

  private static final HashSet<Consumer<RecipeManager>> recipe_responders = new HashSet<>();

  /** This is primarily used by {@link RecipeCollection}s to respond to resource reloads and update
   *  their cached recipes and item filters. So far, this is executed on both the server and client
   *  side. We generally should not be using this and should be transitioning to recipe files and
   *  tags.
   */
  public static final void addResponder(final Consumer<RecipeManager> consumer){
    recipe_responders.add(consumer);
  }

  /** <p>Internal use only. This must be added as a listener to the {@link MinecraftForge#EVENT_BUS}.
   *  This gets called on both the Server and Client side, but in single-player games you can only get
   *  the server on the Client side?? and data saved in static fields can be shared between both sides.
   *  <p>This is the best way to execute code after resources have been reloaded, as this event is
   *  typically fired after resources are reloaded. Resources are reloaded whenever a dedicated server
   *  starts, when clients join a world, or when a player presses F3 + T.
   *  <p>There is the {@link net.minecraftforge.event.AddReloadListenerEvent AddReloadListenerEvent}
   *  but this is fired DURING the resource reload and it is not guarenteed to be fired after recipes
   *  are loaded.
   *  @see net.minecraft.server.MinecraftServer#reloadResources(java.util.Collection)
   *  @see net.minecraft.server.ReloadableServerResources#updateRegistryTags(net.minecraft.core.RegistryAccess)
   */
  public static final void tags_updated(final TagsUpdatedEvent event){
    final String side = event.getUpdateCause() == TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD ? "Server" : "Client";
    ADDSynthCore.log.info("Tags were updated on the "+side+" side.");
    @SuppressWarnings("resource")
    final MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
    if(server != null){
      update_recipes(server.getRecipeManager());
    }
    ADDSynthCore.log.info("Done responding to TagsUpdated event.");
  }

  /** Internal use only. This must be added as a listener to the {@link MinecraftForge#EVENT_BUS}.
   *  This is the only way to get the {@link RecipeManager} on the Client side, unless you have
   *  access to the {@link Level}, then you can call {@link Level#getRecipeManager()}.
   */
  public static final void recipes_updated(final RecipesUpdatedEvent event){
    ADDSynthCore.log.info("Recipes were updated on the Client side.");
    update_recipes(event.getRecipeManager());
    ADDSynthCore.log.info("Done responding to RecipesUpdated event.");
  }

  private static final void update_recipes(final RecipeManager recipe_manager){
    for(final Consumer<RecipeManager> listener : recipe_responders){
      listener.accept(recipe_manager);
    }
  }

  // =======================================================================================

  public static final boolean isIngredient(final Recipe<Container> recipe, final Item item){
    for(final Ingredient ingredient : recipe.getIngredients()){
      for(final ItemStack stack : ingredient.getItems()){
        if(stack.getItem() == item){
          return true;
        }
      }
    }
    return false;
  }

  public static final boolean match(final Recipe<Container> recipe, final ItemStackHandler inventory, final Level world){
    return recipe.matches(InventoryUtil.toInventory(inventory), world);
  }

  /* DELETE in 2026
  @SuppressWarnings("resource")
  public static final <C extends Container, R extends Recipe<C>> List<R> getRecipesofType(final RecipeType<R> type){
    final MinecraftServer server = ServerUtils.getServer();
    if(server != null){
      final RecipeManager recipe_manager = server.getRecipeManager();
      return recipe_manager.getAllRecipesFor(type);
    }
    ADDSynthCore.log.error("RecipeUtil.getRecipesofType(RecipeType type) CANNOT be called on the Client side!");
    Thread.dumpStack();
    final Minecraft minecraft = Minecraft.getInstance();
    return getRecipesofType(type, minecraft.level);
  }
  */

  public static final <C extends Container, R extends Recipe<C>> List<R> getRecipesofType(final RecipeType<R> type, final Level world){
    final RecipeManager recipe_manager = world.getRecipeManager();
    return recipe_manager.getAllRecipesFor(type);
  }

}
