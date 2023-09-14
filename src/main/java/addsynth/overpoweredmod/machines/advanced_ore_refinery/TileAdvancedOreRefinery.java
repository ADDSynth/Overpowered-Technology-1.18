package addsynth.overpoweredmod.machines.advanced_ore_refinery;

import javax.annotation.Nullable;
import addsynth.energy.lib.tiles.machines.TileAlwaysOnMachine;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

/** Doubles the output of any ores. Works with ores from other mods as well. Only works with ores. */
public final class TileAdvancedOreRefinery extends TileAlwaysOnMachine implements MenuProvider {

  public TileAdvancedOreRefinery(BlockPos position, BlockState blockstate){
    super(Tiles.ADVANCED_ORE_REFINERY.get(), position, blockstate,
      1, OreRefineryRecipes::filter, 1,
      MachineValues.advanced_ore_refinery
    );
    inventory.setRecipeProvider(OreRefineryRecipes::get_result);
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerOreRefinery(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return getBlockState().getBlock().getName();
  }

}
