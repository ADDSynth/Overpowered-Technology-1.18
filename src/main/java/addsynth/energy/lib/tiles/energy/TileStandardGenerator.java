package addsynth.energy.lib.tiles.energy;

import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.game.inventory.IInputInventory;
import addsynth.core.game.inventory.InputInventory;
import addsynth.core.game.inventory.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

/** Standard Generators are generators that have an Input inventory and
 *  accept fuel items to be consumed to produce Energy. 
 * @author ADDSynth
 */
public abstract class TileStandardGenerator extends TileAbstractGenerator implements IInputInventory {

  protected final InputInventory input_inventory;

  public TileStandardGenerator(final BlockEntityType type, BlockPos position, BlockState blockstate, final Predicate<ItemStack> filter){
    super(type, position, blockstate);
    this.input_inventory = InputInventory.create(this, 1, filter);
  }

  @Override
  public void serverTick(){
    // standard generator behaviour
    if(energy.isEmpty()){
      if(input_inventory.isEmpty() == false){
        setGeneratorData();
        changed = true;
      }
    }
    // TODO: To enable the Generator to use energy every tick:
    // Multiple energy networks could extract Energy from the Generator during a tick,
    // Either before or after this is ticked. Therefore, we can only remove any remaining
    // energy after all TileEntities have been ticked, such as a PostServerTick or something.
    // This is also better suited to handle the Energy.updateEnergyIO task as well, instead
    // of inside the saveToNBT function.
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
    if(input_inventory != null){ input_inventory.load(nbt);}
  }

  @Override
  protected void saveAdditional(final CompoundTag nbt){
    super.saveAdditional(nbt);
    if(input_inventory != null){ input_inventory.save(nbt);}
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(final @Nonnull Capability<T> capability, final @Nullable Direction side){
    if(remove == false){
      if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
        return InventoryUtil.getInventoryCapability(input_inventory, null, side);
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
  public final void drop_inventory(){
    InventoryUtil.drop_inventories(worldPosition, level, input_inventory);
  }

  @Override
  public final InputInventory getInputInventory(){
    return input_inventory;
  }

}
