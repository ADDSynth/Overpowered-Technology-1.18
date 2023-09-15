package addsynth.core.gui.util;

import java.util.List;
import java.util.Optional;
import addsynth.core.gui.widgets.WidgetUtil;
import addsynth.core.util.math.common.CommonMath;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

/** 
 * @author ADDSynth
 */
public final class GuiUtil {

  public static final int text_color = 4210752;

  @SuppressWarnings("resource")
  @Deprecated
  public static final int getMaxStringWidth(final String ... text){
    final Minecraft minecraft = Minecraft.getInstance();
    return getMaxStringWidth(minecraft.font, text);
  }
  
  public static final int getMaxStringWidth(final Font font, final String ... text){
    final int length = text.length;
    final int[] width = new int[length];
    int i;
    for(i = 0; i < length; i++){
      width[i] = font.width(text[i]);
    }
    return CommonMath.getMax(width);
  }

// ========================================================================================================

  /** <p>Use this to draw 2 ItemStacks but at a linear opacity between the two.
   *  <p>This function draws the first ItemStack first, then the second. With a
   *     <code>blend_factor</code> of <code>0.0f</code>, the first stack is at 100% opacity,
   *     while the second stack is invisible. With the <code>blend_factor</code> at
   *     <code>1.0f</code>, the first stack is invisible and the second stack is fully drawn.
   * @param first_stack
   * @param second_stack
   * @param x
   * @param y
   * @param blend_factor
   */
  public static final void blendItemStacks(final ItemRenderer itemRenderer, ItemStack first_stack, ItemStack second_stack, int x, int y, float blend_factor){
    itemRenderer.renderGuiItem(first_stack, x, y);
    // drawItemStack( first_stack, x, y, 1.0f - blend_factor);
    // drawItemStack(second_stack, x, y,        blend_factor);
  }

  /** This must be called in the {@link AbstractContainerScreen#renderTooltip(PoseStack, int, int)} method.<br>
   *  The X and Y coordinates must have the <code>guiLeft</code> and <code>guiTop</code> values added. */
  // REPLICA of Screen.renderTooltip(PoseStack, ItemStack, mouse_x, mouse_y), but it's protected;
  public static final void drawItemTooltip(PoseStack matrix, Screen screen, ItemStack itemStack, int x, int y, int mouse_x, int mouse_y){
    if(WidgetUtil.isInsideItemStack(x, y, mouse_x, mouse_y)){
      final List<Component> text_components = screen.getTooltipFromItem(itemStack);
      final Optional<TooltipComponent> tooltip_icon = itemStack.getTooltipImage();
      screen.renderTooltip(matrix, text_components, tooltip_icon, x, y, null, itemStack);
    }
  }

}
