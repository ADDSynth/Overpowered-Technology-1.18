package addsynth.energy.lib.tiles.energy;

import addsynth.core.game.tiles.TileBase;
import addsynth.core.util.game.tileentity.ITickingTileEntity;
import addsynth.energy.lib.main.Generator;
import addsynth.energy.lib.main.IEnergyGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** This TileEntity is for machines that generate Energy all on their own. */
public abstract class TileAbstractGenerator extends TileBase implements IEnergyGenerator, ITickingTileEntity {

  protected boolean changed;
  protected final Generator energy;

  public TileAbstractGenerator(final BlockEntityType type, BlockPos position, BlockState blockstate){
    super(type, position, blockstate);
    this.energy = new Generator();
  }

  @Override
  public void serverTick(){
    if(energy.isEmpty()){
      setGeneratorData();
      changed = true;
    }
    if(energy.tick()){
      changed = true;
    }
    if(changed){
      update_data();
      changed = false;
    }
  }

  @Override
  public void load(final CompoundTag nbt){
    super.load(nbt);
    energy.loadFromNBT(nbt);
  }

  @Override
  protected void saveAdditional(final CompoundTag nbt){
    super.saveAdditional(nbt);
    energy.saveToNBT(nbt);
  }
  
  protected abstract void setGeneratorData();

  @Override
  public Generator getEnergy(){
    return energy;
  }

}
