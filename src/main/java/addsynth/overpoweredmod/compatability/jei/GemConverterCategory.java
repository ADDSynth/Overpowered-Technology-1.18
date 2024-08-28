package addsynth.overpoweredmod.compatability.jei;

import addsynth.overpoweredmod.game.reference.GuiReference;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.game.tags.OverpoweredItemTags;
import addsynth.overpoweredmod.machines.gem_converter.GemConverterRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;

public final class GemConverterCategory implements IRecipeCategory<GemConverterRecipe> {

  private static final ResourceLocation id = Names.GEM_CONVERTER;
  public static final RecipeType<GemConverterRecipe> type = new RecipeType<>(id, GemConverterRecipe.class);
  private final IDrawable background;
  private final IDrawable icon;

  public GemConverterCategory(final IGuiHelper gui_helper){
    background = gui_helper.createDrawable(GuiReference.jei_recipe_background, 18, 16, 74, 18);
    icon = gui_helper.createDrawableItemStack(new ItemStack(OverpoweredBlocks.gem_converter.get()));
  }

  public static RecipeType<GemConverterRecipe> getType(){
    return type;
  }

  @Override
  @Deprecated
  public ResourceLocation getUid(){
    return id;
  }

  @Override
  @Deprecated
  public Class<? extends GemConverterRecipe> getRecipeClass(){
    return GemConverterRecipe.class;
  }

  @Override
  public Component getTitle(){
    return new TranslatableComponent(OverpoweredBlocks.gem_converter.get().getDescriptionId());
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
  public void setRecipe(IRecipeLayoutBuilder builder, GemConverterRecipe recipe, IFocusGroup focuses){
    builder.addSlot(RecipeIngredientRole.INPUT,   0, 0).addIngredients(Ingredient.of(OverpoweredItemTags.convertable_gems));
    builder.addSlot(RecipeIngredientRole.OUTPUT, 56, 0).addItemStack(recipe.result);
  }

  @Override
  @Nullable
  public final ResourceLocation getRegistryName(final GemConverterRecipe recipe){
    return recipe.getId();
  }

}
