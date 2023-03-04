package addsynth.core.gui.widgets;

import addsynth.core.util.math.RoundMode;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.util.Mth;

public class ProgressBar {

  private final int x;
  private final int y;
  private final int width;
  private final int height;
  private final int texture_x;
  private final int texture_y;

  public enum Direction {LEFT_TO_RIGHT,RIGHT_TO_LEFT,BOTTOM_TO_TOP,TOP_TO_BOTTOM}

  public ProgressBar(int x, int y, int width, int height, int texture_x, int texture_y){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.texture_x = texture_x;
    this.texture_y = texture_y;
  }

  public void draw(PoseStack matrix, AbstractContainerScreen gui, Direction direction, float value, RoundMode round_mode){
    float texture_percentage = 0.0f;
    switch(direction){
    case LEFT_TO_RIGHT: case RIGHT_TO_LEFT: texture_percentage = width  * Mth.clamp(value, 0.0f, 1.0f); break;
    case BOTTOM_TO_TOP: case TOP_TO_BOTTOM: texture_percentage = height * Mth.clamp(value, 0.0f, 1.0f); break;
    }
    int progress = 0;
    switch(round_mode){
    case Floor: progress = (int)Math.floor(texture_percentage); break;
    case Round: progress = Math.round(texture_percentage); break;
    case Ceiling: progress = (int)Math.ceil(texture_percentage); break;
    }
    switch(direction){
    case LEFT_TO_RIGHT: gui.blit(matrix, gui.getGuiLeft() + x, gui.getGuiTop() + y, texture_x, texture_y, progress, height); break;
    case RIGHT_TO_LEFT: gui.blit(matrix, gui.getGuiLeft() + x + width - progress, gui.getGuiTop() + y, texture_x + width - progress, texture_y, progress, height); break;
    case TOP_TO_BOTTOM: gui.blit(matrix, gui.getGuiLeft() + x, gui.getGuiTop() + y, texture_x, texture_y, width, progress); break;
    case BOTTOM_TO_TOP: gui.blit(matrix, gui.getGuiLeft() + x, gui.getGuiTop() + y + height - progress, texture_x, texture_y + height - progress, width, progress); break;
    }
  }

}
