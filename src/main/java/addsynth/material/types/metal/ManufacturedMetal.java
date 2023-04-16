package addsynth.material.types.metal;

import javax.annotation.Nullable;
import addsynth.core.compat.Compatibility;
import addsynth.core.game.RegistryUtil;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.blocks.MetalBlock;
import addsynth.material.items.MaterialItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

/** Manufactured metals do not have an Ore Block. */
public final class ManufacturedMetal extends Metal {

  private final MaterialColor color;
  
  private final ResourceLocation ingot_name;
  private final ResourceLocation block_name;
  private final ResourceLocation nugget_name;
  
  public ManufacturedMetal(final String name, final MaterialColor color){
    super(name);
    this.color = color;
     ingot_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_ingot");
     block_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_block");
    nugget_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_nugget");
  }

  public final void registerBlocks(final IForgeRegistry<Block> game){
    game.register(new MetalBlock(block_name, color));
  }
  
  public final void registerItems(final IForgeRegistry<Item> game){
    game.register(new MaterialItem(ingot_name));
    game.register(RegistryUtil.create_ItemBlock(getBlock(), ADDSynthMaterials.creative_tab, block_name));
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
  @Nullable
  public final Item getNugget(){
    return null; // ForgeRegistries.ITEMS.getValue(nugget_name);
  }

}
