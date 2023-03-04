package addsynth.overpoweredmod.items.tools;

import addsynth.core.game.item.constants.ToolConstants;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.reference.Names;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;

public class NullPickaxe extends PickaxeItem {

  public NullPickaxe(){
    super(OverpoweredTiers.VOID, ToolConstants.pickaxe_damage, ToolConstants.pickaxe_speed, new Item.Properties().tab(CreativeTabs.creative_tab));
    setRegistryName(Names.VOID_PICKAXE);
  }

  @Override
  public boolean isFoil(ItemStack stack){
    return true;
  }
  
  @Override
  public boolean isEnchantable(ItemStack stack){
    return false;
  }

  @Override
  public Rarity getRarity(ItemStack stack){
    return Rarity.EPIC;
  }

}
