package addsynth.core.block_network;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.util.java.StringUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

/** A Node neatly stores a Block Position, Block Type, and a TileEntity in the same object.
 *  The TileEntity can be null if no TileEntity exists at that location. */
public class Node {

  public final BlockPos position;
  public final Block block;
  protected final BlockEntity tile;

  public Node(final BlockPos position, final Level world){
    this.position = position;
    this.block = world.getBlockState(position).getBlock();
    this.tile = world.getBlockEntity(position);
  }

  public Node(@Nonnull final BlockEntity tile){
    this.position = tile.getBlockPos();
    this.block = tile.getBlockState().getBlock();
    this.tile = tile;
  }

  /** @deprecated This constructor is not recommended because programmers could create nodes with
   *    a block type that might not be the block we find in the BlockPosition.
   * @param position
   * @param block
   */
  @Deprecated
  public Node(final BlockPos position, final Block block){
    this.position = position;
    this.block = block;
    this.tile = null;
  }

  public Node(final BlockPos position, @Nonnull final BlockEntity tile){
    this.position = position;
    this.block = tile.getBlockState().getBlock();
    this.tile = tile;
  }

  public Node(final BlockPos position, final Block block, final BlockEntity tile){
    this.position = position;
    this.block = block;
    this.tile = tile;
  }

  public boolean isInvalid(){
    if(block == null || position == null){
      return true;
    }
    return tile != null ? (tile.isRemoved() || !tile.getBlockPos().equals(position)) : false;
  }

  @Nullable
  public BlockEntity getTile(){
    return tile;
  }

  @Override
  public String toString(){
    if(tile == null){
      return "Node{Position: "+position.toString()+", Block: "+StringUtil.getName(block)+"}";
    }
    return "Node{TileEntity: "+tile.getClass().getSimpleName()+", Position: "+position.toString()+"}";
  }

  @Override
  public boolean equals(final Object obj){
    return obj instanceof Node ? position.equals(((Node)obj).position) : false;
  }

}
