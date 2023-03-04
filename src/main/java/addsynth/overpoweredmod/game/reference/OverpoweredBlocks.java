package addsynth.overpoweredmod.game.reference;

import addsynth.overpoweredmod.blocks.IronFrameBlock;
import addsynth.overpoweredmod.blocks.LightBlock;
import addsynth.overpoweredmod.blocks.NullBlock;
import addsynth.overpoweredmod.blocks.dimension.tree.UnknownLeaves;
import addsynth.overpoweredmod.blocks.dimension.tree.UnknownWood;
import addsynth.overpoweredmod.game.core.Lens;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.AdvancedOreRefineryBlock;
import addsynth.overpoweredmod.machines.black_hole.BlackHoleBlock;
import addsynth.overpoweredmod.machines.crystal_matter_generator.CrystalMatterGeneratorBlock;
import addsynth.overpoweredmod.machines.data_cable.DataCable;
import addsynth.overpoweredmod.machines.energy_extractor.CrystalEnergyExtractorBlock;
import addsynth.overpoweredmod.machines.fusion.chamber.FusionChamberBlock;
import addsynth.overpoweredmod.machines.fusion.control.FusionControlLaserBeam;
import addsynth.overpoweredmod.machines.fusion.control.FusionControlUnit;
import addsynth.overpoweredmod.machines.fusion.converter.FusionEnergyConverterBlock;
import addsynth.overpoweredmod.machines.gem_converter.GemConverterBlock;
import addsynth.overpoweredmod.machines.identifier.IdentifierBlock;
import addsynth.overpoweredmod.machines.inverter.InverterBlock;
import addsynth.overpoweredmod.machines.laser.cannon.LaserCannon;
import addsynth.overpoweredmod.machines.laser.machine.LaserHousingBlock;
import addsynth.overpoweredmod.machines.magic_infuser.MagicInfuserBlock;
import addsynth.overpoweredmod.machines.matter_compressor.MatterCompressorBlock;
import addsynth.overpoweredmod.machines.plasma_generator.PlasmaGeneratorBlock;
import addsynth.overpoweredmod.machines.portal.control_panel.PortalControlPanelBlock;
import addsynth.overpoweredmod.machines.portal.frame.PortalFrameBlock;
import addsynth.overpoweredmod.machines.portal.rift.PortalEnergyBlock;
import addsynth.overpoweredmod.machines.suspension_bridge.EnergyBridge;
import addsynth.overpoweredmod.machines.suspension_bridge.EnergySuspensionBridgeBlock;
import net.minecraft.world.level.block.Block;

public final class OverpoweredBlocks {

  public static final Block                       light_block               = new LightBlock();
  public static final Block                       null_block                = new NullBlock();
  public static final Block                       iron_frame_block          = new IronFrameBlock();
  public static final BlackHoleBlock              black_hole                = new BlackHoleBlock();

  public static final DataCable                   data_cable                = new DataCable();
  public static final CrystalEnergyExtractorBlock crystal_energy_extractor  = new CrystalEnergyExtractorBlock();
  public static final GemConverterBlock           gem_converter             = new GemConverterBlock();
  public static final InverterBlock               inverter                  = new InverterBlock();
  public static final MagicInfuserBlock           magic_infuser             = new MagicInfuserBlock();
  public static final IdentifierBlock             identifier                = new IdentifierBlock();
  public static final PortalControlPanelBlock     portal_control_panel      = new PortalControlPanelBlock();
  public static final PortalFrameBlock            portal_frame              = new PortalFrameBlock();
  public static final PortalEnergyBlock           portal                    = new PortalEnergyBlock();
  public static final CrystalMatterGeneratorBlock crystal_matter_generator  = new CrystalMatterGeneratorBlock();
  public static final AdvancedOreRefineryBlock    advanced_ore_refinery     = new AdvancedOreRefineryBlock();
  public static final PlasmaGeneratorBlock        plasma_generator          = new PlasmaGeneratorBlock();
  public static final MatterCompressorBlock       matter_compressor         = new MatterCompressorBlock();

  public static final FusionEnergyConverterBlock  fusion_converter          = new FusionEnergyConverterBlock();
  public static final FusionChamberBlock          fusion_chamber            = new FusionChamberBlock();
  public static final FusionControlUnit           fusion_control_unit       = new FusionControlUnit();
  public static final LaserCannon                 fusion_control_laser      = new LaserCannon(Names.FUSION_CONTROL_LASER, -1);
  public static final FusionControlLaserBeam      fusion_control_laser_beam = new FusionControlLaserBeam();

  public static final LaserHousingBlock           laser_housing             = new LaserHousingBlock();

  public static final EnergySuspensionBridgeBlock energy_suspension_bridge  = new EnergySuspensionBridgeBlock();
  public static final EnergyBridge                white_energy_bridge       = new EnergyBridge("white_energy_bridge", Lens.WHITE);
  public static final EnergyBridge                red_energy_bridge         = new EnergyBridge("red_energy_bridge", Lens.RED);
  public static final EnergyBridge                orange_energy_bridge      = new EnergyBridge("orange_energy_bridge", Lens.ORANGE);
  public static final EnergyBridge                yellow_energy_bridge      = new EnergyBridge("yellow_energy_bridge", Lens.YELLOW);
  public static final EnergyBridge                green_energy_bridge       = new EnergyBridge("green_energy_bridge", Lens.GREEN);
  public static final EnergyBridge                cyan_energy_bridge        = new EnergyBridge("cyan_energy_bridge", Lens.CYAN);
  public static final EnergyBridge                blue_energy_bridge        = new EnergyBridge("blue_energy_bridge", Lens.BLUE);
  public static final EnergyBridge                magenta_energy_bridge     = new EnergyBridge("magenta_energy_bridge", Lens.MAGENTA);

  // public static final BlockGrassNoDestroy custom_grass_block = new BlockGrassNoDestroy();
  // public static final BlockAirNoDestroy   custom_air_block   = new BlockAirNoDestroy();
  public static final UnknownWood         unknown_wood       = new UnknownWood();
  public static final UnknownLeaves       unknown_leaves     = new UnknownLeaves();

}
