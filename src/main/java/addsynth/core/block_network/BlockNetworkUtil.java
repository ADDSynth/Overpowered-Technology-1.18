package addsynth.core.block_network;

import java.util.function.BiFunction;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class BlockNetworkUtil {

  /**
   * This is called by {@link BlockNetwork#check(BlockNetwork, Level, BlockEntity, BiFunction)}.
   * @param <B> ? extends from BlockNetwork&ltT&gt
   * @param <T> ? extends from TileEntity AND IBlockNetworkUser&ltB&gt
   * @param world
   * @param tile The TileEntity attempting to creat a BlockNetwork.
   * @param constructor The function used to create a new instance of your BlockNetwork.
   *        The default method is to just pass in your BlockNetwork's constructor such as:
   *        <code>MyBlockNetwork::new</code>.
   */
  static final <B extends BlockNetwork<T>, T extends BlockEntity & IBlockNetworkUser<B>> B create_or_join(final Level world, final T tile, final BiFunction<Level, T, B> constructor){
    if(world == null){
      throw new NullPointerException("Can't create BlockNetwork because the world isn't loaded yet.");
    }
    if(!world.isClientSide){
      final B network = find_existing_network(world, tile);
      if(network == null){
        // new BlockNetwork
        return createBlockNetwork(world, tile, constructor);
      }
      // first existing Network that we find becomes the current Network, and overwrites all other networks.
      // tile.setBlockNetwork(network);
      network.updateBlockNetwork(world, tile.getBlockPos());
      return network;
    }
    return null;
  }

  /** Only call this if a BlockNetwork requires data from another BlockNetwork during their tick event,
   *  but calling {@link IBlockNetworkUser#getBlockNetwork()} returned {@code null}. Normal BlockNetwork
   *  initialization is achieved by calling {@link BlockNetwork#check(B, Level, T, BiFunction)}.
   * @param <B>
   * @param <T>
   * @param world
   * @param tile
   * @param constructor
   * @return A new BlockNetwork that has already been updated and had it's data loaded from the TileEntity.
   */
  public static final <B extends BlockNetwork<T>, T extends BlockEntity & IBlockNetworkUser<B>> B createBlockNetwork(final Level world, final T tile, final BiFunction<Level, T, B> constructor){
    if(!world.isClientSide){
      final BlockPos pos = tile.getBlockPos();
      final B network = constructor.apply(world, tile); // The BlockNetwork MUST be allowed to fully construct before we update!
      
      // Network data must be loaded BEFORE update, because BlockNetworks might perform certain actions after an update.
      tile.setBlockNetwork(network);
      tile.load_block_network_data();
      DebugBlockNetwork.DATA_LOADED(network, pos);
      
      network.updateBlockNetwork(world, pos);
      return network;
    }
    return null;
  }

  @SuppressWarnings({ "unchecked" })
  private static final <B extends BlockNetwork<T>, T extends BlockEntity & IBlockNetworkUser<B>> B find_existing_network(final Level world, final T tile){
    final BlockPos position = tile.getBlockPos();
    BlockPos offset;
    T check_tile;
    B network = null;
    for(final Direction direction : Direction.values()){
      offset = position.relative(direction);
      check_tile = (T)MinecraftUtility.getTileEntity(offset, world, tile.getClass());
      if(check_tile != null){
        network = (B)check_tile.getBlockNetwork();
        if(network != null){
          DebugBlockNetwork.JOINED(position, network, offset);
          break;
        }
      }
    }
    return network;
  }

  /** This is a static helper function that must be called in your Block's {@link Block#onRemove}
   *  function, for any blocks that may belong to your BlockNetwork. This properly removes the
   *  TileEntity from your BlockNetwork's list of TileEntities. Here's an example:<br>
   *  <pre><code>  @Override
   *  public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving){
   *    BlockNetworkUtil.onRemove(super::onRemove, MyTileEntity.class, MyBlockNetwork::new, state, world, pos, newState, isMoving);
   *  }</code></pre> */
  public static final <B extends BlockNetwork<T>, T extends BlockEntity & IBlockNetworkUser<B>> void onRemove(BlockRemoveFunction remove_method,
      Class<T> tile_class, BiFunction<Level, T, B> constructor, BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving){
    final T tile = MinecraftUtility.getTileEntity(pos, world, tile_class);
    remove_method.onRemove(state, world, pos, newState, isMoving);
    if(tile != null){
      if(tile.isRemoved()){ // has been removed by super.onRemove()
        // evidently, the onRemove function gets called every time the BlockState changes.
        removeTile(world, tile, constructor);
      }
    }
  }

  /** This must be called in your Block's {@link Block#onRemove} function to remove the TileEntity from your
   *  BlockNetwork's list of TileEntities. If this is the only thing you need to do, then we actually prefer
   *  you call {@link #onRemove}. This is only separated if you need to do other things. First get a reference
   *  to the TileEntity by calling {@link MinecraftUtility#getTileEntity}, then the super.onRemove method to
   *  remove the TileEntity from the world (just marks it for removal), then call this function to remove the
   *  TileEntity from the BlockNetwork, but only if the TileEntity exists, and has actually been removed by
   *  super.onRemove(). Check by calling {@link BlockEntity#isRemoved()}.
   * @param <B>
   * @param <T>
   * @param world
   * @param destroyed_tile
   * @param constructor
   */
  public static final <B extends BlockNetwork<T>, T extends BlockEntity & IBlockNetworkUser<B>> void removeTile(final Level world, final T destroyed_tile, final BiFunction<Level, T, B> constructor){
    if(destroyed_tile != null){
      final B network = destroyed_tile.getBlockNetwork();
      if(network != null){
        network.removeTile(world, destroyed_tile, constructor);
        destroyed_tile.setBlockNetwork(null);
      }
    }
  }

  /** Helper function. Call in block's {@link Block#neighborChanged} function.
   *  Used to cause the BlockNetwork to respond to an adjacent block being added or removed.
   *  @see BlockNetwork#neighbor_was_changed(Level, BlockPos, BlockPos)
   **/
  public static final void neighbor_changed(final Level world, final BlockPos pos, final BlockPos position_of_neighbor){
    if(world.isClientSide == false){
      final BlockEntity tile = world.getBlockEntity(pos);
      if(tile != null){
        if(tile instanceof IBlockNetworkUser){
          final BlockNetwork block_network = ((IBlockNetworkUser)tile).getBlockNetwork();
          if(block_network != null){
            block_network.neighbor_was_changed(world, pos, position_of_neighbor);
          }
        }
      }
    }
  }

}
