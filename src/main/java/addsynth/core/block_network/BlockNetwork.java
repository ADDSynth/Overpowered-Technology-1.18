package addsynth.core.block_network;

import java.util.Collection;
import java.util.function.BiFunction;
import addsynth.core.ADDSynthCore;
import addsynth.core.game.tiles.TileBase;
import addsynth.core.util.game.tileentity.ITickingTileEntity;
import addsynth.energy.lib.energy_network.EnergyNetwork;
import addsynth.overpoweredmod.machines.data_cable.DataCableNetwork;
import addsynth.overpoweredmod.machines.laser.machine.LaserNetwork;
import addsynth.overpoweredmod.machines.suspension_bridge.BridgeNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * <p>A Block Network is a collection of TileEntities that should act as a single entity and/or share
 * data between them. To stay in-sync it needs to be updated whenever a TileEntity is added or removed.
 * 
 * <p>Here are the steps to creating your own BlockNetwork:
 * 
 * <p><b>Step 1:</b> Extend this class with your own derived BlockNetwork class. It must be
 *    sub-typed by your TileEntity you want to use as the BlockNetwork, like this:<br>
 *    <code>public class MyBlockNetwork extends BlockNetwork&lt;MyTileEntity&gt;</code><br>
 *    You must implement the constructor and any missing abstract methods.
 * 
 * <p><b>Step 2:</b> The TileEntity that should act as your BlockNetwork should implement the
 *    {@link IBlockNetworkUser} interface, and sub-typed with your BlockNetwork like this:<br>
 *    <code>public class MyTileEntity implements IBlockNetworkUser&lt;MyBlockNetwork&gt;</code><br>
 *    This TileEntity must keep a reference to your BlockNetwork, but not initialize it.
 *    If you want multiple types of TileEntities to be part of your BlockNetwork, then the
 *    TileEntity that implements the {@link IBlockNetworkUser} interface must be an abstract class.
 * 
 * <p><b>Step 3:</b> In your TileEntity's {@link ITickingTileEntity#serverTick serverTick()} method,
 *    you must call either {@link BlockNetwork#check} to just initialize the BlockNetwork or call
 *    {@link BlockNetwork#tick(BlockNetwork, Level, BlockEntity, BiFunction)}. These methods
 *    automatically ensure your BlockNetwork is initialized on the first tick using your passed
 *    in constructor. It also ensures that the BlockNetwork is only ticked once, by checking the
 *    passed in TileEntity is the first one listed in our internal saved list of TileEntities.
 * <p>On the first tick, this will also call {@link IBlockNetworkUser#load_block_network_data()}
 *    to load the BlockNetwork's data from the TileEntity.
 * <p>You CANNOT create your BlockNetwork in your TileEntity's {@link BlockEntity#onLoad()} function,
 *    because the BlockNetwork updates right after it is created, and that update may cause other
 *    TileEntities to load and also call their {@link BlockEntity#onLoad()} function and create more
 *    BlockNetworks and perform another Update.
 * 
 * <p><b>Step 4:</b> Removing a TileEntity might cause a hole in your BlockNetwork, and some blocks
 *    won't be connected anymore. For any blocks that might be part of your BlockNetwork, you must call
 *    {@link BlockNetworkUtil#onRemove} in your Block's {@link Block#onRemove} function, like this:<br>
 *    <code>BlockNetworkUtil.onRemove(super::onRemove, MyTileEntity.class, MyBlockNetwork::new, oldState, level, pos, newState, isMoving);</code><br>
 *    The {@link Block#onRemove} function is called only on the server side whenever a block is mined
 *    by a player, destroyed by an explosion, or moved by a piston (but pistons can't move BlockEntities).
 * <p>Calling this function will handle everything that needs to be done, by first getting a reference
 *    to your TileEntity, then calling the {@code super.onRemove} method (which removes the TileEntity
 *    from the world) and then calling your BlockNetwork's {@link #removeTile} function.
 *    The passed in constructor is used to create new BlockNetworks for any BlockEntities which may
 *    have been split from the original BlockNetwork.
 * 
 * <p><b>Optional Step 1:</b> If you want to reference data from another BlockNetwork, then you must call
 *    That TileEntity's {@link IBlockNetworkUser#getBlockNetwork()} function to get the BlockNetwork.
 *    If it returns null, then that means that TileEntity hasn't been ticked yet. You can call
 *    {@link BlockNetworkUtil#createBlockNetwork} to create a BlockNetwork at that position. This will
 *    also cause that TileEntity to load it's BlockNetwork data from disk.
 * 
 * <p><b>Optional Step 2:</b> If you want BlockNetwork data to be saved to disk so that is exists after quitting
 *    a world and rejoining, then you must call
 *    your BlockNetwork's {@link BlockList#forAllTileEntities blocks.forAllTileEntities} function, and
 *    call some <code>saveDataFromNetwork</code> method, which writes the TileEntities data, then calls
 *    the {@link TileBase#update_data update_data} method. Here's an example:
 *    <code>blocks.forAllTileEntities((MyTileEntity tile) -> tile.saveDataFromNetwork(data));</code>
 * <p>You must ensure you never update an individual TileEntity's data. BlockNetwork data should be saved
 *    to all TileEntities, because you never know which one will be loaded first on world load.
 * 
 * <p><b>Optional Step 3:</b> Your BlockNetwork will always update whenever a TileEntity associated
 *    with the BlockNetwork is added or removed. If you want your BlockNetwork to update when other blocks
 *    are added or removed, (such as keeping a list of other attached blocks) then you'll want to
 *    implement the {@link #neighbor_was_changed} method. If you're only keeping track of one type
 *    of block, then it's best to check it there. You'll be responsible for updating your own list of
 *    blocks, adding or removing as needed. Rather than detecting if a block was removed, it's best to
 *    check your entire list first, and remove blocks that are invalid or don't exist anymore.
 * <p>In the {@link Block Blocks} associated with your BlockNetwork, you need to override the
 *    {@link Block#neighborChanged(BlockState, Level, BlockPos, Block, BlockPos, boolean)} function
 *    and call {@link BlockNetworkUtil#neighbor_changed(Level, BlockPos, BlockPos)}.
 * <p>If you checking for several types of blocks, then we need to do another approach. You need to call
 *    {@link #updateBlockNetwork(Level, BlockPos)} In the {@link #neighbor_was_changed} function.
 *    It's best to only update the BlockNetwork if the neighbor block is relevant to your BlockNetwork,
 *    so you need to check what kind of block it is. You'll also need to implement the {@link #clear_custom_data()}
 *    and {@link #customSearch(Node, Level)} methods, to clear your data and add new blocks respectively.
 * 
 * <p>-----------------------------------------------------------------------------------------------
 * <p>Here I will describe how BlockNetworks function for my own sanity and others:
 * 
 * <p><b>1.</b> When a player sets down a TileEntity that is new, its block_network variable is null,
 * so it must be initialized to a new BlockNetwork instance. Then it must immediately be updated to add
 * itself to the list of blocks in the BlockNetwork.
 * 
 * <p><b>2.</b> Now say the player places another TileEntity down next to the first one. The easiest
 * solution would be to initialize a new BlockNetwork, and update it. If you do this, then you
 * had BETTER ensure that all TileEntities you find that use this BlockNetwork should be set to THIS
 * BlockNetwork.
 * <p>However, we have a problem. What you did was create a NEW BlockNetwork, then set all TileEntity's
 * block_network variable to this new BlockNetwork. This overwrites the data that any existing
 * BlockNetwork had to their default values.
 * <p>So, the correct way to handle this is, when a TileEntity is created, you first check adjacent
 * positions for existing BlockNetworks, then set our own block_network variable to the one we found.
 * 
 * <p><b>3.</b> So Now let's set down a bunch of TileEntities, and they all share the same BlockNetwork.
 * You Quit the world, then load it back up again. The game will load the TileEntities, but in what order?
 * You can safely assume that the first TileEntity to load will correctly initialize its BlockNetwork field,
 * call its update function, and correctly find all connecting TileEntities and set their block_network variable.
 * So, whenever the World loads the next TileEntity, you'd better ONLY create a new BlockNetwork if it's null.
 * 
 * <p><b>4.</b> What happens when we remove a single TileEntity is pretty straight-forward. Minecraft
 * invalidates the TileEntity and then removes it, and when that happens there are no more references to the
 * TileEntity or its block_network variable. But what about a TileEntity that is a part of a BlockNetwork
 * of multiple TileEntities? Well first we must detect the TileEntity is being removed, then we must update
 * the BlockNetwork, but prevent it from finding the TileEntity that is being removed.
 * 
 * <p><b>5.</b> Continuing from #2, You must've wondered what happens if you place a TileEntity that
 * is adjacent to multiple TileEntities, but they were not connected, so they each have a different
 * BlockNetwork. We still must find an existing BlockNetwork and join it, but which one? Well each one
 * contains the same type of data but possibly different values, it would be far too cumbersome to try
 * to average them all out, so to simplify this case scenerio, we just accept the first one we find and
 * call the update method, which will find all TileEntities and overwrite their block_network variable
 * and their data.
 * 
 * <p><b>6.</b> Continuing from #4, and the inverse of #5, what happens if you have a row of TileEntities,
 * and you remove one in the middle, thus creating multiple BlockNetworks? This is by far the most
 * complicated scenerio that can happen. The TileEntity CAN be removed from the BlockNetwork's internal
 * list of blocks, but all TileEntities are still part of the SAME BlockNetwork, even though there's a gap!
 * So what we need to do is call the updateBlockNetwork function using the position of one of the
 * adjacent TileEntities. This will correctly setup one side of the gap, but not the others! We also need
 * to ensure that we keep the BlockNetwork's data as best as we can.
 * <p>So here's how we do it. when a TileEntity is removed, go ahead and detect which sides have the same
 * type of TileEntity on each side, this means they WERE part of the original BlockNetwork. Call the update
 * method on the first position we find, this will correctly re-assign all block positions. Continue
 * checking all the other sides of the TileEntity being deleted, and if we find a TileEntity, it's either
 * an orphan or now counted as part of the first BlockNetwork. We should check that position against the
 * positions we've just finished discovering from the first BlockNetwork. If they aren't in the new list,
 * that means they are no longer connected, and should be a new BlockNetwork.
 * <p>Any new BlockNetworks we create, we can assign their data to that of the original BlockNetwork's data,
 * but this just duplicates the data. Where there were previously 1 BlockNetwork with the data, now there
 * are 2 or more. This duplicates data such as Energy, or items that were a part of the BlockNetwork.
 * We obviously don't want players to exploit this duplication, so all new BlockNetworks must be reset
 * to their default data.
 *
 * <p><b>7.</b> Oh dang. There's actually one more scenario I forgot about. Some block networks have extra
 * data, sure, but let's say you want to keep a list of TileEntities this BlockNetwork connects to,
 * Tiles that AREN'T part of the Block Network. For example, the {@link EnergyNetwork}
 * keeps track of {@link addsynth.energy.lib.main.IEnergyUser Energy Users} that the network connects to.
 * In this case, you want to update the Block Network (or at least the BlockNetwork's data) whenever
 * you detect the block next to a TileEntity that belongs to this Block Network was CHANGED, because
 * a block could've been added or removed, and you need to update the data accordingly. So, we use
 * {@link Block#neighborChanged(BlockState, Level, BlockPos, Block, BlockPos, boolean)}
 * to detect the block beside us has changed and then call our BlockNetwork's update event.
 *
 * <p><b>8.</b> So now let's talk about BlockNetwork data. Out of the 4 BlockNetwork examples that exist in
 * ADDSynth's mods as of writing this, {@link EnergyNetwork} and {@link DataCableNetwork} only store positions
 * of TileEntities. That can be calculated at runtime and doesn't need to save any data to TileEntities.
 * However, {@link LaserNetwork} has a shared boolean value that determines if the LaserNetwork is running
 * or not. When a player toggles the On/Off switch, this must set the BlockNetwork's variable, and then
 * update all TileEntities. And {@link BridgeNetwork} allows a player to insert a Lens in any Suspension Bridge
 * block, and all Bridge blocks share the same network, so the player can access the Lens from any other block.
 * <p>The function for updating all the TileEntities in the {@link BlockNetwork#blocks} list doesn't exist,
 * so you'll have to make your own, and call it whenever your BlockNetwork's data changes. Also, when the
 * world loads, it does create new BlockNetworks, but you also want it to load saved data. For this reason
 * {@link BlockNetworkUtil#create_or_join} automatically
 * calls your TileEntity's {@link IBlockNetworkUser#load_block_network_data()} function.
 *
 * <p><b>9.</b> One last bit of advice. Sometimes a BlockNetwork wants information on another BlockNetwork,
 * so it calls that TileEntity's {@link IBlockNetworkUser#getBlockNetwork()} function. But the return
 * value of that function MIGHT be null. This only happens during World load, when one BlockNetwork starts
 * loading and updating before the other. Because of the way {@link BlockNetworkUtil#create_or_join} is
 * set up right now, you can't put anything in the {@link IBlockNetworkUser#getBlockNetwork()} that will
 * initialize a BlockNetwork because that would cause an infinite loop. Instead, if you depend on
 * another BlockNetwork during a BlockNetwork update, check if it is null and initialize it yourself
 * by calling {@link BlockNetworkUtil#createBlockNetwork}.
 *
 * <p><b>10.</b> Continuing from #7, This scenerio involves when during the BlockNetwork update, it changes
 * a block in the world, such as by calling {@link Level#setBlock(BlockPos, BlockState, int)}.
 * This will then call {@link Block#neighborChanged(BlockState, Level, BlockPos, Block, BlockPos, boolean)}
 * Which can cause some Block Networks to begin updating again, while during the first update, which may
 * trigger a Null Pointer Exception. Argh, I'm not explaining this very well. You must either check
 * if the Block Network is null, or check what kind of BlockNetwork it is and create a new one yourself,
 * which in turn calls that BlockNetwork's update method anyway.
 * <p>Currently, all of our neighbor detections assumes this only occurs during normal gameplay, and NOT
 * DURING WORLD LOAD! So there are no null checks. Luckily it seems that only the Energy Suspension Bridge
 * changes the world during its Block Network update, and it does not need to detect when blocks next to it
 * has changed.
 *
 * @see EnergyNetwork
 * @see LaserNetwork
 * @see DataCableNetwork
 * @see BridgeNetwork
 * @author ADDSynth
 * @since July 1, 2020
 */
public abstract class BlockNetwork<T extends BlockEntity & IBlockNetworkUser> {

  /** Only used in the {@link #is_valid} function to determine if
   *  the passed-in TileEntity is part of this BlockNetwork. */
  private final Class<? extends BlockEntity> class_type;

  /** All the blocks that are in this block network. */
  protected final BlockList<T> blocks = new BlockList<>();

  public BlockNetwork(final Level world, final T tile){
    if(world == null){
      throw new NullPointerException("Loaded Block Network too early! Level hasn't been loaded yet.");
    }
    if(world.isClientSide){
      throw new RuntimeException("Block Networks SHOULD NOT be created on the Client side!");
    }
    class_type = tile.getClass();
    DebugBlockNetwork.CREATED(this, tile.getBlockPos());
  }

  // This works perfectly and very efficiently. Never change it!
  protected static final void remove_invalid_nodes(final Collection<? extends Node> node_list){
    node_list.removeIf((Node n) -> n == null ? true : n.isInvalid());
  }

  /** This is a static helper function, used to initialize your BlockNetwork
   *  in the {@link ITickingTileEntity#serverTick()} function. Use this if
   *  your BlockNetwork does not need to be ticked. */
  public static final <B extends BlockNetwork<T>, T extends BlockEntity & IBlockNetworkUser<B>> B check(final B network, final Level world, final T tile, final BiFunction<Level, T, B> constructor){
    if(network == null){
      if(!tile.isRemoved()){
        return BlockNetworkUtil.create_or_join(world, tile, constructor);
      }
      return null;
    }
    return network;
  }

  /** Static helper function that automatically initializes your BlockNetwork if needed
   *  and ticks it. This must be called in the {@link ITickingTileEntity#serverTick()}
   *  method and your BlockNetwork should override the {@link #tick(Level)} method. */
  public static final <B extends BlockNetwork<T>, T extends BlockEntity & IBlockNetworkUser<B>> void tick(final B network, final Level world, final T tile, final BiFunction<Level, T, B> constructor){
    if(!world.isClientSide){
      final B good_network = check(network, world, tile, constructor);
      good_network.baseTick(world, tile);
    }
  }

  private final boolean is_valid(final Node node){
    final BlockEntity tile = node.getTile();
    if(tile != null){
      if(!tile.isRemoved()){
        return class_type.isInstance(tile);
      }
    }
    return false;
  }

  /**
   * Must be called when splitting or joining BlockNetworks, and right after creating BlockNetworks during TileEntity load.
   * @param from
   */
  public final void updateBlockNetwork(final Level world, final BlockPos from){
    if(world != null){
      if(world.isClientSide == false){
        try{
          DebugBlockNetwork.UPDATED(this, from);
          clear_custom_data();
          blocks.update(world, from, this, this::is_valid, this::customSearch);
          onUpdateNetworkFinished(world);
        }
        catch(Exception e){
          ADDSynthCore.log.fatal("Error occured in BlockNetwork update! WHAT HAPPENED???", e);
        }
        return;
      }
    }
    ADDSynthCore.log.error("BlockNetwork.updateNetwork() method is not supposed to be called on client-side.");
    // Thread.dumpStack();
  }

  /** This is called by {@link BlockNetworkUtil#removeTile(Level, BlockEntity, BiFunction)}.
   *  This checks all adjacent positions next to the TileEntity that was removed. For the
   *  first valid TileEntity we find, remains as the original BlockNetwork and gets updated.
   *  Any blocks that WERE part of the BlockNetwork but are now separated must be turned
   *  new BlockNetworks. */
  @SuppressWarnings("unchecked")
  final <B extends BlockNetwork> void removeTile(final Level world, final T destroyed_tile, final BiFunction<Level, T, B> constructor){
    final BlockPos tile_position = destroyed_tile.getBlockPos();
    DebugBlockNetwork.TILE_REMOVED(this, tile_position);
    blocks.remove(destroyed_tile);
    if(blocks.size() == 0){
      lastTileWasRemoved(world, destroyed_tile);
    }
    else{
      boolean first = true;
      Node node;
      BlockPos position;
      for(Direction side : Direction.values()){
        position = tile_position.relative(side);
        node = new Node(position, world);
        if(is_valid(node)){ // checks for null and IBlockNetworkUser
          if(first){ // first valid tile
            updateBlockNetwork(world, position);
            // Now all adjacent tiles, even though they hold a reference to the original network,
            // the network won't contain their position AFTER UPDATE if they were disconnected.
            first = false;
          }
          else{
            // if it's an original block, then the network SHOULD NOT contain that position, after update.
            // if it's part of another network we already updated, then that position SHOULD be in it's block list.
            final T tile = (T)node.getTile();
            if(isInvalid(tile)){
              DebugBlockNetwork.SPLIT(position, this);
              final B new_network = constructor.apply(world, tile);
              new_network.updateBlockNetwork(world, position);
            }
          }
        }
      }
    }
  }

  /** This is mainly used internally. Gets THAT TileEntity's BlockNetwork's
   *  {@link BlockList} and checks if the {@link BlockList} contains that TileEntity.
   * @param <T>
   * @param tile
   * @return TRUE  if a previous adjacent TileEntity was Updated and now DOES NOT contain THIS TileEntity.<br>
   *         FALSE if a previous adjacent TileEntity was Updated and now contains this TileEntity.
   */
  public static final <T extends BlockEntity & IBlockNetworkUser> boolean isInvalid(final T tile){
    final BlockNetwork network = tile.getBlockNetwork();
    if(network != null){
      return !network.blocks.contains(tile);
    }
    return true;
  }

  final void baseTick(final Level world, final T tile){
    if(blocks.isFirstTile(tile)){
      tick(world);
    }
  }

  /** Override this method if you want your BlockNetwork to execute code every tick. We have internal
   *  code that ensures your BlockNetwork is only ticked once per tick. To tick your BlockNetwork you
   *  must call the static {@link BlockNetwork#tick(BlockNetwork, Level, BlockEntity, BiFunction)}
   *  in your TileEntity's {@link ITickingTileEntity#serverTick()} method. */
  protected void tick(final Level world){
  }

  public final int getCount(){
    return blocks == null ? 0 : blocks.size();
  }

  protected abstract void clear_custom_data();

  /** Called when {@link #updateBlockNetwork} function is completed. */
  protected void onUpdateNetworkFinished(final Level level){
  }

  protected void customSearch(final Node node, final Level world){
  }

  // TODO: Once we start coding for MC 1.16 and leave 1.12 behind, retest the onNeighborChange / neighborChanged issue, and
  //         remove all references to onNeighborChange.
  /**<p>
   *   Call this in the block's
   *   {@link Block#neighborChanged(BlockState, Level, BlockPos, Block, BlockPos, boolean)} method.<br>
   *   <b>Do not use</b> the {@link Block#onNeighborChange(BlockState, LevelReader, BlockPos, BlockPos)} method!
   *   Starting in Minecraft 1.11 the {@link Level#updateNeighbourForOutputSignal(BlockPos, Block)} method
   *   is no longer called at the end of the {@link Level#setBlockEntity(BlockEntity)} function,
   *   so it doesn't update Block Networks at all.
   * <p>NOTE: It seems <code>onNeighborChange()</code> is added by Forge and only gets called if the neighbor block was
   *   a TileEntity, whereas <code>neighborChanged()</code> gets called every time an adjacent block gets changed.
   *   Best continue to call Minecraft's <code>neighborChanged()</code> function, in case someone's BlockNetwork
   *   wants to keep track of basic blocks.
   * <p>
   *   We actually recommend you call the simplified helper function
   *   {@link BlockNetworkUtil#neighbor_changed(Level, BlockPos, BlockPos)} instead.
   * <p>
   *   Your BlockNetwork automatically responds to block changes if the block is part of your BlockNetwork.
   *   So this is only used to respond to block changes that your BlockNetwork keeps track of, that are
   *   NOT part of your BlockNetwork.
   * <p>
   *   First, you do not want to update on EVERY neighbor block change, only the blocks that affect your
   *   BlockNetwork. Once you detect the type of block, the simplest way to update is to call your
   *   BlockNetwork's {@link #updateBlockNetwork(Level, BlockPos)} function. This will automatically clear
   *   your custom data and call {@link #customSearch(Node, Level)} which again checks the block and then
   *   you can decide what to do with it. This is recommended if your BlockNetwork keeps track of lots of
   *   different kinds of blocks. But if you only track one type of block then we recommend doing the
   *   optimized approach, by checking the position of the neighbor and adding or removing it from your
   *   list accordingly.
   * <p>
   *   If the list of TileEntities you keep track of utilize {@link Node Nodes}, then you can call
   *   {@link #remove_invalid_nodes(Collection)} to automatically remove TileEntities that were removed.
   *   Otherwise you'll have to check for removed TileEntities yourself!
   * @see EnergyNetwork#neighbor_was_changed(Level, BlockPos, BlockPos)
   * @see DataCableNetwork#neighbor_was_changed(Level, BlockPos, BlockPos)
   * @see LaserNetwork#neighbor_was_changed(Level, BlockPos, BlockPos)
   * @param current_position
   * @param position_of_neighbor
   */
  public void neighbor_was_changed(final Level world, final BlockPos current_position, final BlockPos position_of_neighbor){
  }

  /** Override this to perform special actions when the last TileEntity
   *  belongs to this BlockNetwork has been removed. */
  protected void lastTileWasRemoved(final Level world, final T last_tile){
  }

}
