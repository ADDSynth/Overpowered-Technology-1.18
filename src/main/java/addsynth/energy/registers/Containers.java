package addsynth.energy.registers;

import addsynth.energy.gameplay.machines.circuit_fabricator.CircuitFabricatorContainer;
import addsynth.energy.gameplay.machines.compressor.ContainerCompressor;
import addsynth.energy.gameplay.machines.electric_furnace.ContainerElectricFurnace;
import addsynth.energy.gameplay.machines.energy_storage.ContainerEnergyStorage;
import addsynth.energy.gameplay.machines.generator.ContainerGenerator;
import addsynth.energy.gameplay.machines.universal_energy_interface.ContainerUniversalEnergyInterface;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.IContainerFactory;

public final class Containers {

  public static final MenuType<ContainerGenerator> GENERATOR =
    new MenuType<>((IContainerFactory<ContainerGenerator>)ContainerGenerator::new);

  public static final MenuType<ContainerCompressor> COMPRESSOR =
    new MenuType<>((IContainerFactory<ContainerCompressor>)ContainerCompressor::new);

  public static final MenuType<ContainerEnergyStorage> ENERGY_STORAGE_CONTAINER =
    new MenuType<>((IContainerFactory<ContainerEnergyStorage>)ContainerEnergyStorage::new);

  public static final MenuType<ContainerUniversalEnergyInterface> UNIVERSAL_ENERGY_INTERFACE =
    new MenuType<>((IContainerFactory<ContainerUniversalEnergyInterface>)ContainerUniversalEnergyInterface::new);

  public static final MenuType<ContainerElectricFurnace> ELECTRIC_FURNACE =
    new MenuType<>((IContainerFactory<ContainerElectricFurnace>)ContainerElectricFurnace::new);

  public static final MenuType<CircuitFabricatorContainer> CIRCUIT_FABRICATOR =
    new MenuType<>((IContainerFactory<CircuitFabricatorContainer>)CircuitFabricatorContainer::new);

}
