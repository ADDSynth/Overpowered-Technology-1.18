package addsynth.overpoweredmod.machines.magic_infuser.recipes;

import javax.annotation.Nullable;
import addsynth.overpoweredmod.OverpoweredTechnology;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

  @Override
  public MagicInfuserRecipe fromJson(ResourceLocation recipeId, JsonObject json){
    
    final String group = GsonHelper.getAsString(json, "group", "");
    
    final NonNullList<Ingredient> nonnulllist = NonNullList.create();
    nonnulllist.add(Ingredient.of(new ItemStack(Items.BOOK, 1)));
    final Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));
    if(!input.isEmpty()){
      nonnulllist.add(input);
    }
    
    final String enchantment_string = GsonHelper.getAsString(json, "enchantment", null);
    if(enchantment_string == null){
      throw new JsonParseException("Could not read enchantment string correctly for recipe '"+recipeId.toString()+"'.");
    }
    final ResourceLocation enchantment_id = new ResourceLocation(enchantment_string);
    final Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(enchantment_id);
    if(enchantment == null){
      OverpoweredTechnology.log.warn("While parsing recipe "+recipeId.toString()+", Enchantment '"+enchantment_id.toString()+"' does not exist or is not registered.");
      return null;
    }
    return new MagicInfuserRecipe(recipeId, group, enchantment, nonnulllist);
  }

  @Override
  @Nullable
  public MagicInfuserRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer){
    final String group = buffer.readUtf(32767);
    final NonNullList<Ingredient> nonnulllist = NonNullList.withSize(2, Ingredient.EMPTY);
    nonnulllist.set(0, Ingredient.fromNetwork(buffer));
    nonnulllist.set(1, Ingredient.fromNetwork(buffer));
    final ItemStack result = buffer.readItem();
    return new MagicInfuserRecipe(recipeId, group, result, nonnulllist);
  }

  @Override
  public void toNetwork(FriendlyByteBuf buffer, MagicInfuserRecipe recipe){
    buffer.writeUtf(recipe.getGroup());
    final NonNullList<Ingredient> nonnulllist = recipe.getIngredients();
    nonnulllist.get(0).toNetwork(buffer);
    nonnulllist.get(1).toNetwork(buffer);
    buffer.writeItemStack(recipe.getResultItem(), false);
  }

}
