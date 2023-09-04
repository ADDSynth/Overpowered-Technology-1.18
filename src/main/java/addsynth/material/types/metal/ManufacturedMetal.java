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
import net.minecraftforge.registries.RegistryObject;

/** Manufactured metals do not have an Ore Block. */
public final class ManufacturedMetal extends Metal {

  private final MaterialColor color;
  
  private final RegistryObject<Item> ingot;
  private final RegistryObject<Block> block;
  private final RegistryObject<Item> nugget;
  
  public ManufacturedMetal(final String name, final MaterialColor color){
    super(name);
    this.color = color;
     ingot = RegistryObject.create(new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_ingot"),  ForgeRegistries.ITEMS);
     block = RegistryObject.create(new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_block"),  ForgeRegistries.BLOCKS);
    nugget = RegistryObject.create(new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_nugget"), ForgeRegistries.ITEMS);
  }

  public final void registerBlocks(final IForgeRegistry<Block> game){
    game.register(new MetalBlock(block.getId(), color));
  }
  
  public final void registerItems(final IForgeRegistry<Item> game){
    game.register(new MaterialItem(ingot.getId()));
    game.register(RegistryUtil.createItemBlock(block, ADDSynthMaterials.creative_tab));
    if(Compatibility.ADDSYNTH_ENERGY.loaded){
      game.register(new MaterialItem(plate_name));
    }
  }

  @Override
  public final Item getIngot(){
    return ingot.get();
  }
  
  @Override
  public final Block getBlock(){
    return block.get();
  }
  
  @Override
  @Nullable
  public final Item getNugget(){
    return null; // ForgeRegistries.ITEMS.getValue(nugget_name);
  }

}
