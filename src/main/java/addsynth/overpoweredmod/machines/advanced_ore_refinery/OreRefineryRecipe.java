package addsynth.overpoweredmod.machines.advanced_ore_refinery;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public final class OreRefineryRecipe {

  public final Item input;
  public final ItemStack itemStack;
  public final ItemStack output;

  public OreRefineryRecipe(final Item input, final ItemStack output){
    this.input = input;
    this.itemStack = new ItemStack(input, 1);
    this.output = output;
  }
  
  public final ResourceLocation getId(){
    return ForgeRegistries.ITEMS.getKey(output.getItem());
  }
  
}
