package addsynth.overpoweredmod.items.basic;

import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.items.OverpoweredItem;
import net.minecraft.world.item.ItemStack;

public final class VoidCrystal extends OverpoweredItem {

  public VoidCrystal(){
    super(Names.VOID_CRYSTAL);
  }

  @Override
  public boolean isFoil(ItemStack stack){
    return true;
  }

}
