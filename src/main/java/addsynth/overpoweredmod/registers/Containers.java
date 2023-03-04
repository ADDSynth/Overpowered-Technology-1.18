package addsynth.overpoweredmod.registers;

import addsynth.overpoweredmod.machines.advanced_ore_refinery.ContainerOreRefinery;
import addsynth.overpoweredmod.machines.crystal_matter_generator.ContainerCrystalGenerator;
import addsynth.overpoweredmod.machines.energy_extractor.ContainerCrystalEnergyExtractor;
import addsynth.overpoweredmod.machines.fusion.chamber.ContainerFusionChamber;
import addsynth.overpoweredmod.machines.gem_converter.ContainerGemConverter;
import addsynth.overpoweredmod.machines.identifier.ContainerIdentifier;
import addsynth.overpoweredmod.machines.inverter.ContainerInverter;
import addsynth.overpoweredmod.machines.laser.machine.ContainerLaserHousing;
import addsynth.overpoweredmod.machines.magic_infuser.ContainerMagicInfuser;
import addsynth.overpoweredmod.machines.matter_compressor.MatterCompressorContainer;
import addsynth.overpoweredmod.machines.plasma_generator.ContainerPlasmaGenerator;
import addsynth.overpoweredmod.machines.portal.control_panel.ContainerPortalControlPanel;
import addsynth.overpoweredmod.machines.portal.frame.ContainerPortalFrame;
import addsynth.overpoweredmod.machines.suspension_bridge.ContainerSuspensionBridge;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.IContainerFactory;

public final class Containers {

  public static final MenuType<ContainerCrystalEnergyExtractor> CRYSTAL_ENERGY_EXTRACTOR  =
    new MenuType<>((IContainerFactory<ContainerCrystalEnergyExtractor>)ContainerCrystalEnergyExtractor::new);

  public static final MenuType<ContainerInverter> INVERTER =
    new MenuType<>((IContainerFactory<ContainerInverter>)ContainerInverter::new);

  public static final MenuType<ContainerGemConverter> GEM_CONVERTER =
    new MenuType<>((IContainerFactory<ContainerGemConverter>)ContainerGemConverter::new);

  public static final MenuType<ContainerMagicInfuser> MAGIC_INFUSER =
    new MenuType<>((IContainerFactory<ContainerMagicInfuser>)ContainerMagicInfuser::new);

  public static final MenuType<ContainerIdentifier> IDENTIFIER =
    new MenuType<>((IContainerFactory<ContainerIdentifier>)ContainerIdentifier::new);

  public static final MenuType<ContainerSuspensionBridge> ENERGY_SUSPENSION_BRIDGE =
    new MenuType<>((IContainerFactory<ContainerSuspensionBridge>)ContainerSuspensionBridge::new);

  public static final MenuType<ContainerOreRefinery> ADVANCED_ORE_REFINERY =
    new MenuType<>((IContainerFactory<ContainerOreRefinery>)ContainerOreRefinery::new);

  public static final MenuType<ContainerCrystalGenerator> CRYSTAL_MATTER_GENERATOR =
    new MenuType<>((IContainerFactory<ContainerCrystalGenerator>)ContainerCrystalGenerator::new);
  
  public static final MenuType<ContainerPortalControlPanel> PORTAL_CONTROL_PANEL =
    new MenuType<>((IContainerFactory<ContainerPortalControlPanel>)ContainerPortalControlPanel::new);

  public static final MenuType<ContainerPortalFrame> PORTAL_FRAME =
    new MenuType<>((IContainerFactory<ContainerPortalFrame>)ContainerPortalFrame::new);

  public static final MenuType<ContainerLaserHousing> LASER_HOUSING =
    new MenuType<>((IContainerFactory<ContainerLaserHousing>)ContainerLaserHousing::new);

  public static final MenuType<ContainerFusionChamber> FUSION_CHAMBER =
    new MenuType<>((IContainerFactory<ContainerFusionChamber>)ContainerFusionChamber::new);

  public static final MenuType<ContainerPlasmaGenerator> PLASMA_GENERATOR =
    new MenuType<>((IContainerFactory<ContainerPlasmaGenerator>)ContainerPlasmaGenerator::new);

  public static final MenuType<MatterCompressorContainer> MATTER_COMPRESSOR =
    new MenuType<>((IContainerFactory<MatterCompressorContainer>)MatterCompressorContainer::new);

}
