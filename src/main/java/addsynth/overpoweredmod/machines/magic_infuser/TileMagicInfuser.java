package addsynth.overpoweredmod.machines.magic_infuser;

import javax.annotation.Nullable;
import addsynth.core.game.inventory.SlotData;
import addsynth.core.game.inventory.filter.SingleItemFilter;
import addsynth.core.recipe.jobs.JobSystem;
import addsynth.energy.lib.tiles.machines.TileStandardWorkMachine;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.machines.magic_infuser.recipes.MagicInfuserRecipes;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

public final class TileMagicInfuser extends TileStandardWorkMachine implements MenuProvider {

  private static final SlotData[] getSlotData(){
    return new SlotData[] {
      new SlotData(new SingleItemFilter(Items.BOOK)),
      new SlotData(MagicInfuserRecipes.INSTANCE.getFilter(1))
    };
  }

  public TileMagicInfuser(BlockPos position, BlockState blockstate){
    super(Tiles.MAGIC_INFUSER.get(), position, blockstate, getSlotData(), 1, MachineValues.magic_infuser);
    inventory.setResponder(this);
  }

  @Override
  public final void onInventoryChanged(){
    changed = true;
  }

  @Override
  protected final boolean can_work(){
    return !inventory.getInputInventory().getStackInSlot(0).isEmpty() &&
           !inventory.getInputInventory().getStackInSlot(1).isEmpty() &&
            inventory.getOutputInventory().getStackInSlot(0).isEmpty();
  }

  @Override
  protected final void perform_work(){
    final ItemStack input = inventory.getWorkingInventory().getStackInSlot(1);
    final ItemStack enchant_book = MagicInfuserRecipes.getResult(input);
    inventory.getOutputInventory().setStackInSlot(0, enchant_book);
    inventory.getWorkingInventory().setEmpty();
    inventory.recheck();
  }

  @Override
  public final int getJobs(){
    return JobSystem.getMaxNumberOfJobs(inventory.getInputInventory().getItemStacks(), true);
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerMagicInfuser(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return getBlockState().getBlock().getName();
  }

}
