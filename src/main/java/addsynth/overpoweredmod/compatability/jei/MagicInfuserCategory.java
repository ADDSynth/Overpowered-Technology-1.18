package addsynth.overpoweredmod.compatability.jei;

import addsynth.overpoweredmod.game.reference.GuiReference;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.machines.magic_infuser.recipes.MagicInfuserRecipe;
import net.minecraft.core.NonNullList;
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

public final class MagicInfuserCategory implements IRecipeCategory<MagicInfuserRecipe> {

  private static final ResourceLocation id = Names.MAGIC_INFUSER;
  public static final RecipeType<MagicInfuserRecipe> type = new RecipeType<>(id, MagicInfuserRecipe.class);
  private final IDrawable background;
  private final IDrawable icon;

  public MagicInfuserCategory(final IGuiHelper gui_helper){
    background = gui_helper.createDrawable(GuiReference.jei_recipe_background, 0, 16, 92, 18);
          icon = gui_helper.createDrawableItemStack(new ItemStack(OverpoweredBlocks.magic_infuser.get()));
  }

  public static RecipeType<MagicInfuserRecipe> getType(){
    return type;
  }

  @Override
  @Deprecated
  public ResourceLocation getUid(){
    return id;
  }

  @Override
  @Deprecated
  public Class<? extends MagicInfuserRecipe> getRecipeClass(){
    return MagicInfuserRecipe.class;
  }

  @Override
  public Component getTitle(){
    return new TranslatableComponent(OverpoweredBlocks.magic_infuser.get().getDescriptionId());
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
  public void setRecipe(IRecipeLayoutBuilder builder, MagicInfuserRecipe recipe, IFocusGroup focuses){
    final NonNullList<Ingredient> ingredients = recipe.getIngredients();
    builder.addSlot(RecipeIngredientRole.INPUT,   1, 1).addIngredients(ingredients.get(0));
    builder.addSlot(RecipeIngredientRole.INPUT,  19, 1).addIngredients(ingredients.get(1));
    builder.addSlot(RecipeIngredientRole.OUTPUT, 75, 1).addItemStack(recipe.getResultItem());
  }

  @Override
  @Nullable
  public final ResourceLocation getRegistryName(final MagicInfuserRecipe recipe){
    return recipe.getId();
  }

}
