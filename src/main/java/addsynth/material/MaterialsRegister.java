package addsynth.material;

import java.util.List;
import addsynth.core.game.registry.RegistryUtil;
import addsynth.material.blocks.OreBlock;
import addsynth.material.compat.recipe.BronzeModAbsentCondition;
import addsynth.material.compat.recipe.SteelModAbsentCondition;
import addsynth.material.reference.Names;
import addsynth.material.reference.MaterialBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;
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
    game.register(new OreBlock(Names.AMETHYST_ORE, 3, 7));
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
    game.register(RegistryUtil.createItemBlock(MaterialBlocks.amethyst_ore, ADDSynthMaterials.creative_tab));
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

  public static final void onMissingBlockEntries(MissingMappings<Block> event){
    final List<Mapping<Block>> missing_blocks = event.getMappings(ADDSynthMaterials.MOD_ID);
    for(Mapping<Block> map : missing_blocks){
      if(map.key.equals(Names.AMETHYST_BLOCK_LEGACY)){
        map.remap(Blocks.AMETHYST_BLOCK);
      }
    }
  }

  public static final void onMissingItemEntries(MissingMappings<Item> event){
    final List<Mapping<Item>> missing_items = event.getMappings(ADDSynthMaterials.MOD_ID);
    for(Mapping<Item> map : missing_items){
      if(map.key.equals(Names.AMETHYST_BLOCK_LEGACY)){
        map.remap(Items.AMETHYST_BLOCK);
      }
      if(map.key.equals(Names.AMETHYST_LEGACY)){
        map.remap(Items.AMETHYST_SHARD);
      }
    }
  }

}
