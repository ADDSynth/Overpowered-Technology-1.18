package addsynth.core.container.slots;

import java.util.function.Predicate;
import javax.annotation.Nonnull;
import addsynth.core.game.inventory.IInputInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public final class InputSlot extends SlotItemHandler {

  private final Predicate<ItemStack> filter;

  private static final int default_stack_size = 64;
  private final int max_stack_size;

  public InputSlot(IInputInventory tile, int index, int xPosition, int yPosition){
    super(tile.getInputInventory(), index, xPosition, yPosition);
    this.filter = null;
    this.max_stack_size = default_stack_size;
  }

  public InputSlot(IInputInventory tile, int index, Predicate<ItemStack> filter, int xPosition, int yPosition){
    super(tile.getInputInventory(), index, xPosition, yPosition);
    this.filter = filter;
    this.max_stack_size = default_stack_size;
  }

  public InputSlot(IInputInventory tile, int index, int max_stack_size, int xPosition, int yPosition){
    super(tile.getInputInventory(), index, xPosition, yPosition);
    this.filter = null;
    this.max_stack_size = max_stack_size;
  }

  public InputSlot(IInputInventory tile, int index, Predicate<ItemStack> filter, int max_stack_size, int xPosition, int yPosition){
    super(tile.getInputInventory(), index, xPosition, yPosition);
    this.filter = filter;
    this.max_stack_size = max_stack_size;
  }

  @Override
  public final boolean mayPlace(@Nonnull final ItemStack stack){
    if(filter == null){
      return super.mayPlace(stack);
    }
    if(filter.test(stack)){
      return super.mayPlace(stack);
    }
    return false;
  }

  @Override
  public int getMaxStackSize(){
    return max_stack_size;
  }

}
