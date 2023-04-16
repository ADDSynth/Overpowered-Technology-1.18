package addsynth.material.types.basic;

import addsynth.core.game.RegistryUtil;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.MaterialItem;
import addsynth.material.blocks.OreBlock;
import addsynth.material.types.AbstractMaterial;
import addsynth.material.types.OreMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

/** This is a material that just has an item and an ore block. No storage block. */
public final class SimpleOreMaterial extends AbstractMaterial implements OreMaterial {

  private final MaterialColor color;
  private final int min_experience;
  private final int max_experience;

  private final ResourceLocation item_name;
  private final ResourceLocation ore_name;
  
  public SimpleOreMaterial(final String name, final MaterialColor color){
    this(name, color, 0, 0);
  }
  
  public SimpleOreMaterial(final String name, final MaterialColor color, final int min_experience, final int max_experience){
    super(name);
    this.color = color;
     item_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name);
      ore_name = new ResourceLocation(ADDSynthMaterials.MOD_ID, name+"_ore");
    this.min_experience = min_experience;
    this.max_experience = max_experience;
  }
  
  public final void registerBlocks(final IForgeRegistry<Block> game){
    game.register(new OreBlock(ore_name, min_experience, max_experience));
  }
  
  public final void registerItems(final IForgeRegistry<Item> game){
    game.register(new MaterialItem(item_name));
    game.register(RegistryUtil.create_ItemBlock(getOre(), ADDSynthMaterials.creative_tab, ore_name));
  }

  public final Item getItem(){
    return ForgeRegistries.ITEMS.getValue(item_name);
  }
  
  @Override
  public final Block getOre(){
    return ForgeRegistries.BLOCKS.getValue(ore_name);
  }

}
