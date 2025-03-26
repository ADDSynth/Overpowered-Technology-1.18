package addsynth.material.compat;

import addsynth.core.compat.Compatibility;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public final class MaterialsCompat {

  public static final boolean SteelModAbsent(){
    return !(
      Compatibility.IMMERSIVE_ENGINEERING.isLoaded() ||
      Compatibility.MEKANISM.isLoaded()
    );
  }

  public static final boolean BronzeModAbsent(){
    return !(
      Compatibility.IMMERSIVE_ENGINEERING.isLoaded() || // In Alloy Smelter
      Compatibility.MEKANISM.isLoaded()                 // by combining dusts
    );
  }

  public static final void sendIMCMessages(final InterModEnqueueEvent event){
    if(Compatibility.PROJECT_E.isLoaded()){
      ProjectE.register_emc_values();
    }
  }

}
