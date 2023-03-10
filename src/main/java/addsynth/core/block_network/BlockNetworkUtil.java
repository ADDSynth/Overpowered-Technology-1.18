package addsynth.core.block_network;

import java.util.function.BiFunction;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class BlockNetworkUtil {

  /**
   * <p>Use this to initialize your BlockNetwork variable!
   * <p><b>Important!</b> Call this in your TileEntity's tick function, on the first tick!
   *   This produces an error if you call it in the onLoad() event, because the
   *   {@link BlockNetwork#updateBlockNetwork(BlockPos, BlockEntity)} calls
   *   {@link Level#getBlockState(BlockPos)} which could potentially load a chunk and then
   *   load more TileEntities, which call their onLoad() functions and begin creating
   *   another BlockNetwork! While we're already in the middle of updating one.
   * @param <B> ? extends from BlockNetwork&ltT&gt
   * @param <T> ? extends from TileEntity AND IBlockNetworkUser&ltB&gt
   * @param world
   * @param tile The TileEntity attempting to creat a BlockNetwork.
   * @param constructor the function to call to create a new BlockNetwork, usually your BlockNetwork's constructor.
   */
  public static final <B extends BlockNetwork<T>, T extends BlockEntity & IBlockNetworkUser<B>> void create_or_join(final Level world, final T tile, final BiFunction<Level, T, B> constructor){
    if(world == null){
      throw new NullPointerException("Can't create BlockNetwork because the world isn't loaded yet.");
    }
    if(world.isClientSide == false && tile.isRemoved() == false){
      B network = tile.getBlockNetwork();
      if(network == null){ // block network doesn't already exist
        network = find_existing_network(world, tile);
        if(network == null){
          // new BlockNetwork
          createBlockNetwork(world, tile, constructor);
        }
        else{
          tile.setBlockNetwork(network);
          network.updateBlockNetwork(tile.getBlockPos(), tile);
          // first existing Network that we find becomes the current Network, and overwrites all other networks.
        }
      }
    }
  }

  /** Only call this if a BlockNetwork requires data from another BlockNetwork during their update event.
   *  All normal BlockNetwork initializing should use the create_or_join() function!
   * @param world
   * @param tile
   * @param constructor
   */
  public static final <B extends BlockNetwork<T>, T extends BlockEntity & IBlockNetworkUser<B>> void createBlockNetwork(final Level world, final T tile, final BiFunction<Level, T, B> constructor){
    if(world.isClientSide == false){
      final B network = constructor.apply(world, tile);
      tile.setBlockNetwork(network);
      tile.load_block_network_data(); // set BlockNetwork data from first TileEntity that created it.
      network.updateBlockNetwork(tile.getBlockPos(), tile);
    }
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
          break;
        }
      }
    }
    return network;
  }

  /** <b>Required!</b> Call this function in your TileEntity's <code>invalidate()</code> or
   *  <code>remove()</code> function! Remember to call the <code>super</code> method first!
   * @param <B>
   * @param <T>
   * @param destroyed_tile
   * @param constructor
   */
  @SuppressWarnings({ "unchecked", "resource", "null" })
  public static final <B extends BlockNetwork<T>, T extends BlockEntity & IBlockNetworkUser<B>> void tileentity_was_removed(final T destroyed_tile, final BiFunction<Level, T, B> constructor){
    final Level world = destroyed_tile.getLevel();
    if(world.isClientSide){
      // ADDSynthCore.log.error("This should not be run on the client-side!");
      return;
    }
    BlockPos offset;
    BlockEntity tile;
    NodeList blocks = null;
    for(Direction side : Direction.values()){
      offset = destroyed_tile.getBlockPos().relative(side);
      tile = world.getBlockEntity(offset);
      
      if(tile != null){
        if(tile.getClass() == destroyed_tile.getClass()){ // TEST: construct better test to see if adjacent TileEntity belongs to this block network? Some BlockNetworks can have multiple types of BlockEntities connected to it.

          if(blocks == null){
            final B first_network = destroyed_tile.getBlockNetwork();
            if(first_network != null){
              first_network.updateBlockNetwork(offset, (T)tile); // update BlockNetwork with first adjacent TileEntity
              blocks = first_network.getTileEntityList();
            }
          }
          else{ // first adjacent TileEntity should've already updated its BlockNetwork by now.
            if(blocks.contains(offset) == false){
              createNewBlockNetwork(world, (T)tile, constructor);
              // When splitting one BlockNetwork into multiple networks, I could copy all its data,
              // but this creates duplicates, such as Energy, or items. So to avoid creating duplicates,
              // The first network remains intact, while subsequent orphan blocks become new BlockNetworks.
            }
          }

        }
      }
    }
    destroyed_tile.setBlockNetwork(null);
  }

  private static final <B extends BlockNetwork<T>, T extends BlockEntity & IBlockNetworkUser<B>> void createNewBlockNetwork(final Level world, final T tile, final BiFunction<Level, T, B> constructor){
    final B network = constructor.apply(world, tile);
    tile.setBlockNetwork(network);
    network.updateBlockNetwork(tile.getBlockPos(), tile);
  }

  /** Helper function. Call in block's {@link Block#neighborChanged} function.
   *  Used to cause the BlockNetwork to respond to an adjacent block being added or removed.
   *  @see BlockNetwork#neighbor_was_changed(BlockPos, BlockPos)
   **/
  public static final void neighbor_changed(final Level world, final BlockPos pos, final BlockPos position_of_neighbor){
    if(world.isClientSide == false){
      final BlockEntity tile = world.getBlockEntity(pos);
      if(tile != null){
        if(tile instanceof IBlockNetworkUser){
          final BlockNetwork block_network = ((IBlockNetworkUser)tile).getBlockNetwork();
          if(block_network != null){
            block_network.neighbor_was_changed(pos, position_of_neighbor);
          }
        }
      }
    }
  }

}
