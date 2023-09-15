package addsynth.core.gui.widgets.scrollbar;

import java.util.function.BiConsumer;
import javax.annotation.Nonnull;
import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.reference.GuiReference;
import addsynth.core.gui.widgets.Dimensions;
import addsynth.core.gui.widgets.WidgetUtil;
import addsynth.core.util.java.ArrayUtil;
import addsynth.core.util.math.common.MathUtility;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;

/** <p>A Scrollbar is a widget that goes beside a list of values which the player can
 *     move up or down to scroll a list of values. It automatically adjusts its position
 *     and size based on how many list items there are.
 *  <p>First define an array of {@link AbstractListEntry}s and place them on your gui, and add them as buttons.
 *     Then create the Scrollbar. Place it in the gui where you want, give it your full array of values,
 *     and your array of {@link AbstractListEntry}s.
 *  <p>When any {@link AbstractListEntry} is clicked, that value will be selected in the list. Only one value
 *     can be selected at any time.
 *  <p>You can assign a {@link BiConsumer} as a responder, which will be called whenever a list item
 *     is selected. The first argument is the value. The second is the index in the array of values.
 *  <p>You can manually get the selected value by called {@link #getSelected}, or the index by calling
 *     {@link #getSelectedIndex}.
 *  <p>To manually set which entry is selected call {@link #setSelected(int)}. If you don't want the
 *     Scrollbar to call the responder you assigned, call {@link #setSelected(int, boolean, boolean)}.
 *     Absolutely be careful you don't have Scrollbar responders call each other otherwise that will
 *     create an infinite loop!
 *  <p>Call {@link #unSelect} or {@link #setSelected(int)} with any negative value to unselect.
 * @author ADDSynth
 */
public abstract class AbstractScrollbar<E, L extends AbstractListEntry<E>> extends AbstractWidget {

  // texture coordinates
  /** Main scrollbar X texture coordinate. */
  private static final int scrollbar_texture_x = 0;
  /** Main & Background scrollbar Y texture coordinate. */
  private static final int scrollbar_texture_y = 24;
  /** Scrollbar background X texture coordinate. */
  private static final int scrollbar_background_x = 24;
  /** Scrollbar center X texture coordinate. */
  private static final int scrollbar_center_x = 48;
  /** Main scrollbar maximum Y texture coordinate. */
  private static final int scrollbar_texture_max_y = 488;

  // texture dimensions
  /** Scrollbar texture width. */
  private static final int scrollbar_texture_width = 24;
  /** Height of main scrollbar in texture. */
  private static final int max_scrollbar_height = 464; // 512 - 48
  /** Height of scrollbar center in texture. */
  private static final int scrollbar_center_height = 4;

  // gui dimensions
  /** Scrollbar width on the gui. */
  private static final int scrollbar_gui_width = 12;
  private static final int center_gui_height = 2;
  /** Scrollbar texture width scale value. */
  private static final int texture_scale_width = 72;
  /** Scrollbar texture height scale value. */
  private static final int texture_scale_height = 512;

  // other constants
  /** For every scrollbar height that is a multiple of this, we add a center section. */
  private static final int center_ratio = 16;

  // scrollbar size values
  /** Main scrollbar height that shows on the gui. Resizes based on number of list values. */
  private int scrollbar_height;
  /** Calculation value. Half height of Scrollbar. Used to calculate the center_y value. */
  private int scrollbar_half_height;
  /** Main scrollbar center Y position. Used to in conjunction with the Mouse Y to move the scrollbar. */
  private int center_y;
  /** Calculation value. Starting Y Gui coordinate of the center section. */
  private int scrollbar_center_y;
  /** Calculation value. Starting bottom half Y Gui coordinate. */
  private int scrollbar_bottom_y;
  
  // calculations
  private int number_of_center_sections;
  private int[] scrollbar_gui_height_calc;
  
  /** Current position of main scrollbar in gui coordinate space. All drawing starts at this position. */
  private int position_y;
  /** Max Position scrollbar will be at if at last index position. */
  private int max_position_y;
  /** Temp variable used to determine if the index position changed. */
  private int temp_index;
  /** Current scrollbar index. */
  private int index_position;
  /** Maximum scrollbar positions. */
  private int max_positions;
  private int[] index_positions;

  /** The List Entries connected to this Scrollbar. */
  private L[] list_items;
  /** Number of visible List Entries. */
  private int visible_elements;
  /** Full list of values. NEVER CHANGE DIRECTLY. Call {@link #updateScrollbar} to set. */
  protected E[] values;
  /** Number of values in the full list. Use this instead of <code>values.length</code>. */
  protected int list_length;
  /** The selected index. */
  private int selected = -1;

