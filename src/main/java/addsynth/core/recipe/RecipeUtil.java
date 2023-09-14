package addsynth.core.recipe;

import java.util.List;
import addsynth.core.game.inventory.InventoryUtil;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;

/** The Recipe Utility class is now only used for simple / generic recipe queries.
 *  For more specific control over recipes use the {@link RecipeManager} or better
 *  yet, keep an instance of a {@link RecipeCollection} of your {@link RecipeType}.
 *  @see Level#getRecipeManager()
 */
public final class RecipeUtil {

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
