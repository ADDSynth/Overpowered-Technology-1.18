package addsynth.overpoweredmod.machines.magic_infuser;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.container.slots.OutputSlot;
import addsynth.core.game.inventory.filter.SingleItemFilter;
import addsynth.overpoweredmod.machines.magic_infuser.recipes.MagicInfuserRecipes;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;

public final class ContainerMagicInfuser extends TileEntityContainer<TileMagicInfuser> {

  public ContainerMagicInfuser(final int id, final Inventory player_inventory, final TileMagicInfuser tile){
    super(Containers.MAGIC_INFUSER.get(), id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerMagicInfuser(final int id, final Inventory player_inventory, final FriendlyByteBuf data){
    super(Containers.MAGIC_INFUSER.get(), id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final Inventory player_inventory){
    make_player_inventory(player_inventory,8,105);
    addSlot(new  InputSlot(tile, 0, new SingleItemFilter(Items.BOOK), 12, 44));
    addSlot(new  InputSlot(tile, 1, MagicInfuserRecipes.INSTANCE.getFilter(1), 30, 44));
    addSlot(new OutputSlot(tile, 0, 143, 44));
  }

}
