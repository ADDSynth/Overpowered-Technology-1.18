package addsynth.overpoweredmod.items.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import addsynth.core.game.item.constants.ToolConstants;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.reference.Names;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;

public final class OverpoweredSword extends SwordItem {

  public OverpoweredSword(){ // TODO: Does Minecraft 1.16 now allow us to pass the Attack Damage and Speed as paramters? - for 1.16 only, do this when we drop support for earlier versions of MC.
    super(OverpoweredTiers.CELESTIAL_SWORD, ToolConstants.sword_damage, ToolConstants.sword_damage, new Item.Properties().tab(CreativeTabs.creative_tab));
    setRegistryName(Names.CELESTIAL_SWORD);
  }

  @Override
  public boolean isEnchantable(ItemStack stack){
    return false;
  }

  @Override
  public Rarity getRarity(ItemStack stack){
    return Rarity.RARE;
  }

  // https://minecraft.gamepedia.com/Attribute
  @Override
  public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot){

    // Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
    Multimap<Attribute, AttributeModifier> multimap = HashMultimap.<Attribute, AttributeModifier>create();
    if (equipmentSlot == EquipmentSlot.MAINHAND) {
      // Base Attack Damage is 3 (found in attackDamage field initializer in SwordClass),
      // + ToolMaterial Attack Damage, Wood & Gold = 0, Stone = 1, Iron = 2, Diamond = 3
      // + 1 Base attack for when the player doesn't have any tools equipped? I think? I'm not sure how sword damage is calculated.
      multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 11.0d, AttributeModifier.Operation.ADDITION));
      // Base Attack Speed is 4, Vanilla modifier value is -2.4 with operation 0, which adds it.
      // 4 + -2.4 = 1.6. All swords have a Attack Speed of 1.6, which is number of swings per second.
      multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -1.9d, AttributeModifier.Operation.ADDITION));
    }
    return multimap;
  }
  
}
