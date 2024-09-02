package addsynth.overpoweredmod.game.core;

import addsynth.core.game.registry.RegistryUtil;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.machines.magic_infuser.recipes.MagicInfuserRecipe;
import net.minecraft.world.item.crafting.RecipeType;

public final class RecipeTypes {

  public static final RecipeType<MagicInfuserRecipe> MAGIC_INFUSER = RegistryUtil.createRecipeType(Names.MAGIC_INFUSER);

}
