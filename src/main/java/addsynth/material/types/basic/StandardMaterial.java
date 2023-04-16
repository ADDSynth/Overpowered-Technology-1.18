package addsynth.material.types.basic;

import addsynth.core.game.RegistryUtil;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.MaterialItem;
import addsynth.material.blocks.GenericStorageBlock;
import addsynth.material.types.AbstractMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

/** This is a standard material. It just has an item and storage block. */
public final class StandardMaterial extends AbstractMaterial {

  private final MaterialColor color;

  private final ResourceLocation item_name;
  private final ResourceLocation block_name;
  
  public final RegistryObject<Item> item;
  public final RegistryObject<Block> block;
  
  public StandardMaterial(final String name, final MaterialColor color){
    super(name);
    this.color = color;
     item_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name);
    block_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_block");
     item = RegistryObject.create( item_name, ForgeRegistries.ITEMS);
    block = RegistryObject.create(block_name, ForgeRegistries.BLOCKS);
  }
  
  public final void registerBlocks(final IForgeRegistry<Block> game){
    game.register(new GenericStorageBlock(block_name, color));
  }
  
  public final void registerItems(final IForgeRegistry<Item> game){
    game.register(new MaterialItem(item_name));
    game.register(RegistryUtil.create_ItemBlock(block.get(), ADDSynthMaterials.creative_tab, block_name));
  }

}
