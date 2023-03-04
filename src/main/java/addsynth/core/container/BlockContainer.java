package addsynth.core.container;

import addsynth.core.ADDSynthCore;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;

/** This container is used for blocks that don't need to
 *  be a TileEntity.
 */
@Deprecated // REMOVE BlockContainer if this doesn't get used before the year 2024.
public abstract class BlockContainer extends AbstractContainer {

  private final Block block;

  public BlockContainer(final MenuType type, final int id, final Inventory player_inventory, final Block block){
    super(type, id, player_inventory);
    this.block = block;
  }

  public BlockContainer(final MenuType type, final int id, final Inventory player_inventory, final FriendlyByteBuf data){
    super(type, id, player_inventory, data);
    Block block = null;
    try{
      block = player_inventory.player.level.getBlockState(data.readBlockPos()).getBlock();
    }
    catch(Exception e){
      ADDSynthCore.log.error("Developer didn't include Block position when calling a gui? Read stack trace.", e);
    }
    this.block = block;
  }

  @Override
  public boolean stillValid(final Player player){
    return stillValid(ContainerLevelAccess.NULL, player, this.block);
  }

}
