package addsynth.overpoweredmod.machines.portal.control_panel;

import addsynth.core.container.TileEntityContainer;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public final class ContainerPortalControlPanel extends TileEntityContainer<TilePortalControlPanel> {

  public ContainerPortalControlPanel(final int id, final Inventory player_inventory, final TilePortalControlPanel tile){
    super(Containers.PORTAL_CONTROL_PANEL.get(), id, player_inventory, tile);
  }

  public ContainerPortalControlPanel(final int id, final Inventory player_inventory, final FriendlyByteBuf data){
    super(Containers.PORTAL_CONTROL_PANEL.get(), id, player_inventory, data);
  }

}
