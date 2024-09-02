package addsynth.material.types.basic;

import addsynth.core.game.registry.RegistryUtil;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.blocks.GenericStorageBlock;
import addsynth.material.blocks.OreBlock;
import addsynth.material.items.MaterialItem;
import addsynth.material.types.AbstractMaterial;
import addsynth.material.types.OreMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

/** This is a standard material, that has an item, storage block, and ore block. */
public final class StandardOreMaterial extends AbstractMaterial implements OreMaterial {

  private final MaterialColor color;
  private final int min_experience;
  private final int max_experience;

  private final RegistryObject<Item> item;
  private final RegistryObject<Block> block;
  private final RegistryObject<Block> ore;
  
  public StandardOreMaterial(final String name, final MaterialColor color){
    this(name, color, 0, 0);
  }
  
  public StandardOreMaterial(final String name, final MaterialColor color, final int min_experience, final int max_experience){
    super(name);
    this.color = color;
     item = RegistryObject.create(new ResourceLocation(ADDSynthMaterials.MOD_ID, name),          ForgeRegistries.ITEMS);
    block = RegistryObject.create(new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_block"), ForgeRegistries.BLOCKS);
      ore = RegistryObject.create(new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_ore"),   ForgeRegistries.BLOCKS);
    this.min_experience = min_experience;
    this.max_experience = max_experience;
  }
  
  public final void registerBlocks(final IForgeRegistry<Block> game){
    game.register(new GenericStorageBlock(block.getId(), color));
    game.register(new OreBlock(ore.getId(), min_experience, max_experience));
  }
  
  public final void registerItems(final IForgeRegistry<Item> game){
    game.register(new MaterialItem(item.getId()));
    game.register(RegistryUtil.createItemBlock(block, ADDSynthMaterials.creative_tab));
    game.register(RegistryUtil.createItemBlock(ore,   ADDSynthMaterials.creative_tab));
  }

  public final Item getItem(){
    return item.get();
  }
  
  public final Block getBlock(){
    return block.get();
  }
  
  @Override
  public final Block getOre(){
    return ore.get();
  }

}
