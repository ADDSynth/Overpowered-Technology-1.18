package addsynth.energy.gameplay.machines.universal_energy_interface;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.util.game.tileentity.TileEntityUtil;
import addsynth.core.util.java.ArrayUtil;
import addsynth.energy.compat.energy.EnergyCompat;
import addsynth.energy.compat.energy.forge.ForgeEnergyIntermediary;
import addsynth.energy.gameplay.config.Config;
import addsynth.energy.lib.energy_network.tiles.BasicEnergyNetworkTile;
import addsynth.energy.lib.main.Energy;
import addsynth.energy.lib.main.ICustomEnergyUser;
import addsynth.energy.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

public final class TileUniversalEnergyInterface extends BasicEnergyNetworkTile
  // The Universal Energy Interface should REMAIN as an ICustomEnergyUser
  // and SHOULD NOT be treated as a Battery. Confirmed.
  implements ICustomEnergyUser, MenuProvider {

  private final Energy energy = new Energy(Config.universal_energy_interface_buffer.get());

  private final ForgeEnergyIntermediary forge_energy = new ForgeEnergyIntermediary(energy){
    @Override
    public boolean canExtract(){
      return super.canExtract() && transfer_mode.canExtract;
    }
    @Override
    public boolean canReceive(){
      return super.canReceive() && transfer_mode.canReceive;
    }
  };

  private boolean changed;

  private TRANSFER_MODE transfer_mode = TRANSFER_MODE.BI_DIRECTIONAL;

  public TileUniversalEnergyInterface(BlockPos position, BlockState blockstate){
    super(Tiles.UNIVERSAL_ENERGY_INTERFACE, position, blockstate);
  }

  @Override
  public final void serverTick(){
    try{
      super.serverTick(); // handles Energy Network stuff
      final EnergyCompat.CompatEnergyNode[] energy_nodes = EnergyCompat.getConnectedEnergy(worldPosition, level);
      if(energy_nodes.length > 0){
        if(transfer_mode.canReceive){
          EnergyCompat.acceptEnergy(energy_nodes, energy);
        }
        if(transfer_mode.canExtract){
          EnergyCompat.transmitEnergy(energy_nodes, energy);
        }
      }
      if(energy.tick()){
        changed = true;
      }
      if(changed){
        update_data();
        changed = false;
      }
    }
    catch(Exception e){
      TileEntityUtil.report_ticking_error(this, e);
    }
  }

  public final TRANSFER_MODE get_transfer_mode(){
    return transfer_mode;
  }
  
  public final void set_next_transfer_mode(){
    final int mode = (transfer_mode.ordinal() + 1) % TRANSFER_MODE.values().length;
    transfer_mode = TRANSFER_MODE.values()[mode];
    changed = true;
  }

  @Override
  public final double getRequestedEnergy(){
    if(transfer_mode.canExtract){
      return energy.getRequestedEnergy();
    }
    return 0;
  }

  @Override
  public final double getAvailableEnergy(){
    if(transfer_mode.canReceive){
      return energy.getAvailableEnergy();
    }
    return 0;
  }

  @Override
  public final void load(final CompoundTag nbt){
    super.load(nbt);
    energy.loadFromNBT(nbt);
    transfer_mode = ArrayUtil.getArrayValue(TRANSFER_MODE.values(), nbt.getByte("Transfer Mode"));
  }

  @Override
  protected final void saveAdditional(final CompoundTag nbt){
    super.saveAdditional(nbt);
    energy.saveToNBT(nbt);
    nbt.putByte("Transfer Mode", (byte)transfer_mode.ordinal());
  }

  @Override
  public @Nonnull <T> LazyOptional<T> getCapability(final @Nonnull Capability<T> capability, final @Nullable Direction facing){
    if(remove == false){
      if(capability == CapabilityEnergy.ENERGY){
        return forge_energy != null ? (LazyOptional.of(()->forge_energy)).cast() : LazyOptional.empty();
      }
      return super.getCapability(capability, facing);
    }
    return LazyOptional.empty();
  }
  
  @Override
  public final Energy getEnergy(){
    return energy;
  }
  
  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerUniversalEnergyInterface(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return getBlockState().getBlock().getName();
  }

}
