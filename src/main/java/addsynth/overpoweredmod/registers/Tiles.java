package addsynth.overpoweredmod.registers;

import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
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

public final class Tiles {

  public static final BlockEntityType<TileCrystalEnergyExtractor> CRYSTAL_ENERGY_EXTRACTOR =
    BlockEntityType.Builder.of(TileCrystalEnergyExtractor::new, OverpoweredBlocks.crystal_energy_extractor).build(null);

  public static final BlockEntityType<TileGemConverter> GEM_CONVERTER =
    BlockEntityType.Builder.of(TileGemConverter::new, OverpoweredBlocks.gem_converter).build(null);

  public static final BlockEntityType<TileInverter> INVERTER =
    BlockEntityType.Builder.of(TileInverter::new, OverpoweredBlocks.inverter).build(null);

  public static final BlockEntityType<TileMagicInfuser> MAGIC_INFUSER =
    BlockEntityType.Builder.of(TileMagicInfuser::new, OverpoweredBlocks.magic_infuser).build(null);

  public static final BlockEntityType<TileIdentifier> IDENTIFIER =
    BlockEntityType.Builder.of(TileIdentifier::new, OverpoweredBlocks.identifier).build(null);

  public static final BlockEntityType<TileAdvancedOreRefinery> ADVANCED_ORE_REFINERY =
    BlockEntityType.Builder.of(TileAdvancedOreRefinery::new, OverpoweredBlocks.advanced_ore_refinery).build(null);

  public static final BlockEntityType<TileCrystalMatterGenerator> CRYSTAL_MATTER_REPLICATOR =
    BlockEntityType.Builder.of(TileCrystalMatterGenerator::new, OverpoweredBlocks.crystal_matter_generator).build(null);

  public static final BlockEntityType<TileSuspensionBridge> ENERGY_SUSPENSION_BRIDGE =
    BlockEntityType.Builder.of(TileSuspensionBridge::new, OverpoweredBlocks.energy_suspension_bridge).build(null);

  public static final BlockEntityType<TileDataCable> DATA_CABLE =
    BlockEntityType.Builder.of(TileDataCable::new, OverpoweredBlocks.data_cable).build(null);

  public static final BlockEntityType<TilePortalControlPanel> PORTAL_CONTROL_PANEL =
    BlockEntityType.Builder.of(TilePortalControlPanel::new, OverpoweredBlocks.portal_control_panel).build(null);

  public static final BlockEntityType<TilePortalFrame> PORTAL_FRAME =
    BlockEntityType.Builder.of(TilePortalFrame::new, OverpoweredBlocks.portal_frame).build(null);

  public static final BlockEntityType<TilePortal> PORTAL_BLOCK =
    BlockEntityType.Builder.of(TilePortal::new, OverpoweredBlocks.portal).build(null);

  public static final BlockEntityType<TileLaserHousing> LASER_MACHINE =
    BlockEntityType.Builder.of(TileLaserHousing::new, OverpoweredBlocks.laser_housing).build(null);

  public static final BlockEntityType<TileFusionEnergyConverter> FUSION_ENERGY_CONVERTER =
    BlockEntityType.Builder.of(TileFusionEnergyConverter::new, OverpoweredBlocks.fusion_converter).build(null);

  public static final BlockEntityType<TileFusionChamber> FUSION_CHAMBER =
    BlockEntityType.Builder.of(TileFusionChamber::new, OverpoweredBlocks.fusion_chamber).build(null);

  public static final BlockEntityType<TileBlackHole> BLACK_HOLE =
    BlockEntityType.Builder.of(TileBlackHole::new, OverpoweredBlocks.black_hole).build(null);

  public static final BlockEntityType<TilePlasmaGenerator> PLASMA_GENERATOR =
    BlockEntityType.Builder.of(TilePlasmaGenerator::new, OverpoweredBlocks.plasma_generator).build(null);

  public static final BlockEntityType<TileMatterCompressor> MATTER_COMPRESSOR =
    BlockEntityType.Builder.of(TileMatterCompressor::new, OverpoweredBlocks.matter_compressor).build(null);

}
