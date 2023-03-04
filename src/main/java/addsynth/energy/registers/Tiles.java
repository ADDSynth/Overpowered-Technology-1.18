package addsynth.energy.registers;

import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.machines.circuit_fabricator.TileCircuitFabricator;
import addsynth.energy.gameplay.machines.compressor.TileCompressor;
import addsynth.energy.gameplay.machines.electric_furnace.TileElectricFurnace;
import addsynth.energy.gameplay.machines.energy_diagnostics.TileEnergyDiagnostics;
import addsynth.energy.gameplay.machines.energy_storage.TileEnergyStorage;
import addsynth.energy.gameplay.machines.energy_wire.TileEnergyWire;
import addsynth.energy.gameplay.machines.generator.TileGenerator;
import addsynth.energy.gameplay.machines.universal_energy_interface.TileUniversalEnergyInterface;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class Tiles {

  public static final BlockEntityType<TileEnergyWire> ENERGY_WIRE =
    BlockEntityType.Builder.of(TileEnergyWire::new, EnergyBlocks.wire).build(null);

  public static final BlockEntityType<TileGenerator> GENERATOR =
    BlockEntityType.Builder.of(TileGenerator::new, EnergyBlocks.generator).build(null);

  public static final BlockEntityType<TileEnergyStorage> ENERGY_CONTAINER =
    BlockEntityType.Builder.of(TileEnergyStorage::new, EnergyBlocks.energy_storage).build(null);

  public static final BlockEntityType<TileCompressor> COMPRESSOR =
    BlockEntityType.Builder.of(TileCompressor::new, EnergyBlocks.compressor).build(null);

  public static final BlockEntityType<TileElectricFurnace> ELECTRIC_FURNACE =
    BlockEntityType.Builder.of(TileElectricFurnace::new, EnergyBlocks.electric_furnace).build(null);

  public static final BlockEntityType<TileCircuitFabricator> CIRCUIT_FABRICATOR =
    BlockEntityType.Builder.of(TileCircuitFabricator::new, EnergyBlocks.circuit_fabricator).build(null);

  public static final BlockEntityType<TileUniversalEnergyInterface> UNIVERSAL_ENERGY_INTERFACE =
    BlockEntityType.Builder.of(TileUniversalEnergyInterface::new, EnergyBlocks.universal_energy_machine).build(null);

  public static final BlockEntityType<TileEnergyDiagnostics> ENERGY_DIAGNOSTICS_BLOCK =
    BlockEntityType.Builder.of(TileEnergyDiagnostics::new, EnergyBlocks.energy_diagnostics_block).build(null);

}
