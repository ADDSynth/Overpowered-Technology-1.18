package addsynth.core.gameplay.team_manager.gui;

import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.reference.GuiReference;
import addsynth.core.gameplay.team_manager.network_messages.TeamManagerCommand;
import addsynth.core.gui.widgets.WidgetUtil;
import addsynth.core.gui.widgets.buttons.AdjustableButton;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public final class TeamManagerGuiButtons {

  private static final TranslatableComponent add            = new TranslatableComponent("gui.addsynthcore.team_manager.common.add");
  private static final TranslatableComponent edit           = new TranslatableComponent("gui.addsynthcore.team_manager.common.edit");
  private static final TranslatableComponent delete         = new TranslatableComponent("gui.addsynthcore.team_manager.common.delete");
  private static final TranslatableComponent done           = new TranslatableComponent("gui.addsynthcore.team_manager.common.done");
  private static final TranslatableComponent cancel         = new TranslatableComponent("gui.addsynthcore.team_manager.common.cancel");
  private static final TranslatableComponent add_score      = new TranslatableComponent("gui.addsynthcore.team_manager.score.add");
  private static final TranslatableComponent subtract_score = new TranslatableComponent("gui.addsynthcore.team_manager.score.subtract");
  private static final TranslatableComponent set_score      = new TranslatableComponent("gui.addsynthcore.team_manager.score.set");
  private static final TranslatableComponent reset          = new TranslatableComponent("gui.addsynthcore.team_manager.score.reset");
  private static final TranslatableComponent assign         = new TranslatableComponent("gui.addsynthcore.team_manager.display_slot.assign");
  private static final TranslatableComponent clear          = new TranslatableComponent("gui.addsynthcore.team_manager.display_slot.clear");

  private static final Minecraft minecraft = Minecraft.getInstance(); // TEST: probably not a good idea
  public final static int player_button_size = 20;
  public final static int display_slot_button_width = 50;
  public final static int display_slot_button_height = 14;

  public static final class AddTeamButton extends AdjustableButton {
    public AddTeamButton(int x, int y, int width, int height){
      super(x, y, width, height, add);
    }
    @Override
    public void onPress(){
      minecraft.setScreen(new TeamManagerTeamEditGui());
    }
  }

  public static final class EditTeamButton extends AdjustableButton {
    private final TeamManagerGui gui;
    public EditTeamButton(TeamManagerGui gui, int x, int y, int width, int height){
      super(x, y, width, height, edit);
      this.gui = gui;
    }
    @Override
    public void onPress(){
      minecraft.setScreen(new TeamManagerTeamEditGui(gui.getTeamSelected()));
    }
  }

  public static final class DeleteTeamButton extends AdjustableButton {
    private final TeamManagerGui gui;
    public DeleteTeamButton(TeamManagerGui gui, int x, int y, int width, int height){
      super(x, y, width, height, delete);
      this.gui = gui;
    }
    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.DeleteTeam(gui.getTeamSelected()));
      gui.unSelectTeam();
    }
  }

  public static final class MovePlayerToTeamButton extends AbstractButton {

    private final TeamManagerGui gui;
    private final int texture_x = 20;
    private final int texture_y = 184;
    private int final_texture_y;

    public MovePlayerToTeamButton(TeamManagerGui gui, int xIn, int yIn){
      super(xIn, yIn, player_button_size, player_button_size, TextComponent.EMPTY);
      this.gui = gui;
    }

    @Override
    public final void renderButton(PoseStack matrix, int mouse_x, int mouse_y, float partial_ticks){
      final_texture_y = texture_y + (active ? isHovered ? player_button_size : 0 : -player_button_size);
      WidgetUtil.renderButton(matrix, this, GuiReference.widgets, texture_x, final_texture_y, player_button_size, player_button_size);
    }

    @Override
    public final void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.AddPlayerToTeam(gui.getPlayerSelected(), gui.getTeamSelected()));
      gui.unSelectPlayer();
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_){
    }
  
  }
  
  public static final class RemovePlayerFromTeamButton extends AbstractButton {

    private final TeamManagerGui gui;
    private final int texture_x = 0;
    private final int texture_y = 184;
    private int final_texture_y;

    public RemovePlayerFromTeamButton(TeamManagerGui gui, int xIn, int yIn){
      super(xIn, yIn, player_button_size, player_button_size, TextComponent.EMPTY);
      this.gui = gui;
    }

    @Override
    public final void renderButton(PoseStack matrix, int mouse_x, int mouse_y, float partial_ticks){
      final_texture_y = texture_y + (active ? isHovered ? player_button_size : 0 : -player_button_size);
      WidgetUtil.renderButton(matrix, this, GuiReference.widgets, texture_x, final_texture_y, player_button_size, player_button_size);
    }

    @Override
    public final void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.RemovePlayerFromTeam(gui.getPlayerSelected(), gui.getTeamSelected()));
      gui.unSelectPlayer();
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_){
    }
  
  }

  public static final class AddObjectiveButton extends AdjustableButton {
    public AddObjectiveButton(int x, int y, int width, int height){
      super(x, y, width, height, add);
    }
    @Override
    public void onPress(){
      minecraft.setScreen(new TeamManagerObjectiveGui());
    }
  }

  public static final class EditObjectiveButton extends AdjustableButton {
    private final TeamManagerGui gui;
    public EditObjectiveButton(TeamManagerGui gui, int x, int y, int width, int height){
      super(x, y, width, height, edit);
      this.gui = gui;
    }
    @Override
    public void onPress(){
      minecraft.setScreen(new TeamManagerObjectiveGui(gui.getObjectiveSelected()));
    }
  }

  public static final class DeleteObjectiveButton extends AdjustableButton {
    private final TeamManagerGui gui;
    public DeleteObjectiveButton(TeamManagerGui gui, int x, int y, int width, int height){
      super(x, y, width, height, delete);
      this.gui = gui;
    }
    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.DeleteObjective(gui.getObjectiveSelected()));
      gui.unSelectObjective();
    }
  }

  public static final class FinishButton extends AdjustableButton {

    private final Runnable onClick;

    public FinishButton(int x, int y, int width, int height, Runnable onClick){
      super(x, y, width, height, done);
      this.onClick = onClick;
    }

    @Override
    public void onPress(){
      onClick.run();
      minecraft.setScreen(new TeamManagerGui());
    }
  }

  public static final class CancelButton extends AdjustableButton {

    public CancelButton(int x, int y, int width, int height){
      super(x, y, width, height, cancel);
    }

    @Override
    public void onPress(){
      minecraft.setScreen(new TeamManagerGui());
    }
  }

  public static final class SubtractScoreButton extends AdjustableButton {
  
    private final Runnable onClick;
    
    public SubtractScoreButton(int x, int y, int width, int height, Runnable onClick){
      super(x, y, width, height, subtract_score);
      this.onClick = onClick;
    }
    @Override
    public void onPress(){
      onClick.run();
    }
  }

  public static final class AddScoreButton extends AdjustableButton {
  
    private final Runnable onClick;
    
    public AddScoreButton(int x, int y, int width, int height, Runnable onClick){
      super(x, y, width, height, add_score);
      this.onClick = onClick;
    }
    @Override
    public void onPress(){
      onClick.run();
    }
  }

  public static final class SetScoreButton extends AdjustableButton {
  
    private final Runnable onClick;
    
    public SetScoreButton(int x, int y, int width, int height, Runnable onClick){
      super(x, y, width, height, set_score);
      this.onClick = onClick;
    }
    @Override
    public void onPress(){
      onClick.run();
    }
  }

  public static final class ResetScoreButton extends AdjustableButton {
    private final TeamManagerGui gui;
    public ResetScoreButton(TeamManagerGui gui, int x, int y, int width, int height){
      super(x, y, width, height, reset);
      this.gui = gui;
    }
    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(
        new TeamManagerCommand.ResetScore(
          gui.getObjectiveSelected(),
          gui.getPlayerSelected()
        )
      );
    }
  }
  
  public static final class SetDisplaySlotButton extends AdjustableButton {
  
    private final TeamManagerGui gui;
    private final int display_slot;
  
    public SetDisplaySlotButton(TeamManagerGui gui, int x, int y, int display_slot){
      super(x, y, display_slot_button_width, display_slot_button_height, assign);
      this.gui = gui;
      this.display_slot = display_slot;
    }
    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.SetDisplaySlot(gui.getObjectiveSelected(), display_slot));
    }
  }
  
  public static final class ClearDisplaySlotButton extends AdjustableButton {
  
    private final int display_slot;
  
    public ClearDisplaySlotButton(int x, int y, int display_slot){
      super(x, y, display_slot_button_width, display_slot_button_height, clear);
      this.display_slot = display_slot;
    }
    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.ClearDisplaySlot(display_slot));
    }
  }

}
