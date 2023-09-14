package addsynth.overpoweredmod.machines.fusion.chamber;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public final class ContainerFusionChamber extends TileEntityContainer<TileFusionChamber> {

  public ContainerFusionChamber(final int id, final Inventory player_inventory, final TileFusionChamber tile){
    super(Containers.FUSION_CHAMBER.get(), id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerFusionChamber(final int id, final Inventory player_inventory, final FriendlyByteBuf data){
    super(Containers.FUSION_CHAMBER.get(), id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final Inventory player_inventory){
    make_player_inventory(player_inventory);
    addSlot(new InputSlot(tile, 0, TileFusionChamber.filter, 80, 37));
  }

}
