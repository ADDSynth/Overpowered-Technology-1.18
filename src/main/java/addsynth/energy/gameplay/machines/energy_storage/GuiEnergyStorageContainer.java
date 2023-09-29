package addsynth.energy.gameplay.machines.energy_storage;

import addsynth.core.util.java.StringUtil;
import addsynth.energy.gameplay.reference.GuiReference;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.EnergyProgressBar;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public final class GuiEnergyStorageContainer extends GuiEnergyBase<TileEnergyStorage, ContainerEnergyStorage> {

  private final String energy_stored_text = StringUtil.translate("gui.addsynth_energy.common.energy_stored");

  private static final int draw_energy_level_y = 36;
  private final EnergyProgressBar energy_bar = new EnergyProgressBar(9, 59, 174, 17, 9, 106);

  public GuiEnergyStorageContainer(final ContainerEnergyStorage container, final Inventory player_inventory, final Component title){
    super(190, 94, container, player_inventory, title, GuiReference.energy_storage);
  }

  @Override
  protected final void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    draw_background_texture(matrix);
    energy_bar.drawHorizontal(matrix, this, energy);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, final int mouseX, final int mouseY){
    draw_title(matrix);
    draw_text_center(matrix, energy_stored_text+":", center_x, 25);
    draw_text_right(matrix, String.format("%.2f", energy.getEnergy()), 88, draw_energy_level_y);
    draw_text_left(matrix, "/ "+energy.getCapacity(), 93, draw_energy_level_y);
    draw_text_center(matrix, energy_bar.getEnergyPercentage(), center_x, 47);
    draw_energy_difference(matrix, 80);
  }

}
