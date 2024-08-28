package addsynth.energy.compat.jei;

import addsynth.core.compat.JeiUtil;
import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipe;
import addsynth.energy.gameplay.reference.GuiReference;
import addsynth.energy.gameplay.reference.Names;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;

public final class CircuitRecipeCategory implements IRecipeCategory<CircuitFabricatorRecipe> {

  private static final ResourceLocation id = Names.CIRCUIT_FABRICATOR;
  public static final RecipeType<CircuitFabricatorRecipe> type = new RecipeType<>(id, CircuitFabricatorRecipe.class);
  private final IDrawable background;
  private final IDrawable icon;

  public CircuitRecipeCategory(final IGuiHelper gui_helper){
    final IDrawableBuilder drawable_builder = gui_helper.drawableBuilder(GuiReference.circuit_fabricator, 153, 67, 140, 54);
    drawable_builder.setTextureSize(384, 256);
    background = drawable_builder.build();
    icon = gui_helper.createDrawableItemStack(new ItemStack(EnergyBlocks.circuit_fabricator.get()));
  }

  @Override
  public RecipeType<CircuitFabricatorRecipe> getRecipeType(){
    return type;
  }

  @Override
  @Deprecated
  public ResourceLocation getUid(){
    return id;
  }

  @Override
  @Deprecated
  public Class<? extends CircuitFabricatorRecipe> getRecipeClass(){
    return CircuitFabricatorRecipe.class;
  }

  @Override
  public Component getTitle(){
    return new TranslatableComponent(EnergyBlocks.circuit_fabricator.get().getDescriptionId());
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
  public void setRecipe(IRecipeLayoutBuilder builder, CircuitFabricatorRecipe recipe, IFocusGroup focuses){
    final IRecipeSlotBuilder[] slots = {
      builder.addSlot(RecipeIngredientRole.INPUT,  8,  8),
      builder.addSlot(RecipeIngredientRole.INPUT, 26,  8),
      builder.addSlot(RecipeIngredientRole.INPUT, 44,  8),
      builder.addSlot(RecipeIngredientRole.INPUT, 62,  8),
      builder.addSlot(RecipeIngredientRole.INPUT,  8, 26),
      builder.addSlot(RecipeIngredientRole.INPUT, 26, 26),
      builder.addSlot(RecipeIngredientRole.INPUT, 44, 26),
      builder.addSlot(RecipeIngredientRole.INPUT, 62, 26)
    };
    JeiUtil.addInputSlotIngredients(recipe, slots);
    builder.addSlot(RecipeIngredientRole.OUTPUT, 114, 17).addItemStack(recipe.getResultItem());
  }

  @Override
  @Nullable
  public final ResourceLocation getRegistryName(final CircuitFabricatorRecipe recipe){
    return recipe.getId();
  }

}
