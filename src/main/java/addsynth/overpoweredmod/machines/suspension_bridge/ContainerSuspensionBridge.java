package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public final class ContainerSuspensionBridge extends TileEntityContainer<TileSuspensionBridge> {

  public static final int lens_slot_x = 148; // also used in the gui to position the text label.

  public ContainerSuspensionBridge(final int id, final Inventory player_inventory, final TileSuspensionBridge tile){
    super(Containers.ENERGY_SUSPENSION_BRIDGE.get(), id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerSuspensionBridge(final int id, final Inventory player_inventory, final FriendlyByteBuf data){
    super(Containers.ENERGY_SUSPENSION_BRIDGE.get(), id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final Inventory player_inventory){
    make_player_inventory(player_inventory, 76, 103);
    addSlot(new InputSlot(tile, 0, lens_slot_x, 20));
  }

}
