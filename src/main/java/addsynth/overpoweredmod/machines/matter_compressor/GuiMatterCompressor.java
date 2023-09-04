package addsynth.overpoweredmod.machines.matter_compressor;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.ProgressBar;
import addsynth.core.util.StringUtil;
import addsynth.core.util.math.RoundMode;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.game.reference.GuiReference;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public final class GuiMatterCompressor extends GuiEnergyBase<TileMatterCompressor, MatterCompressorContainer> {

  private final String black_hole_text = StringUtil.translate(OverpoweredBlocks.black_hole.get().getDescriptionId());
  private final String matter_text     = StringUtil.translate("gui.overpowered.matter_compressor.matter");

  private final ProgressBar progress_bar = new ProgressBar(8, 83, 166, 11, 7, 190);

  public GuiMatterCompressor(final MatterCompressorContainer container, final Inventory player_inventory, final Component title){
    super(183, 182, container, player_inventory, title, GuiReference.matter_compressor);
  }

  @Override
  protected final void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture(matrix);
    progress_bar.draw(matrix, this, ProgressBar.Direction.LEFT_TO_RIGHT, tile.getProgress(), RoundMode.Floor);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, int mouseX, int mouseY){
    guiUtil.draw_title(matrix, this.title);
    GuiUtil.draw_text_right(matrix, black_hole_text+':', 76, 27);
    // final int slash = font.width("/"); // 6
    // final int space = font.width(" "); // 4
    
    GuiUtil.draw_text_left(matrix, matter_text+":", 6, 71);
    GuiUtil.draw_text_right(matrix, tile.getMatter(), guiUtil.center_x - 7, 71);
    GuiUtil.draw_text_left(matrix, "/ "+Config.max_matter.get(), guiUtil.center_x - 3, 71);
    guiUtil.draw_text_right(matrix, StringUtil.toPercentageString(tile.getProgress(), RoundMode.Floor), 71);
  }

}
