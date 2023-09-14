package addsynth.core.game.inventory;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import addsynth.core.util.player.PlayerUtil;
import addsynth.core.util.world.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** This inventory only allows items to be inserted. It also has an Item filter to control
 *  which items are allowed. Pass null as the item filter to allow all items.
 */
public final class InputInventory extends CommonInventory {

  @Nonnull
  private final SlotData[] slot_data;
  public boolean custom_stack_limit_is_vanilla_dependant = true;

  /** Override this to specify your own way of determining whether an item
   *  is allowed in this inventory.
   */
  @Nonnull
  public BiFunction<Integer, ItemStack, Boolean> isItemStackValid;

  private InputInventory(final IInputInventory responder, @Nonnull final SlotData[] slots){
    super(responder, slots.length);
    this.slot_data = slots;
    isItemStackValid = (Integer slot, ItemStack stack) -> {
      return slot_data[slot].is_item_valid(stack);
    };
  }

  public static final InputInventory create(final IInputInventory responder, final int number_of_slots){
    return number_of_slots > 0 ? new InputInventory(responder, SlotData.create_new_array(number_of_slots)) : null;
  }

  public static final InputInventory create(final IInputInventory responder, final int number_of_slots, final Predicate<ItemStack> filter){
    return number_of_slots > 0 ? new InputInventory(responder, SlotData.create_new_array(number_of_slots, filter)) : null;
  }

  public static final InputInventory create(final IInputInventory responder, final SlotData[] slots){
    return slots != null ? (slots.length > 0 ? new InputInventory(responder, slots) : null) : null;
  }

  @Override
  public @Nonnull ItemStack insertItem(final int slot, @Nonnull final ItemStack stack, final boolean simulate){
    if(is_valid_slot(slot)){
      if(isItemStackValid.apply(slot, stack)){
        return super.insertItem(slot, stack, simulate);
      }
    }
    return stack;
  }

  @Override
  public @Nonnull ItemStack extractItem(int slot, int amount, boolean simulate){
    if(is_valid_slot(slot)){
      return super.extractItem(slot, amount, simulate);
    }
    return ItemStack.EMPTY;
  }

  /**
   * Decreases the ItemStack count in the slot by 1.
   * @param slot
   */
  public final void decrease(final int slot){
    decrease(slot, 1);
  }

  /**
   * Decreases the ItemStack count in the slot you want by the amount that you want.
   * @param slot
   * @param amount
   */
  public final void decrease(final int slot, final int amount){
    if(is_valid_slot(slot)){
      if(stacks.get(slot).isEmpty() == false){
        stacks.get(slot).shrink(amount);
      }
    }
  }

  /** Decreases all input slots by 1. */
  public final void decrease_input(){
    decrease_input(1);
  }

  /** Decreases all input slots by the amount that you specify. */
  public final void decrease_input(final int amount){
    int i;
    for(i = 0; i < stacks.size(); i++){
      if(stacks.get(i).isEmpty() == false){
        stacks.get(i).shrink(amount);
      }
    }
  }

  @Override
  protected final int getStackLimit(final int slot, @Nonnull final ItemStack stack){
    if(this.slot_data[slot].stack_limit < 0){
      return stack.getMaxStackSize();
    }
    if(custom_stack_limit_is_vanilla_dependant){
      return Math.min(this.slot_data[slot].stack_limit, stack.getMaxStackSize());
    }
    return this.slot_data[slot].stack_limit;
  }

  @Override
  public final void save(final CompoundTag nbt){
    nbt.put("InputInventory", serializeNBT());
  }

  @Override
  public final void load(final CompoundTag nbt){
    deserializeNBT(nbt.getCompound("InputInventory"));
  }

  /** Checks all slots in this inventory and returns invalid ItemStacks to the player. */
  public final void ejectInvalidItems(final Player player){
    final ItemStack[] ejected_itemStacks = getInvalidItemStacks();
    for(ItemStack stack : ejected_itemStacks){
      PlayerUtil.add_to_player_inventory(player, stack);
    }
  }

  /** Checks all slots in this inventory, removes invalid ItemStacks that don't
   *  match the filter, and spawns them in the world. */
  public final void ejectInvalidItems(final Level world, final BlockPos pos){
    final ItemStack[] ejected_itemStacks = getInvalidItemStacks();
    for(ItemStack stack : ejected_itemStacks){
      WorldUtil.spawnItemStack(world, pos, stack, false);
    }
  }

  private final ItemStack[] getInvalidItemStacks(){
    final int size = stacks.size();
    final ArrayList<ItemStack> ejected_itemStacks = new ArrayList<>(size);
    int i;
    for(i = 0; i < size; i++){
      if(slot_data[i].is_item_valid(getStackInSlot(i)) == false){
        ejected_itemStacks.add(extractItemStack(i));
      }
    }
    return ejected_itemStacks.toArray(new ItemStack[ejected_itemStacks.size()]);
  }

}
