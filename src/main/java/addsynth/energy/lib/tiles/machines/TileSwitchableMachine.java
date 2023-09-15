package addsynth.energy.lib.tiles.machines;

import addsynth.core.util.StringUtil;
import addsynth.core.util.math.common.RoundMode;
import addsynth.energy.lib.config.MachineData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** Machines with a Power Switch can be switched off to conserve energy.
 *  Most Work Machines derive from this class.
 * @author ADDSynth
 */
public abstract class TileSwitchableMachine extends TileAbstractWorkMachine implements ISwitchableMachine {

  protected boolean power_switch;
  protected int power_time;
  protected final int power_on_time;
  protected final int power_off_time;

  public TileSwitchableMachine(BlockEntityType type, BlockPos position, BlockState blockstate,
                               MachineState initial_state, MachineData data){
    this(type, position, blockstate, initial_state, data, true);
  }

  public TileSwitchableMachine(BlockEntityType type, BlockPos position, BlockState blockstate,
                               MachineState initial_state, MachineData data, boolean initial_power_state){
    super(type, position, blockstate, initial_state, data);
    power_on_time  = data.get_power_time();
    power_off_time = data.get_power_time();
    power_switch = initial_power_state;
  }

  @Override
  public void load(final CompoundTag nbt){
    super.load(nbt);
    power_switch = nbt.getBoolean("Power Switch");
    power_time   = nbt.getInt("Power Time");
  }

  @Override
  protected void saveAdditional(final CompoundTag nbt){
    super.saveAdditional(nbt);
    nbt.putBoolean("Power Switch", power_switch);
    nbt.putInt("Power Time", power_time);
  }

  protected final void powering_off(){
    power_time += 1;
    if(power_time >= power_off_time){
      state = MachineState.OFF;
      power_time = 0;
    }
    changed = true;
  }

  protected final void turn_off(){
    if(power_off_time > 0){
      state = MachineState.POWERING_OFF;
    }
    else{
      state = MachineState.OFF;
    }
    changed = true;
  }

  public final float getPowerCycleTimePercentage(){
    if(state == MachineState.POWERING_ON){
      if(power_on_time > 0){
        return (float)power_time / power_on_time;
      }
    }
    if(state == MachineState.POWERING_OFF){
      if(power_off_time > 0){
        return (float)power_time / power_off_time;
      }
    }
    return 0.0f;
  }

  @Override
  public void togglePowerSwitch(){
    if(onServerSide()){
      power_switch = !power_switch;
      changed = true;
    }
  }

  @Override
  public final boolean get_switch_state(){
    return power_switch;
  }

  @Override
  public String getStatus(){
    if(state == MachineState.POWERING_OFF || state == MachineState.POWERING_ON){
      return super.getStatus() + " " + StringUtil.toPercentageString(getPowerCycleTimePercentage(), RoundMode.Floor);
    }
    return state.getStatus();
  }

}
