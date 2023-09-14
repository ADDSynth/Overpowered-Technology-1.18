package addsynth.core.game.inventory.filter;

import java.util.function.Predicate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** This is the simplest filter. It only allows one item in the slot. */
public final class SingleItemFilter implements Predicate<ItemStack> {

  private final Item item;

  public SingleItemFilter(final Item item){
    this.item = item;
  }

  @Override
  public final boolean test(final ItemStack stack){
    return stack.getItem() == item;
  }

}
