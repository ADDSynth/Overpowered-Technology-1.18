package addsynth.material.items;

import addsynth.material.ADDSynthMaterials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public final class MaterialItem extends Item {

  public MaterialItem(final ResourceLocation name){
    super(new Item.Properties().tab(ADDSynthMaterials.creative_tab));
    setRegistryName(name);
  }

  public MaterialItem(final ResourceLocation name, final Item.Properties properties){
    super(properties.tab(ADDSynthMaterials.creative_tab));
    setRegistryName(name);
  }

}
