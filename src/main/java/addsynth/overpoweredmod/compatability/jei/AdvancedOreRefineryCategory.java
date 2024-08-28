package addsynth.overpoweredmod.compatability.jei;

import addsynth.overpoweredmod.game.reference.GuiReference;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.OreRefineryRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class AdvancedOreRefineryCategory  implements IRecipeCategory<OreRefineryRecipe> {

  private static final ResourceLocation id = Names.ADVANCED_ORE_REFINERY;
  public static final RecipeType<OreRefineryRecipe> type = new RecipeType<>(id, OreRefineryRecipe.class);
  private final IDrawable background;
  private final IDrawable icon;

  public AdvancedOreRefineryCategory(final IGuiHelper gui_helper){
    background = gui_helper.createDrawable(GuiReference.jei_recipe_background, 18, 16, 74, 18);
    icon = gui_helper.createDrawableItemStack(new ItemStack(OverpoweredBlocks.advanced_ore_refinery.get()));
  }

  public static RecipeType<OreRefineryRecipe> getType(){
    return type;
  }

  @Override
  @Deprecated
  public ResourceLocation getUid(){
    return id;
  }

  @Override
  @Deprecated
  public Class<? extends OreRefineryRecipe> getRecipeClass(){
    return OreRefineryRecipe.class;
  }

  @Override
  public Component getTitle(){
    return new TranslatableComponent(OverpoweredBlocks.advanced_ore_refinery.get().getDescriptionId());
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
  public void setRecipe(IRecipeLayoutBuilder builder, OreRefineryRecipe recipe, IFocusGroup focuses){
    builder.addSlot(RecipeIngredientRole.INPUT,   0, 0).addItemStack(recipe.itemStack);
    builder.addSlot(RecipeIngredientRole.OUTPUT, 56, 0).addItemStack(recipe.output);
  }

  @Override
  @Nullable
  public final ResourceLocation getRegistryName(final OreRefineryRecipe recipe){
    return recipe.getId();
  }

}
