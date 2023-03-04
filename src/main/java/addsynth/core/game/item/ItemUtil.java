package addsynth.core.game.item;

import addsynth.core.game.item.constants.ArmorMaterial;
import addsynth.core.game.item.constants.EquipmentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class ItemUtil {

  public static final boolean itemStackExists(final ItemStack stack){
    if(stack == null){   return false; }
    if(stack.isEmpty()){ return false; }
    return true;
  }

  public static final boolean isItemStackEmpty(final ItemStack stack){
    return stack == null ? true : stack.isEmpty();
  }

  /**
   * Used in Items to get the {@link CompoundTag} of ItemStacks.
   * Used in functions like <code>onItemUse()</code> or <code>onItemRightClick()</code>.
   * Then call {@link ItemStack#setTag(CompoundTag)} to save any changes.
   * @param stack
   * @return NBTTag if one exists, otherwise a new CompoundNBT
   */
  public static final CompoundTag getItemStackNBT(final ItemStack stack) throws NullPointerException {
    final CompoundTag nbt = stack.getTag();
    return nbt == null ? new CompoundTag() : nbt;
  }

  /** If the provided ItemStack exists, saves it with the Compound nbt using the key name. */
  public static final void saveItemStackToNBT(final CompoundTag nbt, final ItemStack stack, final String key){
    if(stack != null){
      final CompoundTag item = new CompoundTag();
      stack.save(item);
      nbt.put(key, item);
    }
  }

  /** Loads ItemStack if the entry for it exists. Returns an Empty ItemStack if it does not. */
  public static final ItemStack loadItemStackFromNBT(final CompoundTag nbt, final String key){
    return ItemStack.of(nbt.getCompound(key));
  }

  public static final Item[] toItemArray(final ItemStack[] input){
    int i;
    int length = input.length;
    final Item[] items = new Item[length];
    for(i = 0; i < length; i++){
      items[i] = input[i].getItem();
    }
    return items;
  }

  public static final Item get_armor(final ArmorMaterial armor_material, final EquipmentType equipment_type){
    Item armor = null;
    switch(armor_material){
    case LEATHER:
      switch(equipment_type){
      case HELMET:     armor = Items.LEATHER_HELMET; break;
      case CHESTPLATE: armor = Items.LEATHER_CHESTPLATE; break;
      case LEGGINGS:   armor = Items.LEATHER_LEGGINGS; break;
      case BOOTS:      armor = Items.LEATHER_BOOTS; break;
      }
      break;
    case CHAINMAIL:
      switch(equipment_type){
      case HELMET:     armor = Items.CHAINMAIL_HELMET; break;
      case CHESTPLATE: armor = Items.CHAINMAIL_CHESTPLATE; break;
      case LEGGINGS:   armor = Items.CHAINMAIL_LEGGINGS; break;
      case BOOTS:      armor = Items.CHAINMAIL_BOOTS; break;
      }
      break;
    case IRON:
      switch(equipment_type){
      case HELMET:     armor = Items.IRON_HELMET; break;
      case CHESTPLATE: armor = Items.IRON_CHESTPLATE; break;
      case LEGGINGS:   armor = Items.IRON_LEGGINGS; break;
      case BOOTS:      armor = Items.IRON_BOOTS; break;
      }
      break;
    case GOLD:
      switch(equipment_type){
      case HELMET:     armor = Items.GOLDEN_HELMET; break;
      case CHESTPLATE: armor = Items.GOLDEN_CHESTPLATE; break;
      case LEGGINGS:   armor = Items.GOLDEN_LEGGINGS; break;
      case BOOTS:      armor = Items.GOLDEN_BOOTS; break;
      }
      break;
    case DIAMOND:
      switch(equipment_type){
      case HELMET:     armor = Items.DIAMOND_HELMET; break;
      case CHESTPLATE: armor = Items.DIAMOND_CHESTPLATE; break;
      case LEGGINGS:   armor = Items.DIAMOND_LEGGINGS; break;
      case BOOTS:      armor = Items.DIAMOND_BOOTS; break;
      }
      break;
    case NETHERITE:
      switch(equipment_type){
      case HELMET:     armor = Items.NETHERITE_HELMET; break;
      case CHESTPLATE: armor = Items.NETHERITE_CHESTPLATE; break;
      case LEGGINGS:   armor = Items.NETHERITE_LEGGINGS; break;
      case BOOTS:      armor = Items.NETHERITE_BOOTS; break;
      }
      break;
    }
    return armor;
  }

}
