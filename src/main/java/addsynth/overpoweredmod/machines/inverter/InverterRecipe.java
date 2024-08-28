package addsynth.overpoweredmod.machines.inverter;

import java.util.ArrayList;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public final class InverterRecipe {

  public final ItemStack input;
  public final ItemStack result;
  
  private InverterRecipe(final Item input, final Item output){
    this.input = new ItemStack(input, 1);
    this.result = new ItemStack(output, 1);
  }

  public static final ArrayList<InverterRecipe> get_recipes(){
    final ArrayList<InverterRecipe> list = new ArrayList<>(2);
    final Item energy_crystal = OverpoweredItems.energy_crystal.get();
    final Item   void_crystal = OverpoweredItems.void_crystal.get();
    list.add(new InverterRecipe(energy_crystal, void_crystal));
    list.add(new InverterRecipe(void_crystal, energy_crystal));
    return list;
  }

  public final ResourceLocation getId(){
    return ForgeRegistries.ITEMS.getKey(result.getItem());
  }

}
