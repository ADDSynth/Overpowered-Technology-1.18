package addsynth.overpoweredmod.machines.suspension_bridge;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.core.game.inventory.SlotData;
import addsynth.core.util.constants.Constants;
import addsynth.energy.lib.main.Receiver;
import addsynth.energy.lib.tiles.TileBasicMachine;
import addsynth.overpoweredmod.game.core.Lens;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

public final class TileSuspensionBridge extends TileBasicMachine implements IBlockNetworkUser<BridgeNetwork>, MenuProvider {

  public static final Item[] filter = Lens.index;

  private BridgeNetwork network;

  private BridgeMessage bridge_message;
  private BridgeMessage[] message = new BridgeMessage[6];

  public TileSuspensionBridge(BlockPos position, BlockState blockstate){
    super(Tiles.ENERGY_SUSPENSION_BRIDGE, position, blockstate, new SlotData[] {new SlotData(filter, 1)}, new Receiver());
  }

  @Override
  public final void serverTick(){
    if(network == null){
      BlockNetworkUtil.create_or_join(level, this, BridgeNetwork::new);
    }
    network.tick(this);
  }

  @Override
  public void setRemoved(){
    super.setRemoved();
    BlockNetworkUtil.tileentity_was_removed(this, BridgeNetwork::new);
  }

  @Override
  public void load_block_network_data(){
    network.lens_index = Lens.get_index(inventory.getStackInSlot(0));
  }

  @Override
  public final void onInventoryChanged(){
    if(onServerSide()){
      network.update_lens(inventory.getStackInSlot(0));
    }
  }

  @Override
  public final void setBlockNetwork(BridgeNetwork network){
    this.network = network;
  }

  @Override
  @Nullable
  public final BridgeNetwork getBlockNetwork(){
    return network;
  }

  @Override
  public final Receiver getEnergy(){
    return energy;
  }

  public final void setMessages(final BridgeMessage bridge_message, final BridgeMessage[] messages){
    this.bridge_message = bridge_message;
    this.message = messages;
  }

  public final String getBridgeMessage(){
    if(bridge_message == null){ return Constants.null_error; }
    return bridge_message.getMessage();
  }

  public final String getMessage(final int index){
    if(message        == null){ return Constants.null_error; }
    if(message[index] == null){ return Constants.null_error; }
    return message[index].getMessage();
  }

  @Override
  public final void drop_inventory(){
    if(network.getCount() == 1){
      super.drop_inventory();
    }
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerSuspensionBridge(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return getBlockState().getBlock().getName();
  }

}
