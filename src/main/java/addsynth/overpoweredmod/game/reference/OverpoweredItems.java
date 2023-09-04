package addsynth.overpoweredmod.game.reference;

import addsynth.overpoweredmod.game.core.DeviceColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class OverpoweredItems {

  public static final RegistryObject<Item> celestial_gem             = RegistryObject.create(Names.CELESTIAL_GEM,             ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> energy_crystal_shards     = RegistryObject.create(Names.ENERGY_CRYSTAL_SHARDS,     ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> energy_crystal            = RegistryObject.create(Names.ENERGY_CRYSTAL,            ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> void_crystal              = RegistryObject.create(Names.VOID_CRYSTAL,              ForgeRegistries.ITEMS);
  
  public static final RegistryObject<Item> light_block               = RegistryObject.create(Names.LIGHT_BLOCK,               ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> black_hole                = RegistryObject.create(Names.BLACK_HOLE,                ForgeRegistries.ITEMS);

  public static final RegistryObject<Item> energized_power_core      = RegistryObject.create(Names.ENERGIZED_POWER_CORE,      ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> nullified_power_core      = RegistryObject.create(Names.NULLIFIED_POWER_CORE,      ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> energy_grid               = RegistryObject.create(Names.ENERGY_GRID,               ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> vacuum_container          = RegistryObject.create(Names.VACUUM_CONTAINER,          ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> reinforced_container      = RegistryObject.create(Names.REINFORCED_CONTAINER,      ForgeRegistries.ITEMS);
  
  public static final RegistryObject<Item> beam_emitter              = RegistryObject.create(Names.BEAM_EMITTER,              ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> destructive_laser         = RegistryObject.create(Names.DESTRUCTIVE_LASER,         ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> heavy_light_emitter       = RegistryObject.create(Names.HEAVY_LIGHT_EMITTER,       ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> energy_stabilizer         = RegistryObject.create(Names.ENERGY_STABILIZER,         ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> scanning_laser            = RegistryObject.create(Names.SCANNING_LASER,            ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> matter_energy_transformer = RegistryObject.create(Names.MATTER_ENERGY_TRANSFORMER, ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> high_frequency_beam       = RegistryObject.create(Names.HIGH_FREQUENCY_BEAM,       ForgeRegistries.ITEMS);
  
  public static final RegistryObject<Item> focus_lens                = RegistryObject.create(DeviceColor.WHITE.lens_name,     ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> red_lens                  = RegistryObject.create(DeviceColor.RED.lens_name,       ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> orange_lens               = RegistryObject.create(DeviceColor.ORANGE.lens_name,    ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> yellow_lens               = RegistryObject.create(DeviceColor.YELLOW.lens_name,    ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> green_lens                = RegistryObject.create(DeviceColor.GREEN.lens_name,     ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> cyan_lens                 = RegistryObject.create(DeviceColor.CYAN.lens_name,      ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> blue_lens                 = RegistryObject.create(DeviceColor.BLUE.lens_name,      ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> magenta_lens              = RegistryObject.create(DeviceColor.MAGENTA.lens_name,   ForgeRegistries.ITEMS);
  
  public static final RegistryObject<Item> plasma                    = RegistryObject.create(Names.PLASMA,                    ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> fusion_core               = RegistryObject.create(Names.FUSION_CORE,               ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> matter_energy_converter   = RegistryObject.create(Names.MATTER_ENERGY_CONVERTER,   ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> dimensional_flux          = RegistryObject.create(Names.DIMENSIONAL_FLUX,          ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> dimensional_anchor        = RegistryObject.create(Names.DIMENSIONAL_ANCHOR,        ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> unimatter                 = RegistryObject.create(Names.UNIMATTER,                 ForgeRegistries.ITEMS);
  
  public static final RegistryObject<Item> celestial_sword   = RegistryObject.create(Names.CELESTIAL_SWORD,   ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> celestial_shovel  = RegistryObject.create(Names.CELESTIAL_SHOVEL,  ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> celestial_pickaxe = RegistryObject.create(Names.CELESTIAL_PICKAXE, ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> celestial_axe     = RegistryObject.create(Names.CELESTIAL_AXE,     ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> celestial_hoe     = RegistryObject.create(Names.CELESTIAL_HOE,     ForgeRegistries.ITEMS);
  
  public static final RegistryObject<Item> void_sword   = RegistryObject.create(Names.VOID_SWORD,   ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> void_shovel  = RegistryObject.create(Names.VOID_SHOVEL,  ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> void_pickaxe = RegistryObject.create(Names.VOID_PICKAXE, ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> void_axe     = RegistryObject.create(Names.VOID_AXE,     ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> void_hoe     = RegistryObject.create(Names.VOID_HOE,     ForgeRegistries.ITEMS);
  

  public static final RegistryObject<Item> ring_0 = RegistryObject.create(Names.UNIDENTIFIED_RING[0], ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> ring_1 = RegistryObject.create(Names.UNIDENTIFIED_RING[1], ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> ring_2 = RegistryObject.create(Names.UNIDENTIFIED_RING[2], ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> ring_3 = RegistryObject.create(Names.UNIDENTIFIED_RING[3], ForgeRegistries.ITEMS);
  
  public static final RegistryObject<Item> magic_ring_0 = RegistryObject.create(Names.MAGIC_RING[0], ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> magic_ring_1 = RegistryObject.create(Names.MAGIC_RING[1], ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> magic_ring_2 = RegistryObject.create(Names.MAGIC_RING[2], ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> magic_ring_3 = RegistryObject.create(Names.MAGIC_RING[3], ForgeRegistries.ITEMS);

}
