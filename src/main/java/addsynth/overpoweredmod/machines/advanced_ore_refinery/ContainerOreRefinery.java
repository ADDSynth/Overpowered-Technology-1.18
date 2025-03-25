package addsynth.overpoweredmod.machines.advanced_ore_refinery;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.container.slots.OutputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public final class ContainerOreRefinery extends TileEntityContainer<TileAdvancedOreRefinery> {

  public ContainerOreRefinery(final int id, final Inventory player_inventory, final TileAdvancedOreRefinery tile){
    super(Containers.ADVANCED_ORE_REFINERY.get(), id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerOreRefinery(final int id, final Inventory player_inventory, final FriendlyByteBuf data){
    super(Containers.ADVANCED_ORE_REFINERY.get(), id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final Inventory player_inventory){
    make_player_inventory(player_inventory, 8, 104);
    addSlot(new  InputSlot(tile, 0,  28, 43));
    addSlot(new OutputSlot(tile, 0, 124, 43));
  }

}
