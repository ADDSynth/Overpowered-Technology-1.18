package addsynth.overpoweredmod.machines.advanced_ore_refinery;

import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.game.reference.GuiReference;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public final class GuiAdvancedOreRefinery extends GuiEnergyBase<TileAdvancedOreRefinery, ContainerOreRefinery> {

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 83, 160, 5, 8, 194);

  public GuiAdvancedOreRefinery(final ContainerOreRefinery container, final Inventory player_inventory, final Component title){
    super(176, 186, container, player_inventory, title, GuiReference.advanced_ore_refinery);
  }

  @Override
  protected final void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    draw_background_texture(matrix);
    work_progress_bar.draw(matrix, this, tile);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, int mouseX, int mouseY){
    draw_title(matrix);
    draw_energy_usage(matrix);
    draw_status(matrix, tile.getStatus());
    itemRenderer.renderGuiItem(tile.getWorkingInventory().getStackInSlot(0), 76, 43);
    draw_text_center(matrix, work_progress_bar.getWorkTimeProgress(), 69);
    draw_time_left(matrix, 92);
  }

}
