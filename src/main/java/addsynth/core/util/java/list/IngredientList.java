package addsynth.core.util.java.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

/** This class was created in order to keep an array of {@code ArrayList<Ingredient>}
 *  and get around any type safety warnings.<br>
 *  This is used in {@link addsynth.core.game.inventory.filter.MachineFilter}
 */
public final class IngredientList extends ArrayList<Ingredient> implements Predicate<ItemStack> {

  public IngredientList(){
    super();
  }
  
  public IngredientList(final int length){
    super(length);
  }
  
  public IngredientList(final Collection<Ingredient> collection){
    super(collection);
  }

  @Override
  public final boolean test(final ItemStack stack){
    for(final Ingredient ingredient : this){
      if(ingredient.test(stack)){
        return true;
      }
    }
    return false;
  }

}
