package addsynth.energy.lib.energy_network;

import javax.annotation.Nonnull;
import addsynth.core.block_network.Node;
import addsynth.energy.lib.main.Energy;
import addsynth.energy.lib.main.IEnergyUser;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class EnergyNode extends Node {

  private final Energy energy;

  public <E extends BlockEntity & IEnergyUser> EnergyNode(@Nonnull final E tileEntity){
    super(tileEntity.getBlockPos(), tileEntity.getBlockState().getBlock(), tileEntity);
    this.energy = tileEntity.getEnergy();
  }

  public EnergyNode(@Nonnull final BlockEntity tileEntity, @Nonnull final Energy energy){
    super(tileEntity.getBlockPos(), tileEntity.getBlockState().getBlock(), tileEntity);
    this.energy = energy;
  }

  public final Energy getEnergy(){
    return energy;
  }

  @Override
  public boolean isInvalid(){
    if(block == null || position == null || tile == null || energy == null){
      return true;
    }
    return tile.isRemoved() || !tile.getBlockPos().equals(position);
  }

  @Override
  public String toString(){
    return "Node{TileEntity: "+(tile == null ? "null" : tile.getClass().getSimpleName())+", "+
                "Position: "+(position == null ? "null" : position.toString())+", "+
                (energy == null ? "Energy: null" : energy.toString())+"}";
  }

}
