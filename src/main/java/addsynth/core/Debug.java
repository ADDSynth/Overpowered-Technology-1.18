package addsynth.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import addsynth.core.gameplay.Config;
import addsynth.core.util.color.ColorUtil;
import addsynth.core.util.java.FileUtil;
import addsynth.core.util.java.StringUtil;
import addsynth.core.util.java.list.Sorters;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

public final class Debug {

  public static final boolean debug_recipes = false;
  
  public static final void log_recipe(Recipe recipe){
    if(debug_recipes){
      ADDSynthCore.log.info("Added "+recipe.getClass().getSimpleName()+" "+recipe.getId()+".");
    }
  }

  public static final void block(final Block block){
    block(block, null);
  }

  public static final void block(final Block block, final BlockPos position){
    ADDSynthCore.log.warn(
      "Debug Block: Type: "+block.getClass().getName()+
      ", Registry Name: "+block.getRegistryName()+
      ", Translation Key: "+block.getDescriptionId()+
      (position != null ? ", Position: "+position : ""));
  }

  public static final void item(final Item item){
    ADDSynthCore.log.warn(
      "Debug Item: Type: "+item.getClass().getName()+
      ", Registry Name: "+item.getRegistryName()+
      ", Translation Key: "+item.getDescriptionId());
  }

  public static final void debug(){
    if(Config.dump_map_colors.get()){
      ColorUtil.dump_map_colors();
    }
  }

  // This must be run when tags are done being loaded.
  public static final void dump_tags(){
    if(Config.dump_tags.get()){
      try{
        ADDSynthCore.log.info("Begin dumping tags...");
        
        printTags("block_tags.txt", Block.class, ForgeRegistries.BLOCKS);
        printTags( "item_tags.txt",  Item.class, ForgeRegistries.ITEMS);
        
        ADDSynthCore.log.info("Done dumping tags.");
      }
      catch(Exception e){
        ADDSynthCore.log.error("An error occured during tag dumping.", e);
      }
    }
  }

  private static final <T extends IForgeRegistryEntry<T>> void printTags(final String filename, final Class<T> type, final IForgeRegistry<T> registry){
    printTags(filename, type.getSimpleName(), registry);
  }

  private static final <T extends IForgeRegistryEntry<T>> void printTags(final String filename, final String type_name, final IForgeRegistry<T> registry){
    final ITagManager<T> tag_manager = registry.tags();
    if(tag_manager != null){
      final List<ITag<T>> tag_list = tag_manager.stream().sorted(Sorters.TagComparerMinecraftFirst).toList();
      final File file = FileUtil.getNewFile(filename);
      if(file != null){
        try(final FileWriter writer = new FileWriter(file)){
          writer.write(StringUtil.build("\n", type_name, " Tags: ", tag_list.size(), "\n\n"));
          Iterator<T> iterator;
          for(ITag<T> tag : tag_list){
            writer.write(tag.getKey().location().toString()+" {\n");
            iterator = tag.iterator();
            while(iterator.hasNext()){
              writer.write("  "+registry.getKey(iterator.next())+'\n');
            }
            writer.write("}\n\n");
          }
        }
        catch(IOException e){
          e.printStackTrace();
        }
      }
      return;
    }
    ADDSynthCore.log.error("The "+type_name+" registry does not support Tags.");
  }

}
