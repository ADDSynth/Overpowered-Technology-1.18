package addsynth.energy.compat.jei;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.machines.compressor.recipe.CompressorRecipe;
import addsynth.energy.gameplay.reference.GuiReference;
import addsynth.energy.gameplay.reference.Names;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;

public final class CompressorRecipeCategory implements IRecipeCategory<CompressorRecipe> {

  private static final ResourceLocation id = Names.COMPRESSOR;
  public static final RecipeType<CompressorRecipe> type = new RecipeType<>(id, CompressorRecipe.class);
  private final IDrawable background;
  private final IDrawable icon;
  // private final LoadingCache<CompressorRecipe, CompressorRecipeDisplayData> cached_display_data;

  public CompressorRecipeCategory(final IGuiHelper gui_helper){
    background = gui_helper.createDrawable(GuiReference.widgets, 130, 0, 73, 18);
    icon = gui_helper.createDrawableItemStack(new ItemStack(EnergyBlocks.compressor.get()));
  }

  public static RecipeType<CompressorRecipe> getType(){
    return type;
  }

  @Override
  @Deprecated
  public ResourceLocation getUid(){
    return id;
  }

  @Override
  @Deprecated
  public Class<CompressorRecipe> getRecipeClass(){
    return CompressorRecipe.class;
  }

  @Override
  public Component getTitle(){
    return new TranslatableComponent(EnergyBlocks.compressor.get().getDescriptionId());
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
  public void setRecipe(IRecipeLayoutBuilder builder, CompressorRecipe recipe, IFocusGroup focuses){
    builder.addSlot(RecipeIngredientRole.INPUT,   1, 1).addIngredients(recipe.getIngredients().get(0));
    builder.addSlot(RecipeIngredientRole.OUTPUT, 56, 1).addItemStack(recipe.getResultItem());
  }

  @Override
  @Nullable
  public final ResourceLocation getRegistryName(final CompressorRecipe recipe){
    return recipe.getId();
  }

}
