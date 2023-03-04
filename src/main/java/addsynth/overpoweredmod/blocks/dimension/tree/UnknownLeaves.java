package addsynth.overpoweredmod.blocks.dimension.tree;

import addsynth.overpoweredmod.game.reference.Names;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public final class UnknownLeaves extends Block {

  public UnknownLeaves(){
    super(Block.Properties.of(Material.LEAVES).strength(0.2f, 0.0f).sound(SoundType.GRASS).noOcclusion());
    setRegistryName(Names.UNKNOWN_LEAVES);
  }

}
