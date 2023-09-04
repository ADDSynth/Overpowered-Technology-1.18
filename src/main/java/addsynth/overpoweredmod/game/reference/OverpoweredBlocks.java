package addsynth.overpoweredmod.game.reference;

import addsynth.overpoweredmod.game.core.DeviceColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class OverpoweredBlocks {

  public static final RegistryObject<Block> light_block               = RegistryObject.create(Names.LIGHT_BLOCK,                 ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> null_block                = RegistryObject.create(Names.NULL_BLOCK,                  ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> iron_frame_block          = RegistryObject.create(Names.IRON_FRAME_BLOCK,            ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> black_hole                = RegistryObject.create(Names.BLACK_HOLE,                  ForgeRegistries.BLOCKS);

  public static final RegistryObject<Block> data_cable                = RegistryObject.create(Names.DATA_CABLE,                  ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> crystal_energy_extractor  = RegistryObject.create(Names.CRYSTAL_ENERGY_EXTRACTOR,    ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> gem_converter             = RegistryObject.create(Names.GEM_CONVERTER,               ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> inverter                  = RegistryObject.create(Names.INVERTER,                    ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> magic_infuser             = RegistryObject.create(Names.MAGIC_INFUSER,               ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> identifier                = RegistryObject.create(Names.IDENTIFIER,                  ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> portal_control_panel      = RegistryObject.create(Names.PORTAL_CONTROL_PANEL,        ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> portal_frame              = RegistryObject.create(Names.PORTAL_FRAME,                ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> portal                    = RegistryObject.create(Names.PORTAL_RIFT,                 ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> crystal_matter_generator  = RegistryObject.create(Names.CRYSTAL_MATTER_GENERATOR,    ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> advanced_ore_refinery     = RegistryObject.create(Names.ADVANCED_ORE_REFINERY,       ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> plasma_generator          = RegistryObject.create(Names.PLASMA_GENERATOR,            ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> matter_compressor         = RegistryObject.create(Names.MATTER_COMPRESSOR,           ForgeRegistries.BLOCKS);

  public static final RegistryObject<Block> fusion_converter          = RegistryObject.create(Names.FUSION_CONVERTER,            ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> fusion_chamber            = RegistryObject.create(Names.FUSION_CHAMBER,              ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> fusion_control_unit       = RegistryObject.create(Names.FUSION_CONTROL_UNIT,         ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> fusion_control_laser      = RegistryObject.create(Names.FUSION_CONTROL_LASER,        ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> fusion_control_laser_beam = RegistryObject.create(Names.FUSION_CONTROL_LASER_BEAM,   ForgeRegistries.BLOCKS);

  public static final RegistryObject<Block> laser_housing             = RegistryObject.create(Names.LASER_HOUSING,               ForgeRegistries.BLOCKS);

  public static final RegistryObject<Block> energy_suspension_bridge  = RegistryObject.create(Names.ENERGY_SUSPENSION_BRIDGE,    ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block>   white_energy_bridge     = RegistryObject.create(DeviceColor.WHITE.energy_bridge,   ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block>     red_energy_bridge     = RegistryObject.create(DeviceColor.RED.energy_bridge,     ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block>  orange_energy_bridge     = RegistryObject.create(DeviceColor.ORANGE.energy_bridge,  ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block>  yellow_energy_bridge     = RegistryObject.create(DeviceColor.YELLOW.energy_bridge,  ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block>   green_energy_bridge     = RegistryObject.create(DeviceColor.GREEN.energy_bridge,   ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block>    cyan_energy_bridge     = RegistryObject.create(DeviceColor.CYAN.energy_bridge,    ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block>    blue_energy_bridge     = RegistryObject.create(DeviceColor.BLUE.energy_bridge,    ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> magenta_energy_bridge     = RegistryObject.create(DeviceColor.MAGENTA.energy_bridge, ForgeRegistries.BLOCKS);

  public static final RegistryObject<Block> unknown_wood       = RegistryObject.create(Names.UNKNOWN_WOOD,   ForgeRegistries.BLOCKS);
  public static final RegistryObject<Block> unknown_leaves     = RegistryObject.create(Names.UNKNOWN_LEAVES, ForgeRegistries.BLOCKS);

}
