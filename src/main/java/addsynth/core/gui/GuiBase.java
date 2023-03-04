package addsynth.core.gui;

import addsynth.core.gui.util.GuiUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/** Extend from this class if you just want your own gui screen. */
public abstract class GuiBase extends Screen {

  protected final GuiUtil guiUtil;

  protected GuiBase(final int width, final int height, final Component title, final ResourceLocation gui_texture){
    super(title);
    guiUtil = new GuiUtil(gui_texture, width, height);
  }

  @Override
  public void render(PoseStack matrix, int mouse_x, int mouse_y, float partialTicks){
    // REPLICA: This is a replica of AbstractContainerScreen.render() but without drawing any ItemStacks. Make sure it matches whenever we update Forge.
    super.renderBackground(matrix);
    drawGuiBackgroundLayer(matrix, partialTicks, mouse_x, mouse_y);
    // net.minecraftforge.common.MinecraftForge.EVENT_BUS.pose(new net.minecraftforge.client.event.GuiContainerEvent.DrawBackground(this, mouse_x, mouse_y));
    RenderSystem.disableDepthTest();
    super.render(matrix, mouse_x, mouse_y, partialTicks);
    PoseStack posestack = RenderSystem.getModelViewStack();
    posestack.pushPose();
    posestack.translate(guiUtil.guiLeft, guiUtil.guiTop, 0.0);
    RenderSystem.applyModelViewMatrix();
    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    
    drawGuiForegroundLayer(matrix, mouse_x, mouse_y);
    posestack.popPose();
    RenderSystem.applyModelViewMatrix();
    RenderSystem.enableDepthTest();
  }

  @Override
  public final boolean isPauseScreen(){
    return false;
  }

  protected void drawGuiBackgroundLayer(PoseStack matrix, float partialTicks, int mouse_x, int mouse_y){
    guiUtil.draw_background_texture(matrix);
  }
  
  protected void drawGuiForegroundLayer(PoseStack matrix, int mouse_x, int mouse_y){
  }

}
