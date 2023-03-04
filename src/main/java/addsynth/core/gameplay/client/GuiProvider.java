package addsynth.core.gameplay.client;

import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.gameplay.music_box.gui.GuiMusicBox;
import addsynth.core.gameplay.team_manager.TeamManagerBlock;
import addsynth.core.gameplay.team_manager.gui.TeamManagerGui;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;

public final class GuiProvider {

  @SuppressWarnings("resource")
  public static final void openMusicBoxGui(final TileMusicBox tile, final String title){
    Minecraft.getInstance().setScreen(new GuiMusicBox(tile, new TranslatableComponent(title)));
  }

  @SuppressWarnings("resource")
  public static final void openTeamManagerGui(final TeamManagerBlock block){
    Minecraft.getInstance().setScreen(new TeamManagerGui());
  }

}
