package addsynth.energy.gameplay;

import addsynth.energy.gameplay.machines.circuit_fabricator.CircuitFabricatorBlock;
import addsynth.energy.gameplay.machines.compressor.CompressorBlock;
import addsynth.energy.gameplay.machines.electric_furnace.ElectricFurnaceBlock;
import addsynth.energy.gameplay.machines.energy_diagnostics.EnergyDiagnosticsBlock;
import addsynth.energy.gameplay.machines.energy_storage.EnergyStorageBlock;
import addsynth.energy.gameplay.machines.energy_wire.EnergyWire;
import addsynth.energy.gameplay.machines.generator.GeneratorBlock;
import addsynth.energy.gameplay.machines.universal_energy_interface.UniversalEnergyInterfaceBlock;

public final class EnergyBlocks {

  public static final EnergyWire                    wire                     = new EnergyWire();
  public static final GeneratorBlock                generator                = new GeneratorBlock();
  public static final EnergyStorageBlock            energy_storage           = new EnergyStorageBlock();
  public static final CompressorBlock               compressor               = new CompressorBlock();
  public static final ElectricFurnaceBlock          electric_furnace         = new ElectricFurnaceBlock();
  public static final CircuitFabricatorBlock        circuit_fabricator       = new CircuitFabricatorBlock();
  public static final UniversalEnergyInterfaceBlock universal_energy_machine = new UniversalEnergyInterfaceBlock();
  public static final EnergyDiagnosticsBlock        energy_diagnostics_block = new EnergyDiagnosticsBlock();

}
