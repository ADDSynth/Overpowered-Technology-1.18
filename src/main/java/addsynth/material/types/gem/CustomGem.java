package addsynth.material.types.gem;

import addsynth.core.game.RegistryUtil;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.MaterialItem;
import addsynth.material.blocks.GemBlock;
import addsynth.material.blocks.OreBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

/** This is a standard Gem material. It has a gem item, storage block, ore block, and a gem shard. */
public final class CustomGem extends Gem {

  private final MaterialColor color;
  private final int min_experience;
  private final int max_experience;

  private final ResourceLocation gem_name;
  private final ResourceLocation block_name;
  private final ResourceLocation ore_name;

  public CustomGem(final String name, final MaterialColor color, final int min_experience, final int max_experience){
    super(name);
    this.color = color;
    this.min_experience = min_experience;
    this.max_experience = max_experience;
      gem_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name);
    block_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_block");
      ore_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_ore");
  }
  
  public final void registerBlocks(final IForgeRegistry<Block> game){
    game.register(new GemBlock(block_name, color));
    game.register(new OreBlock(ore_name, min_experience, max_experience));
  }
  
  public final void registerItems(final IForgeRegistry<Item> game){
    game.register(new MaterialItem(gem_name));
    game.register(RegistryUtil.create_ItemBlock(getBlock(), ADDSynthMaterials.creative_tab, block_name));
    game.register(RegistryUtil.create_ItemBlock(getOre(),   ADDSynthMaterials.creative_tab, ore_name));
    game.register(new MaterialItem(shard_name)); // REMOVE shards
  }

  @Override
  public final Item getGem(){
    return ForgeRegistries.ITEMS.getValue(gem_name);
  }
  
  public final Block getBlock(){
    return ForgeRegistries.BLOCKS.getValue(block_name);
  }
  
  @Override
  public final Block getOre(){
    return ForgeRegistries.BLOCKS.getValue(ore_name);
  }
  
}
