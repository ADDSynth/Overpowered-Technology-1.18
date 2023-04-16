package addsynth.material.types.metal;

import javax.annotation.Nullable;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.types.AbstractMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

/** All Metal materials derive from this class.
 *  All Metals have an ingot, storage block, metal plate, and a nugget.
 *  Registering metal plates requires ADDSynth Energy to be loaded. */
public abstract class Metal extends AbstractMaterial {

  protected final ResourceLocation plate_name;
  // MAYBE dusts?

  public Metal(final String name){
    super(name);
    plate_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_plate");
  }

  public abstract Item getIngot();
  public abstract Block getBlock();

  public final Item getMetalPlate(){
    final Item metal_plate = ForgeRegistries.ITEMS.getValue(plate_name);
    if(metal_plate == null){
      ADDSynthMaterials.log.error("ADDSynth Energy is not loaded, so no metal plates were registered. DO NOT REQUEST METAL PLATES! Use a Tag instead.");
    }
    return metal_plate;
  }

  @Nullable
  public abstract Item getNugget();

}
