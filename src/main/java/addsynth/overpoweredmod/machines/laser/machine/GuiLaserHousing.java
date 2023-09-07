package addsynth.overpoweredmod.machines.laser.machine;

import addsynth.core.util.StringUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.AutoShutoffCheckbox;
import addsynth.energy.lib.gui.widgets.EnergyProgressBar;
import addsynth.energy.lib.gui.widgets.OnOffSwitch;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.reference.GuiReference;
import addsynth.overpoweredmod.machines.laser.network_messages.SetLaserDistanceMessage;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;

public final class GuiLaserHousing extends GuiEnergyBase<TileLaserHousing, ContainerLaserHousing> {

  // TODO: The Laser machine also doesn't have any item slots in its inventory. It can derive from a Non-container
  //       GuiEnergyBase, but that means that I need to extract all the common helper functions from GuiEnergyBase
  //       into a GuiEnergyUtil. I can have that extend GuiUtil, then pass that as a reference through the Gui constructors.

  private final String required_energy_text = StringUtil.translate("gui.overpowered.laser_housing.required_energy");
  private final String current_energy_text  = StringUtil.translate("gui.overpowered.laser_housing.current_energy");
  private final String lasers_text          = StringUtil.translate("gui.overpowered.laser_housing.lasers");
  private final String distance_text        = StringUtil.translate("gui.overpowered.laser_housing.distance");

  private EditBox text_box;
  private final EnergyProgressBar energy_bar = new EnergyProgressBar(9, 79, 163, 16, 22, 162);

  private static final int gui_height = 115;
  private static final int space = 4;

  private static final int line_1 = 39;
  private static final int text_box_width = 48;
  private static final int text_box_height = 15;
  private static final int text_box_x = 151;
  private static final int text_box_y = line_1 -3;
  private static final int line_2 = text_box_y + text_box_height + space;

  private static final int line_3 = line_2 + 8 + space;
  private static final int line_4 = 84; // line_3 (67) + 8 + 13
  private static final int line_5 = gui_height - 14;

  private static final int check_box_x = 62;
  private static final int check_box_y = 19;

  public GuiLaserHousing(final ContainerLaserHousing container, final Inventory player_inventory, final Component title){
    super(208, gui_height, container, player_inventory, title, GuiReference.laser_machine);
  }

  private static final class LaserDistanceTextField extends EditBox {

    private final TileLaserHousing tile;

    public LaserDistanceTextField(Font fontIn, int x, int y, int width, int height, TileLaserHousing tile){
      super(fontIn, x, y, width, height, new TextComponent(""));
      this.tile = tile;
      final String initial_distance = Integer.toString(tile.getLaserDistance());
      setValue(initial_distance);
      setMaxLength(4); // FEATURE: add a numbers-only textbox to ADDSynthCore. Also add Unsigned textbox. Set text to red if input is invalid.
      setTextColor(16777215);
      setResponder((String text) -> text_field_changed());
    }

    private final void text_field_changed(){
      int captured_distance = 0;
      try{
        captured_distance = Integer.parseUnsignedInt(getValue());
      }
      catch(NumberFormatException e){
        captured_distance = -1;
      }
      if(captured_distance >= 0){
        if(captured_distance != tile.getLaserDistance()){
          if(captured_distance > LaserNetwork.max_laser_distance){
            captured_distance = LaserNetwork.max_laser_distance;
            setValue(Integer.toString(LaserNetwork.max_laser_distance));
          }
          NetworkHandler.INSTANCE.sendToServer(new SetLaserDistanceMessage(tile.getBlockPos(), captured_distance));
        }
      }
    }

  }

  @Override
  protected final void init(){
    super.init();
    addRenderableWidget(new OnOffSwitch<>(this, tile));
    addRenderableWidget(new AutoShutoffCheckbox<TileLaserHousing>(this.leftPos + check_box_x, this.topPos + check_box_y, tile));
    
    this.text_box = new LaserDistanceTextField(this.font, this.leftPos + text_box_x, this.topPos + text_box_y, text_box_width, text_box_height, tile);
    addWidget(text_box);
  }

  // NOTE: The only thing that doesn't sync with the client is when 2 people have the gui open
  //   and one of them changes the Laser Distance. The energy requirements sucessfully updates,
  //   but not the Laser Distance text field of the other player. Here is my solution, but it looked
  //   too weird and I didn't feel it was absolutely necessary to keep things THAT much in-sync.
  // final int captured_distance = get_laser_distance();
  // if(captured_distance >= 0){
  //   if(captured_distance != tile.getLaserDistance()){
  //     distance_text_field.setText(Integer.toString(tile.getLaserDistance()));
  //   }
  // }

  @Override
  public final void containerTick(){
    if(text_box != null){
      text_box.tick();
    }
  }

  @Override
  public final void render(PoseStack matrix, final int mouseX, final int mouseY, final float partialTicks){
    super.render(matrix, mouseX, mouseY, partialTicks);
    if(text_box != null){ // See how Minecraft draws the TextBox on the Anvil screen.
      text_box.render(matrix, mouseX, mouseY, partialTicks);
    }
  }

  @Override
  protected final void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    draw_background_texture(matrix);
    energy_bar.drawHorizontal(matrix, this, energy);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, int mouseX, int mouseY){
    draw_title(matrix);
    draw_text_left(matrix, lasers_text+": "+tile.number_of_lasers, 6, line_1);
    draw_text_right(matrix, distance_text+": ", text_box_x - 2, line_1);
    draw_energy_requirements(matrix);
    draw_text_right(matrix, energy_bar.getEnergyPercentage(), line_4);
    draw_energy_difference_center(matrix, line_5);
  }

  private final void draw_energy_requirements(final PoseStack matrix){
    final String required_energy = Integer.toString((int)(energy.getCapacity()));
    final String word_1 = required_energy_text+": "+required_energy;
    final int word_1_width = font.width(word_1);
    
    final String current_energy = Integer.toString((int)(energy.getEnergy()));
    final String word_2 = current_energy_text+": "+current_energy;
    final int word_2_width = font.width(word_2);
    
    if(Math.max(word_1_width, word_2_width) == word_1_width){
      draw_text_left(matrix, word_1, 6, line_2);
      draw_text_left(matrix, current_energy_text+":", 6, line_3);
      draw_text_right(matrix, current_energy, 6 + word_1_width, line_3);
    }
    else{
      draw_text_left(matrix, required_energy_text+":", 6, line_2);
      draw_text_right(matrix, required_energy, 6 + word_2_width, line_2);
      draw_text_left(matrix, word_2, 6, line_3);
    }
  }

}
