package addsynth.overpoweredmod.machines.inverter;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.container.slots.OutputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public final class ContainerInverter extends TileEntityContainer<TileInverter> {

  public ContainerInverter(final int id, final Inventory player_inventory, final TileInverter tile){
    super(Containers.INVERTER.get(), id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerInverter(final int id, final Inventory player_inventory, final FriendlyByteBuf data){
    super(Containers.INVERTER.get(), id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final Inventory player_inventory){
    make_player_inventory(player_inventory, 8, 105);
    addSlot(new  InputSlot(tile, 0,  29, 44));
    addSlot(new OutputSlot(tile, 0, 125, 44));
  }

}
