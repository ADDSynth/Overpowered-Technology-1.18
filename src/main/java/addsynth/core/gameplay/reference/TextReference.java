package addsynth.core.gameplay.reference;

import addsynth.core.gameplay.Core;
import addsynth.core.util.command.PermissionLevel;
import net.minecraft.network.chat.TranslatableComponent;

public final class TextReference {

  // Team Manager
  public static final TranslatableComponent team_manager  = new TranslatableComponent(Core.team_manager.get().getDescriptionId());
  public static final TranslatableComponent objective_gui = new TranslatableComponent("gui.addsynthcore.team_manager.objective_edit.gui_title");
  public static final TranslatableComponent team_gui      = new TranslatableComponent("gui.addsynthcore.team_manager.team_edit.gui_title");
  public static final TranslatableComponent you_dont_have_permission = new TranslatableComponent("gui.addsynthcore.team_manager.message.you_do_not_have_permission", PermissionLevel.COMMANDS);

  // public static final TranslatableComponent music_box = new TranslatableComponent(Core.music_box.getDescriptionId());

  // a lot of commands also take in string arguments. I decided to leave them all alone.

  // Music Sheet:
  public static final TranslatableComponent music_sheet_clear    = new TranslatableComponent("gui.addsynthcore.music_sheet.clear");
  public static final TranslatableComponent music_sheet_paste    = new TranslatableComponent("gui.addsynthcore.music_sheet.paste");
  public static final TranslatableComponent music_sheet_copy     = new TranslatableComponent("gui.addsynthcore.music_sheet.copy");
  public static final TranslatableComponent music_sheet_no_data  = new TranslatableComponent("gui.addsynthcore.music_sheet.no_data");
  public static final TranslatableComponent music_sheet_has_data = new TranslatableComponent("gui.addsynthcore.music_sheet.has_data");
  
  // Descriptions:
  public static final TranslatableComponent    music_box_description = new TranslatableComponent("gui.addsynthcore.jei_description.music_box");
  public static final TranslatableComponent  music_sheet_description = new TranslatableComponent("gui.addsynthcore.jei_description.music_sheet");
  public static final TranslatableComponent team_manager_description = new TranslatableComponent("gui.addsynthcore.jei_description.team_manager");

  // also missing the TileEntity encountered an error message in TileEntityUtil, but it's because that also uses arguments.

}
