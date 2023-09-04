package addsynth.overpoweredmod.machines.laser.machine;

import addsynth.core.container.TileEntityContainer;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public final class ContainerLaserHousing extends TileEntityContainer<TileLaserHousing> {

  public ContainerLaserHousing(final int id, final Inventory player_inventory, final TileLaserHousing tile){
    super(Containers.LASER_HOUSING.get(), id, player_inventory, tile);
  }

  public ContainerLaserHousing(final int id, final Inventory player_inventory, final FriendlyByteBuf data){
    super(Containers.LASER_HOUSING.get(), id, player_inventory, data);
  }

}
