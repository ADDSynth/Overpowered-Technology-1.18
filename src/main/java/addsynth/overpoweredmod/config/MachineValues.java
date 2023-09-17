package addsynth.overpoweredmod.config;

import addsynth.energy.lib.config.MachineDataConfig;
import addsynth.energy.lib.config.MachineType;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class MachineValues {

  public static ForgeConfigSpec.IntValue energy_crystal_energy;
  public static ForgeConfigSpec.IntValue energy_crystal_max_extract;
  public static ForgeConfigSpec.IntValue energy_crystal_shards_energy;
  public static ForgeConfigSpec.IntValue energy_crystal_shards_max_extract;
  public static ForgeConfigSpec.IntValue light_block_energy;
  public static ForgeConfigSpec.IntValue light_block_max_extract;

  // Standard Machines
  public static final MachineDataConfig gem_converter = new MachineDataConfig("Gem Converter",    800,  28,    0.06 ,  60); // 22,400 for 40 seconds (allowing 12 conversions per Energy Crystal.)
  public static final MachineDataConfig inverter      = new MachineDataConfig("Inverter"     , 18_000,  15,    0.1  , 200); // 1 full energy crystal for 15 minutes
  public static final MachineDataConfig magic_infuser = new MachineDataConfig("Magic Infuser",  1_200,  50,    0.075,  60); // 60,000
  public static final MachineDataConfig identifier    = new MachineDataConfig("Identifier"   ,    500,  16,    0.05 ,  10); //  8,000

  // Passive Machines
  public static final MachineDataConfig crystal_matter_generator =
    new MachineDataConfig("Crystal Matter Generator", MachineType.PASSIVE, 16_000,  31.25, 0,  600); // 500,000 energy for 1 shard every 13.3 minutes
  public static final MachineDataConfig plasma_generator =
    new MachineDataConfig("Plasma Generator", MachineType.PASSIVE, 4_800, 50, 0.08, 300); // 50 energy per tick for 4 minutes = 240,000 Energy (1 Plasma per Energy Crystal.)
    // First Plasma will require 30 Coal or Charcoal to produce.

  // Manual Activation Machines
  public static final MachineDataConfig portal =
    new MachineDataConfig("Portal Control Panel", MachineType.MANUAL_ACTIVATION, 50_000, 100, 0, 1200); // 5 million Energy, 42 minutes to generate

  // Always On Machines
  public static final MachineDataConfig advanced_ore_refinery =
    new MachineDataConfig("Advanced Ore Refinery", MachineType.ALWAYS_ON, 400, 20, 0.1, 0); // 8,000

  public static ForgeConfigSpec.IntValue required_energy_per_laser;
  public static ForgeConfigSpec.IntValue required_energy_per_laser_distance;
  public static ForgeConfigSpec.IntValue laser_max_receive;

  public static ForgeConfigSpec.IntValue fusion_energy_output_per_tick;

  //    20,000      25,000     |    30,000 |        40,000
  //   180,000     225,000     |   270,000 |       360,000
  // 1,620,000   2,025,000     | 2,430,000 |     3,240,000
  private static final int DEFAULT_ENERGY_CRYSTAL_SHARDS_ENERGY      = 30_000;
  private static final int DEFAULT_ENERGY_CRYSTAL_SHARDS_MAX_EXTRACT = 40;
  private static final int DEFAULT_ENERGY_CRYSTAL_ENERGY             = DEFAULT_ENERGY_CRYSTAL_SHARDS_ENERGY * 9;
  private static final int DEFAULT_ENERGY_CRYSTAL_MAX_EXTRACT        = 100;
  private static final int DEFAULT_LIGHT_BLOCK_ENERGY                = DEFAULT_ENERGY_CRYSTAL_ENERGY * 9;
  private static final int DEFAULT_LIGHT_BLOCK_MAX_EXTRACT           = 500;

  private static final int DEFAULT_FUSION_ENERGY_PER_TICK = 100;

  private static final int DEFAULT_ENERGY_PER_LASER_CANNON   = 5_000;
  private static final int DEFAULT_ENERGY_PER_LASER_DISTANCE =   100;
  private static final int DEFAULT_LASER_MAX_RECEIVE = 1_000;

  private static final Pair<MachineValues, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(MachineValues::new);
  public static final MachineValues INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

  public MachineValues(final ForgeConfigSpec.Builder builder){

    builder.push("Crystal Energy Extractor");
    energy_crystal_shards_energy      = builder.defineInRange("Energy Crystal Shards Produced Energy",
                                          DEFAULT_ENERGY_CRYSTAL_SHARDS_ENERGY, 0, Integer.MAX_VALUE);
    energy_crystal_shards_max_extract = builder.defineInRange("Energy Crystal Shards Extract Rate",
                                          DEFAULT_ENERGY_CRYSTAL_SHARDS_MAX_EXTRACT, 0, Integer.MAX_VALUE);
    energy_crystal_energy             = builder.defineInRange("Energy Crystal Produced Energy",
                                          DEFAULT_ENERGY_CRYSTAL_ENERGY, 0, Integer.MAX_VALUE);
    energy_crystal_max_extract        = builder.defineInRange("Energy Crystal Extract Rate",
                                          DEFAULT_ENERGY_CRYSTAL_MAX_EXTRACT, 0, Integer.MAX_VALUE);
    light_block_energy                = builder.defineInRange("Light Block Produced Energy",
                                          DEFAULT_LIGHT_BLOCK_ENERGY, 0, Integer.MAX_VALUE);
    light_block_max_extract           = builder.defineInRange("Light Block Extract Rate",
                                          DEFAULT_LIGHT_BLOCK_MAX_EXTRACT, 0, Integer.MAX_VALUE);
    builder.pop();
    
    gem_converter.build(builder);
    inverter.build(builder);
    magic_infuser.build(builder);
    identifier.build(builder);
    portal.build(builder);
    
    builder.push("Laser");
    required_energy_per_laser          = builder.defineInRange("Energy per Laser Cannon",
                                              DEFAULT_ENERGY_PER_LASER_CANNON,   0, Integer.MAX_VALUE);
    required_energy_per_laser_distance = builder.defineInRange("Energy per Laser Distance",
                                              DEFAULT_ENERGY_PER_LASER_DISTANCE, 0, Integer.MAX_VALUE);
    laser_max_receive = builder.defineInRange("Max Receive Per Tick", DEFAULT_LASER_MAX_RECEIVE, 1, Integer.MAX_VALUE);
    builder.pop();

    plasma_generator.build(builder);
    crystal_matter_generator.build(builder);
    advanced_ore_refinery.build(builder);

    builder.push("Fusion Energy Converter");
    fusion_energy_output_per_tick = builder.defineInRange("Energy Produced per tick",
                                              DEFAULT_FUSION_ENERGY_PER_TICK, 0, Integer.MAX_VALUE);
    builder.pop();
    
  }

}
