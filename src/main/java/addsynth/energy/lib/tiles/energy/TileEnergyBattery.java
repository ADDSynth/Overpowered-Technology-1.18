package addsynth.energy.lib.tiles.energy;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetwork;
import addsynth.energy.lib.energy_network.EnergyNetwork;
import addsynth.energy.lib.energy_network.tiles.AbstractEnergyNetworkTile;
import addsynth.energy.lib.main.Energy;
import addsynth.energy.lib.main.IEnergyUser;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** TileEntities that act as standard Batteries that should be part of the Energy Network
 *  should extend from this class.
 */
public abstract class TileEnergyBattery extends AbstractEnergyNetworkTile implements IEnergyUser {

  protected final Energy energy;

  public TileEnergyBattery(final BlockEntityType type, BlockPos position, BlockState blockstate, final Energy energy){
    super(type, position, blockstate);
    this.energy = energy;
  }

  @Override
  public void serverTick(){
    BlockNetwork.tick(network, level, this, EnergyNetwork::new);
    if(energy.tick()){
      update_data();
    }
  }

  @Override
  public void load(final CompoundTag nbt){
    super.load(nbt);
    if(energy != null){
      energy.loadFromNBT(nbt);
    }
  }

  @Override
  protected void saveAdditional(final CompoundTag nbt){
    super.saveAdditional(nbt);
    if(energy != null){
      energy.saveToNBT(nbt);
    }
  }

  @Override
  public final void setBlockNetwork(final EnergyNetwork network){
    this.network = network;
  }

  @Override
  @Nullable
  public final EnergyNetwork getBlockNetwork(){
    return network;
  }

  @Override
  public final Energy getEnergy(){
    return energy;
  }

}
