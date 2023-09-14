package addsynth.material.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/** @see net.minecraftforge.common.Tags.Items
 *  @see net.minecraft.tags.ItemTags */
public final class MaterialTag {

  public static class RUBY {
    public static final TagKey<Item>   ORES = ItemTags.create(new ResourceLocation("forge:ores/ruby"));
    public static final TagKey<Item>   GEMS = ItemTags.create(new ResourceLocation("forge:gems/ruby"));
    public static final TagKey<Item> BLOCKS = ItemTags.create(new ResourceLocation("forge:storage_blocks/ruby"));
  }

  public static class TOPAZ {
    public static final TagKey<Item>   ORES = ItemTags.create(new ResourceLocation("forge:ores/topaz"));
    public static final TagKey<Item>   GEMS = ItemTags.create(new ResourceLocation("forge:gems/topaz"));
    public static final TagKey<Item> BLOCKS = ItemTags.create(new ResourceLocation("forge:storage_blocks/topaz"));
  }
  
  public static class CITRINE {
    public static final TagKey<Item>   ORES = ItemTags.create(new ResourceLocation("forge:ores/citrine"));
    public static final TagKey<Item>   GEMS = ItemTags.create(new ResourceLocation("forge:gems/citrine"));
    public static final TagKey<Item> BLOCKS = ItemTags.create(new ResourceLocation("forge:storage_blocks/citrine"));
  }
  
  public static class SAPPHIRE {
    public static final TagKey<Item>   ORES = ItemTags.create(new ResourceLocation("forge:ores/sapphire"));
    public static final TagKey<Item>   GEMS = ItemTags.create(new ResourceLocation("forge:gems/sapphire"));
    public static final TagKey<Item> BLOCKS = ItemTags.create(new ResourceLocation("forge:storage_blocks/sapphire"));
  }
  
  public static class AMETHYST {
    public static final TagKey<Item>   ORES = ItemTags.create(new ResourceLocation("forge:ores/amethyst"));
  }
  
  public static class TIN {
    public static final TagKey<Item>   ORES = ItemTags.create(new ResourceLocation("forge:ores/tin"));
    public static final TagKey<Item> INGOTS = ItemTags.create(new ResourceLocation("forge:ingots/tin"));
    public static final TagKey<Item> BLOCKS = ItemTags.create(new ResourceLocation("forge:storage_blocks/tin"));
  }
  
  public static class ALUMINUM {
    public static final TagKey<Item>   ORES = ItemTags.create(new ResourceLocation("forge:ores/aluminum"));
    public static final TagKey<Item> INGOTS = ItemTags.create(new ResourceLocation("forge:ingots/aluminum"));
    public static final TagKey<Item> BLOCKS = ItemTags.create(new ResourceLocation("forge:storage_blocks/aluminum"));
  }
  
  public static class STEEL {
    public static final TagKey<Item> INGOTS = ItemTags.create(new ResourceLocation("forge:ingots/steel"));
    public static final TagKey<Item> BLOCKS = ItemTags.create(new ResourceLocation("forge:storage_blocks/steel"));
  }
  
  public static class BRONZE {
    public static final TagKey<Item> INGOTS = ItemTags.create(new ResourceLocation("forge:ingots/bronze"));
    public static final TagKey<Item> BLOCKS = ItemTags.create(new ResourceLocation("forge:storage_blocks/bronze"));
  }
  
  public static class SILVER {
    public static final TagKey<Item>   ORES = ItemTags.create(new ResourceLocation("forge:ores/silver"));
    public static final TagKey<Item> INGOTS = ItemTags.create(new ResourceLocation("forge:ingots/silver"));
    public static final TagKey<Item> BLOCKS = ItemTags.create(new ResourceLocation("forge:storage_blocks/silver"));
  }
  
  public static class PLATINUM {
    public static final TagKey<Item>   ORES = ItemTags.create(new ResourceLocation("forge:ores/platinum"));
    public static final TagKey<Item> INGOTS = ItemTags.create(new ResourceLocation("forge:ingots/platinum"));
    public static final TagKey<Item> BLOCKS = ItemTags.create(new ResourceLocation("forge:storage_blocks/platinum"));
  }
  
  public static class TITANIUM {
    public static final TagKey<Item>   ORES = ItemTags.create(new ResourceLocation("forge:ores/titanium"));
    public static final TagKey<Item> INGOTS = ItemTags.create(new ResourceLocation("forge:ingots/titanium"));
    public static final TagKey<Item> BLOCKS = ItemTags.create(new ResourceLocation("forge:storage_blocks/titanium"));
  }

}
