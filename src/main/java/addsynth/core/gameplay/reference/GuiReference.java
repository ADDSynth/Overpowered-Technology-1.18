package addsynth.core.gameplay.reference;

import addsynth.core.ADDSynthCore;
import net.minecraft.resources.ResourceLocation;

public final class GuiReference {

  public static final ResourceLocation widgets       = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/gui_textures.png");
  public static final ResourceLocation highlight     = widgets;
  public static final ResourceLocation scrollbar     = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/scrollbar.png");

  public static final ResourceLocation music_box_gui = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/music_box.png");
  public static final ResourceLocation instruments   = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/instruments.png");

  // Team Manager
  public static final ResourceLocation team_manager       = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/team_manager.png");
  public static final ResourceLocation edit_team_gui      = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/team_manager_team_edit.png");
  public static final ResourceLocation edit_objective_gui = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/team_manager_objective_edit.png");
  public static final ResourceLocation color_buttons      = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/color_buttons.png");

}
