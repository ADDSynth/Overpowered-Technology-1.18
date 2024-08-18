package addsynth.material;

import addsynth.material.types.basic.*;
import addsynth.material.types.gem.*;
import addsynth.material.types.metal.*;
import addsynth.material.util.MaterialTag;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MaterialColor;

/**
 *  <p>Please try to avoid using these! You should be using {@link MaterialTag Tags}
 *  as much as possible, especially if you're checking for input.
 * 
 *  <p>These materials are based on their real-world counterparts, and thus will adhere to
 *  certain rules and properties as best as possible.
 */
public final class Material {

  // vanilla
  public static final VanillaMaterial COAL         = new VanillaMaterial("coal",     Items.COAL,           Blocks.COAL_BLOCK,     Blocks.COAL_ORE);
  public static final VanillaMetal    IRON         = new VanillaMetal(   "iron",     Items.IRON_INGOT,     Blocks.IRON_BLOCK,     Blocks.IRON_ORE,   Items.IRON_NUGGET);
  public static final VanillaMetal    COPPER       = new VanillaMetal(   "copper",   Items.COPPER_INGOT,   Blocks.COPPER_BLOCK,   Blocks.COPPER_ORE, null);
  public static final VanillaMetal    GOLD         = new VanillaMetal(   "gold",     Items.GOLD_INGOT,     Blocks.GOLD_BLOCK,     Blocks.GOLD_ORE,   Items.GOLD_NUGGET);
  public static final VanillaGem      LAPIS_LAZULI = new VanillaGem(     "lapis",    Items.LAPIS_LAZULI,   Blocks.LAPIS_BLOCK,    Blocks.LAPIS_ORE); // OreDictionary Name
  public static final VanillaMaterial REDSTONE     = new VanillaMaterial("redstone", Items.REDSTONE,       Blocks.REDSTONE_BLOCK, Blocks.REDSTONE_ORE);
  public static final VanillaGem      DIAMOND      = new VanillaGem(     "diamond",  Items.DIAMOND,        Blocks.DIAMOND_BLOCK,  Blocks.DIAMOND_ORE);
  public static final VanillaGem      EMERALD      = new VanillaGem(     "emerald",  Items.EMERALD,        Blocks.EMERALD_BLOCK,  Blocks.EMERALD_ORE);
  public static final VanillaGem      QUARTZ       = new VanillaGem(     "quartz",   Items.QUARTZ,         Blocks.QUARTZ_BLOCK,   Blocks.NETHER_QUARTZ_ORE);
  public static final VanillaGem      AMETHYST     = new VanillaGem(     "amethyst", Items.AMETHYST_SHARD, Blocks.AMETHYST_BLOCK, null);

  // New in MC 1.18: Game will crash if you create Blocks and Items, But don't register them! Because in the
  //    class constructors, they create Intrusive Holders, using Registry.[CLASS].createInstrusiveHolder(this).

  // gems
  // public static final CustomGem AMBER       = new CustomGem("amber",       MaterialColor.COLOR_ORANGE,  3, 7);
  public static final CustomGem CITRINE     = new CustomGem("citrine",     MaterialColor.COLOR_YELLOW,  3, 7);
  // public static final CustomGem MALACHITE   = new CustomGem("malachite",   MaterialColor.DIAMOND,       3, 7);
  // public static final CustomGem PERIDOT     = new CustomGem("peridot",     MaterialColor.GRASS,         3, 7);
  public static final CustomGem RUBY        = new CustomGem("ruby",        MaterialColor.FIRE,          3, 7);
  public static final CustomGem SAPPHIRE    = new CustomGem("sapphire",    MaterialColor.WATER,         3, 7);
  // public static final CustomGem TANZANITE   = new CustomGem("tanzanite",   MaterialColor.COLOR_PURPLE,  3, 7);
  public static final CustomGem TOPAZ       = new CustomGem("topaz",       MaterialColor.COLOR_ORANGE,  3, 7);
  public static final SimpleGem ROSE_QUARTZ = new SimpleGem("rose_quartz", MaterialColor.COLOR_PINK,    3, 7);
  
  // MapColor Quartz is slightly darker than Cloth or Snow
  /* Brightness Scale:
    1 SNOW   = new MapColor(8,  16777215);   (White)
    2 CLOTH  = new MapColor(3,  13092807);
    3 METAL  = new MapColor(6,  10987431);
    4 SILVER = new MapColor(22, 10066329);
    5 STONE  = new MapColor(11,  7368816);
    6 GRAY   = new MapColor(21,  5000268);
  */
  
  // common metals
  public static final CustomMetal ALUMINUM = new CustomMetal("aluminum", MaterialColor.ICE);
  // public static final CustomMetal LEAD     = new CustomMetal("lead",     MaterialColor.STONE);
  // public static final CustomMetal NICKEL   = new CustomMetal("nickel",   MaterialColor.METAL);
  public static final CustomMetal TIN      = new CustomMetal("tin",      MaterialColor.METAL);
  // public static final CustomMetal ZINC     = new CustomMetal("zinc",     MaterialColor.METAL);

  // semi-rare metals
  public static final CustomMetal SILVER   = new CustomMetal("silver",   MaterialColor.WOOL);
  // public static final CustomMetal COBALT   = new CustomMetal("cobalt",   MaterialColor.COLOR_BLUE);

  // rare metals
  public static final CustomMetal PLATINUM = new CustomMetal("platinum", MaterialColor.ICE);
  public static final CustomMetal TITANIUM = new CustomMetal("titanium", MaterialColor.SNOW);

  // manufactured metals
  /** Metal alloy of Copper and Zinc. Generally 2 parts Copper, 1 part Zinc.
   *  Used in applications where corrosion resistance and low friction is required, such as door hinges and gears. */
  // public static final ManufacturedMetal BRASS    = new ManufacturedMetal("brass",  MaterialColor.COLOR_YELLOW);
  /** Metal alloy of Tin and Copper. Stronger and more durable than Copper alone. */
  public static final ManufacturedMetal BRONZE   = new ManufacturedMetal("bronze", MaterialColor.COLOR_ORANGE);
  /** Metal alloy of Iron and Nickel. Known for its strong resistance to heat expansion.
   *  Has a simplified Nickel:Iron ratio of 3:5 or 1:2. */
  // public static final ManufacturedMetal INVAR    = new ManufacturedMetal("invar",  MaterialColor.SAND);
  /** An advanced version of Iron. Metal alloy of Iron with a very small amount of Carbon. */
  public static final ManufacturedMetal STEEL    = new ManufacturedMetal("steel",  MaterialColor.COLOR_GRAY);
  
  // other materials
  public static final SimpleOreMaterial SILICON   = new SimpleOreMaterial("silicon",   MaterialColor.COLOR_GRAY);
  // public static final OreMaterial URANIUM   = new OreMaterial("uranium",   MaterialColor.COLOR_LIGHT_GREEN);
  // public static final OreMaterial YELLORIUM = new OreMaterial("yellorium", MaterialColor.COLOR_YELLOW);

}
