package addsynth.core.gui.widgets.scrollbar;

import javax.annotation.Nonnull;
import addsynth.core.util.game.data.CombinedNameComponent;

/** A specific Scrollbar created for use in the Team Manager. Items in the list are of type
 *  {@link CombinedNameComponent} because they need to hold the display name and the ID string.
 *  We can get the ID name for use in Team Manager Commands.
 */
public class CombinedNameScrollbar extends AbstractScrollbar<CombinedNameComponent, CombinedListEntry> {

  public CombinedNameScrollbar(int x, int y, int height, CombinedListEntry[] list_items){
    super(x, y, height, list_items, null);
  }

  public CombinedNameScrollbar(int x, int y, int height, CombinedListEntry[] list_items, CombinedNameComponent[] values){
    super(x, y, height, list_items, values);
  }

  @Override
  @Nonnull
  protected CombinedNameComponent[] createEmptyValueArray(){
    return new CombinedNameComponent[0];
  }

}
