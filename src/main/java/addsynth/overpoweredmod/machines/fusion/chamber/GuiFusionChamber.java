package addsynth.overpoweredmod.machines.fusion.chamber;

import addsynth.core.gui.GuiContainerBase;
import addsynth.overpoweredmod.game.reference.GuiReference;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public final class GuiFusionChamber extends GuiContainerBase<ContainerFusionChamber> {

  public GuiFusionChamber(final ContainerFusionChamber container, final Inventory player_inventory, final Component title){
    super(container, player_inventory, title, GuiReference.fusion_chamber);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, final int mouseX, final int mouseY){
    draw_title(matrix);
  }

}
