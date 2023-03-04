package addsynth.overpoweredmod.blocks.dimension;

import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

@Deprecated
public final class BlockGrassNoDestroy extends Block {

  public BlockGrassNoDestroy(){
    super(MinecraftUtility.setUnbreakable(Block.Properties.of(Material.GRASS)));
    // MAYBE: setRegistryName(new ResourceLocation(OverpoweredTechnology.MOD_ID, "grass_block"));
  }

  /*
  @Override
  public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) {
    if(world.isRemote == false){
      world.setBlockState(pos, Init.custom_grass_block.getDefaultState(), 2);
    }
  }
  */

}
