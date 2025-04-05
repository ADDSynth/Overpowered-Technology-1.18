package addsynth.core.gameplay.team_manager.gui;

import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.reference.GuiReference;
import addsynth.core.gameplay.team_manager.data.CriteriaData;
import addsynth.core.gameplay.team_manager.data.CriteriaType;
import addsynth.core.gameplay.team_manager.data.ObjectiveDataUnit;
import addsynth.core.gameplay.team_manager.data.TeamData;
import addsynth.core.gameplay.team_manager.network_messages.TeamManagerCommand;
import addsynth.core.gui.GuiBase;
import addsynth.core.gui.section.MutableGuiSection;
import addsynth.core.gui.widgets.WidgetUtil;
import addsynth.core.gui.widgets.buttons.RadialButtonGroup;
import addsynth.core.gui.widgets.scrollbar.ListEntry;
import addsynth.core.gui.widgets.scrollbar.TextScrollbar;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public final class TeamManagerObjectiveGui extends GuiBase {

  private static final int gui_height = 314;

  private final boolean new_objective;
  private final String existing_objective;

  private static final TranslatableComponent               objective_gui = new TranslatableComponent("gui.addsynthcore.team_manager.objective_edit.gui_title");
  private static final TranslatableComponent      objective_id_name_text = new TranslatableComponent("gui.addsynthcore.team_manager.objective_edit.id_name");
  private static final TranslatableComponent objective_display_name_text = new TranslatableComponent("gui.addsynthcore.team_manager.objective_edit.display_name");
  private static final TranslatableComponent          criteria_type_text = new TranslatableComponent("gui.addsynthcore.team_manager.objective_edit.type");
  private static final TranslatableComponent        criteria_header_text = new TranslatableComponent("gui.addsynthcore.team_manager.objective_edit.criteria_header");
  private static final TranslatableComponent[] criteria_options = {
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria_type.standard"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria_type.team_kill"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria_type.killed_by_team"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria_type.statistic"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria_type.block_mined"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria_type.item_broken"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria_type.item_crafted"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria_type.item_used"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria_type.item_picked_up"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria_type.item_dropped"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria_type.killed"),
    new TranslatableComponent("gui.addsynthcore.team_manager.criteria_type.killed_by")
  };
  private Component message = TextComponent.EMPTY;

  private EditBox objective_id_name;
  private EditBox objective_display_name;
  private RadialButtonGroup criteria_types;
  private TextScrollbar criteria_list;
  private TeamManagerGuiButtons.FinishButton finish_button;

  private static final int widget_spacing = 2;
  private static final int line_space = 8;
  private static final int text_box_height = 14;
  private static final int entry_height = 11;
  private static final int scrollbar_width = 12;
  private static final int button_width = 80;
  private static final int button_height = 20;

  // Text
  private static final int line_1 = 18;
  private static final int line_2 = line_1 + 8 + widget_spacing + text_box_height + line_space;
  private static final int line_5 = gui_height - 8 - button_height - 12;

  // Widgets
  private static final int left_section_width = 120;
  private static final int middle_section_width = 100;
  private static final MutableGuiSection   left_section = new MutableGuiSection();
  private static final MutableGuiSection middle_section = new MutableGuiSection();
  private static final MutableGuiSection  right_section = new MutableGuiSection();

  public TeamManagerObjectiveGui(){
    super(473, gui_height, objective_gui, GuiReference.edit_objective_gui);
    new_objective = true;
    existing_objective = null;
  }

  public TeamManagerObjectiveGui(final String existing_objective){
    super(473, gui_height, objective_gui, GuiReference.edit_objective_gui);
    new_objective = false;
    this.existing_objective = existing_objective;
  }

  @Override
  protected void init(){
    super.init();
    
    // update position variables that depend on guiBox
    final int bottom = guiBox.top + line_5 - 4;
    final int widget_line_1 = guiBox.top + line_1 + 8 + widget_spacing;
    final int widget_line_2 = guiBox.top + line_2 + 8 + widget_spacing;
      left_section.setBox(         guiBox.left + 6, widget_line_1, guiBox.left        + 6 + left_section_width,   bottom);
    middle_section.setBox(  left_section.right + 6, widget_line_1, left_section.right + 6 + middle_section_width, bottom);
     right_section.setBox(middle_section.right + 6, widget_line_1, guiBox.right       - 6 - scrollbar_width,      guiBox.bottom - 6);
    
    // Name TextBoxes
    objective_id_name = new EditBox(this.font, left_section.x, left_section.y, left_section_width, text_box_height, TextComponent.EMPTY);
    addWidget(objective_id_name);
    objective_display_name = new EditBox(this.font, left_section.x, widget_line_2, left_section_width, text_box_height, TextComponent.EMPTY);
    addWidget(objective_display_name);

    // Criteria Type Selection
    criteria_types = new RadialButtonGroup(middle_section.x, left_section.y, criteria_options, this::setCriteriaList);
    addRenderableWidget(criteria_types);

    // Criteria List
    final int criteria_list_length = right_section.height / entry_height;
    final int list_height = entry_height*criteria_list_length;
    final ListEntry[] objective_entries = new ListEntry[criteria_list_length];
    int i;
    for(i = 0; i < criteria_list_length; i++){
      objective_entries[i] = new ListEntry(right_section.x, right_section.y + (entry_height*i), right_section.width, entry_height);
      addRenderableWidget(objective_entries[i]);
    }
    criteria_list = new TextScrollbar(right_section.right, widget_line_1, list_height, objective_entries);
    addRenderableWidget(criteria_list);

    // Done and Cancel Buttons
    final int button_area = middle_section.right - left_section.left;
    final int[] button_x = WidgetUtil.evenAlignment(button_area, button_width, 2);
    final int   button_y = guiBox.bottom - 8 - button_height;
    finish_button = new TeamManagerGuiButtons.FinishButton(guiBox.left + button_x[0], button_y, button_width, button_height, this::create_objective);
    addRenderableWidget(finish_button);
    addRenderableWidget(new TeamManagerGuiButtons.CancelButton(guiBox.left + button_x[1], button_y, button_width, button_height));
    
    if(new_objective){
      // Initialize Criteria List
      setCriteriaList(CriteriaType.STANDARD);
    }
    else{
      // editting existing Objective, load all data
      final ObjectiveDataUnit objective_data = TeamData.getObjectiveData(existing_objective);
      objective_id_name.setValue(objective_data.name);
      // objective_id_name.isEnabled = false;
      objective_display_name.setValue(objective_data.display_name.getString());
      criteria_types.option_selected = objective_data.criteria_type;
      setCriteria(objective_data.criteria_type, objective_data.criteria_name);
    }
  }

  /** Initializes the criteria list whenever the player changes the criteria type. */
  private final void setCriteriaList(int type){
    criteria_list.setSelected(-1, false, false);
    switch(type){
    case CriteriaType.STANDARD:
      criteria_list.updateScrollbar(CriteriaData.standard_criteria);
      break;
    case CriteriaType.TEAM_KILL:
      criteria_list.updateScrollbar(CriteriaData.chat_colors);
      break;
    case CriteriaType.KILLED_BY_TEAM:
      criteria_list.updateScrollbar(CriteriaData.chat_colors);
      break;
    case CriteriaType.STATISTICS:
      criteria_list.updateScrollbar(CriteriaData.getStatistics());
      break;
    case CriteriaType.BLOCK_MINED:
      criteria_list.updateScrollbar(CriteriaData.getAllBlocks());
      break;
    case CriteriaType.ITEM_BROKEN:
      criteria_list.updateScrollbar(CriteriaData.getItemsWithDurability());
      break;
    case CriteriaType.ITEM_CRAFTED:
      criteria_list.updateScrollbar(CriteriaData.getAllItems());
      break;
    case CriteriaType.ITEM_USED:
      criteria_list.updateScrollbar(CriteriaData.getAllItems());
      break;
    case CriteriaType.ITEM_PICKED_UP:
      criteria_list.updateScrollbar(CriteriaData.getAllItems());
      break;
    case CriteriaType.ITEM_DROPPED:
      criteria_list.updateScrollbar(CriteriaData.getAllItems());
      break;
    case CriteriaType.KILLED:
      criteria_list.updateScrollbar(CriteriaData.getEntities());
      break;
    case CriteriaType.KILLED_BY:
      criteria_list.updateScrollbar(CriteriaData.getEntities());
      break;
    }
  }

  public final void create_objective(){
    final String objective_id = objective_id_name.getValue().replace(' ', '_');
    final String display_name = objective_display_name.getValue();
    final String criteria     = getCriteriaID();
    if(new_objective){
      NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.AddObjective(objective_id, display_name, criteria));
    }
    else{
      NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.EditObjective(objective_id, display_name, criteria));
    }
  }

  /** Gets the Criteria ID given the type selected and the selected List Entry in the Criteria List. */
  private final String getCriteriaID(){
    String criteria = null;
    switch(criteria_types.option_selected){
    case CriteriaType.STANDARD:
      criteria = TeamData.getStandardCriteria(criteria_list.getSelectedIndex());
      break;
    case CriteriaType.TEAM_KILL:
      criteria = CriteriaType.TEAM_KILL_PREFIX + criteria_list.getSelected();
      break;
    case CriteriaType.KILLED_BY_TEAM:
      criteria = CriteriaType.KILLED_BY_TEAM_PREFIX + criteria_list.getSelected();
      break;
    case CriteriaType.STATISTICS:
      criteria = CriteriaType.STATISTICS_PREFIX + CriteriaData.getStatisticID(criteria_list.getSelectedIndex()).replace(':', '.');
      break;
    case CriteriaType.BLOCK_MINED:
      criteria = CriteriaType.BLOCK_MINED_PREFIX + criteria_list.getSelected().getString().replace(':', '.');
      break;
    case CriteriaType.ITEM_BROKEN:
      criteria = CriteriaType.ITEM_BROKEN_PREFIX + criteria_list.getSelected().getString().replace(':', '.');
      break;
    case CriteriaType.ITEM_CRAFTED:
      criteria = CriteriaType.ITEM_CRAFTED_PREFIX + criteria_list.getSelected().getString().replace(':', '.');
      break;
    case CriteriaType.ITEM_DROPPED:
      criteria = CriteriaType.ITEM_DROPPED_PREFIX + criteria_list.getSelected().getString().replace(':', '.');
      break;
    case CriteriaType.ITEM_PICKED_UP:
      criteria = CriteriaType.ITEM_PICKED_UP_PREFIX + criteria_list.getSelected().getString().replace(':', '.');
      break;
    case CriteriaType.ITEM_USED:
      criteria = CriteriaType.ITEM_USED_PREFIX + criteria_list.getSelected().getString().replace(':', '.');
      break;
    case CriteriaType.KILLED:
      criteria = CriteriaType.KILLED_PREFIX + criteria_list.getSelected().getString().replace(':', '.');
      break;
    case CriteriaType.KILLED_BY:
      criteria = CriteriaType.KILLED_BY_PREFIX + criteria_list.getSelected().getString().replace(':', '.');
      break;
    }
    return criteria;
  }

  /** Adjusts the Criteria List to select the criteria. */
  private final void setCriteria(int type, String criteria){
    setCriteriaList(type);
    try{
      switch(type){
      case CriteriaType.STANDARD:
        criteria_list.setSelected(TeamData.getStandardCriteriaIndex(criteria));
        break;
      case CriteriaType.TEAM_KILL: case CriteriaType.KILLED_BY_TEAM:
        criteria_list.setSelected(new TextComponent(criteria.substring(criteria.indexOf('.')+1)));
        break;
      case CriteriaType.STATISTICS:
        criteria_list.setSelected(CriteriaData.getStatisticIndex(criteria.substring(criteria.indexOf(':')+1).replace('.', ':')));
        break;
      default:
        criteria_list.setSelected(new TextComponent(criteria.substring(criteria.indexOf(':')+1).replace('.', ':')));
        break;
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public void tick(){
    super.tick();
    objective_id_name.tick();
    objective_display_name.tick();
    validate();
  }

  private final void validate(){
    if(objective_id_name.getValue().isEmpty()){
      message = TeamManagerMessage.must_specify_name;
      finish_button.active = false;
      return;
    }
    if(objective_id_name.getValue().contains(" ")){
      message = TeamManagerMessage.cannot_contain_spaces;
      finish_button.active = false;
      return;
    }
    if(objective_id_name.getValue().length() > 16){
      message = TeamManagerMessage.must_be_shorter;
      finish_button.active = false;
      return;
    }
    if(criteria_list.hasValidSelection() == false){
      message = TeamManagerMessage.must_specify_criteria;
      finish_button.active = false;
      return;
    }
    message = TextComponent.EMPTY;
    finish_button.active = true;
  }

  @Override
  public void render(PoseStack matrix, int mouse_x, int mouse_y, float partialTicks){
    super.render(matrix, mouse_x, mouse_y, partialTicks);
    objective_id_name.render(matrix, mouse_x, mouse_y, partialTicks);
    objective_display_name.render(matrix, mouse_x, mouse_y, partialTicks);
  }

  @Override
  protected void drawGuiBackgroundLayer(PoseStack matrix, float partialTicks, int mouse_x, int mouse_y){
    draw_custom_background_texture(matrix, 512, 384);
  }

  @Override
  protected void drawGuiForegroundLayer(PoseStack matrix, int mouse_x, int mouse_y){
    draw_title(matrix);
    draw_text_left(matrix,      objective_id_name_text.getString()+":", 6, line_1);
    draw_text_left(matrix, objective_display_name_text.getString()+":", 6, line_2);
    draw_text_left(matrix,          criteria_type_text.getString()+":", middle_section.left - guiBox.left, line_1);
    draw_text_left(matrix,        criteria_header_text.getString()+":",  right_section.left - guiBox.left, line_1);
    draw_text_left(matrix, message,                         6, line_5);
  }

}
