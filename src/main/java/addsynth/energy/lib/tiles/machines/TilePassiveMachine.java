package addsynth.energy.lib.tiles.machines;

import addsynth.energy.lib.config.MachineData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** Passive Machines have no idle state. They are either OFF or RUNNING.
 *  Passive Machines do not have idle energy. */
public abstract class TilePassiveMachine extends TileSwitchableMachine {

  public TilePassiveMachine(final BlockEntityType type, BlockPos position, BlockState blockstate, final MachineData data){
    super(type, position, blockstate, MachineState.RUNNING, data);
  }

  public TilePassiveMachine(final BlockEntityType type, BlockPos position, BlockState blockstate,
                            final MachineData data, final boolean initial_power_state){
    super(type, position, blockstate, initial_power_state ? MachineState.RUNNING : MachineState.OFF, data, initial_power_state);
  }

  @Override
  public void serverTick(){
    machine_tick();
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
    case OFF:
      if(power_switch){
        if(power_on_time > 0){
          state = MachineState.POWERING_ON;
        }
        else{
          state = MachineState.RUNNING;
        }
        changed = true;
      }
      break;

    case POWERING_ON:
      power_time += 1;
      if(power_time >= power_on_time){
        state = MachineState.RUNNING;
        power_time = 0;
      }
      changed = true;
      break;

    case POWERING_OFF:
      powering_off();
      break;
    
    default:
      if(energy.isFull()){
        // PRIORITY: Passive Machines will continue to accept energy, even if it can't insert
        //           any items into the output, also will force insert even though stack is full?
        perform_work();
        energy.setEmpty();
        changed = true;
      }
      if(power_switch == false){
        turn_off();
      }
      break;
    }
  }

  protected abstract void perform_work();

  @Override
  public double getRequestedEnergy(){
    if(state == MachineState.RUNNING){
      return energy.getRequestedEnergy();
    }
    return 0;
  }

}
