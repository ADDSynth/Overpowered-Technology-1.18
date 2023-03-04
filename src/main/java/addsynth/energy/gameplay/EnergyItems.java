package addsynth.energy.gameplay;

import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.reference.Names;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public final class EnergyItems {

  public static final Item            power_core               = register(Names.POWER_CORE);
  public static final Item            advanced_power_core      = register(Names.ADVANCED_POWER_CORE);
  public static final Item            power_regulator          = register(Names.POWER_REGULATOR);
  
  public static final Item            circuit_tier_1           = register(Names.CIRCUIT_TIER_1);
  public static final Item            circuit_tier_2           = register(Names.CIRCUIT_TIER_2);
  public static final Item            circuit_tier_3           = register(Names.CIRCUIT_TIER_3);
  public static final Item            circuit_tier_4           = register(Names.CIRCUIT_TIER_4);
  public static final Item            circuit_tier_5           = register(Names.CIRCUIT_TIER_5);
  public static final Item            circuit_tier_6           = register(Names.CIRCUIT_TIER_6);
  public static final Item            circuit_tier_7           = register(Names.CIRCUIT_TIER_7);
  public static final Item            circuit_tier_8           = register(Names.CIRCUIT_TIER_8);
  public static final Item            circuit_tier_9           = register(Names.CIRCUIT_TIER_9);
  public static final Item[] circuit = {
    circuit_tier_1, circuit_tier_2, circuit_tier_3, circuit_tier_4, circuit_tier_5,
    circuit_tier_6, circuit_tier_7, circuit_tier_8, circuit_tier_9
  };
  
  private static final Item register(final ResourceLocation name){
    final Item item = new Item(new Item.Properties().tab(ADDSynthEnergy.creative_tab));
    item.setRegistryName(name);
    return item;
  }
  
  private static final Item register(final ResourceLocation name, final Item.Properties properties){
    final Item item = new Item(properties);
    item.setRegistryName(name);
    return item;
  }
  
}
