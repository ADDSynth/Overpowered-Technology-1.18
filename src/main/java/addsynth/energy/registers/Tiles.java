package addsynth.energy.registers;

import addsynth.energy.gameplay.machines.circuit_fabricator.TileCircuitFabricator;
import addsynth.energy.gameplay.machines.compressor.TileCompressor;
import addsynth.energy.gameplay.machines.electric_furnace.TileElectricFurnace;
import addsynth.energy.gameplay.machines.energy_diagnostics.TileEnergyDiagnostics;
import addsynth.energy.gameplay.machines.energy_storage.TileEnergyStorage;
import addsynth.energy.gameplay.machines.energy_wire.TileEnergyWire;
import addsynth.energy.gameplay.machines.generator.TileGenerator;
import addsynth.energy.gameplay.machines.universal_energy_interface.TileUniversalEnergyInterface;
import addsynth.energy.gameplay.reference.Names;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class Tiles {

  public static final RegistryObject<BlockEntityType<TileEnergyWire>> ENERGY_WIRE =
    RegistryObject.create(Names.ENERGY_WIRE, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileGenerator>> GENERATOR =
    RegistryObject.create(Names.GENERATOR, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileEnergyStorage>> ENERGY_CONTAINER =
    RegistryObject.create(Names.ENERGY_STORAGE, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileCompressor>> COMPRESSOR =
    RegistryObject.create(Names.COMPRESSOR, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileElectricFurnace>> ELECTRIC_FURNACE =
    RegistryObject.create(Names.ELECTRIC_FURNACE, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileCircuitFabricator>> CIRCUIT_FABRICATOR =
    RegistryObject.create(Names.CIRCUIT_FABRICATOR, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileUniversalEnergyInterface>> UNIVERSAL_ENERGY_INTERFACE =
    RegistryObject.create(Names.UNIVERSAL_ENERGY_INTERFACE, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileEnergyDiagnostics>> ENERGY_DIAGNOSTICS_BLOCK =
    RegistryObject.create(Names.ENERGY_DIAGNOSTICS_BLOCK, ForgeRegistries.BLOCK_ENTITIES);

}
