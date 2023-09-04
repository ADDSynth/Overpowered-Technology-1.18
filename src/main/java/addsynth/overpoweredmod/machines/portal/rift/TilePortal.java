package addsynth.overpoweredmod.machines.portal.rift;

import addsynth.core.game.tiles.TileBase;
import addsynth.core.util.game.tileentity.ITickingTileEntity;
import addsynth.core.util.time.TimeConstants;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class TilePortal extends TileBase implements ITickingTileEntity {

  private int count = 0;
  private final int life = Values.portal_spawn_time.get() * TimeConstants.ticks_per_second;

  public TilePortal(BlockPos position, BlockState blockstate){
    super(Tiles.PORTAL_RIFT.get(), position, blockstate);
  }

  @Override
  public final void serverTick(){
    count += 1;
    if(count >= life){
      final Level level = this.level;
      if(level != null){
        level.removeBlock(worldPosition, false);
      }
    }
  }

}
