package addsynth.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import addsynth.core.gameplay.Config;
import addsynth.core.util.color.ColorUtil;
import addsynth.core.util.java.FileUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
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
        
        // Sorter
        final boolean prioritize_minecraft_tag = true;
        final class TagComparer implements Comparator<TagKey<Block>> {
          @Override
          public int compare(TagKey<Block> k1, TagKey<Block> k2){
            final ResourceLocation o1 = k1.location();
            final ResourceLocation o2 = k2.location();
            if(prioritize_minecraft_tag){
              if(o1.getNamespace().equals("minecraft")){
                if(o2.getNamespace().equals("minecraft")){
                  return o1.compareTo(o2);
                }
                return -1;
              }
              if(o2.getNamespace().equals("minecraft")){
                if(o1.getNamespace().equals("minecraft")){
                  return o1.compareTo(o2);
                }
                return 1;
              }
            }
            return o1.compareTo(o2);
          }
        }
        final class RegistryComparer <T extends ForgeRegistryEntry<T>> implements Comparator<ForgeRegistryEntry<T>> {
          @SuppressWarnings("null")
          @Override
          public int compare(ForgeRegistryEntry<T> o1, ForgeRegistryEntry<T> o2){
            return o1.getRegistryName().toString().compareTo(o2.getRegistryName().toString());
          }
        }
        
        final String block_tags_file = "block_tags.txt";
        final String item_tags_file = "item_tags.txt";
        
        // Blocks PRIORITY: Still need to fix the comparer to sort the list so that Minecraft's tags are listed first!
        final ITagManager<Block> block_tag_manager = ForgeRegistries.BLOCKS.tags();
        final List<ITag<Block>> block_tag_list = block_tag_manager.stream().toList(); // have to convert to a list, because stream.size() will consume the whole stream?
        File file = FileUtil.getNewFile(block_tags_file);
        if(file != null){
          try(final FileWriter writer = new FileWriter(file)){
            writer.write("\nBlock Tags: "+block_tag_list.size()+"\n\n");
            Iterator<Block> iterator;
            for(ITag<Block> block_tag : block_tag_list){
              writer.write(block_tag.getKey().location().toString()+" {\n");
              iterator = block_tag.iterator();
              while(iterator.hasNext()){
                writer.write("  "+iterator.next().getRegistryName()+'\n');
              }
              writer.write("}\n\n");
            }
          }
          catch(IOException e){
            e.printStackTrace();
          }
        }
        
        // Items
        final ITagManager<Item> item_tag_manager = ForgeRegistries.ITEMS.tags();
        final List<ITag<Item>> item_tag_list = item_tag_manager.stream().toList();
        file = FileUtil.getNewFile(item_tags_file);
        if(file != null){
          try(final FileWriter writer = new FileWriter(file)){
            writer.write("\nItem Tags: "+item_tag_list.size()+"\n\n");
            Iterator<Item> iterator;
            for(ITag<Item> item_tag : item_tag_list){
              writer.write(item_tag.getKey().location().toString()+" {\n");
              iterator = item_tag.iterator();
              while(iterator.hasNext()){
                writer.write("  "+iterator.next().getRegistryName()+'\n');
              }
              writer.write("}\n\n");
            }
          }
          catch(IOException e){
            e.printStackTrace();
          }
        }
        
        ADDSynthCore.log.info("Done dumping tags.");
      }
      catch(Exception e){
        ADDSynthCore.log.error("An error occured during tag dumping.", e);
      }
    }
  }

}
