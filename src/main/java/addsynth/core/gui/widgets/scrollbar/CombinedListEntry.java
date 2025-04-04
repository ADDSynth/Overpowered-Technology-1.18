package addsynth.core.gui.widgets.scrollbar;

import addsynth.core.util.game.data.CombinedNameComponent;
import net.minecraft.network.chat.TextComponent;

public class CombinedListEntry extends AbstractListEntry<CombinedNameComponent> {

  public CombinedListEntry(int x, int y, int width, int height){
    super(x, y, width, height);
  }

  @Override
  public final void set(final int entry_id, final CombinedNameComponent value){
    this.entry_id = entry_id;
    setMessage(value.getDisplayName());
  }

  @Override
  public final void setNull(){
    this.entry_id = -1;
    setMessage(TextComponent.EMPTY);
    this.selected = false;
  }

}
