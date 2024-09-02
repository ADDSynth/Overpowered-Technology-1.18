package addsynth.overpoweredmod.registers;

import addsynth.core.compat.Compatibility;
import addsynth.core.game.item.constants.ArmorMaterial;
import addsynth.core.game.item.constants.EquipmentType;
import addsynth.core.game.registry.RegistryUtil;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.Sounds;
import addsynth.overpoweredmod.blocks.*;
import addsynth.overpoweredmod.blocks.dimension.tree.*;
import addsynth.overpoweredmod.game.core.*;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.items.*;
import addsynth.overpoweredmod.items.basic.*;
import addsynth.overpoweredmod.items.register.*;
import addsynth.overpoweredmod.items.tools.*;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.AdvancedOreRefineryBlock;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.ContainerOreRefinery;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.TileAdvancedOreRefinery;
import addsynth.overpoweredmod.machines.black_hole.BlackHoleBlock;
import addsynth.overpoweredmod.machines.black_hole.BlackHoleItem;
import addsynth.overpoweredmod.machines.black_hole.TileBlackHole;
import addsynth.overpoweredmod.machines.crystal_matter_generator.ContainerCrystalGenerator;
import addsynth.overpoweredmod.machines.crystal_matter_generator.CrystalMatterGeneratorBlock;
import addsynth.overpoweredmod.machines.crystal_matter_generator.TileCrystalMatterGenerator;
import addsynth.overpoweredmod.machines.data_cable.DataCable;
import addsynth.overpoweredmod.machines.data_cable.TileDataCable;
import addsynth.overpoweredmod.machines.energy_extractor.ContainerCrystalEnergyExtractor;
import addsynth.overpoweredmod.machines.energy_extractor.CrystalEnergyExtractorBlock;
import addsynth.overpoweredmod.machines.energy_extractor.TileCrystalEnergyExtractor;
import addsynth.overpoweredmod.machines.fusion.chamber.ContainerFusionChamber;
import addsynth.overpoweredmod.machines.fusion.chamber.FusionChamberBlock;
import addsynth.overpoweredmod.machines.fusion.chamber.TileFusionChamber;
import addsynth.overpoweredmod.machines.fusion.control.FusionControlLaserBeam;
import addsynth.overpoweredmod.machines.fusion.control.FusionControlUnit;
import addsynth.overpoweredmod.machines.fusion.converter.FusionEnergyConverterBlock;
import addsynth.overpoweredmod.machines.fusion.converter.TileFusionEnergyConverter;
import addsynth.overpoweredmod.machines.gem_converter.ContainerGemConverter;
import addsynth.overpoweredmod.machines.gem_converter.GemConverterBlock;
import addsynth.overpoweredmod.machines.gem_converter.TileGemConverter;
import addsynth.overpoweredmod.machines.identifier.ContainerIdentifier;
import addsynth.overpoweredmod.machines.identifier.IdentifierBlock;
import addsynth.overpoweredmod.machines.identifier.TileIdentifier;
import addsynth.overpoweredmod.machines.inverter.ContainerInverter;
import addsynth.overpoweredmod.machines.inverter.InverterBlock;
import addsynth.overpoweredmod.machines.inverter.TileInverter;
import addsynth.overpoweredmod.machines.laser.beam.LaserBeam;
import addsynth.overpoweredmod.machines.laser.cannon.LaserCannon;
import addsynth.overpoweredmod.machines.laser.machine.ContainerLaserHousing;
import addsynth.overpoweredmod.machines.laser.machine.LaserHousingBlock;
import addsynth.overpoweredmod.machines.laser.machine.TileLaserHousing;
import addsynth.overpoweredmod.machines.magic_infuser.ContainerMagicInfuser;
import addsynth.overpoweredmod.machines.magic_infuser.MagicInfuserBlock;
import addsynth.overpoweredmod.machines.magic_infuser.TileMagicInfuser;
import addsynth.overpoweredmod.machines.magic_infuser.recipes.MagicInfuserRecipeSerializer;
import addsynth.overpoweredmod.machines.matter_compressor.MatterCompressorBlock;
import addsynth.overpoweredmod.machines.matter_compressor.MatterCompressorContainer;
import addsynth.overpoweredmod.machines.matter_compressor.TileMatterCompressor;
import addsynth.overpoweredmod.machines.plasma_generator.ContainerPlasmaGenerator;
import addsynth.overpoweredmod.machines.plasma_generator.PlasmaGeneratorBlock;
import addsynth.overpoweredmod.machines.plasma_generator.TilePlasmaGenerator;
import addsynth.overpoweredmod.machines.portal.control_panel.ContainerPortalControlPanel;
import addsynth.overpoweredmod.machines.portal.control_panel.PortalControlPanelBlock;
import addsynth.overpoweredmod.machines.portal.control_panel.TilePortalControlPanel;
import addsynth.overpoweredmod.machines.portal.frame.ContainerPortalFrame;
import addsynth.overpoweredmod.machines.portal.frame.PortalFrameBlock;
import addsynth.overpoweredmod.machines.portal.frame.TilePortalFrame;
import addsynth.overpoweredmod.machines.portal.rift.PortalEnergyBlock;
import addsynth.overpoweredmod.machines.portal.rift.TilePortal;
import addsynth.overpoweredmod.machines.suspension_bridge.ContainerSuspensionBridge;
import addsynth.overpoweredmod.machines.suspension_bridge.EnergyBridge;
import addsynth.overpoweredmod.machines.suspension_bridge.EnergySuspensionBridgeBlock;
import addsynth.overpoweredmod.machines.suspension_bridge.TileSuspensionBridge;
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
    
    game.register(new LightBlock());
    game.register(new NullBlock());
    game.register(new IronFrameBlock());
    game.register(new BlackHoleBlock());
    
    game.register(new DataCable());
    game.register(new CrystalEnergyExtractorBlock());
    game.register(new GemConverterBlock());
    game.register(new IdentifierBlock());
    game.register(new InverterBlock());
    game.register(new MagicInfuserBlock());
    
    game.register(new EnergySuspensionBridgeBlock());
    game.register(new EnergyBridge(DeviceColor.WHITE));
    game.register(new EnergyBridge(DeviceColor.RED));
    game.register(new EnergyBridge(DeviceColor.ORANGE));
    game.register(new EnergyBridge(DeviceColor.YELLOW));
    game.register(new EnergyBridge(DeviceColor.GREEN));
    game.register(new EnergyBridge(DeviceColor.CYAN));
    game.register(new EnergyBridge(DeviceColor.BLUE));
    game.register(new EnergyBridge(DeviceColor.MAGENTA));
    
    game.register(new PortalControlPanelBlock());
    game.register(new PortalFrameBlock());
    game.register(new PortalEnergyBlock());
    game.register(new UnknownWood());
    game.register(new UnknownLeaves());
    
    game.register(new PlasmaGeneratorBlock());
    game.register(new CrystalMatterGeneratorBlock());
    game.register(new AdvancedOreRefineryBlock());
    
    game.register(new LaserHousingBlock());
    // Must register the different laser cannons as different blocks so they have different names and produce
    // different color beams, but all the beams can be merged into a single block and use a color property.
    game.register(new LaserCannon(DeviceColor.WHITE));   game.register(new LaserBeam(DeviceColor.WHITE));
    game.register(new LaserCannon(DeviceColor.RED));     game.register(new LaserBeam(DeviceColor.RED));
    game.register(new LaserCannon(DeviceColor.ORANGE));  game.register(new LaserBeam(DeviceColor.ORANGE));
    game.register(new LaserCannon(DeviceColor.YELLOW));  game.register(new LaserBeam(DeviceColor.YELLOW));
    game.register(new LaserCannon(DeviceColor.GREEN));   game.register(new LaserBeam(DeviceColor.GREEN));
    game.register(new LaserCannon(DeviceColor.CYAN));    game.register(new LaserBeam(DeviceColor.CYAN));
    game.register(new LaserCannon(DeviceColor.BLUE));    game.register(new LaserBeam(DeviceColor.BLUE));
    game.register(new LaserCannon(DeviceColor.MAGENTA)); game.register(new LaserBeam(DeviceColor.MAGENTA));
    
    game.register(new FusionEnergyConverterBlock());
    game.register(new FusionControlUnit());
    game.register(new FusionChamberBlock());
    game.register(new LaserCannon());
    game.register(new FusionControlLaserBeam());
    
    game.register(new MatterCompressorBlock());
    
    OverpoweredTechnology.log.info("Finished Block Registration Event.");
  }

  @SubscribeEvent
  public static final void registerItems(final RegistryEvent.Register<Item> event){
    OverpoweredTechnology.log.info("Begin Item Registration Event...");
    
    final IForgeRegistry<Item> game = event.getRegistry();

    game.register(new OverpoweredItem(Names.CELESTIAL_GEM));
    game.register(new EnergyCrystalShards());
    game.register(new EnergyCrystal());
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.light_block));
    game.register(new VoidCrystal());
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.null_block));
    
    game.register(new OverpoweredItem(Names.ENERGIZED_POWER_CORE));
    game.register(new OverpoweredItem(Names.NULLIFIED_POWER_CORE));
    game.register(new OverpoweredItem(Names.ENERGY_GRID));
    game.register(new OverpoweredItem(Names.VACUUM_CONTAINER));
    game.register(new OverpoweredItem(Names.REINFORCED_CONTAINER));
    
    game.register(new OverpoweredItem(Names.BEAM_EMITTER));
    game.register(new OverpoweredItem(Names.SCANNING_LASER));
    game.register(new OverpoweredItem(Names.DESTRUCTIVE_LASER));
    game.register(new OverpoweredItem(Names.HEAVY_LIGHT_EMITTER));
    game.register(new OverpoweredItem(Names.ENERGY_STABILIZER));
    game.register(new OverpoweredItem(Names.MATTER_ENERGY_TRANSFORMER));
    game.register(new OverpoweredItem(Names.HIGH_FREQUENCY_BEAM));
    
    game.register(new LensItem(DeviceColor.WHITE));
    game.register(new LensItem(DeviceColor.RED));
    game.register(new LensItem(DeviceColor.ORANGE));
    game.register(new LensItem(DeviceColor.YELLOW));
    game.register(new LensItem(DeviceColor.GREEN));
    game.register(new LensItem(DeviceColor.CYAN));
    game.register(new LensItem(DeviceColor.BLUE));
    game.register(new LensItem(DeviceColor.MAGENTA));
    
    game.register(new PlasmaItem());
    game.register(new FusionCore());
    game.register(new OverpoweredItem(Names.MATTER_ENERGY_CONVERTER));
    game.register(new DimensionalFlux());
    game.register(new DimensionalAnchor());
    game.register(new OverpoweredItem(Names.UNIMATTER));
    
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.data_cable));
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.crystal_energy_extractor));
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.gem_converter));
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.identifier));
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.inverter));
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.magic_infuser));
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.energy_suspension_bridge));
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.portal_control_panel));
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.portal_frame));
    // MAYBE: register Item versions of the unknown / weird tree  (but item order is specific. don't register them here.)
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.plasma_generator));
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.crystal_matter_generator));
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.advanced_ore_refinery));
    
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.laser_housing));
    game.register(new OverpoweredBlockItem(Laser.WHITE.cannon));
    game.register(new OverpoweredBlockItem(Laser.RED.cannon));
    game.register(new OverpoweredBlockItem(Laser.ORANGE.cannon));
    game.register(new OverpoweredBlockItem(Laser.YELLOW.cannon));
    game.register(new OverpoweredBlockItem(Laser.GREEN.cannon));
    game.register(new OverpoweredBlockItem(Laser.CYAN.cannon));
    game.register(new OverpoweredBlockItem(Laser.BLUE.cannon));
    game.register(new OverpoweredBlockItem(Laser.MAGENTA.cannon));
    
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.fusion_converter));
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.fusion_control_unit));
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.fusion_chamber));
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.fusion_control_laser));
    
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.matter_compressor));
    
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.iron_frame_block));
    game.register(new BlackHoleItem());
    
    game.register(new OverpoweredSword());
    game.register(new OverpoweredShovel());
    game.register(new OverpoweredPickaxe());
    game.register(new OverpoweredAxe());
    game.register(new OverpoweredHoe());
    
    game.register(new NullSword());
    game.register(new NullShovel());
    game.register(new NullPickaxe());
    game.register(new NullAxe());
    game.register(new NullHoe());
    
    for(ArmorMaterial material : ArmorMaterial.values()){
      if(material != ArmorMaterial.NETHERITE){
        for(EquipmentType type : EquipmentType.values()){
          game.register(new UnidentifiedItem (material, type));
        }
      }
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
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.portal, new Item.Properties()));
    game.register(new OverpoweredBlockItem(OverpoweredBlocks.blue_energy_bridge, new Item.Properties()));

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

    game.register(RegistryUtil.createBlockEntityType(TileCrystalEnergyExtractor::new, OverpoweredBlocks.crystal_energy_extractor));
    game.register(RegistryUtil.createBlockEntityType(TileGemConverter::new,           OverpoweredBlocks.gem_converter));
    game.register(RegistryUtil.createBlockEntityType(TileIdentifier::new,             OverpoweredBlocks.identifier));
    game.register(RegistryUtil.createBlockEntityType(TileInverter::new,               OverpoweredBlocks.inverter));
    game.register(RegistryUtil.createBlockEntityType(TileMagicInfuser::new,           OverpoweredBlocks.magic_infuser));
    game.register(RegistryUtil.createBlockEntityType(TileSuspensionBridge::new,       OverpoweredBlocks.energy_suspension_bridge));
    game.register(RegistryUtil.createBlockEntityType(TileLaserHousing::new,           OverpoweredBlocks.laser_housing));
    game.register(RegistryUtil.createBlockEntityType(TileDataCable::new,              OverpoweredBlocks.data_cable));
    game.register(RegistryUtil.createBlockEntityType(TilePortalControlPanel::new,     OverpoweredBlocks.portal_control_panel));
    game.register(RegistryUtil.createBlockEntityType(TilePortalFrame::new,            OverpoweredBlocks.portal_frame));
    game.register(RegistryUtil.createBlockEntityType(TilePortal::new,                 OverpoweredBlocks.portal));
    game.register(RegistryUtil.createBlockEntityType(TilePlasmaGenerator::new,        OverpoweredBlocks.plasma_generator));
    game.register(RegistryUtil.createBlockEntityType(TileCrystalMatterGenerator::new, OverpoweredBlocks.crystal_matter_generator));
    game.register(RegistryUtil.createBlockEntityType(TileAdvancedOreRefinery::new,    OverpoweredBlocks.advanced_ore_refinery));
    game.register(RegistryUtil.createBlockEntityType(TileFusionEnergyConverter::new,  OverpoweredBlocks.fusion_converter));
    game.register(RegistryUtil.createBlockEntityType(TileFusionChamber::new,          OverpoweredBlocks.fusion_chamber));
    game.register(RegistryUtil.createBlockEntityType(TileBlackHole::new,              OverpoweredBlocks.black_hole));
    game.register(RegistryUtil.createBlockEntityType(TileMatterCompressor::new,       OverpoweredBlocks.matter_compressor));
  }

  @SubscribeEvent
  public static final void registerContainers(final RegistryEvent.Register<MenuType<?>> event){
    final IForgeRegistry<MenuType<?>> game = event.getRegistry();
    
    game.register(RegistryUtil.createContainerType(ContainerCrystalEnergyExtractor::new, Names.CRYSTAL_ENERGY_EXTRACTOR));
    game.register(RegistryUtil.createContainerType(ContainerGemConverter::new,           Names.GEM_CONVERTER));
    game.register(RegistryUtil.createContainerType(ContainerIdentifier::new,             Names.IDENTIFIER));
    game.register(RegistryUtil.createContainerType(ContainerInverter::new,               Names.INVERTER));
    game.register(RegistryUtil.createContainerType(ContainerMagicInfuser::new,           Names.MAGIC_INFUSER));
    game.register(RegistryUtil.createContainerType(ContainerSuspensionBridge::new,       Names.ENERGY_SUSPENSION_BRIDGE));
    game.register(RegistryUtil.createContainerType(ContainerLaserHousing::new,           Names.LASER_HOUSING));
    game.register(RegistryUtil.createContainerType(ContainerPlasmaGenerator::new,        Names.PLASMA_GENERATOR));
    game.register(RegistryUtil.createContainerType(ContainerOreRefinery::new,            Names.ADVANCED_ORE_REFINERY));
    game.register(RegistryUtil.createContainerType(ContainerCrystalGenerator::new,       Names.CRYSTAL_MATTER_GENERATOR));
    game.register(RegistryUtil.createContainerType(ContainerFusionChamber::new,          Names.FUSION_CHAMBER));
    game.register(RegistryUtil.createContainerType(ContainerPortalControlPanel::new,     Names.PORTAL_CONTROL_PANEL));
    game.register(RegistryUtil.createContainerType(ContainerPortalFrame::new,            Names.PORTAL_FRAME));
    game.register(RegistryUtil.createContainerType(MatterCompressorContainer::new,       Names.MATTER_COMPRESSOR));
  }

  @SubscribeEvent
  public static final void registerRecipeSerializers(final RegistryEvent.Register<RecipeSerializer<?>> event){
    final IForgeRegistry<RecipeSerializer<?>> game = event.getRegistry();
    game.register(new MagicInfuserRecipeSerializer());
  }

  @SubscribeEvent
  public static final void registerSounds(final RegistryEvent.Register<SoundEvent> event){
    final IForgeRegistry<SoundEvent> game = event.getRegistry();
    game.register(RegistryUtil.createNewSound(Sounds.Names.laser_fire));
  }

  @SubscribeEvent
  public static final void registerBiomes(final RegistryEvent.Register<Biome> event){
    final IForgeRegistry<Biome> game = event.getRegistry();
    // game.register(WeirdDimension.weird_biome);
  }

}
