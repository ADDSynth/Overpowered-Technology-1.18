package addsynth.overpoweredmod.machines.magic_infuser.recipes;

import javax.annotation.Nullable;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.reference.Names;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

/** This allows people to add their own Magic Infuser recipes, so long as they use a valid
  * Enchantment that is registered with another mod. They may also be able to alter the
  * existing Magic Infuser recipes as well.
  * @since Overpowered Technology version 1.5 (September 26, 2022)
  * @author ADDSynth
  */
public final class MagicInfuserRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<MagicInfuserRecipe> {

  public MagicInfuserRecipeSerializer(){
    setRegistryName(Names.MAGIC_INFUSER);
  }

  @Override
  public MagicInfuserRecipe fromJson(ResourceLocation recipeId, JsonObject json){
    
    final String group = GsonHelper.getAsString(json, "group", "");
    
    final Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));
    
    final String enchantment_string = GsonHelper.getAsString(json, "enchantment", null);
    if(enchantment_string == null){
      throw new JsonParseException("Could not read enchantment string correctly for recipe '"+recipeId.toString()+"'.");
    }
    final ResourceLocation enchantment_id = new ResourceLocation(enchantment_string);
    final Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(enchantment_id);
    if(enchantment == null){
      OverpoweredTechnology.log.error("While parsing recipe "+recipeId.toString()+", Enchantment '"+enchantment_string+"' does not exist or is not registered.");
      return null;
    }
    return new MagicInfuserRecipe(recipeId, group, enchantment, input);
  }

  @Override
  @Nullable
  public final MagicInfuserRecipe fromNetwork(final ResourceLocation recipeId, final FriendlyByteBuf buffer){
    final String group = buffer.readUtf();
    final Ingredient ingredient = Ingredient.fromNetwork(buffer);
    final String enchantment = buffer.readUtf();
    return new MagicInfuserRecipe(recipeId, group, enchantment, ingredient);
  }

  @Override
  public final void toNetwork(final FriendlyByteBuf buffer, final MagicInfuserRecipe recipe){
    buffer.writeUtf(recipe.getGroup());
    recipe.main_ingredient.toNetwork(buffer);
    buffer.writeUtf(recipe.enchantment_id.toString());
  }

}
