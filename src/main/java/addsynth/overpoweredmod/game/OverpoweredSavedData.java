package addsynth.overpoweredmod.game;

import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.machines.laser.LaserJobs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.server.ServerStartedEvent;

public final class OverpoweredSavedData extends SavedData {

  // TODO: World Save Data. This is great! I can use this to save the signal data so players HAVE to find it each world!

  private static OverpoweredSavedData overpowered_savedata;
  
  /** This should be called in the {@link FMLServerStartedEvent}. */
  public static final void load(final MinecraftServer server){
    @SuppressWarnings("resource")
    final ServerLevel overworld = server.overworld();
    if(overworld != null){
      overpowered_savedata = overworld.getDataStorage().computeIfAbsent(OverpoweredSavedData::load, OverpoweredSavedData::create, OverpoweredTechnology.MOD_ID);
    }
  }

  /** And this should only be called on the server side! That should be obvious by now! */
  public static final void dataChanged(){
    if(overpowered_savedata != null){
      overpowered_savedata.setDirty(true);
    }
  }

  private static final OverpoweredSavedData create(){
    return new OverpoweredSavedData();
  }

  private static final OverpoweredSavedData load(CompoundTag nbt){
    final OverpoweredSavedData savedata = new OverpoweredSavedData();
    
    LaserJobs.load(nbt);
    return savedata;
  }

  @Override
  public final CompoundTag save(CompoundTag nbt){
    LaserJobs.save(nbt); // TODO: Since Lasers are ticked on a per-world basis, perhaps they should be saved separately in each world as well.
    return nbt;
  }

}
