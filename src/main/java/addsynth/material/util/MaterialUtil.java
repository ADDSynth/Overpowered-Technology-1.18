package addsynth.material.util;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

/** To use an Item tag do {@code MaterialUtil.getTag(TagKey).contains(Item)}
 *  or {@code Ingredient.of(TagKey).test(ItemStack)}.
 *  @see net.minecraftforge.common.Tags.Items
 *  @see ItemTags */
public final class MaterialUtil {

  // Stream.toList() returns an Unmodifiable List!
  public static final List<Item> getOres(){
    return ForgeRegistries.ITEMS.tags().getTag(Tags.Items.ORES).stream().toList();
  }

  // Stream.toList() returns an Unmodifiable List!
  public static final List<Block> getOreBlocks(){
    return ForgeRegistries.BLOCKS.tags().getTag(Tags.Blocks.ORES).stream().toList();
  }

  @Nullable
  public static final ITag<Item> getTag(final TagKey<Item> tag_key){
    final ITagManager<Item> manager = ForgeRegistries.ITEMS.tags();
    return manager.isKnownTagName(tag_key) ? manager.getTag(tag_key) : null;
  }

  public static final boolean match(final Item item, final TagKey<Item> tag){
    final ITagManager<Item> manager = ForgeRegistries.ITEMS.tags();
    return manager.getTag(tag).contains(item);
  }

}
