package addsynth.material;

import addsynth.material.compat.recipe.BronzeModAbsentCondition;
import addsynth.material.compat.recipe.SteelModAbsentCondition;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = ADDSynthMaterials.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class MaterialsRegister {

  @SubscribeEvent
  public static final void registerBlocks(final RegistryEvent.Register<Block> event){
    final IForgeRegistry<Block> game = event.getRegistry();
    
    // gems
    Material.RUBY.registerBlocks(game);
    Material.TOPAZ.registerBlocks(game);
    Material.CITRINE.registerBlocks(game);
    Material.SAPPHIRE.registerBlocks(game);
    Material.AMETHYST.registerBlocks(game);
    Material.ROSE_QUARTZ.registerBlocks(game);
    
    // metals
    Material.TIN.registerBlocks(game);
    Material.ALUMINUM.registerBlocks(game);
    Material.STEEL.registerBlocks(game);
    Material.BRONZE.registerBlocks(game);
    Material.SILVER.registerBlocks(game);
    Material.PLATINUM.registerBlocks(game);
    Material.TITANIUM.registerBlocks(game);
    
    // other materials
    Material.SILICON.registerBlocks(game);
  }
  
  @SubscribeEvent
  public static final void registerItems(final RegistryEvent.Register<Item> event){
    final IForgeRegistry<Item> game = event.getRegistry();

    // gems
    Material.RUBY.registerItems(game);
    Material.TOPAZ.registerItems(game);
    Material.CITRINE.registerItems(game);
    Material.EMERALD.registerItems(game);
    Material.DIAMOND.registerItems(game);
    Material.SAPPHIRE.registerItems(game);
    Material.AMETHYST.registerItems(game);
    Material.QUARTZ.registerItems(game);
    Material.ROSE_QUARTZ.registerItems(game);

    // vanilla metals
    Material.IRON.registerItems(game);
    Material.GOLD.registerItems(game);
    Material.COPPER.registerItems(game);

    // metals
    Material.TIN.registerItems(game);
    Material.ALUMINUM.registerItems(game);
    Material.SILVER.registerItems(game);
    Material.PLATINUM.registerItems(game);
    Material.TITANIUM.registerItems(game);

    // manufactured metals
    Material.STEEL.registerItems(game); // Now that I have the MaterialsCompat.SteelModAbsent() function, I could prevent registering Steel if I wanted to.
    Material.BRONZE.registerItems(game);
    
    // other material items
    Material.SILICON.registerItems(game);
  }

  @SubscribeEvent
  public static final void registerRecipeSerializers(final RegistryEvent.Register<RecipeSerializer<?>> event){
    CraftingHelper.register(SteelModAbsentCondition.Serializer.INSTANCE);
    CraftingHelper.register(BronzeModAbsentCondition.Serializer.INSTANCE);
  }

}
