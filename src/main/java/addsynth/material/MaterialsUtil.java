package addsynth.material;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

@EventBusSubscriber(modid = ADDSynthCore.MOD_ID, bus = Bus.FORGE)
public final class MaterialsUtil {

  // FUTURE: the responders field, registerResponder method, and dispatchEvent method that you see here is
  //         exactly the same as that in the RecipeUtil class. Once we update to Java 9 it is likely that
  //         you can move this common code to an interface and use private access methods, new in Java 9.
  private static final ArrayList<Runnable> responders = new ArrayList<>();

  public static final void registerResponder(final Runnable executable){
    if(responders.contains(executable)){
      ADDSynthCore.log.warn("That function is already registered as an event responder.");
      // Thread.dumpStack();
    }
    else{
      responders.add(executable);
    }
  }

  private static final void dispatchEvent(){
    for(final Runnable responder : responders){
      responder.run();
    }
  }

  @SubscribeEvent
  public static final void reload_tags_event(final TagsUpdatedEvent event){
    // FIX Tag update events sent to the client are now run on Client thread, cannot get the server in this case. GAIN A BETTER UNDERSTANDING OF THE TagsUpdatedEvent and the RecipesUpdatedEvent!
    ADDSynthCore.log.info("Tags were Reloaded. Sending update events...");

    final RegistryAccess tag_manager = event.getTagManager();
    dispatchEvent();

    ADDSynthCore.log.info("Done responding to Tag reload.");
  }

// =======================================================================================

  public static final List<Item> getOres(){
    return ForgeRegistries.ITEMS.tags().getTag(Tags.Items.ORES).stream().toList();
  }

  public static final List<Block> getOreBlocks(){
    return ForgeRegistries.BLOCKS.tags().getTag(Tags.Blocks.ORES).stream().toList();
  }

  @Nullable
  @Deprecated
  public static final ITag<Item> getTag(final ResourceLocation tag_id){
    return getTag(ItemTags.create(tag_id));
  }

  @Nullable
  public static final ITag<Item> getTag(final TagKey<Item> tag_key){
    final ITagManager<Item> manager = ForgeRegistries.ITEMS.tags();
    return manager.isKnownTagName(tag_key) ? manager.getTag(tag_key) : null;
  }

  @Nullable
  @Deprecated
  public static final List<Item> getItemCollection(final ResourceLocation tag_id){
    return getItemCollection(ItemTags.create(tag_id));
  }

  @Nullable
  public static final List<Item> getItemCollection(final TagKey<Item> tag_key){
    final ITag<Item> item_tag = getTag(tag_key);
    return item_tag != null ? item_tag.stream().toList() : null;
  }

  @Deprecated
  public static final Ingredient getTagIngredient(final ResourceLocation tag_id){
    final ITagManager<Item> manager = ForgeRegistries.ITEMS.tags();
    final TagKey<Item> key = ItemTags.create(tag_id);
    return manager.isKnownTagName(key) ? Ingredient.of(key) : Ingredient.EMPTY;
  }

  public static final Ingredient getTagIngredient(final TagKey<Item> tag_key){
    return Ingredient.of(tag_key);
  }

// =======================================================================================

  public static final boolean match(final Item item, final ResourceLocation item_tag){
    final Collection<Item> list = getItemCollection(item_tag);
    if(list != null){
      return list.contains(item);
    }
    return false;
  }

  public static final Item[] getFilter(final ResourceLocation ... tag_list){
    final ArrayList<Item> final_list = new ArrayList<>(100);
    Collection<Item> collection;
    for(final ResourceLocation tag : tag_list){
      collection = getItemCollection(tag);
      if(collection != null){
        final_list.addAll(collection);
        continue;
      }
      ADDSynthCore.log.error(new NullPointerException(
        MaterialsUtil.class.getName()+".getFilter() has detected a null Item Collection! Maybe one of the functions in MaterialsUtil "+
        "that retrieves all the Items in an Item Tag didn't return anything because there ARE no Items registered to that Item Tag!"
      ));
    }
    return final_list.toArray(new Item[final_list.size()]);
  }

}
