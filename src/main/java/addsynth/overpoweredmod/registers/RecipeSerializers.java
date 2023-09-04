package addsynth.overpoweredmod.registers;

import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.machines.magic_infuser.recipes.MagicInfuserRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class RecipeSerializers {

  public static final RegistryObject<RecipeSerializer<MagicInfuserRecipe>> MAGIC_INFUSER =
    RegistryObject.create(Names.MAGIC_INFUSER, ForgeRegistries.RECIPE_SERIALIZERS);

}
