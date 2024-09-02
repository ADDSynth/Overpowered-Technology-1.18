package addsynth.core.game.registry;

import javax.annotation.Nonnull;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.RegistryObject;

// Remember: The order an item shows up in the creative tab depends on
//             the order you register Items in the Item Registry Event.
/** Starting in Minecraft version 1.18, Forge now requires Registry Objects,
 *  such as Blocks, Items, BlockEntity Types, Container Types, and Recipe
 *  Serializers, to be newly created in the Registration Events. */
public final class RegistryUtil {

  public static final Item createItem(final ResourceLocation name){
    final Item item = new Item(new Item.Properties());
    item.setRegistryName(name);
    return item;
  }

  public static final Item createItem(final ResourceLocation name, final CreativeModeTab tab){
    final Item item = new Item(new Item.Properties().tab(tab));
    item.setRegistryName(name);
    return item;
  }
  
  public static final Item createItem(final ResourceLocation name, final Item.Properties properties){
    final Item item = new Item(properties);
    item.setRegistryName(name);
    return item;
  }
  
  public static final BlockItem createItemBlock(final RegistryObject<Block> block, final CreativeModeTab tab){
    final BlockItem item = new BlockItem(block.get(), new Item.Properties().tab(tab));
    item.setRegistryName(block.getId());
    return item;
  }

  public static final <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(final BlockEntityType.BlockEntitySupplier<T> supplier, final RegistryObject<Block> block){
    final BlockEntityType<T> type = BlockEntityType.Builder.of(supplier, block.get()).build(null);
    type.setRegistryName(block.getId());
    return type;
  }

  public static final <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(final BlockEntityType.BlockEntitySupplier<T> supplier, final ResourceLocation name, Block ... validBlocks){
    final BlockEntityType<T> type = BlockEntityType.Builder.of(supplier, validBlocks).build(null);
    type.setRegistryName(name);
    return type;
  }

  public static final <C extends AbstractContainerMenu> MenuType<C> createContainerType(final IContainerFactory<C> factory, final ResourceLocation name){
    final MenuType<C> menu_type = new MenuType<C>(factory);
    menu_type.setRegistryName(name);
    return menu_type;
  }

  public static final SoundEvent createNewSound(final ResourceLocation name){
    final SoundEvent sound = new SoundEvent(name);
    sound.setRegistryName(name);
    return sound;
  }

  public static final <T extends Recipe<?>> RecipeType<T> createRecipeType(final ResourceLocation name){
    return Registry.register(Registry.RECIPE_TYPE, name,
      new RecipeType<T>() {
        @Override
        public final String toString(){
          return name.getPath();
        }
      }
    );
  }

//============================================================================================================

  /** @deprecated Use <code>Item.BY_BLOCK.get(block)</code> or <code>block.asItem()</code> Instead. */
  public static final BlockItem getItemBlock(final RegistryObject<Block> block){
    return getItemBlock(block.get());
  }

  /** @deprecated Use <code>Item.BY_BLOCK.get(block)</code> or <code>block.asItem()</code> Instead. */
  public static final BlockItem getItemBlock(@Nonnull final Block block){
    return (BlockItem)Item.BY_BLOCK.get(block);
  }

}
