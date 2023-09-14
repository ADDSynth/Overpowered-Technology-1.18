package addsynth.overpoweredmod.machines.magic_infuser.recipes;

import addsynth.core.recipe.shapeless.AbstractRecipe;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.registers.RecipeSerializers;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

public final class MagicInfuserRecipe extends AbstractRecipe {

  public MagicInfuserRecipe(ResourceLocation id, String group, Enchantment enchantment, NonNullList<Ingredient> input){
    this(id, group, getEnchantedBook(enchantment), input);
  }

  public MagicInfuserRecipe(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> input){
    super(id, group, output, input);
  }

  public static final ItemStack getEnchantedBook(final Enchantment enchantment){
    return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, 1));
  }

  @Override
  public ItemStack getToastSymbol(){
    return new ItemStack(OverpoweredBlocks.magic_infuser.get(), 1);
  }

  @Override
  public RecipeSerializer<MagicInfuserRecipe> getSerializer(){
    return RecipeSerializers.MAGIC_INFUSER.get();
  }

  @Override
  public RecipeType<?> getType(){
    return MagicInfuserRecipes.INSTANCE.type;
  }

}
