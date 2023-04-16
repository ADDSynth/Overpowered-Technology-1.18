package addsynth.material.blocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public final class GemBlock extends Block {
	
  public GemBlock(final ResourceLocation name, final MaterialColor color){
    // TODO: Vanilla Minecraft uses Material and SoundType METAL instead of STONE.
    super(Block.Properties.of(Material.STONE, color).strength(5.0f, 6.0f).requiresCorrectToolForDrops().sound(SoundType.STONE));
    setRegistryName(name);
  }

}
