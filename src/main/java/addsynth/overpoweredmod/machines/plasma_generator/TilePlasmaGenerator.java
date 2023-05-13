package addsynth.overpoweredmod.machines.plasma_generator;

import javax.annotation.Nullable;
import addsynth.core.game.inventory.IOutputInventory;
import addsynth.core.game.inventory.OutputInventory;
import addsynth.energy.lib.tiles.machines.TilePassiveMachine;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public final class TilePlasmaGenerator extends TilePassiveMachine implements IOutputInventory, MenuProvider {

  private final OutputInventory output_inventory;

  public TilePlasmaGenerator(BlockPos position, BlockState blockstate){
    super(Tiles.PLASMA_GENERATOR, position, blockstate, MachineValues.plasma_generator);
    output_inventory = OutputInventory.create(this, 1);
  }

  @Override
  protected final void perform_work(){
    output_inventory.insertItem(0, new ItemStack(OverpoweredItems.plasma), false);
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerPlasmaGenerator(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return getBlockState().getBlock().getName();
  }

  @Override
  public void onInventoryChanged(){
    // no need to react to inventory change
  }

  @Override
  public void drop_inventory(){
    output_inventory.drop_in_world(level, worldPosition);
  }

  @Override
  public OutputInventory getOutputInventory(){
    return output_inventory;
  }

}
