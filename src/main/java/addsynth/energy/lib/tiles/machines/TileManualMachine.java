package addsynth.energy.lib.tiles.machines;

import addsynth.energy.lib.config.MachineData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** Manual Machines can be switched off, but only accept energy when they're on.
 *  They do not perform any action automatically and you must check for and empty
 *  energy yourself.
 * @author ADDSynth
 */
public abstract class TileManualMachine extends TileSwitchableMachine {

  public TileManualMachine(final BlockEntityType type, BlockPos position, BlockState blockstate, final MachineData data){
    super(type, position, blockstate, MachineState.RUNNING, data);
  }

  public TileManualMachine(final BlockEntityType type, BlockPos position, BlockState blockstate,
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
      if(power_switch == false){
        turn_off();
      }
    }
  }

  @Override
  public double getRequestedEnergy(){
    if(state == MachineState.RUNNING){
      return energy.getRequestedEnergy();
    }
    return 0;
  }

}
