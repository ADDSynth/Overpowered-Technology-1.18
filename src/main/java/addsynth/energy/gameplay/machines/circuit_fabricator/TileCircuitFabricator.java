package addsynth.energy.gameplay.machines.circuit_fabricator;

import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.game.inventory.filter.RecipeFilter;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.config.Config;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipe;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipes;
import addsynth.energy.gameplay.reference.Names;
import addsynth.energy.lib.tiles.machines.TileStandardWorkMachine;
import addsynth.energy.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public final class TileCircuitFabricator extends TileStandardWorkMachine implements MenuProvider {

  private static final ResourceLocation defaultRecipe = Names.CIRCUIT_TIER_1;
  @Nonnull
  private ResourceLocation output_itemStack = defaultRecipe;
  // This RecipeFilter is different for every machine, therefore, it SHOULD NOT BE STATIC.
  private final RecipeFilter filter = new RecipeFilter(8);

  public final Predicate<ItemStack> getFilter(final int slot){
    return filter.getFilter(slot);
  }

  // NBT Labels
  private static final String legacyNBTSaveTag = "Circuit to Craft";
  private static final String saveTag = "Recipe";

  public TileCircuitFabricator(BlockPos position, BlockState blockstate){
    super(Tiles.CIRCUIT_FABRICATOR.get(), position, blockstate, 8, null, 1, Config.circuit_fabricator);
    inventory.getInputInventory().isItemStackValid = filter::test;
    inventory.setRecipeProvider(CircuitFabricatorRecipes.INSTANCE);
    rebuild_filters(); // sets default filter, before we load the previously saved selected recipe.
  }

  public final void change_recipe(final int circuit_id){
    final ResourceLocation circuit = switch(circuit_id){
      case 0 -> Names.CIRCUIT_TIER_1;
      case 1 -> Names.CIRCUIT_TIER_2;
      case 2 -> Names.CIRCUIT_TIER_3;
      case 3 -> Names.CIRCUIT_TIER_4;
      case 4 -> Names.CIRCUIT_TIER_5;
      case 5 -> Names.CIRCUIT_TIER_6;
      case 6 -> Names.CIRCUIT_TIER_7;
      case 7 -> Names.CIRCUIT_TIER_8;
      case 8 -> Names.CIRCUIT_TIER_9;
      default -> defaultRecipe;
    };
    change_recipe(circuit);
  }

  public final void change_recipe(final String new_recipe){
    change_recipe(new ResourceLocation(new_recipe));
  }

  public final void change_recipe(final ResourceLocation new_recipe){
    if(output_itemStack.equals(new_recipe) == false){
     output_itemStack = new_recipe;
     rebuild_filters();
     changed = true;
    }
  }

  public final void rebuild_filters(){
    // find recipe
    final CircuitFabricatorRecipe recipe = CircuitFabricatorRecipes.INSTANCE.find_recipe(output_itemStack);
    if(recipe != null){
      filter.set(recipe);
      // update recipe in gui if on client side
      updateGui();
    }
    else{
      // Handle invalid recipe
      ADDSynthEnergy.log.warn("Circuit Fabricator recipe for "+output_itemStack.toString()+" doesn't exist anymore.");
      // PRIORITY: add a resetMachine() function. Add a call here. Check how we currently handle unexpected machine state errors.
      change_recipe(defaultRecipe);
    }
  }

  /** Go through all inventory slots and eject all items that don't match.
   *  This can only be called when the player clicks on a change recipe button on the gui,
   *  which then sends a network message to the server.
   */
  public final void ejectInvalidItems(final Player player){
    inventory.getInputInventory().ejectInvalidItems(player);
  }

  @SuppressWarnings("null")
  public final void updateGui(){
    if(level != null){
      if(level.isClientSide){
        CircuitFabricatorGui.updateRecipeDisplay(filter.getIngredients());
      }
    }
  }

  public final ItemStack getRecipeOutput(){
    return new ItemStack(ForgeRegistries.ITEMS.getValue(output_itemStack));
  }

  @Override
  protected final void saveAdditional(final CompoundTag nbt){
    super.saveAdditional(nbt);
    nbt.putString(saveTag, output_itemStack.toString());
  }

  @Override
  public final void load(final CompoundTag nbt){
    super.load(nbt);
    
    // handle old saves
    if(nbt.contains(legacyNBTSaveTag)){
      change_recipe(nbt.getInt(legacyNBTSaveTag)); // loads 0 by default
      return;
    }
    
    // handle new saves
    final String recipe = nbt.getString(saveTag);
    // handle if tag doesn't exist
    if(recipe.equals("")){
      change_recipe(defaultRecipe);
      return;
    }
    // handle if item doesn't exist
    if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(recipe)) == false){
      ADDSynthEnergy.log.warn("Loading CircuitFabricator data: Item '"+recipe+"' doesn't exist anymore. Loading default recipe.");
      change_recipe(defaultRecipe);
      return;
    }
    // load normally
    change_recipe(recipe);
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new CircuitFabricatorContainer(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return getBlockState().getBlock().getName();
  }

}
