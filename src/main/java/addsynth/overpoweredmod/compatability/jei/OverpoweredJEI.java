package addsynth.overpoweredmod.compatability.jei;

import java.util.ArrayList;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.compatability.CompatabilityManager;
import addsynth.overpoweredmod.game.core.Laser;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public final class OverpoweredJEI implements IModPlugin {

// https://github.com/micdoodle8/Galacticraft/blob/MC1.10/src/main/java/micdoodle8/mods/galacticraft/core/client/jei/GalacticraftJEI.java#L90

  @Override
  public void onRuntimeAvailable(IJeiRuntime jeiRuntime){
    final ArrayList<ItemStack> blacklist = new ArrayList<>(1);
    blacklist.add(new ItemStack(OverpoweredItems.portal_image));
    if(CompatabilityManager.are_rings_enabled()){
      if(OverpoweredItems.magic_ring_0.isPresent()){
        blacklist.add(new ItemStack(OverpoweredItems.magic_ring_0.get()));
      }
      if(OverpoweredItems.magic_ring_1.isPresent()){
        blacklist.add(new ItemStack(OverpoweredItems.magic_ring_1.get()));
      }
      if(OverpoweredItems.magic_ring_2.isPresent()){
        blacklist.add(new ItemStack(OverpoweredItems.magic_ring_2.get()));
      }
      if(OverpoweredItems.magic_ring_3.isPresent()){
        blacklist.add(new ItemStack(OverpoweredItems.magic_ring_3.get()));
      }
    }
    blacklist.add(new ItemStack(OverpoweredItems.bridge_image));
    jeiRuntime.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, blacklist);
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
    registration.addRecipes(GemConverterCategory.type, GemConverterRecipe.recipes);
    registration.addRecipes(AdvancedOreRefineryCategory.type, OreRefineryRecipes.recipes);
    registration.addRecipes(InverterCategory.type, InverterRecipe.get_recipes());
    registration.addRecipes(MagicInfuserCategory.type, MagicInfuserRecipes.recipes);
    add_information(registration);
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){
    registration.addRecipeCatalyst(new ItemStack(OverpoweredBlocks.gem_converter), GemConverterCategory.type);
    registration.addRecipeCatalyst(new ItemStack(OverpoweredBlocks.advanced_ore_refinery), AdvancedOreRefineryCategory.type);
    registration.addRecipeCatalyst(new ItemStack(OverpoweredBlocks.inverter), InverterCategory.type);
    registration.addRecipeCatalyst(new ItemStack(OverpoweredBlocks.magic_infuser), MagicInfuserCategory.type);
  }

  private static final void add_information(IRecipeRegistration registry){
    // Celestial Gem, Energy Crystal, Void Crystal
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.celestial_gem),         VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.celestial_gem"));
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.energy_crystal_shards), VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.energy_crystal_shards"));
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.energy_crystal),        VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.energy_crystal"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.light_block),          VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.light_block"));
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.void_crystal),          VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.void_crystal"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.null_block),           VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.null_block"));
    
    // Celestial Tools
    final ArrayList<ItemStack> celestial_tools = new ArrayList<>(6);
    celestial_tools.add(new ItemStack(OverpoweredItems.celestial_sword));
    celestial_tools.add(new ItemStack(OverpoweredItems.celestial_shovel));
    celestial_tools.add(new ItemStack(OverpoweredItems.celestial_axe));
    celestial_tools.add(new ItemStack(OverpoweredItems.celestial_pickaxe));
    celestial_tools.add(new ItemStack(OverpoweredItems.celestial_hoe));
    registry.addIngredientInfo(celestial_tools, VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.celestial_tools"));

    // Void Tools
    final ArrayList<ItemStack> void_tools = new ArrayList<>(5);
    void_tools.add(new ItemStack(OverpoweredItems.void_sword));
    void_tools.add(new ItemStack(OverpoweredItems.void_shovel));
    void_tools.add(new ItemStack(OverpoweredItems.void_axe));
    void_tools.add(new ItemStack(OverpoweredItems.void_pickaxe));
    void_tools.add(new ItemStack(OverpoweredItems.void_hoe));
    registry.addIngredientInfo(void_tools, VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.void_tools"));

    // Beam Items
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.scanning_laser),            VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.scanning_laser"));
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.destructive_laser),         VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.destructive_laser"));
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.energy_stabilizer),         VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.energy_stabilizer"));
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.heavy_light_emitter),       VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.heavy_light_emitter"));
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.matter_energy_transformer), VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.matter_energy_transformer"));

    // Items
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.plasma),                  VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.plasma"));
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.matter_energy_converter), VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.matter_energy_converter"));
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.vacuum_container),        VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.vacuum_container"));
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.reinforced_container),    VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.reinforced_container"));
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.unimatter),               VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.unimatter"));
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.dimensional_anchor),      VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.dimensional_anchor"));

    // Machines 1
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.crystal_energy_extractor), VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.crystal_energy_extractor"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.data_cable),               VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.data_cable"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.gem_converter),            VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.gem_converter"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.inverter),                 VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.inverter"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.magic_infuser),            VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.magic_infuser"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.identifier),               VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.identifier"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.portal_control_panel),     VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.portal_control_panel"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.portal_frame),             VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.portal_frame"));
    
    // Lasers
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.laser_housing), VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.laser_housing"));
    final ArrayList<ItemStack> lasers = new ArrayList<>(8);
    lasers.add(new ItemStack(Laser.RED.cannon));
    lasers.add(new ItemStack(Laser.ORANGE.cannon));
    lasers.add(new ItemStack(Laser.YELLOW.cannon));
    lasers.add(new ItemStack(Laser.GREEN.cannon));
    lasers.add(new ItemStack(Laser.CYAN.cannon));
    lasers.add(new ItemStack(Laser.BLUE.cannon));
    lasers.add(new ItemStack(Laser.MAGENTA.cannon));
    lasers.add(new ItemStack(Laser.WHITE.cannon));
    registry.addIngredientInfo(lasers, VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.laser"));
    
    // Machines 2
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.energy_suspension_bridge), VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.energy_suspension_bridge"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.advanced_ore_refinery),    VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.advanced_ore_refinery"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.plasma_generator),         VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.plasma_generator"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.matter_compressor),        VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.matter_compressor"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.crystal_matter_generator), VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.crystal_matter_generator"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.black_hole),               VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.black_hole"));
    
    // Fusion Machines
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.fusion_chamber),       VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.fusion_chamber"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.fusion_control_unit),  VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.fusion_control_unit"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.fusion_control_laser), VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.fusion_control_laser"));
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.fusion_converter),     VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.overpowered.jei_description.fusion_converter"));
  }

}
