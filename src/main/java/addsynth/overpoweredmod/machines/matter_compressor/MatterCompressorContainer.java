package addsynth.overpoweredmod.machines.matter_compressor;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.container.slots.OutputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public final class MatterCompressorContainer extends TileEntityContainer<TileMatterCompressor> {

  public MatterCompressorContainer(final int id, final Inventory player_inventory, final TileMatterCompressor tile){
    super(Containers.MATTER_COMPRESSOR.get(), id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public MatterCompressorContainer(final int id, final Inventory player_inventory, final FriendlyByteBuf data){
    super(Containers.MATTER_COMPRESSOR.get(), id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final Inventory player_inventory){
    make_player_inventory(player_inventory, 11, 100);
    addSlot(new  InputSlot(tile, 0,  83, 24));
    addSlot(new  InputSlot(tile, 1,  59, 50));
    addSlot(new OutputSlot(tile, 0, 107, 50));
  }

}
