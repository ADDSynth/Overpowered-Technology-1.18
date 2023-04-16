package addsynth.material.types.metal;

import addsynth.core.compat.Compatibility;
import addsynth.core.game.RegistryUtil;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.blocks.MetalBlock;
import addsynth.material.blocks.OreBlock;
import addsynth.material.items.MaterialItem;
import addsynth.material.types.OreMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

/** This is a standard metal material. These metals have an ingot,
 *  storage block, ore block, metal plate, and a nugget. */
public final class CustomMetal extends Metal implements OreMaterial {

  private final MaterialColor color;
  
  private final ResourceLocation ingot_name;
  private final ResourceLocation block_name;
  private final ResourceLocation ore_name;
  private final ResourceLocation nugget_name;
  
  public CustomMetal(final String name, final MaterialColor color){
    super(name);
    this.color = color;
     ingot_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_ingot");
     block_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_block");
       ore_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_ore");
    nugget_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_nugget");
  }
  
  public final void registerBlocks(final IForgeRegistry<Block> game){
    game.register(new MetalBlock(block_name, color));
    game.register(new OreBlock(ore_name));
  }
  
  public final void registerItems(final IForgeRegistry<Item> game){
    game.register(new MaterialItem(ingot_name));
    game.register(RegistryUtil.create_ItemBlock(getBlock(), ADDSynthMaterials.creative_tab, block_name));
    game.register(RegistryUtil.create_ItemBlock(getOre(),   ADDSynthMaterials.creative_tab,   ore_name));
    if(Compatibility.ADDSYNTH_ENERGY.loaded){
      game.register(new MaterialItem(plate_name));
    }
  }
  
  @Override
  public final Item getIngot(){
    return ForgeRegistries.ITEMS.getValue(ingot_name);
  }
  
  @Override
  public final Block getBlock(){
    return ForgeRegistries.BLOCKS.getValue(block_name);
  }
  
  @Override
  public final Block getOre(){
    return ForgeRegistries.BLOCKS.getValue(ore_name);
  }
  
  @Override
  public final Item getNugget(){
    return null;
  }

}
