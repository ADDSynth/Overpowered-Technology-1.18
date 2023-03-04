package addsynth.overpoweredmod.items;

import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class OverpoweredItem extends Item {

  public OverpoweredItem(final ResourceLocation name){
    super(new Item.Properties().tab(CreativeTabs.creative_tab));
    setRegistryName(name);
  }

  public OverpoweredItem(final ResourceLocation name, final CreativeModeTab tab){
    super(new Item.Properties().tab(tab));
    setRegistryName(name);
  }

  public OverpoweredItem(final ResourceLocation name, final Item.Properties properties){
    super(properties);
    setRegistryName(name);
  }

}
