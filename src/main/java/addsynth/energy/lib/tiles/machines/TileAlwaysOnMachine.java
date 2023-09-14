package addsynth.energy.lib.tiles.machines;

import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.game.inventory.*;
import addsynth.core.game.inventory.machine.IMachineInventory;
import addsynth.core.game.inventory.machine.MachineInventory;
import addsynth.energy.lib.config.MachineData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

/** Machines that are always running cannot be turned off. They switch to an
 *  Idle state when they can't do work. These machines don't have idle energy. */
public abstract class TileAlwaysOnMachine extends TileAbstractWorkMachine
  implements IInputInventory, IOutputInventory, IMachineInventory {

  protected final MachineInventory inventory;

  public TileAlwaysOnMachine(BlockEntityType type, BlockPos position, BlockState blockstate,
                             SlotData[] slots, int output_slots, MachineData data){
    super(type, position, blockstate, MachineState.IDLE, data);
    this.inventory = new MachineInventory(slots, output_slots);
  }

  public TileAlwaysOnMachine(BlockEntityType type, BlockPos position, BlockState blockstate,
                             int input_slots, Predicate<ItemStack> filter, int output_slots, MachineData data){
    super(type, position, blockstate, MachineState.IDLE, data);
    this.inventory = new MachineInventory(input_slots, filter, output_slots);
  }

  @Override
  public final void serverTick(){
    machine_tick();
    if(inventory.tick()){
      changed = true;
    }
    if(energy.tick()){
      changed = true;
    }
    if(changed){
      update_data();
      changed = false;
    }
  }

  @Override
  protected void machine_tick(){
    switch(state){
    case RUNNING:
      if(energy.isFull()){
        inventory.finish_work();
        energy.setEmpty();
        if(inventory.can_work()){
          inventory.begin_work();
        }
        else{
          state = MachineState.IDLE;
        }
        changed = true;
      }
      break;

    case IDLE:
      if(inventory.can_work()){
        state = MachineState.RUNNING;
        inventory.begin_work();
        changed = true;
      }
      break;
    
    default:
      state = MachineState.IDLE;
      changed = true;
    }
  }

  // Even though this is meant to be somewhat of a library or API, there's currently no way
  // to specify non-default behiavour for this type of machine. See TileStandardWorkMachine.

  @Override
  public final void onInventoryChanged(){
    changed = true;
  }

  @Override
  public final int getJobs(){
    return inventory.getJobs();
  }

  @Override
  public void load(final CompoundTag nbt){
    super.load(nbt);
    inventory.loadFromNBT(nbt);
  }

  @Override
  protected void saveAdditional(final CompoundTag nbt){
    super.saveAdditional(nbt);
    inventory.saveToNBT(nbt);
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side){
    if(remove == false){
      if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
        return InventoryUtil.getInventoryCapability(inventory.getInputInventory(), inventory.getOutputInventory(), side);
      }
      return super.getCapability(capability, side);
    }
    return LazyOptional.empty();
  }

  @Override
  public double getRequestedEnergy(){
    if(state == MachineState.RUNNING){
      return energy.getRequestedEnergy();
    }
    return 0;
  }

  @Override
  public void drop_inventory(){
    inventory.drop(worldPosition, level);
  }

  @Override
  public InputInventory getInputInventory(){
    return inventory.getInputInventory();
  }

  @Override
  public OutputInventory getOutputInventory(){
    return inventory.getOutputInventory();
  }

  @Override
  public final CommonInventory getWorkingInventory(){
    return inventory.getWorkingInventory();
  }
  
  @Override
  public final String getStatus(){
    if(state == MachineState.IDLE){
      return inventory.can_add_to_output() ? MachineState.IDLE.getStatus() : MachineState.OUTPUT_FULL.getStatus();
    }
    if(state == MachineState.RUNNING){
      // if(energy.hasEnergy()){
        return energy.getDifference() != 0 ? MachineState.RUNNING.getStatus() : MachineState.NOT_RECEIVING_ENERGY.getStatus();
      // }
      // return MachineState.NO_ENERGY.getStatus();
    }
    return state.getStatus();
  }
  
}
