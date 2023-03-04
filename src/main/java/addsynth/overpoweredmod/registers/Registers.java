package addsynth.overpoweredmod.registers;

import addsynth.core.game.RegistryUtil;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.Sounds;
import addsynth.overpoweredmod.compatability.CompatabilityManager;
import addsynth.overpoweredmod.config.Features;
import addsynth.overpoweredmod.game.core.*;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
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
    
    if(Features.light_block.get()){ game.register(OverpoweredBlocks.light_block); }
    if(Features.null_block.get()){ game.register(OverpoweredBlocks.null_block); }
    
    if(Features.iron_frame_block.get()){ game.register(OverpoweredBlocks.iron_frame_block); }
    if(Features.black_hole.get()){ game.register(OverpoweredBlocks.black_hole); }
    
    game.register(OverpoweredBlocks.data_cable);
    if(Features.crystal_energy_extractor.get()){ game.register(OverpoweredBlocks.crystal_energy_extractor); }
    if(Features.gem_converter.get()){ game.register(OverpoweredBlocks.gem_converter); }
    game.register(OverpoweredBlocks.inverter);
    if(Features.magic_infuser.get()){ game.register(OverpoweredBlocks.magic_infuser); }
    if(Features.identifier.get()){ game.register(OverpoweredBlocks.identifier); }
    if(Features.energy_suspension_bridge.get()){
      game.register(OverpoweredBlocks.energy_suspension_bridge);
      game.register(OverpoweredBlocks.white_energy_bridge);
      game.register(OverpoweredBlocks.red_energy_bridge);
      game.register(OverpoweredBlocks.orange_energy_bridge);
      game.register(OverpoweredBlocks.yellow_energy_bridge);
      game.register(OverpoweredBlocks.green_energy_bridge);
      game.register(OverpoweredBlocks.cyan_energy_bridge);
      game.register(OverpoweredBlocks.blue_energy_bridge);
      game.register(OverpoweredBlocks.magenta_energy_bridge);
    }
    if(Features.portal.get()){
      game.register(OverpoweredBlocks.portal_control_panel);
      game.register(OverpoweredBlocks.portal_frame);
      game.register(OverpoweredBlocks.portal);
      if(Features.unknown_dimension.get()){
        game.register(OverpoweredBlocks.unknown_wood);
        game.register(OverpoweredBlocks.unknown_leaves);
      }
    }
    game.register(OverpoweredBlocks.plasma_generator);
    if(Features.crystal_matter_generator.get()){ game.register(OverpoweredBlocks.crystal_matter_generator); }
    if(Features.advanced_ore_refinery.get()){ game.register(OverpoweredBlocks.advanced_ore_refinery); }
    if(Features.lasers.get()){
      game.register(OverpoweredBlocks.laser_housing);
      // MAYBE: everywhere I check for each laser is enabled, I should've had the config change an enabled boolean variable in the Laser class!
      if(Features.white_laser.get()){   game.register(Laser.WHITE.cannon);   game.register(Laser.WHITE.beam);   }
      if(Features.red_laser.get()){     game.register(Laser.RED.cannon);     game.register(Laser.RED.beam);     }
      if(Features.orange_laser.get()){  game.register(Laser.ORANGE.cannon);  game.register(Laser.ORANGE.beam);  }
      if(Features.yellow_laser.get()){  game.register(Laser.YELLOW.cannon);  game.register(Laser.YELLOW.beam);  }
      if(Features.green_laser.get()){   game.register(Laser.GREEN.cannon);   game.register(Laser.GREEN.beam);   }
      if(Features.cyan_laser.get()){    game.register(Laser.CYAN.cannon);    game.register(Laser.CYAN.beam);    }
      if(Features.blue_laser.get()){    game.register(Laser.BLUE.cannon);    game.register(Laser.BLUE.beam);    }
      if(Features.magenta_laser.get()){ game.register(Laser.MAGENTA.cannon); game.register(Laser.MAGENTA.beam); }
    }
    if(Features.fusion.get()){
      game.register(OverpoweredBlocks.fusion_converter);
      game.register(OverpoweredBlocks.fusion_control_unit);
      game.register(OverpoweredBlocks.fusion_chamber);
      game.register(OverpoweredBlocks.fusion_control_laser);
      game.register(OverpoweredBlocks.fusion_control_laser_beam);
    }
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
    if(Features.light_block.get()){ game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.light_block)); }
    game.register(OverpoweredItems.void_crystal);
    if(Features.null_block.get()){ game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.null_block)); }
    
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
    if(Features.dimensional_anchor.get()){ game.register(OverpoweredItems.dimensional_anchor); }
    game.register(OverpoweredItems.unimatter);
    
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.data_cable));
    if(Features.crystal_energy_extractor.get()){ game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.crystal_energy_extractor)); }
    if(Features.gem_converter.get()){    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.gem_converter)); }
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.inverter));
    if(Features.magic_infuser.get()){    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.magic_infuser)); }
    if(Features.identifier.get()){       game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.identifier)); }
    if(Features.energy_suspension_bridge.get()){ game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.energy_suspension_bridge)); }
    if(Features.portal.get()){
      game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.portal_control_panel));
      game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.portal_frame));
      // MAYBE: register Item versions of the unknown / weird tree  (but item order is specific. don't register them here.)
    }
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.plasma_generator));
    if(Features.crystal_matter_generator.get()){ game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.crystal_matter_generator)); }
    if(Features.advanced_ore_refinery.get()){ game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.advanced_ore_refinery)); }
    
    if(Features.lasers.get()){
      game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.laser_housing));
      if(Features.white_laser.get()){   game.register(RegistryUtil.getItemBlock(Laser.WHITE.cannon));   }
      if(Features.red_laser.get()){     game.register(RegistryUtil.getItemBlock(Laser.RED.cannon));     }
      if(Features.orange_laser.get()){  game.register(RegistryUtil.getItemBlock(Laser.ORANGE.cannon));  }
      if(Features.yellow_laser.get()){  game.register(RegistryUtil.getItemBlock(Laser.YELLOW.cannon));  }
      if(Features.green_laser.get()){   game.register(RegistryUtil.getItemBlock(Laser.GREEN.cannon));   }
      if(Features.cyan_laser.get()){    game.register(RegistryUtil.getItemBlock(Laser.CYAN.cannon));    }
      if(Features.blue_laser.get()){    game.register(RegistryUtil.getItemBlock(Laser.BLUE.cannon));    }
      if(Features.magenta_laser.get()){ game.register(RegistryUtil.getItemBlock(Laser.MAGENTA.cannon)); }
    }
    
    if(Features.fusion.get()){
      game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.fusion_converter));
      game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.fusion_control_unit));
      game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.fusion_chamber));
      game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.fusion_control_laser));
    }
    
    game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.matter_compressor));
    
    if(Features.iron_frame_block.get()){ game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.iron_frame_block)); }
    if(Features.black_hole.get()){       game.register(RegistryUtil.getItemBlock(OverpoweredBlocks.black_hole)); }
    
    if(Features.celestial_tools.get()){
      game.register(OverpoweredItems.celestial_sword);
      game.register(OverpoweredItems.celestial_shovel);
      game.register(OverpoweredItems.celestial_pickaxe);
      game.register(OverpoweredItems.celestial_axe);
      game.register(OverpoweredItems.celestial_hoe);
    }
    if(Features.void_tools.get()){
      game.register(OverpoweredItems.void_sword);
      game.register(OverpoweredItems.void_shovel);
      game.register(OverpoweredItems.void_pickaxe);
      game.register(OverpoweredItems.void_axe);
      game.register(OverpoweredItems.void_hoe);
    }
    if(Features.identifier.get()){
      for(Item[] armor_set : OverpoweredItems.unidentified_armor){
        for(Item armor : armor_set){ game.register(armor); }
      }
      if(CompatabilityManager.are_rings_enabled()){
        game.register(OverpoweredItems.ring_0);
        game.register(OverpoweredItems.ring_1);
        game.register(OverpoweredItems.ring_2);
        game.register(OverpoweredItems.ring_3);
        game.register(OverpoweredItems.magic_ring_0);
        game.register(OverpoweredItems.magic_ring_1);
        game.register(OverpoweredItems.magic_ring_2);
        game.register(OverpoweredItems.magic_ring_3);
      }
    }
    
    // Items for advancements only
    game.register(OverpoweredItems.portal_image);
    game.register(OverpoweredItems.bridge_image);

    OverpoweredTechnology.log.info("Finished Item Registration Event.");
  }

  @SubscribeEvent
  public static final void registerTileEntities(final RegistryEvent.Register<BlockEntityType<?>> event){ // TEST: Maybe don't register TileEntities if they aren't enabled in the config.
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
