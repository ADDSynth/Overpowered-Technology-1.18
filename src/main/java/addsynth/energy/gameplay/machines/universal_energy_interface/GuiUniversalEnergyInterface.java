package addsynth.energy.gameplay.machines.universal_energy_interface;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.buttons.AdjustableButton;
import addsynth.core.util.StringUtil;
import addsynth.energy.gameplay.NetworkHandler;
import addsynth.energy.gameplay.reference.GuiReference;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.EnergyProgressBar;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;

public final class GuiUniversalEnergyInterface extends GuiEnergyBase<TileUniversalEnergyInterface, ContainerUniversalEnergyInterface> {

  private final String mode_text   = StringUtil.translate("gui.addsynth_energy.common.mode");
  private final String energy_text = StringUtil.translate("gui.addsynth_energy.common.energy");

  private static final int button_width = 90;
  private final EnergyProgressBar energy_bar = new EnergyProgressBar(156, 18, 12, 34, 206, 28);
  
  private static final int line_1 = 21;
  private static final int line_2 = 41;

  public GuiUniversalEnergyInterface(final ContainerUniversalEnergyInterface container, final Inventory player_inventory, final Component title){
    super(176, 60, container, player_inventory, title, GuiReference.universal_interface);
  }

  private static final class CycleTransferModeButton extends AdjustableButton {

    private final TileUniversalEnergyInterface tile;

    public CycleTransferModeButton(int xIn, int yIn, TileUniversalEnergyInterface tile){
      super(xIn, yIn, button_width, 16);
      this.tile = tile;
    }

    @Override
    public final void renderButton(PoseStack matrix, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
      setMessage(new TextComponent(tile.get_transfer_mode().toString())); // TEST check to see if we need to redesign how to get the TRANSFER_MODE string, should only return a TranslationTextComponent?
      super.renderButton(matrix, p_renderButton_1_, p_renderButton_2_, p_renderButton_3_);
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new CycleTransferModeMessage(tile.getBlockPos()));
    }

  }

  @Override
  public final void init(){
    super.init();
    final int button_x = leftPos + guiUtil.center_x - (button_width / 2) + 4;
    addRenderableWidget(new CycleTransferModeButton(button_x, topPos + 17, tile));
  }

  @Override
  protected final void renderBg(PoseStack matrix, final float partialTicks, final int mouseX, final int mouseY){
    guiUtil.draw_background_texture(matrix);
    energy_bar.drawVertical(matrix, this, energy);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, final int mouseX, final int mouseY){
    guiUtil.draw_title(matrix, this.title);
    GuiUtil.draw_text_left(matrix, mode_text+":", 6, line_1);
    GuiUtil.draw_text_left(matrix, energy_text+":", 6, line_2);
    GuiUtil.draw_text_right(matrix, energy.getEnergy() + " / "+energy.getCapacity(), 130, line_2);
  }

}
