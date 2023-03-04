package addsynth.energy.lib.tiles;

import addsynth.core.game.tiles.TileBase;
import addsynth.core.util.game.tileentity.ITickingTileEntity;
import addsynth.energy.lib.main.IEnergyConsumer;
import addsynth.energy.lib.main.Receiver;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** All machines that only receive energy to do work derive from this class. */
public abstract class TileAbstractMachine extends TileBase implements ITickingTileEntity, IEnergyConsumer {

  protected final Receiver energy;

  public TileAbstractMachine(final BlockEntityType type, BlockPos position, BlockState blockstate, final Receiver energy){
    super(type, position, blockstate);
    this.energy = energy;
  }

  @Override
  public void load(final CompoundTag nbt){
    super.load(nbt);
    if(energy != null){ energy.loadFromNBT(nbt);}
  }

  @Override
  protected void saveAdditional(final CompoundTag nbt){
    super.saveAdditional(nbt);
    if(energy != null){ energy.saveToNBT(nbt);}
  }

  @Override
  public Receiver getEnergy(){
    return energy;
  }

}
