package addsynth.overpoweredmod.game.core;

import addsynth.core.game.item.ItemUtil;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.items.basic.LensItem;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.MaterialColor;

public enum Lens {

  WHITE  (0, Names.FOCUS_LENS,   ChatFormatting.WHITE,        MaterialColor.SNOW),
  RED    (1, Names.RED_LENS,     ChatFormatting.DARK_RED,     MaterialColor.COLOR_RED),
  ORANGE (2, Names.ORANGE_LENS,  ChatFormatting.GOLD,         MaterialColor.COLOR_ORANGE),
  YELLOW (3, Names.YELLOW_LENS,  ChatFormatting.YELLOW,       MaterialColor.GOLD),
  GREEN  (4, Names.GREEN_LENS,   ChatFormatting.DARK_GREEN,   MaterialColor.EMERALD),
  CYAN   (5, Names.CYAN_LENS,    ChatFormatting.AQUA,         MaterialColor.DIAMOND),
  BLUE   (6, Names.BLUE_LENS,    ChatFormatting.BLUE,         MaterialColor.LAPIS),
  MAGENTA(7, Names.MAGENTA_LENS, ChatFormatting.LIGHT_PURPLE, MaterialColor.COLOR_MAGENTA);

  public final LensItem lens;
  public final MaterialColor color;

  private Lens(final int index, final ResourceLocation name, final ChatFormatting format_code, final MaterialColor material){
    lens = new LensItem(index, name, format_code);
    this.color = material;
  }

  public static final Item focus_lens = WHITE.lens;
  public static final Item red        = RED.lens;
  public static final Item orange     = ORANGE.lens;
  public static final Item yellow     = YELLOW.lens;
  public static final Item green      = GREEN.lens;
  public static final Item cyan       = CYAN.lens;
  public static final Item blue       = BLUE.lens;
  public static final Item magenta    = MAGENTA.lens;

  public static final Item[] index = { focus_lens, red, orange, yellow, green, cyan, blue, magenta};
  
  public static final int get_index(final ItemStack stack){
    if(ItemUtil.itemStackExists(stack)){
      return get_index(stack.getItem());
    }
    return -1;
  }
  
  public static final int get_index(final Item lens){
    if(lens != null){
      if(lens instanceof LensItem){
        return ((LensItem)lens).index;
      }
    }
    return -1;
  }
  
}
