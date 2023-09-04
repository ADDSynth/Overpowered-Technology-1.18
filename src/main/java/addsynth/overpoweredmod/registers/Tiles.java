package addsynth.overpoweredmod.registers;

import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.TileAdvancedOreRefinery;
import addsynth.overpoweredmod.machines.black_hole.TileBlackHole;
import addsynth.overpoweredmod.machines.crystal_matter_generator.TileCrystalMatterGenerator;
import addsynth.overpoweredmod.machines.data_cable.TileDataCable;
import addsynth.overpoweredmod.machines.energy_extractor.TileCrystalEnergyExtractor;
import addsynth.overpoweredmod.machines.fusion.chamber.TileFusionChamber;
import addsynth.overpoweredmod.machines.fusion.converter.TileFusionEnergyConverter;
import addsynth.overpoweredmod.machines.gem_converter.TileGemConverter;
import addsynth.overpoweredmod.machines.identifier.TileIdentifier;
import addsynth.overpoweredmod.machines.inverter.TileInverter;
import addsynth.overpoweredmod.machines.laser.machine.TileLaserHousing;
import addsynth.overpoweredmod.machines.magic_infuser.TileMagicInfuser;
import addsynth.overpoweredmod.machines.matter_compressor.TileMatterCompressor;
import addsynth.overpoweredmod.machines.plasma_generator.TilePlasmaGenerator;
import addsynth.overpoweredmod.machines.portal.control_panel.TilePortalControlPanel;
import addsynth.overpoweredmod.machines.portal.frame.TilePortalFrame;
import addsynth.overpoweredmod.machines.portal.rift.TilePortal;
import addsynth.overpoweredmod.machines.suspension_bridge.TileSuspensionBridge;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class Tiles {

  public static final RegistryObject<BlockEntityType<TileCrystalEnergyExtractor>> CRYSTAL_ENERGY_EXTRACTOR =
    RegistryObject.create(Names.CRYSTAL_ENERGY_EXTRACTOR, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileGemConverter>> GEM_CONVERTER =
    RegistryObject.create(Names.GEM_CONVERTER, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileInverter>> INVERTER =
    RegistryObject.create(Names.INVERTER, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileMagicInfuser>> MAGIC_INFUSER =
    RegistryObject.create(Names.MAGIC_INFUSER, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileIdentifier>> IDENTIFIER =
    RegistryObject.create(Names.IDENTIFIER, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileAdvancedOreRefinery>> ADVANCED_ORE_REFINERY =
    RegistryObject.create(Names.ADVANCED_ORE_REFINERY, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileCrystalMatterGenerator>> CRYSTAL_MATTER_REPLICATOR =
    RegistryObject.create(Names.CRYSTAL_MATTER_GENERATOR, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileSuspensionBridge>> ENERGY_SUSPENSION_BRIDGE =
    RegistryObject.create(Names.ENERGY_SUSPENSION_BRIDGE, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileDataCable>> DATA_CABLE =
    RegistryObject.create(Names.DATA_CABLE, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TilePortalControlPanel>> PORTAL_CONTROL_PANEL =
    RegistryObject.create(Names.PORTAL_CONTROL_PANEL, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TilePortalFrame>> PORTAL_FRAME =
    RegistryObject.create(Names.PORTAL_FRAME, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TilePortal>> PORTAL_RIFT =
    RegistryObject.create(Names.PORTAL_RIFT, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileLaserHousing>> LASER_MACHINE =
    RegistryObject.create(Names.LASER_HOUSING, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileFusionEnergyConverter>> FUSION_ENERGY_CONVERTER =
    RegistryObject.create(Names.FUSION_CONVERTER, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileFusionChamber>> FUSION_CHAMBER =
    RegistryObject.create(Names.FUSION_CHAMBER, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileBlackHole>> BLACK_HOLE =
    RegistryObject.create(Names.BLACK_HOLE, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TilePlasmaGenerator>> PLASMA_GENERATOR =
    RegistryObject.create(Names.PLASMA_GENERATOR, ForgeRegistries.BLOCK_ENTITIES);

  public static final RegistryObject<BlockEntityType<TileMatterCompressor>> MATTER_COMPRESSOR =
    RegistryObject.create(Names.MATTER_COMPRESSOR, ForgeRegistries.BLOCK_ENTITIES);

}
