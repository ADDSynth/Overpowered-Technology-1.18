package addsynth.core.util.color;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.TreeSet;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.java.FileUtil;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

// What? Java doesn't have unsigned numeric types? That's the stupidest thing I've ever heard!
// https://stackoverflow.com/questions/430346/why-doesnt-java-support-unsigned-ints
// https://en.wikipedia.org/wiki/Criticism_of_Java#Unsigned_integer_types

/** <p>This utility class is used to assist in dealing with Minecraft's color codes.
 *     Minecraft's color codes are integers that use 8 bits each to store the red,
 *     green, and blue channels in that order.
 *  <p>ADDSynthCore will automatically call {@link #dump_map_colors()} if it is enabled
 *     in the config.
 * @author ADDSynth
 * @see Color
 * @see MinecraftColor
 * @since October 23, 2019
 */
public final class ColorUtil {

  public static final ImmutableMap<MaterialColor, String> map_color_names = new ImmutableMap.Builder<MaterialColor, String>()
    .put(MaterialColor.NONE,                   "MaterialColor.NONE")
    .put(MaterialColor.GRASS,                  "MaterialColor.GRASS")
    .put(MaterialColor.SAND,                   "MaterialColor.SAND")
    .put(MaterialColor.WOOL,                   "MaterialColor.WOOL")
    .put(MaterialColor.FIRE,                   "MaterialColor.FIRE")
    .put(MaterialColor.ICE,                    "MaterialColor.ICE")
    .put(MaterialColor.METAL,                  "MaterialColor.METAL")
    .put(MaterialColor.PLANT,                  "MaterialColor.PLANT")
    .put(MaterialColor.SNOW,                   "MaterialColor.SNOW")
    .put(MaterialColor.CLAY,                   "MaterialColor.CLAY")
    .put(MaterialColor.DIRT,                   "MaterialColor.DIRT")
    .put(MaterialColor.STONE,                  "MaterialColor.STONE")
    .put(MaterialColor.WATER,                  "MaterialColor.WATER")
    .put(MaterialColor.WOOD,                   "MaterialColor.WOOD")
    .put(MaterialColor.QUARTZ,                 "MaterialColor.QUARTZ")
    .put(MaterialColor.COLOR_ORANGE,           "MaterialColor.COLOR_ORANGE")
    .put(MaterialColor.COLOR_MAGENTA,          "MaterialColor.COLOR_MAGENTA")
    .put(MaterialColor.COLOR_LIGHT_BLUE,       "MaterialColor.COLOR_LIGHT_BLUE")
    .put(MaterialColor.COLOR_YELLOW,           "MaterialColor.COLOR_YELLOW")
    .put(MaterialColor.COLOR_LIGHT_GREEN,      "MaterialColor.COLOR_LIGHT_GREEN")
    .put(MaterialColor.COLOR_PINK,             "MaterialColor.COLOR_PINK")
    .put(MaterialColor.COLOR_GRAY,             "MaterialColor.COLOR_GRAY")
    .put(MaterialColor.COLOR_LIGHT_GRAY,       "MaterialColor.COLOR_LIGHT_GRAY")
    .put(MaterialColor.COLOR_CYAN,             "MaterialColor.COLOR_CYAN")
    .put(MaterialColor.COLOR_PURPLE,           "MaterialColor.COLOR_PURPLE")
    .put(MaterialColor.COLOR_BLUE,             "MaterialColor.COLOR_BLUE")
    .put(MaterialColor.COLOR_BROWN,            "MaterialColor.COLOR_BROWN")
    .put(MaterialColor.COLOR_GREEN,            "MaterialColor.COLOR_GREEN")
    .put(MaterialColor.COLOR_RED,              "MaterialColor.COLOR_RED")
    .put(MaterialColor.COLOR_BLACK,            "MaterialColor.COLOR_BLACK")
    .put(MaterialColor.GOLD,                   "MaterialColor.GOLD")
    .put(MaterialColor.DIAMOND,                "MaterialColor.DIAMOND")
    .put(MaterialColor.LAPIS,                  "MaterialColor.LAPIS")
    .put(MaterialColor.EMERALD,                "MaterialColor.EMERALD")
    .put(MaterialColor.PODZOL,                 "MaterialColor.PODZOL")
    .put(MaterialColor.NETHER,                 "MaterialColor.NETHER")
    .put(MaterialColor.TERRACOTTA_WHITE,       "MaterialColor.TERRACOTTA_WHITE")
    .put(MaterialColor.TERRACOTTA_ORANGE,      "MaterialColor.TERRACOTTA_ORANGE")
    .put(MaterialColor.TERRACOTTA_MAGENTA,     "MaterialColor.TERRACOTTA_MAGENTA")
    .put(MaterialColor.TERRACOTTA_LIGHT_BLUE,  "MaterialColor.TERRACOTTA_LIGHT_BLUE")
    .put(MaterialColor.TERRACOTTA_YELLOW,      "MaterialColor.TERRACOTTA_YELLOW")
    .put(MaterialColor.TERRACOTTA_LIGHT_GREEN, "MaterialColor.TERRACOTTA_LIGHT_GREEN")
    .put(MaterialColor.TERRACOTTA_PINK,        "MaterialColor.TERRACOTTA_PINK")
    .put(MaterialColor.TERRACOTTA_GRAY,        "MaterialColor.TERRACOTTA_GRAY")
    .put(MaterialColor.TERRACOTTA_LIGHT_GRAY,  "MaterialColor.TERRACOTTA_LIGHT_GRAY")
    .put(MaterialColor.TERRACOTTA_CYAN,        "MaterialColor.TERRACOTTA_CYAN")
    .put(MaterialColor.TERRACOTTA_PURPLE,      "MaterialColor.TERRACOTTA_PURPLE")
    .put(MaterialColor.TERRACOTTA_BLUE,        "MaterialColor.TERRACOTTA_BLUE")
    .put(MaterialColor.TERRACOTTA_BROWN,       "MaterialColor.TERRACOTTA_BROWN")
    .put(MaterialColor.TERRACOTTA_GREEN,       "MaterialColor.TERRACOTTA_GREEN")
    .put(MaterialColor.TERRACOTTA_RED,         "MaterialColor.TERRACOTTA_RED")
    .put(MaterialColor.TERRACOTTA_BLACK,       "MaterialColor.TERRACOTTA_BLACK")
    .put(MaterialColor.CRIMSON_NYLIUM,         "MaterialColor.CRIMSON_NYLIUM")
    .put(MaterialColor.CRIMSON_STEM,           "MaterialColor.CRIMSON_STEM")
    .put(MaterialColor.CRIMSON_HYPHAE,         "MaterialColor.CRIMSON_HYPHAE")
    .put(MaterialColor.WARPED_NYLIUM,          "MaterialColor.WARPED_NYLIUM")
    .put(MaterialColor.WARPED_STEM,            "MaterialColor.WARPED_STEM")
    .put(MaterialColor.WARPED_HYPHAE,          "MaterialColor.WARPED_HYPHAE")
    .put(MaterialColor.WARPED_WART_BLOCK,      "MaterialColor.WARPED_WART_BLOCK")
    .build();

