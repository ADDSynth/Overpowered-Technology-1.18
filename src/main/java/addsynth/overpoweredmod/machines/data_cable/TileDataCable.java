package addsynth.overpoweredmod.machines.data_cable;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.core.game.tiles.TileBase;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public final class TileDataCable extends TileBase implements IBlockNetworkUser<DataCableNetwork> {

  private DataCableNetwork cable_network;

  public TileDataCable(BlockPos position, BlockState blockstate){
    super(Tiles.DATA_CABLE.get(), position, blockstate);
  }

  @Override
  public final void serverTick(){
    BlockNetwork.check(cable_network, level, this, DataCableNetwork::new);
  }

  @Override
  public final void setBlockNetwork(final DataCableNetwork network){
    this.cable_network = network;
  }

  @Override
  @Nullable
  public final DataCableNetwork getBlockNetwork(){
    return cable_network;
  }

}
