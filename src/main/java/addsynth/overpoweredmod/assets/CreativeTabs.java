package addsynth.overpoweredmod.assets;

import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

// Creative Tab is kept in a seperate class because there were multiple creative tabs
// at one point in the past. Just leave it as-is because it doesn't affect anything,
// and doesn't give that much performance benefit if moved into an existing class.
// Only move it if we need to rewrite the whole mod.
public final class CreativeTabs {

  public static final CreativeModeTab creative_tab = new CreativeModeTab("overpowered")
  {
    @Override
    public final ItemStack makeIcon(){
      return new ItemStack(OverpoweredBlocks.inverter.get(), 1);
    }
  };

}
