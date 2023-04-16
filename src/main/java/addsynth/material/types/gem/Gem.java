package addsynth.material.types.gem;

import javax.annotation.Nullable;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.types.AbstractMaterial;
import addsynth.material.types.OreMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

/** All Gem-type materials derive from this abstract type. */
public abstract class Gem extends AbstractMaterial implements OreMaterial {

  protected final ResourceLocation shard_name;

  public Gem(final String name){
    super(name);
    shard_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_shard");
  }

  public abstract Item getGem();

  @Nullable
  public final Item getGemShard(){
    return ForgeRegistries.ITEMS.getValue(shard_name);
  }

}
