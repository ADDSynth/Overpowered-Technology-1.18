package addsynth.energy.lib.energy_network.tiles;

import javax.annotation.Nullable;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.core.game.tiles.TileBase;
import addsynth.energy.gameplay.machines.energy_wire.EnergyWire;
import addsynth.energy.lib.energy_network.EnergyNetwork;
import addsynth.energy.lib.tiles.energy.TileEnergyBattery;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** TileEntities that want to be part of the Energy Network, but NOT UPDATE IT,
 * are derived from this. This is mainly used by {@link TileEnergyBattery}s.
 * This is also used to determine which blocks the {@link EnergyWire} can
 * connect to.
 * @author ADDSynth
 */
public abstract class AbstractEnergyNetworkTile extends TileBase implements IBlockNetworkUser<EnergyNetwork> {

  @Nullable
  protected EnergyNetwork network;

  public AbstractEnergyNetworkTile(final BlockEntityType type, BlockPos position, BlockState blockstate){
    super(type, position, blockstate);
  }

}
