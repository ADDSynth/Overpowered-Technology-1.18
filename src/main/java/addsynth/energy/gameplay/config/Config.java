package addsynth.energy.gameplay.config;

import org.apache.commons.lang3.tuple.Pair;
import addsynth.energy.lib.config.MachineDataConfig;
import addsynth.energy.lib.config.MachineType;
import addsynth.energy.lib.config.SimpleBatteryConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public final class Config {

  public static final MachineDataConfig compressor         = new MachineDataConfig("Compressor",         MachineType.ALWAYS_ON,  200, 10, 0, 0); // 2,000
  public static final MachineDataConfig circuit_fabricator = new MachineDataConfig("Circuit Fabricator", MachineType.ALWAYS_ON, 1000, 25, 0, 0);

  public static final SimpleBatteryConfig energy_storage = new SimpleBatteryConfig("Energy Storage Block", 200_000, 100);

  private static final int DEFAULT_UNIVERSAL_ENERGY_INTERFACE_BUFFER = 1_000;
  public static ForgeConfigSpec.IntValue     universal_energy_interface_buffer;

  private static final Pair<Config, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(Config::new);
  public static final Config INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

  public Config(final ForgeConfigSpec.Builder builder){
  
    compressor.build(builder);
    circuit_fabricator.build(builder);
    
    energy_storage.build(builder);
    
    builder.push("Universal Energy Interface");
    universal_energy_interface_buffer     = builder.defineInRange("Universal Energy Interface Buffer",
                                              DEFAULT_UNIVERSAL_ENERGY_INTERFACE_BUFFER, 0, Integer.MAX_VALUE);
    builder.pop();
  }

}
