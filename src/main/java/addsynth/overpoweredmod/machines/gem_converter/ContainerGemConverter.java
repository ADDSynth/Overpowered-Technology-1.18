package addsynth.overpoweredmod.machines.gem_converter;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.container.slots.OutputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public final class ContainerGemConverter extends TileEntityContainer<TileGemConverter> {

  public ContainerGemConverter(final int id, final Inventory player_inventory, final TileGemConverter tile){
    super(Containers.GEM_CONVERTER.get(), id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerGemConverter(final int id, final Inventory player_inventory, final FriendlyByteBuf data){
    super(Containers.GEM_CONVERTER.get(), id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final Inventory player_inventory){
    make_player_inventory(player_inventory,8,112);
    addSlot(new  InputSlot(tile, 0, TileGemConverter.filter, 28, 45));
    addSlot(new OutputSlot(tile, 0, 124, 45));
  }

}
