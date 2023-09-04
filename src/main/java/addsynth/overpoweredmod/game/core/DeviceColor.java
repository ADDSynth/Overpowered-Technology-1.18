package addsynth.overpoweredmod.game.core;

import addsynth.overpoweredmod.OverpoweredTechnology;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.MaterialColor;

/** Many items and devices in the Overpowered Technology mod have a variety
 *  of colors associated with them. This enum is used to specify the different colors.
 *  Since these colors are based on Light, it is unlikely that any new colors will be added.
 *  @see addsynth.overpoweredmod.items.basic.LensItem
 *  @see Laser
 *  @see addsynth.overpoweredmod.machines.suspension_bridge.EnergyBridge
 */
public enum DeviceColor {

  WHITE  (0, "white",   ChatFormatting.WHITE,        MaterialColor.SNOW),
  RED    (1, "red",     ChatFormatting.DARK_RED,     MaterialColor.COLOR_RED),
  ORANGE (2, "orange",  ChatFormatting.GOLD,         MaterialColor.COLOR_ORANGE),
  YELLOW (3, "yellow",  ChatFormatting.YELLOW,       MaterialColor.GOLD),
  GREEN  (4, "green",   ChatFormatting.DARK_GREEN,   MaterialColor.EMERALD),
  CYAN   (5, "cyan",    ChatFormatting.AQUA,         MaterialColor.DIAMOND),
  BLUE   (6, "blue",    ChatFormatting.BLUE,         MaterialColor.LAPIS),
  MAGENTA(7, "magenta", ChatFormatting.LIGHT_PURPLE, MaterialColor.COLOR_MAGENTA);

  public final int index;
  // public final String name;
  public final ResourceLocation energy_bridge;
  public final ResourceLocation lens_name;
  public final ResourceLocation laser_cannon;
  public final ResourceLocation laser_beam;
  public final ChatFormatting format_code;
  public final MaterialColor color;

  private DeviceColor(final int index, final String name, final ChatFormatting format_code, final MaterialColor material){
    this.index       = index;
    // this.name        = name;
    energy_bridge = new ResourceLocation(OverpoweredTechnology.MOD_ID, name+"_energy_bridge");
        lens_name = new ResourceLocation(OverpoweredTechnology.MOD_ID, index == 0 ? "focus_lens" : name+"_lens");
     laser_cannon = new ResourceLocation(OverpoweredTechnology.MOD_ID, name+"_laser");
       laser_beam = new ResourceLocation(OverpoweredTechnology.MOD_ID, name+"_laser_beam");
    this.format_code = format_code;
    this.color       = material;
  }

}
