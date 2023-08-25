package addsynth.energy.registers;

import addsynth.core.game.RegistryUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.EnergyItems;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipes;
import addsynth.energy.gameplay.machines.compressor.recipe.CompressorRecipes;
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

    game.register(EnergyBlocks.wire);
    game.register(EnergyBlocks.generator);
    game.register(EnergyBlocks.energy_storage);
    game.register(EnergyBlocks.compressor);
    game.register(EnergyBlocks.electric_furnace);
    game.register(EnergyBlocks.circuit_fabricator);
    game.register(EnergyBlocks.universal_energy_machine);
    game.register(EnergyBlocks.energy_diagnostics_block);
  }

  @SubscribeEvent
  public static final void registerItems(final RegistryEvent.Register<Item> event){
    final IForgeRegistry<Item> game = event.getRegistry();

    game.register(RegistryUtil.getItemBlock(EnergyBlocks.wire));
    game.register(RegistryUtil.getItemBlock(EnergyBlocks.generator));
    game.register(RegistryUtil.getItemBlock(EnergyBlocks.energy_storage));
    game.register(RegistryUtil.getItemBlock(EnergyBlocks.compressor));
    game.register(RegistryUtil.getItemBlock(EnergyBlocks.electric_furnace));
    game.register(RegistryUtil.getItemBlock(EnergyBlocks.circuit_fabricator));
    game.register(RegistryUtil.getItemBlock(EnergyBlocks.universal_energy_machine));
    game.register(RegistryUtil.getItemBlock(EnergyBlocks.energy_diagnostics_block));

    game.register(EnergyItems.power_core);
    game.register(EnergyItems.advanced_power_core);
    game.register(EnergyItems.power_regulator);
    game.register(EnergyItems.circuit_tier_1);
    game.register(EnergyItems.circuit_tier_2);
    game.register(EnergyItems.circuit_tier_3);
    game.register(EnergyItems.circuit_tier_4);
    game.register(EnergyItems.circuit_tier_5);
    game.register(EnergyItems.circuit_tier_6);
    game.register(EnergyItems.circuit_tier_7);
    game.register(EnergyItems.circuit_tier_8);
    game.register(EnergyItems.circuit_tier_9);
  }

  @SubscribeEvent
  public static final void registerTileEntities(final RegistryEvent.Register<BlockEntityType<?>> event){
    final IForgeRegistry<BlockEntityType<?>> game = event.getRegistry();
    RegistryUtil.register(game, Tiles.ENERGY_WIRE,                Names.ENERGY_WIRE);
    RegistryUtil.register(game, Tiles.GENERATOR,                  Names.GENERATOR);
    RegistryUtil.register(game, Tiles.ENERGY_CONTAINER,           Names.ENERGY_STORAGE);
    RegistryUtil.register(game, Tiles.COMPRESSOR,                 Names.COMPRESSOR);
    RegistryUtil.register(game, Tiles.ELECTRIC_FURNACE,           Names.ELECTRIC_FURNACE);
    RegistryUtil.register(game, Tiles.CIRCUIT_FABRICATOR,         Names.CIRCUIT_FABRICATOR);
    RegistryUtil.register(game, Tiles.UNIVERSAL_ENERGY_INTERFACE, Names.UNIVERSAL_ENERGY_INTERFACE);
    RegistryUtil.register(game, Tiles.ENERGY_DIAGNOSTICS_BLOCK,   Names.ENERGY_DIAGNOSTICS_BLOCK);
  }

  @SubscribeEvent
  public static final void registerContainers(final RegistryEvent.Register<MenuType<?>> event){
    final IForgeRegistry<MenuType<?>> game = event.getRegistry();
    RegistryUtil.register(game, Containers.GENERATOR,                  Names.GENERATOR);
    RegistryUtil.register(game, Containers.ENERGY_STORAGE_CONTAINER,   Names.ENERGY_STORAGE);
    RegistryUtil.register(game, Containers.COMPRESSOR,                 Names.COMPRESSOR);
    RegistryUtil.register(game, Containers.ELECTRIC_FURNACE,           Names.ELECTRIC_FURNACE);
    RegistryUtil.register(game, Containers.CIRCUIT_FABRICATOR,         Names.CIRCUIT_FABRICATOR);
    RegistryUtil.register(game, Containers.UNIVERSAL_ENERGY_INTERFACE, Names.UNIVERSAL_ENERGY_INTERFACE);
  }

  @SubscribeEvent
  public static final void registerRecipeSerializers(final RegistryEvent.Register<RecipeSerializer<?>> event){
    final IForgeRegistry<RecipeSerializer<?>> game = event.getRegistry();
    RegistryUtil.register(game,        CompressorRecipes.INSTANCE.serializer, Names.COMPRESSOR);
    RegistryUtil.register(game, CircuitFabricatorRecipes.INSTANCE.serializer, Names.CIRCUIT_FABRICATOR);
  }

}
