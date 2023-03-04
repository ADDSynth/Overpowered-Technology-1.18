package addsynth.energy.compat.energy;

import java.util.ArrayList;
import addsynth.core.compat.Compatibility;
import addsynth.core.util.math.MathUtility;
import addsynth.energy.compat.energy.forge.ForgeEnergy;
import addsynth.energy.compat.energy.redstoneflux.RedstoneFluxEnergy;
import addsynth.energy.compat.energy.tesla.TeslaEnergy;
import addsynth.energy.lib.main.Energy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public final class EnergyCompat {

  public enum EnergySystem {
    FORGE(true),
    RF(Compatibility.REDSTONE_FLUX.loaded),
    TESLA(Compatibility.TESLA.loaded);
    
    public final boolean exists;
    
    public final void setError(final Exception e){
    }
    
    private EnergySystem(final boolean loaded){
      this.exists = loaded;
    }
  }
  
  public static final class CompatEnergyNode {
    public final EnergySystem type;
    public final Object energy;
    public final Direction side;
    
    public CompatEnergyNode(final EnergySystem type, final Object energy, final Direction side){
      this.type = type;
      this.energy = energy;
      this.side = side;
    }
  }
  
  public static final CompatEnergyNode[] getConnectedEnergy(final BlockPos position, final Level world){
    final ArrayList<CompatEnergyNode> nodes = new ArrayList<>(6);
    BlockEntity tile;
    Direction capability_side;
    // Object energy;

    for(Direction side : Direction.values()){
      tile = world.getBlockEntity(position.relative(side));
      if(tile != null){
        capability_side = side.getOpposite();

        // Forge Energy
        final IEnergyStorage energy = tile.getCapability(CapabilityEnergy.ENERGY, capability_side).orElse(null);
        if(energy != null){
          nodes.add(new CompatEnergyNode(EnergySystem.FORGE, energy, capability_side));
          continue;
        }
        
        try{
        
          // RF Energy
          if(EnergySystem.RF.exists){
            if(RedstoneFluxEnergy.check(tile)){
              nodes.add(new CompatEnergyNode(EnergySystem.RF, tile, capability_side));
              continue;
            }
          }
          
          // Tesla Energy
          if(EnergySystem.TESLA.exists){
            if(TeslaEnergy.check(tile)){
              continue;
            }
          }
        }
        catch(Exception e){
        }
      }
    }
    return nodes.toArray(new CompatEnergyNode[nodes.size()]);
  }

  public static final void acceptEnergy(final CompatEnergyNode[] nodes, final Energy our_energy){
    // get needed energy
    final int energy_needed = (int)our_energy.getEnergyNeeded();
    final int[] available_energy = new int[nodes.length];
    int i;

    // get available energy
    for(i = 0; i < nodes.length; i++){
      try{
        switch(nodes[i].type){
        case FORGE: available_energy[i] = ForgeEnergy       .get(nodes[i].energy, energy_needed, true);                break;
        case RF:    available_energy[i] = RedstoneFluxEnergy.get(nodes[i].energy, energy_needed, true, nodes[i].side); break;
        case TESLA: available_energy[i] = TeslaEnergy       .get(nodes[i].energy, energy_needed, true);                break;
        }
      }
      catch(Exception e){
        nodes[i].type.setError(e);
      }
    }

    // set requested amount for each energy source
    final int[] energy_to_extract = MathUtility.divide_evenly(energy_needed, available_energy);

    // extract energy
    for(i = 0; i < nodes.length; i++){
      try{
        switch(nodes[i].type){
        case FORGE: ForgeEnergy       .get(nodes[i].energy, energy_to_extract[i], false);                break;
        case RF:    RedstoneFluxEnergy.get(nodes[i].energy, energy_to_extract[i], false, nodes[i].side); break;
        case TESLA: TeslaEnergy       .get(nodes[i].energy, energy_to_extract[i], false);                break;
        }
        our_energy.receiveEnergy(energy_to_extract[i]);
      }
      catch(Exception e){
        nodes[i].type.setError(e);
      }
    }
  }

  public static final void transmitEnergy(final CompatEnergyNode[] nodes, final Energy our_energy){
    // get available energy, divide evenly amongst the number of external machines.
    final int[] energy_available = MathUtility.divide_evenly((int)our_energy.getEnergy(), nodes.length);
    int actual_energy_extracted = 0;
    int i;
    // attempt to insert energy into external machines, record energy that was really transferred.
    for(i = 0; i < nodes.length; i++){
      try{
        switch(nodes[i].type){
        case FORGE: actual_energy_extracted = ForgeEnergy.send(       nodes[i].energy, energy_available[i]);                break;
        case RF:    actual_energy_extracted = RedstoneFluxEnergy.send(nodes[i].energy, energy_available[i], nodes[i].side); break;
        case TESLA: actual_energy_extracted = TeslaEnergy.send(       nodes[i].energy, energy_available[i]);                break;
        }
        // decrease internal energy by the amount that was actually transferred.
        our_energy.extractEnergy(actual_energy_extracted);
      }
      catch(Exception e){
        nodes[i].type.setError(e);
      }
    }
  }
  
}
