package addsynth.material.types.basic;

import addsynth.core.game.RegistryUtil;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.MaterialItem;
import addsynth.material.blocks.GenericStorageBlock;
import addsynth.material.blocks.OreBlock;
import addsynth.material.types.AbstractMaterial;
import addsynth.material.types.OreMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

/** This is a standard material, that has an item, storage block, and ore block. */
public final class StandardOreMaterial extends AbstractMaterial implements OreMaterial {

  private final MaterialColor color;
  private final int min_experience;
  private final int max_experience;

  private final ResourceLocation item_name;
  private final ResourceLocation block_name;
  private final ResourceLocation ore_name;
  
  public StandardOreMaterial(final String name, final MaterialColor color){
    this(name, color, 0, 0);
  }
  
  public StandardOreMaterial(final String name, final MaterialColor color, final int min_experience, final int max_experience){
    super(name);
    this.color = color;
     item_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name);
    block_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_block");
      ore_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_ore");
    this.min_experience = min_experience;
    this.max_experience = max_experience;
  }
  
  public final void registerBlocks(final IForgeRegistry<Block> game){
    game.register(new GenericStorageBlock(block_name, color));
    game.register(new OreBlock(ore_name, min_experience, max_experience));
  }
  
  public final void registerItems(final IForgeRegistry<Item> game){
    game.register(new MaterialItem(item_name));
    game.register(RegistryUtil.create_ItemBlock(getBlock(), ADDSynthMaterials.creative_tab, block_name));
    game.register(RegistryUtil.create_ItemBlock(getOre(),   ADDSynthMaterials.creative_tab, ore_name));
  }

  public final Item getItem(){
    return ForgeRegistries.ITEMS.getValue(item_name);
  }
  
  public final Block getBlock(){
    return ForgeRegistries.BLOCKS.getValue(block_name);
  }
  
  @Override
  public final Block getOre(){
    return ForgeRegistries.BLOCKS.getValue(ore_name);
  }

}
