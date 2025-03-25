package addsynth.overpoweredmod.machines.portal.frame;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public final class ContainerPortalFrame extends TileEntityContainer<TilePortalFrame> {

  public ContainerPortalFrame(final int id, final Inventory player_inventory, final TilePortalFrame tile){
    super(Containers.PORTAL_FRAME.get(), id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerPortalFrame(final int id, final Inventory player_inventory, final FriendlyByteBuf data){
    super(Containers.PORTAL_FRAME.get(), id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final Inventory player_inventory){
    make_player_inventory(player_inventory);
    addSlot(new InputSlot(tile, 0, 80, 37));
  }

}
