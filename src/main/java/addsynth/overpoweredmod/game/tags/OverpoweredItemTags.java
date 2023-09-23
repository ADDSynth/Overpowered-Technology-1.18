package addsynth.overpoweredmod.game.tags;

import addsynth.overpoweredmod.OverpoweredTechnology;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class OverpoweredItemTags {

  public static final TagKey<Item> advanced_ore_refinery = create("advanced_ore_refinery_recipes");
  /** An Item Tag that contains all of the 8 different types of gems.
   *  Since this is a tag, it will automatically be updated when resources are reloaded. */
  public static final TagKey<Item> convertable_gems      = create("convertable_gems");
  public static final TagKey<Item> portal_fuel           = create("portal_fuel");

  private static final TagKey<Item> create(final String name){
    return ItemTags.create(new ResourceLocation(OverpoweredTechnology.MOD_ID, name));
  }

}
