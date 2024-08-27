package addsynth.core.gameplay.client;

import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.gameplay.music_box.gui.GuiMusicBox;
import addsynth.core.gameplay.team_manager.gui.TeamManagerGui;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;

public final class GuiProvider {

  @SuppressWarnings("resource")
  public static final void openMusicBoxGui(final TileMusicBox tile, final MutableComponent title){
    Minecraft.getInstance().setScreen(new GuiMusicBox(tile, title));
  }

  @SuppressWarnings("resource")
  public static final void openTeamManagerGui(){
    Minecraft.getInstance().setScreen(new TeamManagerGui());
  }

}
