package addsynth.overpoweredmod.machines.laser;

// quite a lot of imports for such a small class file.
import java.util.ArrayList;
import java.util.Collection;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.OverpoweredSavedData;
import addsynth.overpoweredmod.game.core.Laser;
import addsynth.overpoweredmod.machines.laser.beam.LaserBeam;
import addsynth.overpoweredmod.machines.laser.cannon.LaserCannon;
import addsynth.overpoweredmod.machines.laser.machine.LaserNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.server.ServerLifecycleHooks;

@EventBusSubscriber(modid = OverpoweredTechnology.MOD_ID, bus = Bus.FORGE)
public final class LaserJobs {

  private static final int beam_size = 10;
  private static final ArrayList<LaserJob> laser_jobs = new ArrayList<>(1);

  private final static class LaserBeamData {
    private final int color;
    private boolean move_forward = true;
    private final Direction direction;
    private MutableBlockPos position;
    private MutableBlockPos end_position;
    private boolean delete = false;
    
    private LaserBeamData(BlockPos position, int color, Direction direction){
      this.color = color;
      // passed in position is the position of the laser.
      // We must increment the position towards the direction the laser is facing.
      final int x = position.getX() + direction.getStepX();
      final int y = position.getY() + direction.getStepY();
      final int z = position.getZ() + direction.getStepZ();
      this.position = new MutableBlockPos(x, y, z);
       end_position = new MutableBlockPos(x, y, z);
      this.direction = direction;
    }
    
    private LaserBeamData(CompoundTag tag){
      color        = Math.max(tag.getInt("color"), 0) % Laser.values().length;
      move_forward = tag.getBoolean("move_forward");
      direction    = Direction.from3DDataValue(tag.getInt("direction"));
      position     = new MutableBlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
      end_position = new MutableBlockPos(tag.getInt("tail_x"), tag.getInt("tail_y"), tag.getInt("tail_z"));
    }
    
    private final void tick(final Level world, final int life){
      Block block;
      // if we can move forward
      if(move_forward){
        // check if next block can be destroyed
        block = world.getBlockState(position).getBlock();
        if(block == Blocks.BEDROCK){
          // if we can't destroy next block, stop moving forward
          move_forward = false;
        }
        else{
          // destroy block
          world.destroyBlock(position, true);
          // put a laser beam there
          world.setBlock(position, Laser.index[color].beam.get().defaultBlockState(), Block.UPDATE_CLIENTS);
        }
        // increment position
        position.move(direction);
      }
      
      // erase end laser beam, if it exists
      if(delete == false){
        if(life >= beam_size){
          block = world.getBlockState(end_position).getBlock();
          if(block instanceof LaserBeam){
            world.removeBlock(end_position, false);
            // NOTE: Error exists: If you shoot 2 laser beams into each other, it won't look right
            //       when one deletes the other. But this is minor and can be fixed later.
          }
          // increment position
          end_position.move(direction);
          // mark as delete if end position has reached the start position
          delete = end_position.equals(position);
        }
      }
    }
    
    private final CompoundTag save(){
      final CompoundTag tag = new CompoundTag();
      tag.putInt("color", color);
      tag.putInt("direction", direction.get3DDataValue());
      tag.putBoolean("move_forward", move_forward);
      // tag.putLong("position", position.asLong());
      // tag.putLong("end_position", end_position.asLong());
      tag.putInt("x", position.getX());
      tag.putInt("y", position.getY());
      tag.putInt("z", position.getZ());
      tag.putInt("tail_x", end_position.getX());
      tag.putInt("tail_y", end_position.getY());
      tag.putInt("tail_z", end_position.getZ());
      return tag;
    }
  }
  
  private final static class LaserJob {
    private final int distance;
    private final ArrayList<LaserBeamData> beams;
    private int life = 0;
    private final ResourceLocation dimension;
    
    private LaserJob(final Level world, final Collection<BlockPos> positions, final int distance){
      this.distance = distance;
      dimension = world.dimension().location();
      
      beams = new ArrayList<LaserBeamData>(positions.size());
      BlockState state;
      LaserCannon block;
      for(final BlockPos position : positions){
        state = world.getBlockState(position);
        if(state.getBlock() instanceof LaserCannon){
          block = (LaserCannon)state.getBlock();
          beams.add(new LaserBeamData(position, block.color, state.getValue(LaserCannon.FACING)));
        }
      }
    }

    private LaserJob(final CompoundTag tag){
      dimension = new ResourceLocation(tag.getString("dimension"));
      distance = Mth.clamp(tag.getInt("distance"), 1, LaserNetwork.max_laser_distance);
      life = Math.max(tag.getInt("life"), 0);
      final ListTag beam_list = tag.getList("beams", Tag.TAG_COMPOUND);
      int i;
      final int length = beam_list.size();
      beams = new ArrayList<LaserBeamData>(length);
      for(i = 0; i < length; i++){
        beams.add(new LaserBeamData(beam_list.getCompound(i)));
      }
    }
    
    private final void tick(final Level world){
      // tick all beams
      for(LaserBeamData beam : beams){
        beam.tick(world, life);
      }

      // increment life
      life++;
      
      // if we've reached end-of-life, stop incrementing begin position
      if(life == distance){
        for(LaserBeamData beam : beams){
          beam.move_forward = false;
        }
      }
      
      // delete any laser beams who's end position has reached the start position
      beams.removeIf((LaserBeamData b) -> b.delete);
    }

    private final CompoundTag save(){
      final CompoundTag tag = new CompoundTag();
      tag.putString("dimension", dimension.toString());
      tag.putInt("distance", distance);
      tag.putInt("life", life);
      final ListTag beam_list = new ListTag();
      for(LaserBeamData beam : beams){
        beam_list.add(beam.save());
      }
      tag.put("beams", beam_list);
      return tag;
    }

    private final boolean shouldDelete(){
      return (beams == null ? true : beams.isEmpty()) || life >= distance + beam_size;
    }
  }

  public static final void addNew(final Level world, final Collection<BlockPos> lasers, final int distance){
    laser_jobs.add(new LaserJob(world, lasers, distance));
    OverpoweredSavedData.dataChanged();
  }

  @SubscribeEvent
  public static final void tick(final ServerTickEvent event){
    if(event.phase == Phase.START){
      @SuppressWarnings("resource")
      final MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      if(server != null){
        server.getProfiler().push(OverpoweredTechnology.MOD_NAME + " Lasers");
        if(laser_jobs.size() > 0){
          for(ServerLevel world : server.getAllLevels()){
            for(LaserJob job : laser_jobs){
              if(world.dimension().location().equals(job.dimension)){
                job.tick(world);
              }
            }
          }
          laser_jobs.removeIf((LaserJob job) -> job.shouldDelete());
          OverpoweredSavedData.dataChanged();
        }
        server.getProfiler().pop();
      }
    }
  }

  public static final void load(CompoundTag nbt){
    final ListTag laser_tag = nbt.getList("Laser Jobs", Tag.TAG_COMPOUND);
    int i;
    for(i = 0; i < laser_tag.size(); i++){
      laser_jobs.add(new LaserJob(laser_tag.getCompound(i)));
    }
  }
  
  public static final void save(CompoundTag nbt){
    final ListTag laser_tag = new ListTag();
    for(LaserJob job : laser_jobs){
      laser_tag.add(job.save());
    }
    nbt.put("Laser Jobs", laser_tag);
  }

}
