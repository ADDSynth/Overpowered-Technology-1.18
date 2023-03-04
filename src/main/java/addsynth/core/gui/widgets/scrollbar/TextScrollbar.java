package addsynth.core.gui.widgets.scrollbar;

import java.util.function.BiConsumer;
import javax.annotation.Nonnull;

/** <p>A Scrollbar is a widget that goes beside a list of values which the player can
 *     move up or down to scroll a list of values. It automatically adjusts its position
 *     and size based on how many list items there are.
 *  <p>First define an array of {@link ListEntry}s and place them on your gui, and add them as buttons.
 *     Then create the Scrollbar. Place it in the gui where you want, give it your full array of string
 *     values, and your array of {@link ListEntry}s.
 *  <p>When any {@link ListEntry} is clicked, that value will be selected in the list. Only one value
 *     can be selected at any time.
 *  <p>You can assign a {@link BiConsumer} as a responder, which will be called whenever a list item
 *     is selected. The first argument is the string value. The second is the index in the array of values.
 *  <p>You can manually get the selected value by called {@link #getSelected}, or the index by calling
 *     {@link #getSelectedIndex}.
 *  <p>To manually set which entry is selected call {@link #setSelected(int)}. If you don't want the
 *     Scrollbar to call the responder you assigned, call {@link #setSelected(int, boolean, boolean)}.
 *     Absolutely be careful you don't have Scrollbar responders call each other otherwise that will
 *     create an infinite loop!
 *  <p>Call {@link #unSelect} or {@link #setSelected(int)} with any negative value to unselect.
 * @author ADDSynth
 */
public final class TextScrollbar extends AbstractScrollbar<String, ListEntry> {

  public TextScrollbar(int x, int y, int height, ListEntry[] list_items){
    super(x, y, height, list_items, null);
  }

  public TextScrollbar(int x, int y, int height, ListEntry[] list_items, String[] values){
    super(x, y, height, list_items, values);
  }
  
  @Override
  @Nonnull
  protected String[] createEmptyValueArray(){
    return new String[0];
  }
  
}
