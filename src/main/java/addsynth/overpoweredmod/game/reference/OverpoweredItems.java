package addsynth.overpoweredmod.game.reference;

import addsynth.core.game.RegistryUtil;
import addsynth.core.game.item.constants.ArmorMaterial;
import addsynth.core.game.item.constants.EquipmentType;
import addsynth.overpoweredmod.items.*;
import addsynth.overpoweredmod.items.basic.*;
import addsynth.overpoweredmod.items.tools.*;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class OverpoweredItems {

  public static final Item celestial_gem             = new OverpoweredItem(Names.CELESTIAL_GEM);
  public static final Item energy_crystal_shards     = new EnergyCrystalShards();
  public static final Item energy_crystal            = new EnergyCrystal();
  public static final Item void_crystal              = new VoidCrystal();

  public static final Item energized_power_core      = new OverpoweredItem(Names.ENERGIZED_POWER_CORE);
  public static final Item nullified_power_core      = new OverpoweredItem(Names.NULLIFIED_POWER_CORE);
  public static final Item energy_grid               = new OverpoweredItem(Names.ENERGY_GRID);
  public static final Item vacuum_container          = new OverpoweredItem(Names.VACUUM_CONTAINER);
  public static final Item reinforced_container      = new OverpoweredItem(Names.REINFORCED_CONTAINER);
  
  public static final Item beam_emitter              = new OverpoweredItem(Names.BEAM_EMITTER);
  public static final Item destructive_laser         = new OverpoweredItem(Names.DESTRUCTIVE_LASER);
  public static final Item heavy_light_emitter       = new OverpoweredItem(Names.HEAVY_LIGHT_EMITTER);
  public static final Item energy_stabilizer         = new OverpoweredItem(Names.ENERGY_STABILIZER);
  public static final Item scanning_laser            = new OverpoweredItem(Names.SCANNING_LASER);
  public static final Item matter_energy_transformer = new OverpoweredItem(Names.MATTER_ENERGY_TRANSFORMER);
  public static final Item high_frequency_beam       = new OverpoweredItem(Names.HIGH_FREQUENCY_BEAM);
  
  public static final Item plasma                    = new PlasmaItem();
  public static final Item fusion_core               = new FusionCore();
  public static final Item matter_energy_converter   = new OverpoweredItem(Names.MATTER_ENERGY_CONVERTER);
  public static final Item dimensional_flux          = new DimensionalFlux();
  public static final Item dimensional_anchor        = new DimensionalAnchor();
  public static final Item unimatter                 = new OverpoweredItem(Names.UNIMATTER);
  
  public static final SwordItem   celestial_sword   = new OverpoweredSword();
  public static final ShovelItem  celestial_shovel  = new OverpoweredShovel();
  public static final PickaxeItem celestial_pickaxe = new OverpoweredPickaxe();
  public static final AxeItem     celestial_axe     = new OverpoweredAxe();
  public static final HoeItem     celestial_hoe     = new OverpoweredHoe();
  
  public static final SwordItem   void_sword   = new NullSword();
  public static final ShovelItem  void_shovel  = new NullShovel();
  public static final PickaxeItem void_pickaxe = new NullPickaxe();
  public static final AxeItem     void_axe     = new NullAxe();
  public static final HoeItem     void_hoe     = new NullHoe();
  
  public static final Item[][] unidentified_armor = new Item[5][4];
  static {
    for(ArmorMaterial material : ArmorMaterial.values()){
      if(material != ArmorMaterial.NETHERITE){
        for(EquipmentType type : EquipmentType.values()){
          unidentified_armor[material.ordinal()][type.ordinal()] = new UnidentifiedItem(material, type);
        }
      }
    }
  }

  public static final RegistryObject<Item> ring_0 = RegistryObject.create(Names.UNIDENTIFIED_RING[0], ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> ring_1 = RegistryObject.create(Names.UNIDENTIFIED_RING[1], ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> ring_2 = RegistryObject.create(Names.UNIDENTIFIED_RING[2], ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> ring_3 = RegistryObject.create(Names.UNIDENTIFIED_RING[3], ForgeRegistries.ITEMS);
  
  public static final RegistryObject<Item> magic_ring_0 = RegistryObject.create(Names.MAGIC_RING[0], ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> magic_ring_1 = RegistryObject.create(Names.MAGIC_RING[1], ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> magic_ring_2 = RegistryObject.create(Names.MAGIC_RING[2], ForgeRegistries.ITEMS);
  public static final RegistryObject<Item> magic_ring_3 = RegistryObject.create(Names.MAGIC_RING[3], ForgeRegistries.ITEMS);

  /** Item form of Portal, used only for Achievement icon. Does not show up in jei or creative tab.
   *  But players can still get it by using the /give command. */
  public static final Item portal_image = RegistryUtil.getItemBlock(OverpoweredBlocks.portal);
  public static final Item bridge_image = RegistryUtil.create_ItemBlock(OverpoweredBlocks.cyan_energy_bridge);

}
