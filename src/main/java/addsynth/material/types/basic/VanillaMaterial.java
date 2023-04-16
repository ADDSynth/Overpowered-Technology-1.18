package addsynth.material.types.basic;

import addsynth.material.types.AbstractMaterial;
import addsynth.material.types.OreMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/** Encapsulates a vanilla material, such as Coal or Redstone. */
public final class VanillaMaterial extends AbstractMaterial implements OreMaterial {

  public final Item item;
  public final Block block;
  public final Block ore;

  public VanillaMaterial(final String name, final Item item, final Block block, final Block ore){
    super(name);
    this.item = item;
    this.block = block;
    this.ore = ore;
  }
  
  @Override
  public final Block getOre(){
    return ore;
  }

}