  private static final class ColorSet implements Comparable<ColorSet> {
    public MaterialColor mapcolor;
    public int difference;
    public Block[] blocks;
    
    public ColorSet(final MaterialColor mapcolor, final int difference, final Block[] blocks){
      this.mapcolor = mapcolor;
      this.difference = difference;
      this.blocks = blocks;
    }

    @Override
    public final int compareTo(final ColorSet obj){
      return (int)Math.signum(this.difference - obj.difference);
    }

    @Override
    public final String toString(){
      return map_color_names.get(mapcolor);
    }
  }

  public static final void dump_map_colors(){
    ADDSynthCore.log.info("Begin dumping Minecraft Map Colors...");

    final String debug_file = "debug_map_colors.txt";
    final File file = FileUtil.getNewFile(debug_file);

    if(file != null){
      try(final FileWriter writer = new FileWriter(file)){
        writer.write("ADDSynthCore: debug Minecraft Map Colors:\n\n\n");
  
        int i;
        final MinecraftColor[] color_values = MinecraftColor.values();
        final int length = color_values.length;
        final ColorSet[][] set = build_color_list();

        for(i = 0; i < length; i++){
          writer.write(color_values[i].name+" colors: "+printColor(color_values[i].value)+"\n");
          for(ColorSet color : set[i]){
            writer.write("   "+map_color_names.get(color.mapcolor)+" "+printColor(color.mapcolor.col)+"\n");
            for(Block block : color.blocks){
              writer.write("      "+block.getRegistryName()+"\n");
            }
          }
          writer.write("\n\n");
        }
      }
      catch(IOException e){
        e.printStackTrace();
      }
    }
    
    ADDSynthCore.log.info("Done dumping Minecraft Map Colors.");
  }

