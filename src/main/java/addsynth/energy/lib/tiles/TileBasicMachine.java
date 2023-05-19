package addsynth.energy.lib.tiles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.game.inventory.IInputInventory;
import addsynth.core.game.inventory.InputInventory;
import addsynth.core.game.inventory.InventoryUtil;
import addsynth.core.game.inventory.SlotData;
import addsynth.energy.lib.main.Receiver;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

/** This is for TileEntities that need energy to do work, and also need an
 *  Input Inventory, but only for Input-only storage. If you need to do
 *  work on the items, use one of the other Machine classes.
 * @author ADDSynth
 */
public abstract class TileBasicMachine extends TileAbstractMachine implements IInputInventory {

  private boolean changed;
  protected final InputInventory inventory;

  public TileBasicMachine(BlockEntityType type, BlockPos position, BlockState blockstate,
                          SlotData[] slots, Receiver energy){
    super(type, position, blockstate, energy);
    this.inventory = InputInventory.create(this, slots);
  }

  public TileBasicMachine(BlockEntityType type, BlockPos position, BlockState blockstate,
                          int input_slots, Item[] filter, Receiver energy){
    super(type, position, blockstate, energy);
    this.inventory = InputInventory.create(this, input_slots, filter);
  }

  @Override
  public void serverTick(){
    if(energy.tick()){
      changed = true;
    }
    if(changed){
      update_data();
      changed = false;
    }
  }

  @Override
  public void load(final CompoundTag nbt){
    super.load(nbt);
    if(inventory != null){ inventory.load(nbt);}
  }

  @Override
  protected void saveAdditional(final CompoundTag nbt){
    super.saveAdditional(nbt);
    if(inventory != null){ inventory.save(nbt);}
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(final @Nonnull Capability<T> capability, final @Nullable Direction side){
    if(remove == false){
      if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
        return InventoryUtil.getInventoryCapability(inventory, null, side);
      }
      return super.getCapability(capability, side);
    }
    return LazyOptional.empty();
  }

  @Override
  public void onInventoryChanged(){
    changed = true;
  }

  @Override
  public void drop_inventory(){
    InventoryUtil.drop_inventories(worldPosition, level, inventory);
  }

  @Override
  public InputInventory getInputInventory(){
    return inventory;
  }

}
