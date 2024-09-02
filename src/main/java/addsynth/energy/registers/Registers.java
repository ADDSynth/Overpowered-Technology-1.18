package addsynth.energy.registers;

import addsynth.core.game.registry.RegistryUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.machines.circuit_fabricator.CircuitFabricatorBlock;
import addsynth.energy.gameplay.machines.circuit_fabricator.CircuitFabricatorContainer;
import addsynth.energy.gameplay.machines.circuit_fabricator.TileCircuitFabricator;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipeSerializer;
import addsynth.energy.gameplay.machines.compressor.CompressorBlock;
import addsynth.energy.gameplay.machines.compressor.ContainerCompressor;
import addsynth.energy.gameplay.machines.compressor.TileCompressor;
import addsynth.energy.gameplay.machines.compressor.recipe.CompressorRecipeSerializer;
import addsynth.energy.gameplay.machines.electric_furnace.ContainerElectricFurnace;
import addsynth.energy.gameplay.machines.electric_furnace.ElectricFurnaceBlock;
import addsynth.energy.gameplay.machines.electric_furnace.TileElectricFurnace;
import addsynth.energy.gameplay.machines.energy_diagnostics.EnergyDiagnosticsBlock;
import addsynth.energy.gameplay.machines.energy_diagnostics.TileEnergyDiagnostics;
import addsynth.energy.gameplay.machines.energy_storage.ContainerEnergyStorage;
import addsynth.energy.gameplay.machines.energy_storage.EnergyStorageBlock;
import addsynth.energy.gameplay.machines.energy_storage.TileEnergyStorage;
import addsynth.energy.gameplay.machines.energy_wire.EnergyWire;
import addsynth.energy.gameplay.machines.energy_wire.TileEnergyWire;
import addsynth.energy.gameplay.machines.generator.ContainerGenerator;
import addsynth.energy.gameplay.machines.generator.GeneratorBlock;
import addsynth.energy.gameplay.machines.generator.TileGenerator;
import addsynth.energy.gameplay.machines.universal_energy_interface.ContainerUniversalEnergyInterface;
import addsynth.energy.gameplay.machines.universal_energy_interface.TileUniversalEnergyInterface;
import addsynth.energy.gameplay.machines.universal_energy_interface.UniversalEnergyInterfaceBlock;
import addsynth.energy.gameplay.reference.Names;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = ADDSynthEnergy.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class Registers {

  @SubscribeEvent
  public static final void registerBlocks(final RegistryEvent.Register<Block> event){
    final IForgeRegistry<Block> game = event.getRegistry();

    game.register(new EnergyWire());
    game.register(new GeneratorBlock());
    game.register(new EnergyStorageBlock());
    game.register(new CompressorBlock());
    game.register(new ElectricFurnaceBlock());
    game.register(new CircuitFabricatorBlock());
    game.register(new UniversalEnergyInterfaceBlock());
    game.register(new EnergyDiagnosticsBlock());
  }

  @SubscribeEvent
  public static final void registerItems(final RegistryEvent.Register<Item> event){
    final IForgeRegistry<Item> game = event.getRegistry();

    game.register(RegistryUtil.createItemBlock(EnergyBlocks.wire,                     ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItemBlock(EnergyBlocks.generator,                ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItemBlock(EnergyBlocks.energy_storage,           ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItemBlock(EnergyBlocks.compressor,               ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItemBlock(EnergyBlocks.electric_furnace,         ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItemBlock(EnergyBlocks.circuit_fabricator,       ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItemBlock(EnergyBlocks.universal_energy_machine, ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItemBlock(EnergyBlocks.energy_diagnostics_block, ADDSynthEnergy.creative_tab));

    game.register(RegistryUtil.createItem(Names.POWER_CORE,          ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItem(Names.ADVANCED_POWER_CORE, ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItem(Names.POWER_REGULATOR,     ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItem(Names.CIRCUIT_TIER_1,      ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItem(Names.CIRCUIT_TIER_2,      ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItem(Names.CIRCUIT_TIER_3,      ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItem(Names.CIRCUIT_TIER_4,      ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItem(Names.CIRCUIT_TIER_5,      ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItem(Names.CIRCUIT_TIER_6,      ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItem(Names.CIRCUIT_TIER_7,      ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItem(Names.CIRCUIT_TIER_8,      ADDSynthEnergy.creative_tab));
    game.register(RegistryUtil.createItem(Names.CIRCUIT_TIER_9,      ADDSynthEnergy.creative_tab));
  }

  @SubscribeEvent
  public static final void registerTileEntities(final RegistryEvent.Register<BlockEntityType<?>> event){
    final IForgeRegistry<BlockEntityType<?>> game = event.getRegistry();
    game.register(RegistryUtil.createBlockEntityType(TileEnergyWire::new,               EnergyBlocks.wire));
    game.register(RegistryUtil.createBlockEntityType(TileGenerator::new,                EnergyBlocks.generator));
    game.register(RegistryUtil.createBlockEntityType(TileEnergyStorage::new,            EnergyBlocks.energy_storage));
    game.register(RegistryUtil.createBlockEntityType(TileCompressor::new,               EnergyBlocks.compressor));
    game.register(RegistryUtil.createBlockEntityType(TileElectricFurnace::new,          EnergyBlocks.electric_furnace));
    game.register(RegistryUtil.createBlockEntityType(TileCircuitFabricator::new,        EnergyBlocks.circuit_fabricator));
    game.register(RegistryUtil.createBlockEntityType(TileUniversalEnergyInterface::new, EnergyBlocks.universal_energy_machine));
    game.register(RegistryUtil.createBlockEntityType(TileEnergyDiagnostics::new,        EnergyBlocks.energy_diagnostics_block));
  }

  @SubscribeEvent
  public static final void registerContainers(final RegistryEvent.Register<MenuType<?>> event){
    final IForgeRegistry<MenuType<?>> game = event.getRegistry();
    game.register(RegistryUtil.createContainerType(ContainerGenerator::new,                Names.GENERATOR));
    game.register(RegistryUtil.createContainerType(ContainerEnergyStorage::new,            Names.ENERGY_STORAGE));
    game.register(RegistryUtil.createContainerType(ContainerCompressor::new,               Names.COMPRESSOR));
    game.register(RegistryUtil.createContainerType(ContainerElectricFurnace::new,          Names.ELECTRIC_FURNACE));
    game.register(RegistryUtil.createContainerType(CircuitFabricatorContainer::new,        Names.CIRCUIT_FABRICATOR));
    game.register(RegistryUtil.createContainerType(ContainerUniversalEnergyInterface::new, Names.UNIVERSAL_ENERGY_INTERFACE));
  }

  @SubscribeEvent
  public static final void registerRecipeSerializers(final RegistryEvent.Register<RecipeSerializer<?>> event){
    final IForgeRegistry<RecipeSerializer<?>> game = event.getRegistry();
    game.register(new CompressorRecipeSerializer());
    game.register(new CircuitFabricatorRecipeSerializer());
  }

}
