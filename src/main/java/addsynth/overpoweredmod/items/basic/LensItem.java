package addsynth.overpoweredmod.items.basic;

import addsynth.core.game.item.ItemUtil;
import addsynth.overpoweredmod.game.core.DeviceColor;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import addsynth.overpoweredmod.items.register.OverpoweredItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class LensItem extends OverpoweredItem {

  public final int index;
  private final ChatFormatting color_code;
  // https://minecraft.gamepedia.com/Formatting_codes

  public LensItem(final DeviceColor color){
    super(color.lens_name);
    this.index = color.index;
    color_code = color.format_code;
  }

  /** Gets a Lens Item of the specified color index. */
  public static final Item get(final int index){
    return switch(index){
      case 0 -> OverpoweredItems.focus_lens.get();
      case 1 -> OverpoweredItems.red_lens.get();
      case 2 -> OverpoweredItems.orange_lens.get();
      case 3 -> OverpoweredItems.yellow_lens.get();
      case 4 -> OverpoweredItems.green_lens.get();
      case 5 -> OverpoweredItems.cyan_lens.get();
      case 6 -> OverpoweredItems.blue_lens.get();
      case 7 -> OverpoweredItems.magenta_lens.get();
      default -> null;
    };
  }

  /** Determines the color index from the specified ItemStack that contains a Lens Item. */
  public static final int get_index(final ItemStack stack){
    if(ItemUtil.itemStackExists(stack)){
      final Item item = stack.getItem();
      if(item instanceof LensItem){
        return ((LensItem)item).index;
      }
    }
    return -1;
  }
  
  @Override
  public Component getName(final ItemStack stack){
    return ((MutableComponent)super.getName(stack)).withStyle(color_code);
  }

}
