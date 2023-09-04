package addsynth.overpoweredmod.machines.plasma_generator;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.OutputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public final class ContainerPlasmaGenerator extends TileEntityContainer<TilePlasmaGenerator> {

  public ContainerPlasmaGenerator(final int id, final Inventory player_inventory, final TilePlasmaGenerator tile){
    super(Containers.PLASMA_GENERATOR.get(), id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerPlasmaGenerator(final int id, final Inventory player_inventory, final FriendlyByteBuf data){
    super(Containers.PLASMA_GENERATOR.get(), id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final Inventory player_inventory){
    make_player_inventory(player_inventory, 11, 114);
    addSlot(new OutputSlot(tile, 0, 83, 70));
  }

}
