package addsynth.core.gui;

import addsynth.core.gui.util.GuiUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/** Extend from this class if your gui screen contains item slots. */
public abstract class GuiContainerBase<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

  protected final ResourceLocation GUI_TEXTURE;
  /** Center of gui. For use in drawing graphics, NOT text! */
  protected int guiCenter;
  /** Center of gui. Used for drawing text. */
  protected final int center_x;
  /** Right edge of gui. Used for drawing text against the right edge.
   *  This is equivalent to <code>{@link AbstractContainerScreen#imageWidth imageWidth} - 6</code>. */
  protected final int right_edge;
  
  public GuiContainerBase(final T container, final Inventory player_inventory, final Component title, final ResourceLocation gui_texture){
    super(container, player_inventory, title);
    GUI_TEXTURE = gui_texture;
    center_x = 88;
    right_edge = 170;
  }

  public GuiContainerBase(int width, int height, T container, Inventory player_inventory, Component title, ResourceLocation gui_texture){
    super(container, player_inventory, title);
    GUI_TEXTURE = gui_texture;
    this.imageWidth = width;
    this.imageHeight = height;
    center_x = width / 2;
    right_edge = width - 6;
  }

// ========================================================================================================
  
  /** Draws entire gui texture. (at default texture width and height of 256x256.) */
  protected final void draw_background_texture(final PoseStack matrix){
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, GUI_TEXTURE);
    GuiComponent.blit(matrix, leftPos, topPos, imageWidth, imageHeight, 0, 0, imageWidth, imageHeight, 256, 256);
  }

  /** Draws the background texture with custom scaled width and height. Use this
   *  if you have a background texture that is not the default size of 256x256.
   * @param texture_width
   * @param texture_height
   */
  protected final void draw_custom_background_texture(final PoseStack matrix, final int texture_width, int texture_height){
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, GUI_TEXTURE);
    GuiComponent.blit(matrix, leftPos, topPos, imageWidth, imageHeight, 0, 0, imageWidth, imageHeight, texture_width, texture_height);
  }

  /** Draws another part of the gui texture at the coordinates you specify.
   *  This draws the texture portion the same size that is drawn on the gui.
   *  Assumes a default texture size of 256x256. */
  protected final void draw(final PoseStack matrix, final int x, final int y, final int u, final int v, final int width, final int height){
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, GUI_TEXTURE);
    GuiComponent.blit(matrix, leftPos + x, topPos + y, width, height, u, v, width, height, 256, 256);
  }

  /** Draws another part of the gui texture at the coordinates you specify.
   *  If you need more control over how it's drawn, you might as well use the
   *  vanilla {@link GuiComponent#blit} function directly. */
  protected final void draw(final PoseStack matrix, int x, int y, int u, int v, int width, int height, int texture_width, int texture_height){
    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    RenderSystem.setShaderTexture(0, GUI_TEXTURE);
    GuiComponent.blit(matrix, leftPos + x, topPos + y, width, height, u, v, texture_width, texture_height, 256, 256);
  }

// ========================================================================================================
  
  protected final void draw_title(final PoseStack matrix){
    font.draw(matrix, title, center_x - (font.width(title) / 2), 6, GuiUtil.text_color);
  }

  /** This will render the string in a different color if you prefix the string with
   *  {@link net.minecraft.ChatFormatting ChatFormatting.COLOR.toString()}. */
  protected final void draw_text_left(final PoseStack matrix, final String text, final int x, final int y){
    font.draw(matrix, text, x, y, GuiUtil.text_color);
  }

  /** Draws center-aligned text at the center of the gui.<br />
   *  This will render the string in a different color if you prefix the string with
   *  {@link net.minecraft.ChatFormatting ChatFormatting.COLOR.toString()}. */
  protected final void draw_text_center(final PoseStack matrix, final String text, final int y){
    font.draw(matrix, text, center_x - (font.width(text) / 2), y, GuiUtil.text_color);
  }

  /** Vanilla has their own method but mine assumes a few arguments to make it easier.<br />
   *  This will render the string in a different color if you prefix the string with
   *  {@link net.minecraft.ChatFormatting ChatFormatting.COLOR.toString()}.
   * @see net.minecraft.client.gui.GuiComponent#drawCenteredString
   * @param text
   * @param x
   * @param y
   */
  protected final void draw_text_center(final PoseStack matrix, final String text, final int x, final int y){
    font.draw(matrix, text, x - (font.width(text) / 2), y, GuiUtil.text_color);
  }

  /** Draws along the right-edge of the gui.<br />
   *  This will render the string in a different color if you prefix the string with
   *  {@link net.minecraft.ChatFormatting ChatFormatting.COLOR.toString()}. */
  protected final void draw_text_right(final PoseStack matrix, final String text, final int y){
    font.draw(matrix, text, right_edge - font.width(text), y, GuiUtil.text_color);
  }

  /** Draws the text right-aligned.
   *  This will render the string in a different color if you prefix the string with
   *  {@link net.minecraft.ChatFormatting ChatFormatting.COLOR.toString()}. */
  protected final void draw_text_right(final PoseStack matrix, final String text, final int x, final int y){
    font.draw(matrix, text, x - font.width(text), y, GuiUtil.text_color);
  }

// ========================================================================================================

  @Override
  protected void init(){
    super.init();
    guiCenter = leftPos + center_x;
  }

  /** REPLICA: {@link ContainerEventHandler#mouseDragged}<br/>
   *  On normal {@link net.minecraft.client.gui.screens.Screen Screens}, the
   *  {@link ContainerEventHandler#mouseDragged} method is called by
   *  {@link net.minecraft.client.MouseHandler#onMove}. However, in
   *  {@link AbstractContainerScreen ContainerScreens} the
   *  {@link AbstractContainerScreen#mouseDragged} method is overridden with different
   *  instructions. Therefore, we override it again, and call both functions.
   *  This is replicated because we need to call the
   *  {@link net.minecraft.client.gui.components.events.GuiEventListener#mouseDragged} method,
   *  Because {@link addsynth.core.gui.widgets.scrollbar.AbstractScrollbar#onDrag Scrollbars}
   *  depend on this method to move properly!
   */
  @Override
  public boolean mouseDragged(double gui_x, double gui_y, int widget_id, double screen_x, double screen_y){
    super.mouseDragged(gui_x, gui_y, widget_id, screen_x, screen_y);
    // return ((ContainerEventHandler)this).mouseDragged(gui_x, gui_y, widget_id, screen_x, screen_y); Causes a Stackoverflow because we're still calling THIS method.
    final GuiEventListener focused = this.getFocused();
    if(focused != null && this.isDragging() && widget_id == 0){
      return focused.mouseDragged(gui_x, gui_y, widget_id, screen_x, screen_y);
    }
    return false;
  }

  /** Main render function. */
  @Override
  public void render(PoseStack matrix, final int mouseX, final int mouseY, final float partialTicks){
    this.renderBackground(matrix);
    super.render(matrix, mouseX, mouseY, partialTicks);
    this.renderTooltip(matrix, mouseX, mouseY);
  }

  /** This draws your main gui window.
   *  {@link #renderBackground(PoseStack)} dims the screen behind your gui. */
  @Override
  protected void renderBg(PoseStack matrix, final float partialTicks, final int mouseX, final int mouseY){
    draw_background_texture(matrix);
  }

}
