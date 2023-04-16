package addsynth.material.types.basic;

import addsynth.material.ADDSynthMaterials;
import addsynth.material.items.MaterialItem;
import addsynth.material.types.AbstractMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

/** This is the simplest form of a material. It just has an item. */
public final class SimpleMaterial extends AbstractMaterial {

  private final MaterialColor color;
  private final ResourceLocation item_name;
  public final RegistryObject<Item> item;
  
  public SimpleMaterial(final String name, final MaterialColor color){
    super(name);
    this.color = color;
     item_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name);
     item = RegistryObject.create( item_name, ForgeRegistries.ITEMS);
  }
  
  public final void registerItems(final IForgeRegistry<Item> game){
    game.register(new MaterialItem(item_name));
  }

}
