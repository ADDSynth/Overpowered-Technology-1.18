package addsynth.energy.lib.energy_network.tiles;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetwork;
import addsynth.energy.lib.energy_network.EnergyNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** This class defines a block that is part of an Energy Network. Machines that
 *  work with Energy must be connected to one of these types of blocks.
 *  This is mainly used by Energy Wires.
 * @author ADDSynth
 * @see addsynth.energy.gameplay.machines.universal_energy_interface.TileUniversalEnergyInterface
 */
public abstract class BasicEnergyNetworkTile extends AbstractEnergyNetworkTile {

  public BasicEnergyNetworkTile(final BlockEntityType type, BlockPos position, BlockState blockstate){
    super(type, position, blockstate);
  }

  @Override
  public void serverTick(){
    BlockNetwork.tick(network, level, this, EnergyNetwork::new);
  }

  @Override
  @Nullable
  public final EnergyNetwork getBlockNetwork(){
    return network;
  }

  @Override
  public final void setBlockNetwork(final EnergyNetwork network){
    this.network = network;
  }

}
