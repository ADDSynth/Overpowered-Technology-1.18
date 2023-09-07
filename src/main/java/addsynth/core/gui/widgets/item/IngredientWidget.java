package addsynth.core.gui.widgets.item;

import javax.annotation.Nonnull;
import addsynth.core.gui.util.GuiUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public final class IngredientWidget {

  private int index;
  private ItemStack[] stacks;
  
  public IngredientWidget(){}
  
  public IngredientWidget(final ItemStack[] ingredients){
    this.stacks = ingredients;
  }

  public IngredientWidget(final Ingredient ingredient){
    this.stacks = ingredient.getItems();
  }

  public final void update(){
    if(stacks != null){
      index += 1;
      if(index >= stacks.length){
        index = 0;
      }
    }
  }

  public final void setIngredient(@Nonnull ItemStack[] ingredient){
    stacks = ingredient;
    index = 0;
  }

  public final void setIngredient(@Nonnull Ingredient ingredient){
    stacks = ingredient.getItems();
    index = 0;
  }

  public final void draw(final ItemRenderer itemRenderer, int x, int y){
    if(stacks != null){
      itemRenderer.renderGuiItem(stacks[index], x, y);
    }
  }

  /** This must be called in the ContainerScreen.renderHoveredToolTip() method.<br>
   *  You must add <code>guiLeft</code> and <code>guiTop</code> to the x and y coordinates. */
  public final void drawTooltip(PoseStack matrix, final Screen screen, final int x, final int y, final int mouse_x, final int mouse_y){
    if(stacks != null){
      GuiUtil.drawItemTooltip(matrix, screen, stacks[index], x, y, mouse_x, mouse_y);
    }
  }

}
