package addsynth.core.util.color;

import net.minecraft.world.level.material.MaterialColor;

public enum MinecraftColor {

  // Some of these colors values were obtained from looking them up on Wikipedia.
  WHITE(    "White",     0xFFFFFF, MaterialColor.SNOW, MaterialColor.QUARTZ),
  SILVER(   "Silver",    0xC0C0C0, MaterialColor.WOOL, MaterialColor.METAL, MaterialColor.CLAY),
  GRAY(     "Gray",      0x808080, MaterialColor.STONE, MaterialColor.COLOR_LIGHT_GRAY),
  DARK_GRAY("Dark Gray", 0x404040, MaterialColor.COLOR_GRAY),
  BLACK(    "Black",     0x000000, MaterialColor.NONE, MaterialColor.COLOR_BLACK),
  RED(      "Red",       0xFF0000, MaterialColor.FIRE, MaterialColor.COLOR_RED, MaterialColor.NETHER),
  ORANGE(   "Orange",    0xFF8000, MaterialColor.COLOR_ORANGE),
  YELLOW(   "Yellow",    0xFFFF00, MaterialColor.COLOR_YELLOW, MaterialColor.GOLD),
  GREEN(    "Green",     0X00FF00, MaterialColor.GRASS, MaterialColor.COLOR_LIGHT_GREEN, MaterialColor.PLANT, MaterialColor.COLOR_GREEN, MaterialColor.EMERALD),
  CYAN(     "Cyan",      0x00FFFF, MaterialColor.COLOR_CYAN, MaterialColor.DIAMOND),
  BLUE(     "Blue",      0x0000FF, MaterialColor.COLOR_BLUE, MaterialColor.COLOR_LIGHT_BLUE, MaterialColor.WATER, MaterialColor.ICE, MaterialColor.LAPIS),
  MAGENTA(  "Magenta",   0xFF00FF, MaterialColor.COLOR_MAGENTA),
  PURPLE(   "Purple",    0x800080, MaterialColor.COLOR_PURPLE),
  PINK(     "Pink",      0xFFC0CB, MaterialColor.COLOR_PINK),
  PEACH(    "Peach",     0xFFE5B4, MaterialColor.SAND),
  BROWN(    "Brown",     0x964B00, MaterialColor.COLOR_BROWN, MaterialColor.DIRT, MaterialColor.WOOD, MaterialColor.PODZOL);

  public final String name;
  public final int value;
  public final MaterialColor[] colors;
  
  private MinecraftColor(final String name, final int value, final MaterialColor ... overrides){
    this.name = name;
    this.value = value;
    this.colors = overrides;
  }

  @Override
  public final String toString(){
    return name+": "+value;
  }

}
