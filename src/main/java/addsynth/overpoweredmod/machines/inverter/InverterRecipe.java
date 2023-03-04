package addsynth.overpoweredmod.machines.inverter;

import java.util.ArrayList;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class InverterRecipe {

  public final ItemStack input;
  public final ItemStack result;
  
  private InverterRecipe(final Item input, final Item output){
    this.input = new ItemStack(input, 1);
    this.result = new ItemStack(output, 1);
  }

  public static final ArrayList<InverterRecipe> get_recipes(){
    final ArrayList<InverterRecipe> list = new ArrayList<>(2);
    list.add(new InverterRecipe(OverpoweredItems.energy_crystal, OverpoweredItems.void_crystal));
    list.add(new InverterRecipe(OverpoweredItems.void_crystal, OverpoweredItems.energy_crystal));
    return list;
  }

}
