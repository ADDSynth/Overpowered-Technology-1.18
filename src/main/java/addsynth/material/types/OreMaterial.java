package addsynth.material.types;

import addsynth.material.MaterialItem;
import addsynth.material.blocks.OreBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;

public class OreMaterial extends BaseMaterial {

  public final Block ore;

  /** Null Ore Material */
  public OreMaterial(final String name){
    super(name);
    this.ore = null;
  }

  /** Vanilla Material */
  public OreMaterial(final String name, final Item item, final Block block, final Block ore){
    super(false, name, item, block);
    this.ore = ore;
  }

  /** Manufactured Metal Material */
  protected OreMaterial(final String name, final Item item, final Block block){
    super(true, name, item, block);
    this.ore = null;
  }

  /** Custom Material */
  public OreMaterial(final String name, final MaterialColor color){
    // Silicon, Uranium, and Yellorium
    super(true, name, new MaterialItem(name));
    this.ore = new OreBlock(name+"_ore");
  }

  /** Specific Type Material */
  protected OreMaterial(final String name, final Item item, final Block block, final int min_experience, final int max_experience){
    super(true, name, item, block);
    this.ore = new OreBlock(name+"_ore", min_experience, max_experience);
  }

}
