package addsynth.core.game.inventory;

import java.util.function.Predicate;
import javax.annotation.Nonnull;
import addsynth.core.recipe.RecipeCollection;
import net.minecraft.world.item.ItemStack;

/** SlotData allows you to specify item filter and stack limit on a per-slot basis.
 *  All items are accepted by default. To restrict which items can go in this slot,
 *  specify your own Item filter during creation. Filters should be dynamically
 *  tested whenever possible, but if the number of items is too large, then you can
 *  use {@link RecipeCollection#getFilter(int)}. {@link InputInventory}s uses this. */
public final class SlotData {

  private final Predicate<ItemStack> is_valid;
  public final int stack_limit;

  public final static SlotData[] create_new_array(final int number_of_slots){
    final SlotData[] data = new SlotData[number_of_slots];
    int i;
    for(i = 0; i < number_of_slots; i++){
      data[i] = new SlotData();
    }
    return data;
  }

  public final static SlotData[] create_new_array(final int number_of_slots, @Nonnull final Predicate<ItemStack> filter){
    final SlotData[] data = new SlotData[number_of_slots];
    int i;
    for(i = 0; i < number_of_slots; i++){
      data[i] = new SlotData(filter);
    }
    return data;
  }

  /** This constructor uses the default Item filter, which allows everything. */
  public SlotData(){
    this.is_valid = (ItemStack) -> true;
    this.stack_limit = -1; // stack size depends on the ItemStack
  }

  /** This constructor uses the default Item filter, which allows everything. */
  public SlotData(final int slot_limit){
    this.is_valid = (ItemStack) -> true;
    this.stack_limit = slot_limit;
  }

  public SlotData(@Nonnull final Predicate<ItemStack> filter){
    this.is_valid = filter;
    this.stack_limit = -1;
  }

  public SlotData(@Nonnull final Predicate<ItemStack> filter, final int slot_limit){
    this.is_valid = filter;
    this.stack_limit = slot_limit;
  }

  public final boolean is_item_valid(@Nonnull final ItemStack stack){
    if(stack.isEmpty() == false){
      return is_valid.test(stack);
    }
    return false;
  }

}
