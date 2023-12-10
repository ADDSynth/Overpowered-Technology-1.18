package addsynth.material.types.gem;

import addsynth.material.ADDSynthMaterials;
import addsynth.material.types.AbstractMaterial;
import addsynth.material.types.OreMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/** All Gem-type materials derive from this abstract type. */
public abstract class Gem extends AbstractMaterial implements OreMaterial {

  protected final RegistryObject<Item> shard;

  public Gem(final String name){
    super(name);
    shard = RegistryObject.create(new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_shard"), ForgeRegistries.ITEMS);
  }

  public abstract Item getGem();

  public final Item getGemShard(){
    if(shard.isPresent()){
      return shard.get();
    }
    return null;
  }

}
