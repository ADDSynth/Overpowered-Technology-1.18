package addsynth.overpoweredmod.compatability.jei;

import addsynth.overpoweredmod.game.reference.GuiReference;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.machines.inverter.InverterRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;

public final class InverterCategory implements IRecipeCategory<InverterRecipe> {

  private static final ResourceLocation id = Names.INVERTER;
  public static final RecipeType<InverterRecipe> type = new RecipeType<>(id, InverterRecipe.class);
  private final IDrawable background;
  private final IDrawable icon;

  public InverterCategory(final IGuiHelper gui_helper){
    background = gui_helper.createDrawable(GuiReference.jei_recipe_background, 18, 16, 74, 18);
    icon = gui_helper.createDrawableItemStack(new ItemStack(OverpoweredBlocks.inverter.get()));
  }

  public static RecipeType<InverterRecipe> getType(){
    return type;
  }

  @Override
  @Deprecated
  public ResourceLocation getUid(){
    return id;
  }

  @Override
  @Deprecated
  public Class<? extends InverterRecipe> getRecipeClass(){
    return InverterRecipe.class;
  }

  @Override
  public Component getTitle(){
    return new TranslatableComponent(OverpoweredBlocks.inverter.get().getDescriptionId());
  }

  @Override
  public IDrawable getBackground(){
    return background;
  }

  @Override
  public IDrawable getIcon(){
    return icon;
  }

  @Override
  public void setRecipe(IRecipeLayoutBuilder builder, InverterRecipe recipe, IFocusGroup focuses){
    builder.addSlot(RecipeIngredientRole.INPUT,   0, 0).addItemStack(recipe.input);
    builder.addSlot(RecipeIngredientRole.OUTPUT, 56, 0).addItemStack(recipe.result);
  }

  @Override
  @Nullable
  public final ResourceLocation getRegistryName(final InverterRecipe recipe){
    return recipe.getId();
  }

}
