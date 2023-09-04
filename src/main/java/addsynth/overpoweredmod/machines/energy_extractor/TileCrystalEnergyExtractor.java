package addsynth.overpoweredmod.machines.energy_extractor;

import javax.annotation.Nullable;
import addsynth.energy.lib.tiles.energy.TileStandardGenerator;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

public final class TileCrystalEnergyExtractor extends TileStandardGenerator implements MenuProvider {

  public static final Item[] input_filter = new Item[] {
    OverpoweredItems.energy_crystal_shards.get(),
    OverpoweredItems.energy_crystal.get(),
    Item.BY_BLOCK.get(OverpoweredBlocks.light_block.get())
  };

  public TileCrystalEnergyExtractor(BlockPos position, BlockState blockstate){
    super(Tiles.CRYSTAL_ENERGY_EXTRACTOR.get(), position, blockstate, input_filter);
  }

  @Override
  protected final void setGeneratorData(){
    final Item item = input_inventory.extractItem(0, 1, false).getItem();
    if(item == OverpoweredItems.energy_crystal.get()){
      energy.setEnergyAndCapacity(MachineValues.energy_crystal_energy.get());
      energy.setMaxExtract(MachineValues.energy_crystal_max_extract.get());
    }
    if(item == OverpoweredItems.energy_crystal_shards.get()){
      energy.setEnergyAndCapacity(MachineValues.energy_crystal_shards_energy.get());
      energy.setMaxExtract(MachineValues.energy_crystal_shards_max_extract.get());
    }
    if(item == OverpoweredItems.light_block.get()){
      energy.setEnergyAndCapacity(MachineValues.light_block_energy.get());
      energy.setMaxExtract(MachineValues.light_block_max_extract.get());
    }
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(final int windowID, final Inventory player_inventory, final Player player){
    return new ContainerCrystalEnergyExtractor(windowID, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return getBlockState().getBlock().getName();
  }

}
