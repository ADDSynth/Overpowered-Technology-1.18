package addsynth.core.gui;

import addsynth.core.gui.util.GuiUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/** Extend from this class if your gui screen contains item slots. */
public abstract class GuiContainerBase<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

  protected final GuiUtil guiUtil;
  
  public GuiContainerBase(final T container, final Inventory player_inventory, final Component title, final ResourceLocation gui_texture){
    super(container, player_inventory, title);
    guiUtil = new GuiUtil(gui_texture, 176, 166);
  }

  public GuiContainerBase(int width, int height, T container, Inventory player_inventory, Component title, ResourceLocation gui_texture){
    super(container, player_inventory, title);
    guiUtil = new GuiUtil(gui_texture, width, height);
    this.imageWidth = width;
    this.imageHeight = height;
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
    return this.getFocused() != null && this.isDragging() && widget_id == 0 ? this.getFocused().mouseDragged(gui_x, gui_y, widget_id, screen_x, screen_y) : false;
  }

  @Override
  public void render(PoseStack matrix, final int mouseX, final int mouseY, final float partialTicks){
    this.renderBackground(matrix);
    super.render(matrix, mouseX, mouseY, partialTicks);
    this.renderTooltip(matrix, mouseX, mouseY);
  }

  @Override
  protected void renderBg(PoseStack matrix, final float partialTicks, final int mouseX, final int mouseY){
    guiUtil.draw_background_texture(matrix);
  }

}
