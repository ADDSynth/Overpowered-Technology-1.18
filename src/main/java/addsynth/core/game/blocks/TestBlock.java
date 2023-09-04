package addsynth.core.game.blocks;

import addsynth.core.gameplay.reference.Names;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public final class TestBlock extends Block {

  public TestBlock(){
    super(Block.Properties.of(Material.STONE, MaterialColor.SNOW).strength(0.2f, 6.0f));
    setRegistryName(Names.TEST_BLOCK);
  }

}
