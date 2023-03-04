package addsynth.core.container.slots;

import javax.annotation.Nonnull;
import addsynth.core.game.inventory.IOutputInventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.SlotItemHandler;

public final class OutputSlot extends SlotItemHandler {

  public OutputSlot(IOutputInventory tile, int index, int xPosition, int yPosition) {
    super(tile.getOutputInventory(), index, xPosition, yPosition);
  }

  @Override
  public final boolean mayPlace(@Nonnull final ItemStack stack){
    return false;
  }

  // NOTE: warning. it's possible this is run on client and server!
  @Override
  public final void onTake(final Player player, final ItemStack stack){
    ForgeEventFactory.firePlayerCraftingEvent(player, stack, new SimpleContainer(0)); // TODO: This wants the crafting matrix, be nice and give it to them.
    setChanged();
  }

}
