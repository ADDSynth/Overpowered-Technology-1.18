package addsynth.overpoweredmod.machines.identifier;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.container.slots.OutputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public final class ContainerIdentifier extends TileEntityContainer<TileIdentifier> {

  public ContainerIdentifier(final int id, final Inventory player_inventory, final TileIdentifier tile){
    super(Containers.IDENTIFIER.get(), id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerIdentifier(final int id, final Inventory player_inventory, final FriendlyByteBuf data){
    super(Containers.IDENTIFIER.get(), id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final Inventory player_inventory){
    make_player_inventory(player_inventory,8,87);
    addSlot(new  InputSlot(tile, 0, TileIdentifier.filter, 1, 28, 42));
    addSlot(new OutputSlot(tile, 0, 124, 42));
  }

}