  private static final ColorSet[][] build_color_list(){
    // Part 1: verify MaterialColor array is in order
    final int total_length = 64;
    int number_of_colors = 0;
    int i;
    for(i = 0; i < total_length; i++){
      if(MaterialColor.byId(i) != MaterialColor.NONE){
        number_of_colors += 1;
      }
    }
    number_of_colors += 1; // so we include MaterialColor 0, which is NONE.
    final MaterialColor[] map_colors = new MaterialColor[number_of_colors];
    for(i = 0; i < number_of_colors; i++){
      map_colors[i] = MaterialColor.byId(i);
    }

    // Part 2: create difference list. get the difference for each MaterialColor aggainst each color we're testing.
    final MinecraftColor[] color_values = MinecraftColor.values();
    final int length = color_values.length;
    final int[][] difference = new int[number_of_colors][length];
    int j;
    int k;
    int map_color;
    int color;
    int red;
    int green;
    int blue;

    for(j = 0; j < number_of_colors; j++){
      for(k = 0; k < length; k++){
        map_color = map_colors[j].col;
        color     = color_values[k].value;
        red   = Math.abs(Color.getRed(map_color) - Color.getRed(color));
        green = Math.abs(Color.getGreen(map_color) - Color.getGreen(color));
        blue  = Math.abs(Color.getBlue(map_color) - Color.getBlue(color));
        difference[j][k] = red + green + blue;
      }
    }

    // Part 3: For each color in color_list, get associated MaterialColors via override or closest match.
    final ColorSet[][] list = new ColorSet[length][];
    int lowest_value;
    int lowest_index;
    TreeSet<ColorSet> values;
    boolean found_override;

    for(i = 0; i < length; i++){
      values = new TreeSet<ColorSet>();
      for(j = 0; j < number_of_colors; j++){
      
        found_override = false;

        // Part 3a: look for override
        for(k = 0; k < length && !found_override; k++){
          for(MaterialColor mp : color_values[k].colors){
            if(map_colors[j] == mp){
              found_override = true;
              if(k == i){
                values.add(new ColorSet(mp, difference[j][i], get_blocks_that_match_color(mp)));
              }
              break;
            }
          }
        }
        // Part 3b: no override found, match color with the lowest difference
        if(found_override == false){
          // get lowest for this row
          lowest_index = 0;
          lowest_value = difference[j][0];
          for(k = 1; k < length; k++){
            if(difference[j][k] < lowest_value){
              lowest_value = difference[j][k];
              lowest_index = k;
            }
          }
          if(lowest_index == i){
            values.add(new ColorSet(map_colors[j], lowest_value, get_blocks_that_match_color(map_colors[j])));
          }
        }
      }

      // Part 3c: Set value list to array for Color i
      list[i] = values.toArray(new ColorSet[values.size()]);
    }

    return list;
  }

  public static final Block[] get_blocks_that_match_color(final MaterialColor test_color){
    final ArrayList<Block> blocks = new ArrayList<>(200);
    try{
      final Field field = ObfuscationReflectionHelper.findField(Block.class, "field_181083_K");
      for(Block block : ForgeRegistries.BLOCKS){
        if(test_color == (MaterialColor)field.get(block)){
          blocks.add(block);
        }
      }
    }
    catch(Exception e){
      System.err.println(e.toString());
    }
    return blocks.toArray(new Block[blocks.size()]);
  }

  public static final String printColor(final int color){ // MAYBE: move this to StringUtil?
    return "( "+Color.getRed(color)+" , "+Color.getGreen(color)+" , "+Color.getBlue(color)+" )";
  }

}
