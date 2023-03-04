package addsynth.material.blocks;

import addsynth.core.game.RegistryUtil;
import addsynth.core.util.math.random.RandomUtil;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.MiningStrength;
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
   * @param strength
   */
  public OreBlock(final String name, final MiningStrength strength){
    this(name, strength, 0, 0);
  }

  /**
   * Use this constructor if this Ore Block drops an item, such as Coal, Diamond, Lapis, Redstone, or Quartz.
   * @param name
   * @param strength
   * @param min_experience
   * @param max_experience
   */
  public OreBlock(final String name, final MiningStrength strength, int min_experience, int max_experience){
    super(Block.Properties.of(Material.STONE).strength(3.0f, 6.0f).requiresCorrectToolForDrops());
    // https://minecraft.gamepedia.com/Breaking#Blocks_by_hardness
    RegistryUtil.register_block(this, new ResourceLocation(ADDSynthMaterials.MOD_ID, name), ADDSynthMaterials.creative_tab);
    this.min_experience = min_experience;
    this.max_experience = max_experience;
  }

  @Override
  public final int getExpDrop(BlockState state, LevelReader world, BlockPos pos, int fortune, int silktouch){
    return silktouch == 0 ? RandomUtil.RandomRange(min_experience, max_experience) : 0;
  }

}
