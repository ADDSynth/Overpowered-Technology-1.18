package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.core.gui.section.GuiSection;
import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.buttons.AdjustableButton;
import addsynth.core.util.constants.DirectionConstant;
import addsynth.core.util.java.StringUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.reference.GuiReference;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public final class GuiEnergySuspensionBridge extends GuiEnergyBase<TileSuspensionBridge, ContainerSuspensionBridge> {

  // translation strings
  private final String lens_string = StringUtil.translate("gui.overpowered.energy_suspension_bridge.lens");
  private final String down  = StringUtil.translate("gui.addsynthcore.direction.down");
  private final String up    = StringUtil.translate("gui.addsynthcore.direction.up");
  private final String north = StringUtil.translate("gui.addsynthcore.direction.north");
  private final String south = StringUtil.translate("gui.addsynthcore.direction.south");
  private final String west  = StringUtil.translate("gui.addsynthcore.direction.west");
  private final String east  = StringUtil.translate("gui.addsynthcore.direction.east");
  private final String length = StringUtil.translate("gui.overpowered.energy_suspension_bridge.length");

  private static final int gui_width = 312;

  private static final int lens_text_x = ContainerSuspensionBridge.lens_slot_x - 10;
  private static final int lens_text_y = 24;
  
  // vertical space: 8
  // horizontal space: 3
  // section width: 98
  // first y = lens_text_y + 16
  private static final GuiSection    up_section = GuiSection.dimensions(  6, 41, 98, 19);
  private static final GuiSection north_section = GuiSection.dimensions(107, 41, 98, 19);
  private static final GuiSection  down_section = GuiSection.dimensions(208, 41, 98, 19);
  private static final GuiSection  west_section = GuiSection.dimensions(  6, 68, 98, 19);
  private static final GuiSection south_section = GuiSection.dimensions(107, 68, 98, 19);
  private static final GuiSection  east_section = GuiSection.dimensions(208, 68, 98, 19);
  
  private static final int[] message_x = new int[3];

  private static final int button_width = 50;
  private static final int button_x = gui_width - 6 - button_width;
  private static final int button_y = 17;

  private RotateButton rotate_button;

  private static final class RotateButton extends AdjustableButton {

    private final TileSuspensionBridge tile;

    public RotateButton(int x, int y, TileSuspensionBridge tile){
      super(x, y, button_width, 20, StringUtil.translate("gui.overpowered.energy_suspension_bridge.rotate"));
      this.tile = tile;
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new RotateBridgeMessage(tile.getBlockPos()));
    }

  }

  public GuiEnergySuspensionBridge(final ContainerSuspensionBridge container, final Inventory inventory, final Component title){
    super(gui_width, 185, container, inventory, title, GuiReference.energy_suspension_bridge);
  }

  @Override
  protected final void init(){
    super.init();
    rotate_button = new RotateButton(this.leftPos + button_x, this.topPos + button_y, tile);
    addRenderableWidget(rotate_button);
    message_x[0] =    up_section.horizontal_center + GuiUtil.getMaxStringWidth(font,  west+":",    up+":") / 2;
    message_x[1] = north_section.horizontal_center + GuiUtil.getMaxStringWidth(font, south+":", north+":") / 2;
    message_x[2] =  down_section.horizontal_center + GuiUtil.getMaxStringWidth(font,  east+":",  down+":") / 2;
  }

  @Override
  protected final void containerTick(){
    rotate_button.active = tile.can_rotate();
  }

  @Override
  protected void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    draw_custom_background_texture(matrix, 384, 256);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, int mouseX, int mouseY){
    draw_title(matrix);
    draw_text_right(matrix, lens_string+":", lens_text_x, lens_text_y);

    draw_text_left(matrix, up+":",       up_section.x,    up_section.y);
    draw_text_center(matrix, tile.getMessage(DirectionConstant.UP),    message_x[0],    up_section.y);
    draw_text_center(matrix, length+": "+tile.getLength(DirectionConstant.UP), up_section.horizontal_center, up_section.y + 11);

    draw_text_left(matrix, west+":",   west_section.x,  west_section.y);
    draw_text_center(matrix, tile.getMessage(DirectionConstant.WEST),  message_x[0],  west_section.y);
    draw_text_center(matrix, length+": "+tile.getLength(DirectionConstant.WEST), west_section.horizontal_center, west_section.y + 11);

    draw_text_left(matrix, north+":", north_section.x, north_section.y);
    draw_text_center(matrix, tile.getMessage(DirectionConstant.NORTH), message_x[1], north_section.y);
    draw_text_center(matrix, length+": "+tile.getLength(DirectionConstant.NORTH), north_section.horizontal_center, north_section.y + 11);

    draw_text_left(matrix, south+":", south_section.x, south_section.y);
    draw_text_center(matrix, tile.getMessage(DirectionConstant.SOUTH), message_x[1], south_section.y);
    draw_text_center(matrix, length+": "+tile.getLength(DirectionConstant.SOUTH), south_section.horizontal_center, south_section.y + 11);

    draw_text_left(matrix, down+":",   down_section.x,  down_section.y);
    draw_text_center(matrix, tile.getMessage(DirectionConstant.DOWN),  message_x[2],  down_section.y);
    draw_text_center(matrix, length+": "+tile.getLength(DirectionConstant.DOWN), down_section.horizontal_center, down_section.y + 11);

    draw_text_left(matrix, east+":",   east_section.x,  east_section.y);
    draw_text_center(matrix, tile.getMessage(DirectionConstant.EAST),  message_x[2],  east_section.y);
    draw_text_center(matrix, length+": "+tile.getLength(DirectionConstant.EAST), east_section.horizontal_center, east_section.y + 11);

    draw_text_center(matrix, tile.getBridgeMessage(), 91);
  }

}
