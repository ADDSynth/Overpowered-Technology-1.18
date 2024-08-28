package addsynth.overpoweredmod.compatability.jei;

import java.util.ArrayList;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.core.Laser;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import addsynth.overpoweredmod.game.reference.TextReference;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.OreRefineryRecipes;
import addsynth.overpoweredmod.machines.gem_converter.GemConverterRecipe;
import addsynth.overpoweredmod.machines.inverter.InverterRecipe;
import addsynth.overpoweredmod.machines.magic_infuser.recipes.MagicInfuserRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public final class OverpoweredJEI implements IModPlugin {

// https://github.com/micdoodle8/Galacticraft/blob/MC1.10/src/main/java/micdoodle8/mods/galacticraft/core/client/jei/GalacticraftJEI.java#L90

  @Override
  public void onRuntimeAvailable(IJeiRuntime jeiRuntime){
    // JEI builds the Ingredient list from items in the Creative Inventory.
    // See: mezz.jei.common.plugins.vanilla.ingredients.item.ItemStackListFactory.create()
    // Since the items we don't want players to access are not in the Creative Inventory,
    // there's no need to remove them from the JEI Ingredient list.
  }

  @Override
  public ResourceLocation getPluginUid(){
    return new ResourceLocation(OverpoweredTechnology.MOD_ID, "jei_plugin");
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration){
    final IJeiHelpers jei_helpers = registration.getJeiHelpers();
    final IGuiHelper gui_helper = jei_helpers.getGuiHelper();
    registration.addRecipeCategories(
      new GemConverterCategory(gui_helper),
      new AdvancedOreRefineryCategory(gui_helper),
      new InverterCategory(gui_helper),
      new MagicInfuserCategory(gui_helper)
    );
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration){
    registration.addRecipes(GemConverterCategory.type, GemConverterRecipe.getRecipes());
    registration.addRecipes(AdvancedOreRefineryCategory.type, OreRefineryRecipes.get_recipes());
    registration.addRecipes(InverterCategory.type, InverterRecipe.get_recipes());
    registration.addRecipes(MagicInfuserCategory.type, MagicInfuserRecipes.INSTANCE.getRecipes());
    add_information(registration);
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){
    registration.addRecipeCatalyst(new ItemStack(OverpoweredBlocks.gem_converter.get()), GemConverterCategory.type);
    registration.addRecipeCatalyst(new ItemStack(OverpoweredBlocks.advanced_ore_refinery.get()), AdvancedOreRefineryCategory.type);
    registration.addRecipeCatalyst(new ItemStack(OverpoweredBlocks.inverter.get()), InverterCategory.type);
    registration.addRecipeCatalyst(new ItemStack(OverpoweredBlocks.magic_infuser.get()), MagicInfuserCategory.type);
  }

  private static final void add_information(IRecipeRegistration registry){
    // Celestial Gem, Energy Crystal, Void Crystal
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.celestial_gem.get()),         VanillaTypes.ITEM_STACK, TextReference.celestial_gem_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.energy_crystal_shards.get()), VanillaTypes.ITEM_STACK, TextReference.energy_crystal_shards_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.energy_crystal.get()),        VanillaTypes.ITEM_STACK, TextReference.energy_crystal_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.light_block.get()),          VanillaTypes.ITEM_STACK, TextReference.light_block_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.void_crystal.get()),          VanillaTypes.ITEM_STACK, TextReference.void_crystal_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.null_block.get()),           VanillaTypes.ITEM_STACK, TextReference.null_block_description);
    
    // Celestial Tools
    final ArrayList<ItemStack> celestial_tools = new ArrayList<>(6);
    celestial_tools.add(new ItemStack(OverpoweredItems.celestial_sword.get()));
    celestial_tools.add(new ItemStack(OverpoweredItems.celestial_shovel.get()));
    celestial_tools.add(new ItemStack(OverpoweredItems.celestial_axe.get()));
    celestial_tools.add(new ItemStack(OverpoweredItems.celestial_pickaxe.get()));
    celestial_tools.add(new ItemStack(OverpoweredItems.celestial_hoe.get()));
    registry.addIngredientInfo(celestial_tools, VanillaTypes.ITEM_STACK, TextReference.celestial_tools_description);

    // Void Tools
    final ArrayList<ItemStack> void_tools = new ArrayList<>(5);
    void_tools.add(new ItemStack(OverpoweredItems.void_sword.get()));
    void_tools.add(new ItemStack(OverpoweredItems.void_shovel.get()));
    void_tools.add(new ItemStack(OverpoweredItems.void_axe.get()));
    void_tools.add(new ItemStack(OverpoweredItems.void_pickaxe.get()));
    void_tools.add(new ItemStack(OverpoweredItems.void_hoe.get()));
    registry.addIngredientInfo(void_tools, VanillaTypes.ITEM_STACK, TextReference.void_tools_description);

    // Beam Items
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.scanning_laser.get()),            VanillaTypes.ITEM_STACK, TextReference.scanning_laser_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.destructive_laser.get()),         VanillaTypes.ITEM_STACK, TextReference.destructive_laser_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.energy_stabilizer.get()),         VanillaTypes.ITEM_STACK, TextReference.energy_stabilizer_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.heavy_light_emitter.get()),       VanillaTypes.ITEM_STACK, TextReference.heavy_light_emitter_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.matter_energy_transformer.get()), VanillaTypes.ITEM_STACK, TextReference.matter_energy_transformer_description);

    // Items
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.plasma.get()),                  VanillaTypes.ITEM_STACK, TextReference.plasma_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.matter_energy_converter.get()), VanillaTypes.ITEM_STACK, TextReference.matter_energy_converter_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.vacuum_container.get()),        VanillaTypes.ITEM_STACK, TextReference.vacuum_container_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.reinforced_container.get()),    VanillaTypes.ITEM_STACK, TextReference.reinforced_container_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.dimensional_flux.get()),        VanillaTypes.ITEM_STACK, TextReference.dimensional_flux_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.unimatter.get()),               VanillaTypes.ITEM_STACK, TextReference.unimatter_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.dimensional_anchor.get()),      VanillaTypes.ITEM_STACK, TextReference.dimensional_anchor_description);

    // Machines 1
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.crystal_energy_extractor.get()), VanillaTypes.ITEM_STACK, TextReference.crystal_energy_extractor_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.data_cable.get()),               VanillaTypes.ITEM_STACK, TextReference.data_cable_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.gem_converter.get()),            VanillaTypes.ITEM_STACK, TextReference.gem_converter_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.inverter.get()),                 VanillaTypes.ITEM_STACK, TextReference.inverter_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.magic_infuser.get()),            VanillaTypes.ITEM_STACK, TextReference.magic_infuser_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.identifier.get()),               VanillaTypes.ITEM_STACK, TextReference.identifier_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.portal_control_panel.get()),     VanillaTypes.ITEM_STACK, TextReference.portal_control_panel_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.portal_frame.get()),             VanillaTypes.ITEM_STACK, TextReference.portal_frame_description);
    
    // Lasers
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.laser_housing.get()), VanillaTypes.ITEM_STACK, TextReference.laser_housing_description);
    final ArrayList<ItemStack> lasers = new ArrayList<>(8);
    lasers.add(new ItemStack(Laser.RED.cannon.get()));
    lasers.add(new ItemStack(Laser.ORANGE.cannon.get()));
    lasers.add(new ItemStack(Laser.YELLOW.cannon.get()));
    lasers.add(new ItemStack(Laser.GREEN.cannon.get()));
    lasers.add(new ItemStack(Laser.CYAN.cannon.get()));
    lasers.add(new ItemStack(Laser.BLUE.cannon.get()));
    lasers.add(new ItemStack(Laser.MAGENTA.cannon.get()));
    lasers.add(new ItemStack(Laser.WHITE.cannon.get()));
    registry.addIngredientInfo(lasers, VanillaTypes.ITEM_STACK, TextReference.laser_description);
    
    // Machines 2
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.energy_suspension_bridge.get()), VanillaTypes.ITEM_STACK, TextReference.energy_suspension_bridge_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.advanced_ore_refinery.get()),    VanillaTypes.ITEM_STACK, TextReference.advanced_ore_refinery_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.plasma_generator.get()),         VanillaTypes.ITEM_STACK, TextReference.plasma_generator_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.matter_compressor.get()),        VanillaTypes.ITEM_STACK, TextReference.matter_compressor_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.crystal_matter_generator.get()), VanillaTypes.ITEM_STACK, TextReference.crystal_matter_generator_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.black_hole.get()),               VanillaTypes.ITEM_STACK, TextReference.black_hole_description);
    
    // Fusion Machines
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.fusion_chamber.get()),       VanillaTypes.ITEM_STACK, TextReference.fusion_chamber_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.fusion_control_unit.get()),  VanillaTypes.ITEM_STACK, TextReference.fusion_control_unit_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.fusion_control_laser.get()), VanillaTypes.ITEM_STACK, TextReference.fusion_control_laser_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.fusion_converter.get()),     VanillaTypes.ITEM_STACK, TextReference.fusion_converter_description);
  }

}
