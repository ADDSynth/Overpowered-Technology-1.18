package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.reference.Names;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class OverpoweredHoe extends HoeItem {

  public OverpoweredHoe(){
    super(OverpoweredTiers.CELESTIAL, -4, 0.0f, new Item.Properties().tab(CreativeTabs.creative_tab));
    setRegistryName(Names.CELESTIAL_HOE);
  }

  @Override
  public boolean isEnchantable(ItemStack stack){
    return false;
  }

  @Override
  public Rarity getRarity(ItemStack stack){
    return Rarity.RARE;
  }
}
