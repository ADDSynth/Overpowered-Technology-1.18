package addsynth.energy.lib.gui.widgets;

import addsynth.core.gui.widgets.ProgressBar;
import addsynth.core.util.math.common.RoundMode;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.main.Energy;
import com.mojang.blaze3d.vertex.PoseStack;

public final class EnergyProgressBar extends ProgressBar {

  private float energy_percentage;

  public EnergyProgressBar(int x, int y, int width, int height, int texture_x, int texture_y){
    super(x, y, width, height, texture_x, texture_y);
  }

  /** Draws the Energy bar LEFT to RIGHT. */
  public final void drawHorizontal(PoseStack matrix, GuiEnergyBase gui, Energy energy){
    energy_percentage = energy.getEnergyPercentage();
    super.draw(matrix, gui, Direction.LEFT_TO_RIGHT, energy_percentage, RoundMode.Round);
  }

  /** Draws the Energy bar BOTTOM to TOP. */
  public final void drawVertical(PoseStack matrix, GuiEnergyBase gui, Energy energy){
    energy_percentage = energy.getEnergyPercentage();
    super.draw(matrix, gui, Direction.BOTTOM_TO_TOP, energy_percentage, RoundMode.Round);
  }

  public final void draw(PoseStack matrix, GuiEnergyBase gui, Direction direction, Energy energy){
    energy_percentage = energy.getEnergyPercentage();
    super.draw(matrix, gui, direction, energy_percentage, RoundMode.Round);
  }

  public final String getEnergyPercentage(){
    return Math.round(energy_percentage*100) + "%";
  }

}
