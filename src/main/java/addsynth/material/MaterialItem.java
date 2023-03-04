package addsynth.material;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public final class MaterialItem extends Item {

  public MaterialItem(final String name){
    super(new Item.Properties().tab(ADDSynthMaterials.creative_tab));
    setRegistryName(new ResourceLocation(ADDSynthMaterials.MOD_ID, name));
  }

  public MaterialItem(final Item.Properties properties, final String name){
    super(properties.tab(ADDSynthMaterials.creative_tab));
    setRegistryName(new ResourceLocation(ADDSynthMaterials.MOD_ID, name));
  }

}
