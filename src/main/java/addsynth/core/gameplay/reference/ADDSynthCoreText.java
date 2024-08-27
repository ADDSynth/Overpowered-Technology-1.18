package addsynth.core.gameplay.reference;

import addsynth.core.util.color.ColorCode;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

/** The TextReference class should only be used for common words
 *  or commonly used text that can be used in multiple projects.
 *  Text that is only used in one place, should be defined there.
 *  
 *  Also, keep in mind that just because a word has multiple
 *  meanings in my language, does not mean it has the same meanings
 *  in other languages. */
public final class ADDSynthCoreText {

  public static final MutableComponent null_error = new TranslatableComponent("gui.addsynthcore.null_error").withStyle(ColorCode.ERROR);

  public static final TranslatableComponent down  = new TranslatableComponent("gui.addsynthcore.direction.down");
  public static final TranslatableComponent up    = new TranslatableComponent("gui.addsynthcore.direction.up");
  public static final TranslatableComponent north = new TranslatableComponent("gui.addsynthcore.direction.north");
  public static final TranslatableComponent south = new TranslatableComponent("gui.addsynthcore.direction.south");
  public static final TranslatableComponent west  = new TranslatableComponent("gui.addsynthcore.direction.west");
  public static final TranslatableComponent east  = new TranslatableComponent("gui.addsynthcore.direction.east");
  public static final TranslatableComponent getDirection(int direction){
    return switch(direction % 6){
    case 0 -> down;
    case 1 -> up;
    case 2 -> north;
    case 3 -> south;
    case 4 -> west;
    case 5 -> east;
    default -> north;
    };
  }

  // Descriptions:
  public static final TranslatableComponent    music_box_description = new TranslatableComponent("gui.addsynthcore.jei_description.music_box");
  public static final TranslatableComponent  music_sheet_description = new TranslatableComponent("gui.addsynthcore.jei_description.music_sheet");
  public static final TranslatableComponent team_manager_description = new TranslatableComponent("gui.addsynthcore.jei_description.team_manager");

}
