package addsynth.overpoweredmod.machines.data_cable;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.core.game.tiles.TileBase;
import addsynth.core.util.game.tileentity.ITickingTileEntity;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public final class TileDataCable extends TileBase implements IBlockNetworkUser<DataCableNetwork>, ITickingTileEntity {

  private DataCableNetwork cable_network;

  public TileDataCable(BlockPos position, BlockState blockstate){
    super(Tiles.DATA_CABLE, position, blockstate);
  }

  @Override
  public final void serverTick(){
    if(cable_network == null){
      BlockNetworkUtil.create_or_join(level, this, DataCableNetwork::new);
    }
  }

  @Override
  public final void setRemoved(){
    super.setRemoved();
    // BlockNetworkUtil.tileentity_was_removed(this, DataCableNetwork::new);
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
