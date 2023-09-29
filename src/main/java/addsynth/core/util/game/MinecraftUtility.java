package addsynth.core.util.game;

import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.java.StringUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class MinecraftUtility {

  public static final boolean isVanilla(final ItemStack stack){
    return isVanilla(stack.getItem());
  }

  public static final boolean isVanilla(final BlockItem itemblock){
    return isVanilla(itemblock.getBlock());
  }

  public static final boolean isVanilla(final Item item){
    if(item instanceof BlockItem){
      return isVanilla(((BlockItem)item).getBlock());
    }
    final ResourceLocation registry_name = item.getRegistryName();
    if(registry_name != null){
      return registry_name.getNamespace().equals("minecraft");
    }
    ADDSynthCore.log.error(new NullPointerException("The item '"+StringUtil.getName(item)+"' doesn't have its registry name set!"));
    return false;
  }

  public static final boolean isVanilla(final Block block){
    final ResourceLocation registry_name = block.getRegistryName();
    if(registry_name != null){
      return registry_name.getNamespace().equals("minecraft");
    }
    ADDSynthCore.log.error(new NullPointerException("The block '"+StringUtil.getName(block)+"' doesn't have its registry name set!"));
    return false;
  }

  public static final Block.Properties setUnbreakable(final Block.Properties properties){
    return properties.strength(-1.0F, 3600000.0F).noDrops(); // Bedrock, Barrier
  }

  /** <p>This is a helper method to get a specific type of TileEntity.
   *  <p>Returns a TileEntity cast to the type you specified if the TileEntity we
   *     found is an instance of the class you specified. Returns null otherwise.
   */
  @Nullable
  public static final <T extends BlockEntity> T getTileEntity(final BlockPos position, final Level world, final Class<T> specific_tile_entity_class){
    final BlockEntity tile = world.getBlockEntity(position);
    if(tile != null){
      if(specific_tile_entity_class.isInstance(tile)){
        return specific_tile_entity_class.cast(tile);
      }
    }
    return null;
  }

}
