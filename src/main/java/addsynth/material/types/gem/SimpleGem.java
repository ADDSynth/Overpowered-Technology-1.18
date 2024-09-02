package addsynth.material.types.gem;

import addsynth.core.game.registry.RegistryUtil;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.blocks.OreBlock;
import addsynth.material.items.MaterialItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

/** Use this class for Gem materials that don't have a storage block
 *  or gem shard, but they do have a gem item and an ore block. */
public final class SimpleGem extends Gem {

  private final MaterialColor color;
  private final int min_experience;
  private final int max_experience;

  private final RegistryObject<Item> gem;
  private final RegistryObject<Block> ore;

  public SimpleGem(final String name, final MaterialColor color, final int min_experience, final int max_experience){
    super(name);
    this.color = color;
    this.min_experience = min_experience;
    this.max_experience = max_experience;
    gem = RegistryObject.create(new ResourceLocation(ADDSynthMaterials.MOD_ID, name),        ForgeRegistries.ITEMS);
    ore = RegistryObject.create(new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_ore"), ForgeRegistries.BLOCKS);
  }
  
  public final void registerBlocks(final IForgeRegistry<Block> game){
    game.register(new OreBlock(ore.getId(), min_experience, max_experience));
  }
  
  public final void registerItems(final IForgeRegistry<Item> game){
    game.register(new MaterialItem(gem.getId()));
    game.register(RegistryUtil.createItemBlock(ore, ADDSynthMaterials.creative_tab));
  }

  @Override
  public final Item getGem(){
    return gem.get();
  }
  
  @Override
  public final Block getOre(){
    return ore.get();
  }

}
