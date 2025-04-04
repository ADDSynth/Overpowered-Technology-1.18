package addsynth.core.game.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public final class InventoryUtil {

  /** <p>Used to safely return the Inventory Capability. Use this if your inventory allows bi-directional
   *  transfer of items because we return the inventory regardless of which side we're checking from.<br />
   *  <b>Remember:</b> ONLY USE THIS if you're checking for the
   *  {@link CapabilityItemHandler#ITEM_HANDLER_CAPABILITY ITEM_HANDLER_CAPABILITY}.</p>
   *  <p>Use case:<br />
   *  <pre><code>
   *  &#64;Override
   *  public &lt;T&gt; LazyOptional&lt;T&gt; getCapability(&#64;Nonnull Capability&lt;T&gt; capability, &#64;Nullable Direction direction){
   *    if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
   *      return InventoryUtil.getInventoryCapability(inventory);
   *    }
   *    return super.getCapability(capability, direction);
   *  }
   *  </code></pre></p>
   * @param <T>
   * @param inventory
   */
  public static final <T> LazyOptional<T> getInventoryCapability(final CommonInventory inventory){
    return inventory != null ? (LazyOptional.of(() -> inventory)).cast() : LazyOptional.empty();
  }

  /** <p>Used to return either the Input Inventory or Output Inventory depending on which side
   *  we're querying. Pass null to either inventory if your TileEntity doesn't have them.<br />
   *  <b>Remember</b> ONLY USE THIS if you're checking for the
   *  {@link net.minecraftforge.items.CapabilityItemHandler#ITEM_HANDLER_CAPABILITY ITEM_HANDLER_CAPABILITY}.</p>
   *  <p>Use case:<br />
   *  <pre><code>
   *  &#64;Override
   *  public &lt;T&gt; LazyOptional&lt;T&gt; getCapability(&#64;Nonnull Capability&lt;T&gt; capability, &#64;Nullable Direction direction){
   *    if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
   *      return InventoryUtil.getInventoryCapability(input_inventory, output_inventory, direction);
   *    }
   *    return super.getCapability(capability, direction);
   *  }
   *  </code></pre></p>
   * @param <T>
   * @param input_inventory
   * @param output_inventory
   * @param facing
   */
  public static final <T> LazyOptional<T> getInventoryCapability
  (InputInventory input_inventory, OutputInventory output_inventory, Direction facing){
    if(facing != null){
      if(facing == Direction.DOWN){
        return output_inventory != null ? (LazyOptional.of(() -> output_inventory)).cast() : LazyOptional.empty();
      }
      return input_inventory != null ? (LazyOptional.of(() -> input_inventory)).cast() : LazyOptional.empty();
    }
    return LazyOptional.empty();
  }
    

  public static final void drop_inventories(final BlockPos pos, final Level world, final CommonInventory ... inventories){
    for(final CommonInventory inventory : inventories){
      if(inventory != null){
        inventory.drop_in_world(world, pos);
      }
    }
  }

  /** This returns the {@link ItemStackHandler} converted to an Inventory. However, the contents
   *  of the inventory SHOULD NOT be modified. This should only be used as input to the 
   *  {@link Recipe#matches(net.minecraft.world.Container, Level) Recipe.matches()}
   *  method to find a matching recipe.
   * @param handler
   */
  public static final SimpleContainer toInventory(final ItemStackHandler handler){
    return new SimpleContainer(getItemStacks(handler));
  }

  /** Returns an array of all ItemStacks in this Inventory. However, you SHOULD NOT modify
   *  these ItemStacks as the changes will be reflected in the inventory. This should only
   *  be used to help convert the {@link ItemStackHandler} to an array of ItemStacks for
   *  comparing against crafting recipes.
   * @param handler
   */
  public static final ItemStack[] getItemStacks(final ItemStackHandler handler){
    final int max = handler.getSlots();
    final ItemStack[] stacks = new ItemStack[max];
    int i;
    for(i = 0; i < max; i++){
      stacks[i] = handler.getStackInSlot(i);
    }
    return stacks;
  }

}
