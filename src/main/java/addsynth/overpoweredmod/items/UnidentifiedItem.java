package addsynth.overpoweredmod.items;

import java.util.HashMap;
import javax.annotation.Nonnull;
import addsynth.core.game.item.constants.ArmorMaterial;
import addsynth.core.game.item.constants.EquipmentType;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.items.register.OverpoweredItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class UnidentifiedItem extends OverpoweredItem {

  /** Since the ResourceLocations are dynamically created here,
   *  this is where we will store the {@link RegistryObject} holders. */
  private static final HashMap<ResourceLocation, RegistryObject<Item>> list = new HashMap<>(32);
  public final int ring_id;
  public final ArmorMaterial armor_material;
  public final EquipmentType equipment_type;

  /** Use this constructor to create unidentified rings. */
  public UnidentifiedItem(final int ring_id){
    super(Names.UNIDENTIFIED_RING[ring_id]);
    this.ring_id = ring_id;
    armor_material = null;
    equipment_type = null;
  }

  /** Use this constructor to create unidentified armor pieces. */
  public UnidentifiedItem(@Nonnull final ArmorMaterial material, @Nonnull final EquipmentType type){
    super(getResourceLocation(material, type));
    this.ring_id = -1;
    this.armor_material = material;
    this.equipment_type = type;
  }

  /** This is used to create the ResourceLocation from the {@link ArmorMaterial} and {@link EquipmentType}. */
  public static final ResourceLocation getResourceLocation(final ArmorMaterial material, final EquipmentType type){
    return new ResourceLocation(OverpoweredTechnology.MOD_ID, "unidentified_"+material.name+"_"+type.name);
  }

  /** Use this to get the Item associated with this {@link ArmorMaterial} and {@link EquipmentType}. */
  public static final Item get(@Nonnull final ArmorMaterial material, @Nonnull final EquipmentType type){
    final ResourceLocation name = getResourceLocation(material, type);
    return list.computeIfAbsent(name, n -> RegistryObject.create(n, ForgeRegistries.ITEMS)).get();
  }

  @Override
  public final Component getName(final ItemStack stack){
    return ((MutableComponent)super.getName(stack)).withStyle(ChatFormatting.ITALIC);
  }

}
