package addsynth.overpoweredmod.machines.portal.control_panel;

import addsynth.core.gui.widgets.buttons.AdjustableButton;
import addsynth.core.gui.widgets.item.IngredientWidgetGroup;
import addsynth.core.util.java.StringUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.AutoShutoffCheckbox;
import addsynth.energy.lib.gui.widgets.EnergyProgressBar;
import addsynth.energy.lib.gui.widgets.OnOffSwitch;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.material.util.MaterialTag;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.reference.GuiReference;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

public final class GuiPortalControlPanel extends GuiEnergyBase<TilePortalControlPanel, ContainerPortalControlPanel> {

  private static final IngredientWidgetGroup gem_blocks = new IngredientWidgetGroup(8);

  private static final int checkbox_x = 80;
  private static final int checkbox_y = 19;

  private static final int energy_percentage_y = 48;
  private static final int energy_change_y = 60;

  private final EnergyProgressBar energy_bar = new EnergyProgressBar(193, 59, 17, 64, 227, 24);

  private static final int image_x = 14;
  private static final int image_y = 71;
  private static final int space_x = 44;
  private static final int space_y = 18;

  private static final int button_x = 27;
  private static final int button_y = 108;
  private static final int button_width = 136;
  private static final int button_height = 16;
  
  private static final int status_message_y = button_y + button_height + 6;

  public GuiPortalControlPanel(final ContainerPortalControlPanel container, final Inventory player_inventory, final Component title){
    super(218, 146, container, player_inventory, title, GuiReference.portal_control_panel);
  }

  private static final class GeneratePortalButton extends AdjustableButton {

    private final TilePortalControlPanel tile;

    public GeneratePortalButton(final int x, final int y, final TilePortalControlPanel tile){
      super(x, y, button_width, button_height, StringUtil.translate("gui.overpowered.portal_control_panel.generate_portal"));
      this.tile = tile;
    }

    @Override
    public void renderButton(PoseStack matrix, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
      this.active = tile.isValid();
      super.renderButton(matrix, p_renderButton_1_, p_renderButton_2_, p_renderButton_3_);
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new GeneratePortalMessage(tile.getBlockPos()));
    }

  }

  @Override
  protected final void init(){
    super.init();
    addRenderableWidget(new OnOffSwitch<>(this, tile));
    addRenderableWidget(new AutoShutoffCheckbox<TilePortalControlPanel>(this.leftPos + checkbox_x, this.topPos + checkbox_y, tile));
    addRenderableWidget(new GeneratePortalButton(this.leftPos + button_x, this.topPos + button_y, tile));
    
    // Set Portal Control Panel Gui Displayed ItemStacks
    final Ingredient[] portal_control_panel_displayed_itemstacks = {
      Ingredient.of(MaterialTag.RUBY.BLOCKS),
      Ingredient.of(MaterialTag.TOPAZ.BLOCKS),
      Ingredient.of(MaterialTag.CITRINE.BLOCKS),
      Ingredient.of(Tags.Items.STORAGE_BLOCKS_EMERALD),
      Ingredient.of(Tags.Items.STORAGE_BLOCKS_DIAMOND),
      Ingredient.of(MaterialTag.SAPPHIRE.BLOCKS),
      Ingredient.of(Tags.Items.STORAGE_BLOCKS_AMETHYST),
      Ingredient.of(Tags.Items.STORAGE_BLOCKS_QUARTZ)
    };
    gem_blocks.setRecipe(portal_control_panel_displayed_itemstacks);
  }

  @Override
  protected void containerTick(){
    gem_blocks.tick();
  }

  @Override
  protected final void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    draw_background_texture(matrix);
    energy_bar.drawVertical(matrix, this, energy);
    draw_portal_items(matrix);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, final int mouseX, final int mouseY){
    draw_title(matrix);
    draw_energy_below_switch(matrix);
    draw_status(matrix, tile.getStatus(), energy_percentage_y);
    draw_text_right(matrix, WorkProgressBar.getWorkTimeProgress(tile), energy_percentage_y);
    draw_energy_difference(matrix, energy_change_y);
    draw_text_center(matrix, tile.getMessage(), status_message_y);
  }
  
  /**
   * This is used to show non-interactable slots on the Gui showing each of the 8 gem blocks, and shows a
   * check mark or X by each of them indicating if the portal frame has it or not.
   */
  private final void draw_portal_items(final PoseStack matrix){
    // GuiContainer class -> drawScreen() method -> Line 100
    // GuiContainer class -> drawSlot() method -> Line 298
    int i;
    int j;
    int index;
    int x;
    int y;
    for(j = 0; j < 2; j++){
      for(i = 0; i < 4; i++){
        index = (j*4) + i;
        x = this.leftPos + image_x + (i * space_x);
        y = this.topPos  + image_y + (j * space_y);
        gem_blocks.drawIngredient(itemRenderer, index, x, y);
      }
    }
    RenderSystem.setShaderTexture(0, GuiReference.icons);
    for(j = 0; j < 2; j++){
      for(i = 0; i < 4; i++){
        index = (j*4) + i;
        x = this.leftPos + image_x + (i * space_x) + 16;
        y = this.topPos + image_y + (j * space_y);
        if(tile.getPortalItem(index)){
          blit(matrix, x, y, 64, 0, 16, 16);
        }
        else{
          blit(matrix, x, y, 80, 0, 16, 16);
        }
      }
    }
  }

  @Override
  protected void renderTooltip(PoseStack matrix, int mouse_x, int mouse_y){
    super.renderTooltip(matrix, mouse_x, mouse_y);
    int i;
    int j;
    int index;
    int x;
    int y;
    for(j = 0; j < 2; j++){
      for(i = 0; i < 4; i++){
        index = (j*4) + i;
        x = this.leftPos + image_x + (i * space_x);
        y = this.topPos  + image_y + (j * space_y);
        gem_blocks.drawTooltip(matrix, this, index, x, y, mouse_x, mouse_y);
      }
    }
  }

}
