package addsynth.energy.gameplay.machines.circuit_fabricator;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.container.slots.OutputSlot;
import addsynth.energy.registers.Containers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public final class CircuitFabricatorContainer extends TileEntityContainer<TileCircuitFabricator> {

  public CircuitFabricatorContainer(int id, Inventory player_inventory, FriendlyByteBuf data){
    super(Containers.CIRCUIT_FABRICATOR.get(), id, player_inventory, data);
    common_setup(player_inventory);
  }

  public CircuitFabricatorContainer(int id, Inventory player_inventory, TileCircuitFabricator tile){
    super(Containers.CIRCUIT_FABRICATOR.get(), id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public static final int input_slot_x = 76;
  public static final int input_slot_y = 48;

  private final void common_setup(final Inventory player_inventory){
    make_player_inventory(player_inventory, 87, 157);
    for(InputSlot slot : tile.getInputSlots()){
      addSlot(slot);
    }
    addSlot(new OutputSlot(tile, 0, 268, 85));
  }

}
