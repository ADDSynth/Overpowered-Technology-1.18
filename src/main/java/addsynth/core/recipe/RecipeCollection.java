package addsynth.core.recipe;

import java.util.ArrayList;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import addsynth.core.game.inventory.filter.MachineFilter;
import addsynth.core.game.resource.ResourceUtil;
import addsynth.core.util.server.ServerUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.ForgeRegistries;

/** The RecipeCollection keeps a list of recipes of the {@link RecipeType}
 *  that you passed into the constructor. It also maintains an item
 *  filter that only allows an item in that slot if there's a recipe that
 *  needs an item in that slot.The recipe cache and item filter are
 *  automatically updated whenever resource are reloaded.
 */
public class RecipeCollection<T extends Recipe<Container>> {

  public final RecipeType<T> type;
  private final ArrayList<T> recipes = new ArrayList<T>();
  private final MachineFilter filter;

  public RecipeCollection(RecipeType<T> type, int recipe_max_size){
    this.type = type;
    filter = new MachineFilter(recipe_max_size);
  }

  /** This ensures the input filter gets rebuilt whenever resources are reloaded. */
  public final void registerResponders(){
    // rebuild filter on resource reload.
    ResourceUtil.addListener(() -> {
      @SuppressWarnings("resource")
      final MinecraftServer server = ServerUtils.getServer();
      if(server != null){
        build_filter(server.getRecipeManager());
      }
    });
    MinecraftForge.EVENT_BUS.addListener((RecipesUpdatedEvent event) -> {
      build_filter(event.getRecipeManager());
    });
  }

  /** This builds the ingredient filter. */
  private final void build_filter(final RecipeManager recipe_manager){
    // rebuild recipe cache
    recipes.clear();
    recipes.addAll(recipe_manager.getAllRecipesFor(type));
    
    if(recipes.size() == 0){
      ADDSynthCore.log.error("No recipes of type "+type.getClass().getSimpleName()+" exist!");
      return;
    }
    filter.set(recipes);
    ADDSynthCore.log.info(type.getClass().getSimpleName()+" input filter was rebuilt.");
  }

  /** Gets the filter for the first slot. Useful for recipes that only have 1 ingredient. */
  public final Predicate<ItemStack> getFilter(){
    return filter.get(0);
  }

  public final Predicate<ItemStack> getFilter(final int slot){
    return filter.get(slot);
  }

  /** This is mainly used for adding the recipes to JEI. */
  public final ArrayList<T> getRecipes(){
    return new ArrayList<T>(recipes);
  }

  /** Returns whether the input items matches a recipe in this collection. */
  public final boolean match(final ItemStack[] input, final Level world){
    final SimpleContainer inventory = new SimpleContainer(input);
    for(final T recipe : recipes){
      if(recipe.matches(inventory, world)){
        return true;
      }
    }
    return false;
  }

  /** Returns the recipe output, or ItemStack.EMPTY if there is no matching recipe. */
  @Nonnull
  public final ItemStack getResult(final ItemStack input){
    return getResult(new ItemStack[] {input});
  }

  /** Returns the recipe output, or ItemStack.EMPTY if there is no matching recipe. */
  @Nonnull
  public final ItemStack getResult(final ItemStack[] input){
    final StackedContents recipe_item_helper = new StackedContents();
    int count;
    
    for(final T recipe : recipes){
    
      recipe_item_helper.clear();
      count = 0;
      
      for(final ItemStack stack : input){
        if(stack.isEmpty() == false){
          count += 1;
          recipe_item_helper.accountStack(stack);
        }
      }
      if(count == recipe.getIngredients().size() && recipe_item_helper.canCraft(recipe, null)){
        return recipe.getResultItem().copy();
      }
    }
    return ItemStack.EMPTY;
  }
  
  /** Finds the recipe with an output that matches the passed in ItemStack.
   *  Warning: Finds the FIRST recipe with a matching output. There may be
   *  more than one recipe that makes that item. Returns null if no recipe was found.
   */
  @Nullable
  public final T find_recipe(final ItemStack output){
    T recipe = null;
    for(T r : recipes){
      if(r.getResultItem().sameItem(output)){
        recipe = r;
        break;
      }
    }
    return recipe;
  }

  @Nullable
  public final T find_recipe(final ResourceLocation registry_key){
    final Item output = ForgeRegistries.ITEMS.getValue(registry_key);
    if(output == null){
      return null;
    }
    T recipe = null;
    for(T r : recipes){
      if(r.getResultItem().getItem() == output){
        recipe = r;
        break;
      }
    }
    return recipe;
  }

}
