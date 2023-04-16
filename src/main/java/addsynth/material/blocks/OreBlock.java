package addsynth.material.blocks;

import addsynth.core.util.math.random.RandomUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class OreBlock extends Block {

  private final int min_experience;
  private final int max_experience;

  /**
   * Use this constructor if this Ore Block should be mined and smelted in a Furnace. The Furnace gives experience to the player.
   * @param name
   */
  public OreBlock(final ResourceLocation name){
    this(name, 0, 0);
  }

  /**
   * Use this constructor if this Ore Block drops an item, such as Coal, Diamond, Lapis, Redstone, or Quartz.
   * @param name
   * @param min_experience
   * @param max_experience
   */
  public OreBlock(final ResourceLocation name, int min_experience, int max_experience){
    super(Block.Properties.of(Material.STONE).strength(3.0f, 6.0f).requiresCorrectToolForDrops());
    // https://minecraft.gamepedia.com/Breaking#Blocks_by_hardness
    setRegistryName(name);
    this.min_experience = min_experience;
    this.max_experience = max_experience;
  }

  @Override
  public final int getExpDrop(BlockState state, LevelReader world, BlockPos pos, int fortune, int silktouch){
    return silktouch == 0 ? RandomUtil.RandomRange(min_experience, max_experience) : 0;
  }

}
