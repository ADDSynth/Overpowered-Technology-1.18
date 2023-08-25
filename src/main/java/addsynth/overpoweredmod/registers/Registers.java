package addsynth.overpoweredmod.registers;

import addsynth.core.compat.Compatibility;
import addsynth.core.game.RegistryUtil;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.Sounds;
import addsynth.overpoweredmod.game.core.*;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import addsynth.overpoweredmod.items.Ring;
import addsynth.overpoweredmod.items.UnidentifiedItem;
import addsynth.overpoweredmod.machines.magic_infuser.recipes.MagicInfuserRecipes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = OverpoweredTechnology.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class Registers {

  @SubscribeEvent
  public static final void registerBlocks(final RegistryEvent.Register<Block> event){
    OverpoweredTechnology.log.info("Begin Block Registration Event...");
    
    final IForgeRegistry<Block> game = event.getRegistry();
    
    game.register(OverpoweredBlocks.light_block);
    game.register(OverpoweredBlocks.null_block);
    game.register(OverpoweredBlocks.iron_frame_block);
    game.register(OverpoweredBlocks.black_hole);
    
    game.register(OverpoweredBlocks.data_cable);
    game.register(OverpoweredBlocks.crystal_energy_extractor);
    game.register(OverpoweredBlocks.gem_converter);
    game.register(OverpoweredBlocks.inverter);
    game.register(OverpoweredBlocks.magic_infuser);
    game.register(OverpoweredBlocks.identifier);
    
    game.register(OverpoweredBlocks.energy_suspension_bridge);
    game.register(OverpoweredBlocks.white_energy_bridge);
    game.register(OverpoweredBlocks.red_energy_bridge);
    game.register(OverpoweredBlocks.orange_energy_bridge);
    game.register(OverpoweredBlocks.yellow_energy_bridge);
    game.register(OverpoweredBlocks.green_energy_bridge);
    game.register(OverpoweredBlocks.cyan_energy_bridge);
    game.register(OverpoweredBlocks.blue_energy_bridge);
    game.register(OverpoweredBlocks.magenta_energy_bridge);
    
    game.register(OverpoweredBlocks.portal_control_panel);
    game.register(OverpoweredBlocks.portal_frame);
    game.register(OverpoweredBlocks.portal);
    game.register(OverpoweredBlocks.unknown_wood);
    game.register(OverpoweredBlocks.unknown_leaves);
    
    game.register(OverpoweredBlocks.plasma_generator);
    game.register(OverpoweredBlocks.crystal_matter_generator);
    game.register(OverpoweredBlocks.advanced_ore_refinery);
    
    game.register(OverpoweredBlocks.laser_housing);
    game.register(Laser.WHITE.cannon);   game.register(Laser.WHITE.beam);
    game.register(Laser.RED.cannon);     game.register(Laser.RED.beam);
    game.register(Laser.ORANGE.cannon);  game.register(Laser.ORANGE.beam);
    game.register(Laser.YELLOW.cannon);  game.register(Laser.YELLOW.beam);
    game.register(Laser.GREEN.cannon);   game.register(Laser.GREEN.beam);
    game.register(Laser.CYAN.cannon);    game.register(Laser.CYAN.beam);
    game.register(Laser.BLUE.cannon);    game.register(Laser.BLUE.beam);
    game.register(Laser.MAGENTA.cannon); game.register(Laser.MAGENTA.beam);
    
    game.register(OverpoweredBlocks.fusion_converter);
    game.register(OverpoweredBlocks.fusion_control_unit);
    game.register(OverpoweredBlocks.fusion_chamber);
    game.register(OverpoweredBlocks.fusion_control_laser);
    game.register(OverpoweredBlocks.fusion_control_laser_beam);
    
    game.register(OverpoweredBlocks.matter_compressor);
    
    OverpoweredTechnology.log.info("Finished Block Registration Event.");
  }

  @SubscribeEvent
  public static final void registerItems(final RegistryEvent.Register<Item> event){
    OverpoweredTechnology.log.info("Begin Item Registration Event...");
    
    final IForgeRegistry<Item> game = event.getRegistry();

    game.register(OverpoweredItems.celestial_gem);
    game.register(OverpoweredItems.energy_crystal_shards);
    game.register(OverpoweredItems.energy_crystal);
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.light_block));
    game.register(OverpoweredItems.void_crystal);
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.null_block));
    
    game.register(OverpoweredItems.energized_power_core);
    game.register(OverpoweredItems.nullified_power_core);
    game.register(OverpoweredItems.energy_grid);
    game.register(OverpoweredItems.vacuum_container);
    game.register(OverpoweredItems.reinforced_container);
    
    game.register(OverpoweredItems.beam_emitter);
    game.register(OverpoweredItems.scanning_laser);
    game.register(OverpoweredItems.destructive_laser);
    game.register(OverpoweredItems.heavy_light_emitter);
    game.register(OverpoweredItems.energy_stabilizer);
    game.register(OverpoweredItems.matter_energy_transformer);
    game.register(OverpoweredItems.high_frequency_beam);
    
    for(Lens lens : Lens.values()){
      game.register(lens.lens);
    }
    game.register(OverpoweredItems.plasma);
    game.register(OverpoweredItems.fusion_core);
    game.register(OverpoweredItems.matter_energy_converter);
    game.register(OverpoweredItems.dimensional_flux);
    game.register(OverpoweredItems.dimensional_anchor);
    game.register(OverpoweredItems.unimatter);
    
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.data_cable));
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.crystal_energy_extractor));
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.gem_converter));
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.inverter));
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.magic_infuser));
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.identifier));
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.energy_suspension_bridge));
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.portal_control_panel));
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.portal_frame));
    // MAYBE: register Item versions of the unknown / weird tree  (but item order is specific. don't register them here.)
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.plasma_generator));
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.crystal_matter_generator));
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.advanced_ore_refinery));
    
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.laser_housing));
    game.register(RegistryUtil.getItemBlock(Laser.WHITE.cannon));
    game.register(RegistryUtil.getItemBlock(Laser.RED.cannon));
    game.register(RegistryUtil.getItemBlock(Laser.ORANGE.cannon));
    game.register(RegistryUtil.getItemBlock(Laser.YELLOW.cannon));
    game.register(RegistryUtil.getItemBlock(Laser.GREEN.cannon));
    game.register(RegistryUtil.getItemBlock(Laser.CYAN.cannon));
    game.register(RegistryUtil.getItemBlock(Laser.BLUE.cannon));
    game.register(RegistryUtil.getItemBlock(Laser.MAGENTA.cannon));
    
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.fusion_converter));
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.fusion_control_unit));
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.fusion_chamber));
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.fusion_control_laser));
    
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.matter_compressor));
    
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.iron_frame_block));
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.black_hole));
    
    game.register(OverpoweredItems.celestial_sword);
    game.register(OverpoweredItems.celestial_shovel);
    game.register(OverpoweredItems.celestial_pickaxe);
    game.register(OverpoweredItems.celestial_axe);
    game.register(OverpoweredItems.celestial_hoe);
    
    game.register(OverpoweredItems.void_sword);
    game.register(OverpoweredItems.void_shovel);
    game.register(OverpoweredItems.void_pickaxe);
    game.register(OverpoweredItems.void_axe);
    game.register(OverpoweredItems.void_hoe);
    
    for(Item[] armor_set : OverpoweredItems.unidentified_armor){
      for(Item armor : armor_set){ game.register(armor); }
    }
    if(Compatibility.CURIOS.loaded){
      game.register(new UnidentifiedItem(0));
      game.register(new UnidentifiedItem(1));
      game.register(new UnidentifiedItem(2));
      game.register(new UnidentifiedItem(3));
      game.register(new Ring(0));
      game.register(new Ring(1));
      game.register(new Ring(2));
      game.register(new Ring(3));
    }
    
    // Items for advancements only
    game.register(OverpoweredItems.portal_image);
    game.register(OverpoweredItems.bridge_image);

    OverpoweredTechnology.log.info("Finished Item Registration Event.");
  }

  @SubscribeEvent
  public static final void registerTileEntities(final RegistryEvent.Register<BlockEntityType<?>> event){
    /*
      https://github.com/MinecraftForge/MinecraftForge/pull/4681#issuecomment-405115908
      TODO: If anyone needs an example of how to fix the warning caused by this change without breaking old saved games,
      I (not ADDSynth, someone else) just updated all of McJty's mods to use a DataFixer to do so.
    */
    final IForgeRegistry<BlockEntityType<?>> game = event.getRegistry();

    RegistryUtil.register(game, Tiles.CRYSTAL_ENERGY_EXTRACTOR,   Names.CRYSTAL_ENERGY_EXTRACTOR);
    RegistryUtil.register(game, Tiles.GEM_CONVERTER,              Names.GEM_CONVERTER);
    RegistryUtil.register(game, Tiles.INVERTER,                   Names.INVERTER);
    RegistryUtil.register(game, Tiles.MAGIC_INFUSER,              Names.MAGIC_INFUSER);
    RegistryUtil.register(game, Tiles.IDENTIFIER,                 Names.IDENTIFIER);
    RegistryUtil.register(game, Tiles.ENERGY_SUSPENSION_BRIDGE,   Names.ENERGY_SUSPENSION_BRIDGE);
    RegistryUtil.register(game, Tiles.LASER_MACHINE,              Names.LASER_HOUSING);
    RegistryUtil.register(game, Tiles.DATA_CABLE,                 Names.DATA_CABLE);
    RegistryUtil.register(game, Tiles.PORTAL_CONTROL_PANEL,       Names.PORTAL_CONTROL_PANEL);
    RegistryUtil.register(game, Tiles.PORTAL_FRAME,               Names.PORTAL_FRAME);
    RegistryUtil.register(game, Tiles.PORTAL_BLOCK,               Names.PORTAL_RIFT);
    RegistryUtil.register(game, Tiles.PLASMA_GENERATOR,           Names.PLASMA_GENERATOR);
    RegistryUtil.register(game, Tiles.CRYSTAL_MATTER_REPLICATOR,  Names.CRYSTAL_MATTER_GENERATOR);
    RegistryUtil.register(game, Tiles.ADVANCED_ORE_REFINERY,      Names.ADVANCED_ORE_REFINERY);
    RegistryUtil.register(game, Tiles.FUSION_ENERGY_CONVERTER,    Names.FUSION_CONVERTER);
    RegistryUtil.register(game, Tiles.FUSION_CHAMBER,             Names.FUSION_CHAMBER);
    RegistryUtil.register(game, Tiles.BLACK_HOLE,                 Names.BLACK_HOLE);
    RegistryUtil.register(game, Tiles.MATTER_COMPRESSOR,          Names.MATTER_COMPRESSOR);
  }

  @SubscribeEvent
  public static final void registerContainers(final RegistryEvent.Register<MenuType<?>> event){
    final IForgeRegistry<MenuType<?>> game = event.getRegistry();
    
    RegistryUtil.register(game, Containers.CRYSTAL_ENERGY_EXTRACTOR,   Names.CRYSTAL_ENERGY_EXTRACTOR);
    RegistryUtil.register(game, Containers.GEM_CONVERTER,              Names.GEM_CONVERTER);
    RegistryUtil.register(game, Containers.INVERTER,                   Names.INVERTER);
    RegistryUtil.register(game, Containers.IDENTIFIER,                 Names.IDENTIFIER);
    RegistryUtil.register(game, Containers.MAGIC_INFUSER,              Names.MAGIC_INFUSER);
    RegistryUtil.register(game, Containers.ENERGY_SUSPENSION_BRIDGE,   Names.ENERGY_SUSPENSION_BRIDGE);
    RegistryUtil.register(game, Containers.LASER_HOUSING,              Names.LASER_HOUSING);
    RegistryUtil.register(game, Containers.PLASMA_GENERATOR,           Names.PLASMA_GENERATOR);
    RegistryUtil.register(game, Containers.ADVANCED_ORE_REFINERY,      Names.ADVANCED_ORE_REFINERY);
    RegistryUtil.register(game, Containers.CRYSTAL_MATTER_GENERATOR,   Names.CRYSTAL_MATTER_GENERATOR);
    RegistryUtil.register(game, Containers.FUSION_CHAMBER,             Names.FUSION_CHAMBER);
    RegistryUtil.register(game, Containers.PORTAL_CONTROL_PANEL,       Names.PORTAL_CONTROL_PANEL);
    RegistryUtil.register(game, Containers.PORTAL_FRAME,               Names.PORTAL_FRAME);
    RegistryUtil.register(game, Containers.MATTER_COMPRESSOR,          Names.MATTER_COMPRESSOR);
  }

  @SubscribeEvent
  public static final void registerRecipeSerializers(final RegistryEvent.Register<RecipeSerializer<?>> event){
    final IForgeRegistry<RecipeSerializer<?>> game = event.getRegistry();
    RegistryUtil.register(game, MagicInfuserRecipes.serializer, Names.MAGIC_INFUSER);
  }

  @SubscribeEvent
  public static final void registerSounds(final RegistryEvent.Register<SoundEvent> event){
    final IForgeRegistry<SoundEvent> game = event.getRegistry();
    game.register(Sounds.laser_fire_sound);
  }

  @SubscribeEvent
  public static final void registerBiomes(final RegistryEvent.Register<Biome> event){
    final IForgeRegistry<Biome> game = event.getRegistry();
    // game.register(WeirdDimension.weird_biome);
  }

}