  /** This is passed the new selected Index every time it is changed,
   *  even if it is changed to an invalid value. */
  private BiConsumer<E, Integer> onSelected;

  public AbstractScrollbar(int x, int y, int height, L[] list_items, E[] values){
    super(x, y, scrollbar_gui_width, height, new TextComponent(""));
    if(height > max_scrollbar_height - 8){
      ADDSynthCore.log.error("Requested Scrollbar height is bigger than Max Scrollbar height!");
    }
    for(L entry : list_items){
      entry.setScrollbar(this);
    }
    this.list_items = list_items;
    visible_elements = list_items.length;
    updateScrollbar(values);
  }
  
  /** Call this to assign the full list of values. The scrollbar will automatically update its displayed list and size. */
  public final void updateScrollbar(E[] values){
    this.values = values != null ? values : createEmptyValueArray();
    list_length = this.values.length;
    
    recalculateScrollbar();
    updateList();
  }
  
  @Nonnull
  protected abstract E[] createEmptyValueArray();
  
  private final void recalculateScrollbar(){
    try{
      // recalculate everything for now. it doesn't hurt.
  
      // scrollbar
      if(list_length > visible_elements){
        final double ratio = (double)visible_elements / list_length;
        scrollbar_height = Math.max((int)Math.round(height * ratio), 6);
        max_positions = list_length - visible_elements + 1;
        index_position = Mth.clamp(index_position, 0, max_positions - 1);
      }
      else{
        scrollbar_height = this.height;
        max_positions = 1;
        index_position = 0;
      }
      // update position_y based on new index value
      max_position_y = y + height - scrollbar_height;
      index_positions = MathUtility.getPositions(y, max_position_y, max_positions);
      position_y = index_positions[index_position];

      // setup
      scrollbar_half_height = scrollbar_height / 2;
      number_of_center_sections = (int)Math.round((double)scrollbar_height / center_ratio);
      if(number_of_center_sections == 1){
        number_of_center_sections = 0;
      }
      scrollbar_gui_height_calc = WidgetUtil.get_half_lengths(scrollbar_height - (number_of_center_sections * center_gui_height));
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public void render(PoseStack matrix, int p_render_1_, int p_render_2_, float p_render_3_){
    WidgetUtil.common_button_render_setup(GuiReference.scrollbar);

    // Background
    WidgetUtil.verticalSplitRender(matrix,
      new Dimensions(x, y, scrollbar_gui_width, height, scrollbar_gui_width, max_scrollbar_height),
      new Dimensions(scrollbar_background_x, scrollbar_texture_y, scrollbar_texture_width, height*2, scrollbar_texture_width, max_scrollbar_height),
      texture_scale_width, texture_scale_height
    );

    // scrollbar values
    scrollbar_center_y = position_y + scrollbar_gui_height_calc[0];
    scrollbar_bottom_y = position_y + scrollbar_height - scrollbar_gui_height_calc[1];
    
    // Scrollbar
    blit(matrix, x, position_y, scrollbar_gui_width, scrollbar_gui_height_calc[0], scrollbar_texture_x, scrollbar_texture_y, scrollbar_texture_width, scrollbar_gui_height_calc[0]*2, texture_scale_width, texture_scale_height);
    blit(matrix, x, scrollbar_center_y, scrollbar_gui_width, center_gui_height * number_of_center_sections, scrollbar_center_x, 0, scrollbar_texture_width, scrollbar_center_height * number_of_center_sections, texture_scale_width, texture_scale_height);
    blit(matrix, x, scrollbar_bottom_y, scrollbar_gui_width, scrollbar_gui_height_calc[1], scrollbar_texture_x, scrollbar_texture_max_y - scrollbar_gui_height_calc[1]*2, scrollbar_texture_width, scrollbar_gui_height_calc[1]*2, texture_scale_width, texture_scale_height);
  }

  @Override
  protected final void onDrag(double gui_x, double gui_y, double screen_x, double screen_y){
    center_y = (int)Math.round(gui_y); // center of scrollbar follows mouse
    position_y = Mth.clamp(center_y - scrollbar_half_height, this.y, max_position_y);
    
    temp_index = MathUtility.getPositionIndex(position_y, index_positions);
    if(temp_index != index_position){
      index_position = temp_index;
      updateList();
    }
  }

  /** Adjusts the scrollbar's position based on the provided index position,
   *  and also updates the displayed list. */
  private final void setScrollbarPosition(final int scrollbar_position_index){
    if(ArrayUtil.isInsideBounds(scrollbar_position_index, index_positions.length)){
      index_position = scrollbar_position_index;
      position_y = index_positions[scrollbar_position_index];
      updateList();
    }
  }

  /** Used to update the displayed list entries. Must be called every time the scrollbar's position changes. */
  private void updateList(){
    int i;
    int id;
    for(i = 0; i < visible_elements; i++){
      id = index_position + i;
      if(id < list_length){
        list_items[i].set(id, values[id]);
        list_items[i].setSelected(selected);
      }
      else{
        list_items[i].setNull();
      }
    }
  }

  @Override
  public final void onRelease(double p_onRelease_1_, double p_onRelease_3_){
    position_y = index_positions[index_position];
  }

  public final void setResponder(final BiConsumer<E, Integer> responder){
    this.onSelected = responder;
  }

  /** Shortcut to unselect the Index. Does not move scrollbar. */
  public void unSelect(){
    setSelected(-1, true, false);
  }

  /** Shortcut to set the Index. Always responds and adjusts scrollbar. */
  public void setSelected(int list_entry_index){
    setSelected(list_entry_index, true, true);
  }
  
  /** Primary function to set the selected Index. Can also Unselect the index.
   *  Calls the {@link #onSelected} BiConsumer, if available, to respond to changes.
   * @param list_entry_index Set to any index outside of range to unselect
   * @param respond Whether to call the onSelected BiConsumer to respond to changes
   * @param adjust_scrollbar
   */
  public void setSelected(int list_entry_index, boolean respond, boolean adjust_scrollbar){
    // MAYBE: responding to selection changes now happens all the time by default, consider removing
    //        the parameter. But I may want to control this in the future, so it's left as-is for now.
    //        Remove this in 2027 if it's no longer necessary.
    selected = list_entry_index;
    for(final L e : list_items){
      e.setSelected(selected);
    }
    
    if(respond){
      if(onSelected != null){
        onSelected.accept(getSelected(), selected);
      }
    }
    
    // TODO: Always scroll by default, only scroll if we select a valid index, add a reset function that unselects and scrolls to position 0.
    if(adjust_scrollbar){
      scroll_to_value();
    }
  }

  /** Set this scrollbar's selected index to the value that you specify.
   *  Unselects the index if we didn't find your value. */
  public final void setSelected(final E value){
    setSelected(find(value));
  }

  /** Set this scrollbar's selected index to the value that you specify.
   *  Unselects the index if we didn't find your value.
   * @param respond Whether to call the onSelected BiConsumer to respond to changes
   */
  public final void setSelected(final E value, final boolean respond){
    setSelected(find(value), respond, true);
  }

  /** Searches through the list of values and returns the index if we find the value
   *  you're looking for. Returns -1 if we did not find it in the list. */
  public int find(final E value){
    if(values != null){
      int i;
      for(i = 0; i < list_length; i++){
        if(values[i].equals(value)){
          return i;
        }
      }
    }
    return -1;
  }

  @Override
  public final boolean mouseScrolled(double mouse_x, double mouse_y, double scroll_direction){
    mouseScrollWheel((int)scroll_direction);
    return true;
  }

  /** Call this to respond to scrollwheel actions. Positive values scroll up, negative values scroll down. */
  public final void mouseScrollWheel(int direction){
    final int temp_index = Mth.clamp(index_position - direction, 0, max_positions - 1);
    if(index_position != temp_index){
      setScrollbarPosition(temp_index);
    }
  }

  /** Moves scrollbar so that the selected value is shown in the middle.
   *  If there is no selection then scrollbar moves to first position. */
  private final void scroll_to_value(){ // seperate as a function in case we need to call it externally?
    if(hasValidSelection()){
      final int index = Mth.clamp(selected - (visible_elements / 2), 0, index_positions.length - 1);
      setScrollbarPosition(index);
    }
    else{
      setScrollbarPosition(0);
    }
  }

  public final E getSelected(){
    return hasValidSelection() ? values[selected] : null;
  }

  public final int getSelectedIndex(){
    return selected;
  }

  public final boolean hasValidSelection(){
    return ArrayUtil.isInsideBounds(selected, list_length);
  }

  @Override
  public final void playDownSound(SoundManager p_playDownSound_1_){
  }

  @Override
  public final void updateNarration(NarrationElementOutput p_169152_){
  }

}
