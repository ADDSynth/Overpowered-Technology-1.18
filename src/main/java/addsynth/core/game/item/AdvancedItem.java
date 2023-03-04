package addsynth.core.game.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

// UNUSED: AdvancedItem(ChatFormatting style)
public class AdvancedItem extends Item {

  private final ChatFormatting style;

  public AdvancedItem(final ResourceLocation name, final ChatFormatting style){
    super(new Item.Properties());
    this.style = style;
    setRegistryName(name);
  }
  
  public AdvancedItem(final ResourceLocation name, final ChatFormatting style, final CreativeModeTab tab){
    super(new Item.Properties().tab(tab));
    this.style = style;
    setRegistryName(name);
  }
  
  public AdvancedItem(final ResourceLocation name, final ChatFormatting style, final Item.Properties properties){
    super(properties);
    this.style = style;
    setRegistryName(name);
  }
  
  @Override
  public Component getName(ItemStack stack){
    return ((MutableComponent)super.getName(stack)).withStyle(style);
  }

}
