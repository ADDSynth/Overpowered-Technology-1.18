package addsynth.overpoweredmod.machines.energy_extractor;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public final class ContainerCrystalEnergyExtractor extends TileEntityContainer<TileCrystalEnergyExtractor> {

  public ContainerCrystalEnergyExtractor(final int id, final Inventory player_inventory, final TileCrystalEnergyExtractor tile){
    super(Containers.CRYSTAL_ENERGY_EXTRACTOR.get(), id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerCrystalEnergyExtractor(final int id, final Inventory player_inventory, final FriendlyByteBuf data){
    super(Containers.CRYSTAL_ENERGY_EXTRACTOR.get(), id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final Inventory player_inventory){
    make_player_inventory(player_inventory, 12, 106);
    addSlot(new InputSlot(tile, 0, TileCrystalEnergyExtractor.input_filter, 84, 20));
  }

}
