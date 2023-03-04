package addsynth.overpoweredmod.compatability;

import addsynth.core.compat.Compatibility;
import addsynth.overpoweredmod.compatability.curios.OverpoweredCurios;
import addsynth.overpoweredmod.config.Features;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public final class CompatabilityManager {

  public static final void inter_mod_communications(final InterModEnqueueEvent event){
    if(Compatibility.PROJECT_E.loaded){
      ProjectE.register_emc_values();
    }
    if(Compatibility.CURIOS.loaded){
      OverpoweredCurios.register_slots();
    }
  }

  public static final boolean are_rings_enabled(){
    // could be a field, but a function can return a different value if these are changed during runtime.
    return Compatibility.CURIOS.loaded && Features.rings.get();
  }

}
