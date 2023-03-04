package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.buttons.AdjustableButton;
import addsynth.core.util.StringUtil;
import addsynth.core.util.constants.DirectionConstant;
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

  private static final int gui_width = 206;

  private static final int lens_text_x = (6 + ContainerSuspensionBridge.lens_slot_x) / 2;
  private static final int lens_text_y = 24;
  private final int[] text_x = {
                   6,                6 + GuiUtil.getMaxStringWidth(north+": ", south+": ", west+": "),
    guiUtil.center_x, guiUtil.center_x + GuiUtil.getMaxStringWidth(up+": ", down+": ", east+": ")
  };
  private static final int[] text_y = {lens_text_y + 16, lens_text_y + 27, lens_text_y + 38};

  private static final int button_width = 50;
  private static final int button_x = gui_width - 6 - button_width;
  private static final int button_y = 17;

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
    super(gui_width, 167, container, inventory, title, GuiReference.energy_suspension_bridge);
  }

  @Override
  public final void init(){
    super.init();
    addRenderableWidget(new RotateButton(this.leftPos + button_x, this.topPos + button_y, tile));
  }

  @Override
  protected final void renderLabels(PoseStack matrix, int mouseX, int mouseY){
    guiUtil.draw_title(matrix, this.title);
    GuiUtil.draw_text_center(matrix, lens_string+":", lens_text_x, lens_text_y);
    GuiUtil.draw_text_left(matrix, north+":", text_x[0], text_y[0]);
    GuiUtil.draw_text_left(matrix, south+":", text_x[0], text_y[1]);
    GuiUtil.draw_text_left(matrix, west+":",  text_x[0], text_y[2]);
    GuiUtil.draw_text_left(matrix, tile.getMessage(DirectionConstant.NORTH), text_x[1], text_y[0]);
    GuiUtil.draw_text_left(matrix, tile.getMessage(DirectionConstant.SOUTH), text_x[1], text_y[1]);
    GuiUtil.draw_text_left(matrix, tile.getMessage(DirectionConstant.WEST),  text_x[1], text_y[2]);
    GuiUtil.draw_text_left(matrix, up+":",   text_x[2], text_y[0]);
    GuiUtil.draw_text_left(matrix, down+":", text_x[2], text_y[1]);
    GuiUtil.draw_text_left(matrix, east+":", text_x[2], text_y[2]);
    GuiUtil.draw_text_left(matrix, tile.getMessage(DirectionConstant.UP),    text_x[3], text_y[0]);
    GuiUtil.draw_text_left(matrix, tile.getMessage(DirectionConstant.DOWN),  text_x[3], text_y[1]);
    GuiUtil.draw_text_left(matrix, tile.getMessage(DirectionConstant.EAST),  text_x[3], text_y[2]);
    guiUtil.draw_text_center(matrix, tile.getBridgeMessage(), 73);
  }

}
