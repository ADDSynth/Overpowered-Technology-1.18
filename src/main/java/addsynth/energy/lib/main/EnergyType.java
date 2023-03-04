package addsynth.energy.lib.main;

import net.minecraft.world.level.block.entity.BlockEntity;

public enum EnergyType {

  GENERATOR(0),
  RECEIVER(2),
  BATTERY(1);

  public final int order;

  private EnergyType(int compare_order){
   this.order = compare_order;
  }

  public static final EnergyType determine(final BlockEntity tile){
    if(tile instanceof IEnergyGenerator){
      return GENERATOR;
    }
    if(tile instanceof IEnergyConsumer){
      return RECEIVER;
    }
    if(tile instanceof IEnergyUser){
      return BATTERY;
    }
    return null;
  }

}
