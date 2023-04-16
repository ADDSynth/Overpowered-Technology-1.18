package addsynth.material.blocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

// Based off of the vanilla Coal Block
public final class GenericStorageBlock extends Block {

  public GenericStorageBlock(final ResourceLocation name, final MaterialColor color){
    super(Block.Properties.of(Material.STONE, color).strength(5.0f, 6.0f).sound(SoundType.STONE));
    setRegistryName(name);
  }

}
