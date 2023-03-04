package addsynth.energy.lib.tiles.machines;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.game.inventory.*;
import addsynth.core.game.inventory.machine.IMachineInventory;
import addsynth.core.game.inventory.machine.MachineInventory;
import addsynth.core.util.game.tileentity.TileEntityUtil;
import addsynth.energy.lib.config.MachineData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

/** The Standard Work Machine, can be turned off, has an idle state, and when
 *  it can do work it transfers an item from the input inventory to the working
 *  inventory and switches to the Running state. This machine has idle energy.
 * @author ADDSynth
 */
public abstract class TileStandardWorkMachine extends TileSwitchableMachine
  implements IInputInventory, IOutputInventory, IMachineInventory {

  protected final MachineInventory inventory;
  private final double idle_energy;

  public TileStandardWorkMachine(BlockEntityType type, BlockPos position, BlockState blockstate,
                                 SlotData[] slots, int output_slots, MachineData data){
    super(type, position, blockstate, MachineState.IDLE, data);
    this.inventory = new MachineInventory(slots, output_slots);
    this.idle_energy = data.get_idle_energy();
  }

  public TileStandardWorkMachine(BlockEntityType type, BlockPos position, BlockState blockstate,
                                 int input_slots, Item[] filter, int output_slots, MachineData data){
    super(type, position, blockstate, MachineState.IDLE, data);
    this.inventory = new MachineInventory(input_slots, filter, output_slots);
    this.idle_energy = data.get_idle_energy();
  }

  @Override
  public final void serverTick(){
    try{
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
    catch(Exception e){
      TileEntityUtil.report_ticking_error(this, e);
    }
  }

  @Override
  protected final void machine_tick(){
    switch(state){
    case OFF:
      if(power_switch){
        if(power_on_time > 0){
          state = MachineState.POWERING_ON;
        }
        else{
          state = MachineState.IDLE;
        }
        changed = true;
      }
      break;

    case POWERING_ON:
      power_time += 1;
      if(power_time >= power_on_time){
        state = MachineState.IDLE;
        power_time = 0;
      }
      changed = true;
      break;

    case POWERING_OFF:
      powering_off();
      break;

    case IDLE: case OUTPUT_FULL:
      if(power_switch == false){
        turn_off();
      }
      else{
        if(can_work()){
          state = MachineState.RUNNING;
          begin_work();
          changed = true;
        }
      }
      break;
      
    default: // Running
      if(energy.isFull()){
        perform_work();
        energy.setEmpty();
        if(power_switch == false){
          turn_off();
        }
        else{
          if(can_work()){
            begin_work();
          }
          else{
            state = MachineState.IDLE;
          }
        }
        changed = true;
      }
      else{
        if(power_switch == false){
          turn_off();
        }
      }
      machine_running();

      break;
    }
  }

  /** Override this to specify additional instructions while the machine is actively working on something.
   *  This is called every tick on the server side. There is no need to call the super method! */
  protected void machine_running(){}

  /** Called multiple times a tick. Returns whether the machine can perform work.
   *  Override to specify non-default behaviour.
   */
  protected boolean can_work(){
    return inventory.can_work();
  }

  /** This is called to start a job.
   *  Override to specify non-default behaviour.
   */
  protected void begin_work(){
    inventory.begin_work();
  }

  /** Finishes working on the center ItemStack and increments the output.
   *  Override to specify non-default behaviour.
   */
  protected void perform_work(){
    inventory.finish_work();
  }

  @Override
  public void onInventoryChanged(){
    changed = true;
  }

  @Override
  public int getJobs(){
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
  public <T> LazyOptional<T> getCapability(final @Nonnull Capability<T> capability, final @Nullable Direction side){
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
    if(state == MachineState.IDLE){
    }
    return 0;
  }

  @Override
  public final void drop_inventory(){
    inventory.drop(worldPosition, level);
  }

  @Override
  public final InputInventory getInputInventory(){
    return inventory.getInputInventory();
  }
  
  @Override
  public final OutputInventory getOutputInventory(){
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
    return super.getStatus();
  }
  
}
