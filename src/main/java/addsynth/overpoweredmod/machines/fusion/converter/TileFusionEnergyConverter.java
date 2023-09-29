package addsynth.overpoweredmod.machines.fusion.converter;

import java.util.ArrayList;
import addsynth.core.game.tiles.TileBase;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.core.util.game.tileentity.ITickingTileEntity;
import addsynth.energy.lib.main.Generator;
import addsynth.energy.lib.main.IEnergyGenerator;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.machines.data_cable.DataCableNetwork;
import addsynth.overpoweredmod.machines.data_cable.TileDataCable;
import addsynth.overpoweredmod.machines.fusion.chamber.TileFusionChamber;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class TileFusionEnergyConverter extends TileBase implements IEnergyGenerator, ITickingTileEntity {

  private final Generator energy = new Generator(MachineValues.fusion_energy_output_per_tick.get());
  private static final int sync_timer = 4; // TODO: remove sync timer in version 1.7
  private final ArrayList<DataCableNetwork> data_cable_networks = new ArrayList<>(1);
  private TileFusionChamber fusion_chamber;
  private boolean activated;
  private boolean valid;

  public TileFusionEnergyConverter(BlockPos position, BlockState blockstate){
    super(Tiles.FUSION_ENERGY_CONVERTER.get(), position, blockstate);
  }

  @Override
  public final void serverTick(){
    if(level.getGameTime() % sync_timer == 0){
      
      final BlockPos previous_position = fusion_chamber != null ? fusion_chamber.getBlockPos() : null;
      final boolean previous_valid = valid;
      
      check_connection(); // keep up-to-date, always.
      activated = level.hasNeighborSignal(worldPosition);
      
      
      if(fusion_chamber == null){
        if(previous_position != null){
          fusion_chamber = MinecraftUtility.getTileEntity(previous_position, level, TileFusionChamber.class);
        }
      }
      
      if(fusion_chamber != null){ // Cannot be valid without fusion chamber
        fusion_chamber.set_state(activated && valid, owner); // keep fusion chamber up-to-date if it exists.
        if(activated && valid == false && previous_valid == true){
          // only explodes if valid goes from true to false. Loading a world is safe because it goes from false to true.
          fusion_chamber.explode();
          fusion_chamber = null;
        }
      }
    }
    // every tick
    if(activated && valid){
      energy.set_to_full();
    }
    energy.updateEnergyIO();
  }

  @Override
  public void load(final CompoundTag nbt){
    super.load(nbt);
    loadPlayerData(nbt);
  }

  @Override
  protected void saveAdditional(final CompoundTag nbt){
    super.saveAdditional(nbt);
    savePlayerData(nbt);
  }

  private final void check_connection(){
    get_networks();
    valid = false;
    fusion_chamber = null;
    BlockPos position;
    if(data_cable_networks.size() > 0){
      for(DataCableNetwork network : data_cable_networks){
        position = network.get_valid_fusion_container(level);
        if(position != null){
          fusion_chamber = (TileFusionChamber)level.getBlockEntity(position);
          if(fusion_chamber != null){
            if(fusion_chamber.has_fusion_core()){
              valid = true;
              break;
            }
          }
        }
      }
    }
  }

  private final void get_networks(){
    data_cable_networks.clear();
    BlockEntity tile;
    DataCableNetwork data_network;
    for(Direction facing : Direction.values()){
      tile = level.getBlockEntity(worldPosition.relative(facing));
      if(tile != null){
        if(tile instanceof TileDataCable){
          data_network = ((TileDataCable)tile).getBlockNetwork();
          if(data_network != null){
            if(data_cable_networks.contains(data_network) == false){
              data_cable_networks.add(data_network);
            }
          }
        }
      }
    }
  }

  @Override
  public final Generator getEnergy(){
    return energy;
  }

}
