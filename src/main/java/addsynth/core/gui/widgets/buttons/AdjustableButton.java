package addsynth.core.gui.widgets.buttons;

import addsynth.core.ADDSynthCore;
import addsynth.core.gui.widgets.WidgetUtil;
import addsynth.core.util.java.StringUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

/** Hey! This has a maximum height of 20! So setting it any higher will have no effect. */
public abstract class AdjustableButton extends AbstractButton {

  private static final int max_width = 200;
  private static final int max_height = 20;

  /**
   * This constructor passes an empty string to the button. Only use this if you intend to
   * change the button text on a per-frame basis, such as responding to changes to a TileEntity.
   * @param x
   * @param y
   * @param width
   * @param height
   */
  public AdjustableButton(int x, int y, int width, int height){
    super(x, y, width, Math.min(height, max_height), TextComponent.EMPTY);
    if(height > max_height){
      ADDSynthCore.log.warn(StringUtil.build("Cannot set height of ", AdjustableButton.class.getSimpleName(), " higher than ", max_height, "."));
    }
  }

  /**
   * Only use this constructor if the button text remains static.
   * @param x
   * @param y
   * @param width
   * @param height
   * @param buttonText
   */
  public AdjustableButton(int x, int y, int width, int height, final Component buttonText){
    super(x, y, width, Math.min(height, max_height), buttonText);
    if(height > max_height){
      ADDSynthCore.log.warn(StringUtil.build("Cannot set height of ", AdjustableButton.class.getSimpleName(), " higher than ", max_height, "."));
    }
  }

    /**
     * Draws this button to the screen. This is my own overridden method. It auto adjusts for a variable width AND height.
     * Otherwise it is exactly the same as {@link AbstractWidget#renderButton}.
     */
    // REPLICA: Custom replica of AbstractWidget.renderButton()
    @Override
    @SuppressWarnings("resource")
    public void renderButton(PoseStack matrix, final int mouseX, final int mouseY, final float partialTicks){
      final Minecraft minecraft = Minecraft.getInstance();
      RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
      
      final int hover_state = this.getYImage(this.isHovered);
      final int button_texture_y = 46 + (hover_state * 20);
      
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      
      WidgetUtil.crossSplitRender(matrix, x, y, 0, button_texture_y, width, height, max_width, max_height);
      this.renderBg(matrix, minecraft, mouseX, mouseY);
      
      final int text_color = getFGColor(); // 14737632; TODO: Is this the text color I'm looking for? Move this to constants. But now there's one in GuiUtil as well.
      drawCenteredString(matrix, minecraft.font, this.getMessage(), x + (width/2), y + (height/2) - 4, text_color);
    }

  @Override
  public void updateNarration(NarrationElementOutput p_169152_){
  }

}
