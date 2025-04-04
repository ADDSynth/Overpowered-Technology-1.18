package addsynth.core.gui.widgets.scrollbar;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public final class ListEntry extends AbstractListEntry<Component> {

  public ListEntry(int x, int y, int width, int height){
    super(x, y, width, height);
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
