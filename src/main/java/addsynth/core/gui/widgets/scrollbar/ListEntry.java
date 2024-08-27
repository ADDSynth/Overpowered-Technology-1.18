package addsynth.core.gui.widgets.scrollbar;

import addsynth.core.util.color.Color;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public final class ListEntry extends AbstractListEntry<Component> {

  public ListEntry(int x, int y, int width, int height){
    super(x, y, width, height);
  }

  @Override
  @SuppressWarnings("resource")
  public void renderButton(PoseStack matrix, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
    Minecraft minecraft = Minecraft.getInstance();
    Font fontrenderer = minecraft.font;
    drawListEntryHighlight(matrix);
    drawString(matrix, fontrenderer, getMessage(), this.x + 1, this.y + 1, Color.WHITE.get());
  }

  @Override
  public void set(final int entry_id, final Component message){
    this.entry_id = entry_id;
    setMessage(message);
  }

  @Override
  public void setNull(){
    this.entry_id = -1;
    setMessage(TextComponent.EMPTY);
    this.selected = false;
  }

}
