package addsynth.energy.lib.tiles.machines;

import addsynth.energy.lib.config.MachineData;
import addsynth.energy.lib.main.ICustomEnergyUser;
import addsynth.energy.lib.main.Receiver;
import addsynth.energy.lib.tiles.TileAbstractMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** Work Machines are the most commonly used Machine types. Their behaviour is
 *  specifically defined and are well managed.
 * @author ADDSynth
 */
public abstract class TileAbstractWorkMachine extends TileAbstractMachine implements ICustomEnergyUser {

  /** Do not call {@link #update_data()}. Instead set this to true whenever
   *  important data is changed. Check for this in the TileEntity's tick() function.
   */
  protected boolean changed;
  protected MachineState state;

  public TileAbstractWorkMachine(final BlockEntityType type, BlockPos position, BlockState blockstate,
                                 final MachineState initial_state, final MachineData data){
    super(type, position, blockstate, new Receiver(data.get_total_energy_needed(), data.get_max_receive()));
    this.state = initial_state;
  }

  @Override
  public void load(final CompoundTag nbt){
    super.load(nbt);
    state = MachineState.value[nbt.getInt("State")];
  }

  @Override
  protected void saveAdditional(final CompoundTag nbt){
    super.saveAdditional(nbt);
    nbt.putInt("State", state.ordinal());
  }

  protected abstract void machine_tick();

  public final float getWorkTimePercentage(){
    return energy.getEnergyPercentage();
  }

  @Override
  public final double getAvailableEnergy(){
    return 0;
  }

  public final MachineState getState(){
    return state;
  }

  public String getStatus(){
    return state.getStatus();
  }

}
