package addsynth.material.types.gem;

import addsynth.core.game.RegistryUtil;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.blocks.GemBlock;
import addsynth.material.blocks.OreBlock;
import addsynth.material.items.MaterialItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

/** This is a standard Gem material. It has a gem item, storage block, ore block, and a gem shard. */
public final class CustomGem extends Gem {

  private final MaterialColor color;
  private final int min_experience;
  private final int max_experience;

  private final RegistryObject<Item> gem;
  private final RegistryObject<Block> block;
  private final RegistryObject<Block> ore;

  public CustomGem(final String name, final MaterialColor color, final int min_experience, final int max_experience){
    super(name);
    this.color = color;
    this.min_experience = min_experience;
    this.max_experience = max_experience;
      gem = RegistryObject.create(new ResourceLocation(ADDSynthMaterials.MOD_ID, name),          ForgeRegistries.ITEMS);
    block = RegistryObject.create(new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_block"), ForgeRegistries.BLOCKS);
      ore = RegistryObject.create(new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_ore"),   ForgeRegistries.BLOCKS);
  }
  
  public final void registerBlocks(final IForgeRegistry<Block> game){
    game.register(new GemBlock(block.getId(), color));
    game.register(new OreBlock(ore.getId(), min_experience, max_experience));
  }
  
  public final void registerItems(final IForgeRegistry<Item> game){
    game.register(new MaterialItem(gem.getId()));
    game.register(RegistryUtil.createItemBlock(block, ADDSynthMaterials.creative_tab));
    game.register(RegistryUtil.createItemBlock(ore,   ADDSynthMaterials.creative_tab));
    game.register(new MaterialItem(shard.getId())); // REMOVE shards
  }

  @Override
  public final Item getGem(){
    return gem.get();
  }
  
  public final Block getBlock(){
    return block.get();
  }
  
  @Override
  public final Block getOre(){
    return ore.get();
  }
  
}
