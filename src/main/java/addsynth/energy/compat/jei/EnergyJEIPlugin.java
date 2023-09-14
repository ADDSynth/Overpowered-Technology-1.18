package addsynth.energy.compat.jei;

import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.EnergyItems;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipes;
import addsynth.energy.gameplay.machines.compressor.recipe.CompressorRecipes;
import addsynth.energy.gameplay.reference.TextReference;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public final class EnergyJEIPlugin implements IModPlugin {

  public static final ResourceLocation id = new ResourceLocation(ADDSynthEnergy.MOD_ID, "jei_plugin");

  @Override
  public ResourceLocation getPluginUid(){
    return id;
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration){
    final IJeiHelpers jei_helpers = registration.getJeiHelpers();
    final IGuiHelper gui_helper = jei_helpers.getGuiHelper();
    registration.addRecipeCategories(
      new CompressorRecipeCategory(gui_helper),
      new CircuitRecipeCategory(gui_helper)
    );
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration){
    registration.addRecipes(CompressorRecipeCategory.type, CompressorRecipes.INSTANCE.getRecipes());
    registration.addRecipes(CircuitRecipeCategory.type, CircuitFabricatorRecipes.INSTANCE.getRecipes());
    add_information(registration);
  }

  private static void add_information(IRecipeRegistration registry){
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.wire.get()),               VanillaTypes.ITEM_STACK, TextReference.wire_description);
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.generator.get()),          VanillaTypes.ITEM_STACK, TextReference.generator_description);
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.energy_storage.get()),     VanillaTypes.ITEM_STACK, TextReference.energy_storage_description);
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.electric_furnace.get()),   VanillaTypes.ITEM_STACK, TextReference.electric_furnace_description);
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.compressor.get()),         VanillaTypes.ITEM_STACK, TextReference.compressor_description);
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.circuit_fabricator.get()), VanillaTypes.ITEM_STACK, TextReference.circuit_fabricator_description);
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.universal_energy_machine.get()), VanillaTypes.ITEM_STACK, TextReference.energy_interface_description);
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.energy_diagnostics_block.get()), VanillaTypes.ITEM_STACK, TextReference.energy_diagnostics_description);
    
    registry.addIngredientInfo(new ItemStack(EnergyItems.power_regulator.get()), VanillaTypes.ITEM_STACK, TextReference.power_regulator_description);
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){
    registration.addRecipeCatalyst(new ItemStack(EnergyBlocks.compressor.get()),         CompressorRecipeCategory.type);
    registration.addRecipeCatalyst(new ItemStack(EnergyBlocks.electric_furnace.get()),   RecipeTypes.SMELTING);
    registration.addRecipeCatalyst(new ItemStack(EnergyBlocks.circuit_fabricator.get()), CircuitRecipeCategory.type);
  }

}
