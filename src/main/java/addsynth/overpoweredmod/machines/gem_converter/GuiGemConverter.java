package addsynth.overpoweredmod.machines.gem_converter;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.buttons.AdjustableButton;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.material.Material;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.reference.GuiReference;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public final class GuiGemConverter extends GuiEnergyBase<TileGemConverter, ContainerGemConverter> {

  private static final ItemStack[] gem = new ItemStack[] {
    new ItemStack(Material.RUBY.getGem(), 1),
    new ItemStack(Material.TOPAZ.getGem(), 1),
    new ItemStack(Material.CITRINE.getGem(), 1),
    new ItemStack(Material.EMERALD.gem, 1),
    new ItemStack(Material.DIAMOND.gem, 1),
    new ItemStack(Material.SAPPHIRE.getGem(), 1),
    new ItemStack(Material.AMETHYST.getGem(), 1),
    new ItemStack(Material.QUARTZ.gem, 1)
  };

  private static final int left_button_x = 64;
  private static final int cycle_button_y = 64;
  private static final int cycle_button_width = 10;
  private static final int cycle_button_buffer = 2;
  private static final int right_button_x = left_button_x + cycle_button_width + 16 + (cycle_button_buffer*2);
  private static final int cycle_button_height = 16;
  
  private final WorkProgressBar work_progress_bar = new WorkProgressBar(43, 89, 122, 5, 40, 199);
  
  public GuiGemConverter(final ContainerGemConverter container, final Inventory player_inventory, final Component title){
    super(176, 194, container, player_inventory, title, GuiReference.gem_converter);
  }

  private static final class CycleGemButton extends AdjustableButton {

    private final TileGemConverter tile;
    private final boolean direction;

    public CycleGemButton(int xIn, int yIn, boolean direction, TileGemConverter tile){
      super(xIn, yIn, cycle_button_width, cycle_button_height, direction ? ">" : "<"); // true goes right
      this.tile = tile;
      this.direction = direction;
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new CycleGemConverterMessage(tile.getBlockPos(),direction));
    }

  }

  @Override
  public final void init(){
    super.init();
    addRenderableWidget(new CycleGemButton(this.leftPos + left_button_x, this.topPos + cycle_button_y,false, tile));
    addRenderableWidget(new CycleGemButton(this.leftPos + right_button_x, this.topPos + cycle_button_y,true, tile));
  }

  @Override
  protected final void renderBg(PoseStack matrix, final float partialTicks, final int mouseX, final int mouseY){
    guiUtil.draw_background_texture(matrix);
    work_progress_bar.draw(matrix, this, tile);
    GuiUtil.drawItemStack(gem[tile.get_gem_selected()], this.leftPos + left_button_x + 12, this.topPos + cycle_button_y);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, final int mouseX, final int mouseY){
    guiUtil.draw_title(matrix, this.title);
    draw_energy_usage(matrix);
    draw_status(matrix, tile.getStatus());
    
    final ItemStack s1 = tile.getWorkingInventory().getStackInSlot(0);
    if(Config.blend_working_item.get()){
      final ItemStack s2 = gem[tile.getConvertingStack()];
      GuiUtil.blendItemStacks(s1, s2, 76, 45, work_progress_bar.getWorkTime());
    }
    else{
      GuiUtil.drawItemStack(s1, 76, 45);
    }
    
    GuiUtil.draw_text_center(matrix, work_progress_bar.getWorkTimeProgress(), 25, 88);
    draw_time_left(matrix, 99);
  }

}
