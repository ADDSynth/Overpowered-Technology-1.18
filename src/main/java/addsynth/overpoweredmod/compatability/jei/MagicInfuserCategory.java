package addsynth.overpoweredmod.compatability.jei;

import addsynth.overpoweredmod.game.reference.GuiReference;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.machines.magic_infuser.recipes.MagicInfuserRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
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
  public void setIngredients(MagicInfuserRecipe recipe, IIngredients ingredients){
    ingredients.setInputIngredients(recipe.getIngredients());
    ingredients.setOutput(VanillaTypes.ITEM_STACK, recipe.getResultItem());
  }

  @Override
  public void setRecipe(IRecipeLayout recipeLayout, MagicInfuserRecipe recipe, IIngredients ingredients){
    final IGuiItemStackGroup gui_item_stacks = recipeLayout.getItemStacks();
    gui_item_stacks.init(0, true,   0, 0);
    gui_item_stacks.init(1, true,  18, 0);
    gui_item_stacks.init(2, false, 74, 0);
    gui_item_stacks.set(ingredients);
  }

}
