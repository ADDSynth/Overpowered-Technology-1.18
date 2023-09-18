package addsynth.core.util.block;

import addsynth.core.ADDSynthCore;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class BlockShape {

  // !!! This MUST follow the same order as the Direction enum!
  public static final int DOWN  =  1;
  public static final int UP    =  2;
  public static final int NORTH =  4;
  public static final int SOUTH =  8;
  public static final int WEST  = 16;
  public static final int EAST  = 32;

  public static final VoxelShape[] create_six_sided_binary_voxel_shapes(final double thickness){
    final double half_width = thickness / 2;
    return create_six_sided_binary_voxel_shapes(0.5 - half_width, 0.5 + half_width);
  }

  public static final VoxelShape[] create_six_sided_binary_voxel_shapes(final double min_size, final double max_size){
    final int max = 64;
    final VoxelShape[] shapes = new VoxelShape[max];
    final VoxelShape center = Shapes.box(min_size, min_size, min_size, max_size, max_size, max_size);
    final VoxelShape west   = Shapes.box(0, min_size, min_size, max_size, max_size, max_size);
    final VoxelShape down   = Shapes.box(min_size, 0, min_size, max_size, max_size, max_size);
    final VoxelShape north  = Shapes.box(min_size, min_size, 0, max_size, max_size, max_size);
    final VoxelShape east   = Shapes.box(min_size, min_size, min_size, 1, max_size, max_size);
    final VoxelShape up     = Shapes.box(min_size, min_size, min_size, max_size, 1, max_size);
    final VoxelShape south  = Shapes.box(min_size, min_size, min_size, max_size, max_size, 1);
    int i;
    for(i = 0; i < max; i++){
      
      shapes[i] = center;
      if((i & DOWN)  == DOWN ){ shapes[i] = Shapes.or(shapes[i], down);  }
      if((i & UP)    == UP   ){ shapes[i] = Shapes.or(shapes[i], up);    }
      if((i & NORTH) == NORTH){ shapes[i] = Shapes.or(shapes[i], north); }
      if((i & SOUTH) == SOUTH){ shapes[i] = Shapes.or(shapes[i], south); }
      if((i & WEST)  == WEST ){ shapes[i] = Shapes.or(shapes[i], west);  }
      if((i & EAST)  == EAST ){ shapes[i] = Shapes.or(shapes[i], east);  }
    }
    return shapes;
  }

  public static final int getIndex(final BlockState state){
    final int down  = state.getValue(BlockStateProperties.DOWN)  ? DOWN  : 0;
    final int up    = state.getValue(BlockStateProperties.UP)    ? UP    : 0;
    final int north = state.getValue(BlockStateProperties.NORTH) ? NORTH : 0;
    final int south = state.getValue(BlockStateProperties.SOUTH) ? SOUTH : 0;
    final int west  = state.getValue(BlockStateProperties.WEST)  ? WEST  : 0;
    final int east  = state.getValue(BlockStateProperties.EAST)  ? EAST  : 0;
    return down | up | north | south | east | west;
  }

  public static final VoxelShape combine(final VoxelShape ... shapes){
    if(shapes.length == 0){
      ADDSynthCore.log.error(new IllegalArgumentException("Improper use of the "+BlockShape.class.getSimpleName()+".combine(VoxelShape[] shapes) function! There are no shapes to combine!"));
      return null;
    }
    if(shapes.length == 1){ return shapes[0]; }
    VoxelShape final_shape = shapes[0];
    int i;
    for(i = 1; i < shapes.length; i++){
      final_shape = Shapes.or(final_shape, shapes[i]);
    }
    return final_shape;
  }

  private static final boolean is_90_degrees(final int degrees){
    if(degrees == 0 || degrees == 90 || degrees == 180 || degrees == 270){ return true; }
    ADDSynthCore.log.error(new IllegalArgumentException("degrees input for rotation functions in "+BlockShape.class.getSimpleName()+" is not a multiple of 90!"));
    return false;
  }

  // public static final VoxelShape[] rotate(final VoxelShape[] shape, final Direction.Axis axis, final int degrees){
  //   
  // } FEATURE Add BlockUtil.rotate() function.

  @Deprecated
  public static final VoxelShape rotate(double x0, double x1, double y0, double y1, double z0, double z1, Direction.Axis axis, int degrees){
    if(is_90_degrees(degrees)){
      switch(axis){
      case X: // left/right
        // damn quaternion magic...
        break;
      case Y:
        break;
      case Z:
        break;
      }
    }
    return Shapes.box(x0, y0, z0, x1, y1, z1);
  }

}
