package addsynth.core.gameplay.compat;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.Core;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;

@JeiPlugin
public final class CoreJEIPlugin  implements IModPlugin {

  public static final ResourceLocation id = new ResourceLocation(ADDSynthCore.MOD_ID, "jei_plugin");

  @Override
  public ResourceLocation getPluginUid(){
    return id;
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration){
    add_information(registration);
  }

  private static final void add_information(IRecipeRegistration registry){
    registry.addIngredientInfo(new ItemStack(Core.music_box), VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.addsynthcore.jei_description.music_box"));
    registry.addIngredientInfo(new ItemStack(Core.music_sheet), VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.addsynthcore.jei_description.music_sheet"));
    // registry.addIngredientInfo(new ItemStack(Core.team_manager), VanillaTypes.ITEM_STACK, new TranslatableComponent("gui.addsynthcore.jei_description.team_manager"));
  }

}
