package addsynth.energy.gameplay.machines.electric_furnace;

import javax.annotation.Nullable;
import addsynth.core.recipe.FurnaceRecipes;
import addsynth.energy.lib.config.MachineData;
import addsynth.energy.lib.config.MachineType;
import addsynth.energy.lib.tiles.machines.TileAlwaysOnMachine;
import addsynth.energy.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public final class TileElectricFurnace extends TileAlwaysOnMachine implements MenuProvider {

  private static final MachineData machine_data = new MachineData(MachineType.ALWAYS_ON, 200, 5, 0, 0);

  public TileElectricFurnace(BlockPos position, BlockState blockstate){
    super(Tiles.ELECTRIC_FURNACE.get(), position, blockstate, 1, FurnaceRecipes.INSTANCE.getFilter(), 1, machine_data);
    inventory.setRecipeProvider(FurnaceRecipes.INSTANCE);
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerElectricFurnace(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return getBlockState().getBlock().getName();
  }

}
