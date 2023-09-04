package addsynth.overpoweredmod.registers;

import addsynth.overpoweredmod.game.reference.Names;
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
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class Containers {

  public static final RegistryObject<MenuType<ContainerCrystalEnergyExtractor>> CRYSTAL_ENERGY_EXTRACTOR  =
    RegistryObject.create(Names.CRYSTAL_ENERGY_EXTRACTOR, ForgeRegistries.CONTAINERS);

  public static final RegistryObject<MenuType<ContainerInverter>> INVERTER =
    RegistryObject.create(Names.INVERTER, ForgeRegistries.CONTAINERS);

  public static final RegistryObject<MenuType<ContainerGemConverter>> GEM_CONVERTER =
    RegistryObject.create(Names.GEM_CONVERTER, ForgeRegistries.CONTAINERS);

  public static final RegistryObject<MenuType<ContainerMagicInfuser>> MAGIC_INFUSER =
    RegistryObject.create(Names.MAGIC_INFUSER, ForgeRegistries.CONTAINERS);

  public static final RegistryObject<MenuType<ContainerIdentifier>> IDENTIFIER =
    RegistryObject.create(Names.IDENTIFIER, ForgeRegistries.CONTAINERS);

  public static final RegistryObject<MenuType<ContainerSuspensionBridge>> ENERGY_SUSPENSION_BRIDGE =
    RegistryObject.create(Names.ENERGY_SUSPENSION_BRIDGE, ForgeRegistries.CONTAINERS);

  public static final RegistryObject<MenuType<ContainerOreRefinery>> ADVANCED_ORE_REFINERY =
    RegistryObject.create(Names.ADVANCED_ORE_REFINERY, ForgeRegistries.CONTAINERS);

  public static final RegistryObject<MenuType<ContainerCrystalGenerator>> CRYSTAL_MATTER_GENERATOR =
    RegistryObject.create(Names.CRYSTAL_MATTER_GENERATOR, ForgeRegistries.CONTAINERS);
  
  public static final RegistryObject<MenuType<ContainerPortalControlPanel>> PORTAL_CONTROL_PANEL =
    RegistryObject.create(Names.PORTAL_CONTROL_PANEL, ForgeRegistries.CONTAINERS);

  public static final RegistryObject<MenuType<ContainerPortalFrame>> PORTAL_FRAME =
    RegistryObject.create(Names.PORTAL_FRAME, ForgeRegistries.CONTAINERS);

  public static final RegistryObject<MenuType<ContainerLaserHousing>> LASER_HOUSING =
    RegistryObject.create(Names.LASER_HOUSING, ForgeRegistries.CONTAINERS);

  public static final RegistryObject<MenuType<ContainerFusionChamber>> FUSION_CHAMBER =
    RegistryObject.create(Names.FUSION_CHAMBER, ForgeRegistries.CONTAINERS);

  public static final RegistryObject<MenuType<ContainerPlasmaGenerator>> PLASMA_GENERATOR =
    RegistryObject.create(Names.PLASMA_GENERATOR, ForgeRegistries.CONTAINERS);

  public static final RegistryObject<MenuType<MatterCompressorContainer>> MATTER_COMPRESSOR =
    RegistryObject.create(Names.MATTER_COMPRESSOR, ForgeRegistries.CONTAINERS);

}
