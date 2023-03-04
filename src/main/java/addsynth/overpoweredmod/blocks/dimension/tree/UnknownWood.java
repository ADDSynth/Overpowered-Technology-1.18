package addsynth.overpoweredmod.blocks.dimension.tree;

import addsynth.core.block_network.Node;
import addsynth.core.util.block.BlockUtil;
import addsynth.core.util.world.WorldUtil;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public final class UnknownWood extends Block {

  public UnknownWood(){
    super(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0f));
    setRegistryName(Names.UNKNOWN_WOOD);
  }

  // TEST: I think I prefer this one because it works if the player is in Creative Mode as well.
  @Override
  public void playerWillDestroy(Level world, BlockPos position, BlockState state, Player player){
    if(world.isClientSide == false){
      BlockUtil.find_blocks(position, world, (Node node) -> valid(node, position)).forEach(
        (Node node) -> {
          if(node.position != position){
            world.removeBlock(node.position, false);
          }
        }
      );
      WorldUtil.spawnItemStack(world, position, new ItemStack(OverpoweredItems.void_crystal, 1));
    }
  }

  private static final boolean valid(final Node node, final BlockPos from){
    return node.block == OverpoweredBlocks.unknown_wood || node.block == OverpoweredBlocks.unknown_leaves || node.position == from;
  }

}
