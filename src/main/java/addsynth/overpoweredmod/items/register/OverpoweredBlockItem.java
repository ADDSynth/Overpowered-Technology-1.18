package addsynth.overpoweredmod.items.register;

import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public final class OverpoweredBlockItem extends BlockItem {

  public OverpoweredBlockItem(final RegistryObject<Block> block_holder){
    super(block_holder.get(), new Item.Properties().tab(CreativeTabs.creative_tab));
    setRegistryName(block_holder.getId());
  }

  public OverpoweredBlockItem(final RegistryObject<Block> block_holder, final Item.Properties properties){
    super(block_holder.get(), properties);
    setRegistryName(block_holder.getId());
  }

}
