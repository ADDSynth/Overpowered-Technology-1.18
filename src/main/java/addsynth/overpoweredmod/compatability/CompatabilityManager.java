package addsynth.overpoweredmod.compatability;

import addsynth.core.compat.Compatibility;
import addsynth.overpoweredmod.compatability.curios.OverpoweredCurios;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public final class CompatabilityManager {

  public static final void inter_mod_communications(final InterModEnqueueEvent event){
    if(Compatibility.PROJECT_E.isLoaded()){
      ProjectE.register_emc_values();
    }
    if(Compatibility.CURIOS.isLoaded()){
      OverpoweredCurios.register_slots();
    }
  }

}
