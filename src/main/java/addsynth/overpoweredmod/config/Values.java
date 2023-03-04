package addsynth.overpoweredmod.config;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;

public final class Values {

  private static final Pair<Values, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(Values::new);
  public static final Values INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

  public  static final double DEFAULT_MOB_DROP_CHANCE      = 0.01;
  private static final double DEFAULT_HARD_MOB_DROP_CHANCE = 1;
  private static final double DEFAULT_BOSS_MOB_DROP_CHANCE = 7;

  private static final int DEFAULT_LEATHER_ARMOR_WEIGHT = 30;
  private static final int DEFAULT_GOLD_ARMOR_WEIGHT    = 18;
  private static final int DEFAULT_IRON_ARMOR_WEIGHT    =  9;
  private static final int DEFAULT_DIAMOND_ARMOR_WEIGHT =  3;
  private static final int DEFAULT_RING_WEIGHT          =  4;
  /*
  Without Rings:
      Leather: 10 / 23 (43%)
         Gold:  6 / 23 (26%)
    Chainmail:  3 / 23 (13%)
         Iron:  3 / 23 (13%)
      Diamond:  1 / 23 ( 4%)
  With Rings:
    Armor: 69 / 73 (95%)
        Leather: 30 / 73 (41%)
           Gold: 18 / 73 (25%)
      Chainmail:  9 / 73 (12%)
           Iron:  9 / 73 (12%)
        Diamond:  3 / 73 ( 4%)
    Rings:  4 / 73 (5%)
  You get 1 custom loot drop every 100 mobs on average.
  You get 1 Ring every 1,825 mobs on average.
  */

  private static final int DEFAULT_COMMON_RING_WEIGHT = 10;
  private static final int DEFAULT_GOOD_RING_WEIGHT   =  6;
  private static final int DEFAULT_RARE_RING_WEIGHT   =  3;
  private static final int DEFAULT_UNIQUE_RING_WEIGHT =  1;
  // add Legendary?

  private static final int   DEFAULT_PORTAL_SPAWN_TIME = 40;
  private static final double DEFAULT_UNKNOWN_TREE_SPAWN_CHANCE = 0.0005; // 1 / 2000

  public static final class MOBS {
    // Undead
    public static final UnidentifiedItemDropConfig ZOMBIE          = new UnidentifiedItemDropConfig("Zombie");
    public static final UnidentifiedItemDropConfig ZOMBIE_VILLAGER = new UnidentifiedItemDropConfig("Zombie Villager");
    public static final UnidentifiedItemDropConfig HUSK            = new UnidentifiedItemDropConfig("Husk");
    public static final UnidentifiedItemDropConfig SKELETON        = new UnidentifiedItemDropConfig("Skeleton");
    public static final UnidentifiedItemDropConfig STRAY           = new UnidentifiedItemDropConfig("Stray");
    public static final UnidentifiedItemDropConfig PHANTOM         = new UnidentifiedItemDropConfig("Phantom");
    public static final UnidentifiedItemDropConfig DROWNED         = new UnidentifiedItemDropConfig("Drowned");
    public static final UnidentifiedItemDropConfig SKELETON_HORSE  = new UnidentifiedItemDropConfig("Skeleton Horse");
    
    // Illagers
    public static final UnidentifiedItemDropConfig PILLAGER        = new UnidentifiedItemDropConfig("Pillager");
    public static final UnidentifiedItemDropConfig VINDICATOR      = new UnidentifiedItemDropConfig("Vindicator");
    public static final UnidentifiedItemDropConfig EVOKER =
      new UnidentifiedItemDropConfig("Evoker", DEFAULT_HARD_MOB_DROP_CHANCE);
    public static final UnidentifiedItemDropConfig VEX             = new UnidentifiedItemDropConfig("Vex");
    public static final UnidentifiedItemDropConfig RAVAGER =
      new UnidentifiedItemDropConfig("Ravager", DEFAULT_HARD_MOB_DROP_CHANCE);
    public static final UnidentifiedItemDropConfig ILLUSIONER =
      new UnidentifiedItemDropConfig("Illusioner", DEFAULT_HARD_MOB_DROP_CHANCE);
    
    // Overworld Mobs
    public static final UnidentifiedItemDropConfig CREEPER         = new UnidentifiedItemDropConfig("Creeper");
    public static final UnidentifiedItemDropConfig SPIDER          = new UnidentifiedItemDropConfig("Spider");
    public static final UnidentifiedItemDropConfig CAVE_SPIDER     = new UnidentifiedItemDropConfig("Cave Spider");
    public static final UnidentifiedItemDropConfig WITCH           = new UnidentifiedItemDropConfig("Witch");
    public static final UnidentifiedItemDropConfig GUARDIAN        = new UnidentifiedItemDropConfig("Guardian");
    public static final UnidentifiedItemDropConfig ELDER_GUARDIAN  =
      new UnidentifiedItemDropConfig("Elder Guardian", DEFAULT_HARD_MOB_DROP_CHANCE);
    
    // Nether Mobs
    public static final UnidentifiedItemDropConfig ZOMBIE_PIGMAN   = new UnidentifiedItemDropConfig("Zombie Pigman");
    public static final UnidentifiedItemDropConfig BLAZE           = new UnidentifiedItemDropConfig("Blaze");
    public static final UnidentifiedItemDropConfig GHAST           = new UnidentifiedItemDropConfig("Ghast");
    public static final UnidentifiedItemDropConfig MAGMA_CUBE      = new UnidentifiedItemDropConfig("Magma Cube");
    public static final UnidentifiedItemDropConfig WITHER_SKELETON = new UnidentifiedItemDropConfig("Wither Skeleton");
    
    // End Mobs
    public static final UnidentifiedItemDropConfig ENDERMAN        = new UnidentifiedItemDropConfig("Enderman");
    public static final UnidentifiedItemDropConfig SHULKER         = new UnidentifiedItemDropConfig("Shulker");
    
    // Boss Mobs
    public static final UnidentifiedItemDropConfig END_DRAGON =
      new UnidentifiedItemDropConfig("End Dragon", DEFAULT_BOSS_MOB_DROP_CHANCE);
    public static final UnidentifiedItemDropConfig WITHER =
      new UnidentifiedItemDropConfig("Wither", DEFAULT_BOSS_MOB_DROP_CHANCE);
  }

  public static ForgeConfigSpec.IntValue   leather_armor_drop_weight;
  public static ForgeConfigSpec.IntValue      gold_armor_drop_weight;
  public static ForgeConfigSpec.IntValue chainmail_armor_drop_weight;
  public static ForgeConfigSpec.IntValue      iron_armor_drop_weight;
  public static ForgeConfigSpec.IntValue   diamond_armor_drop_weight;
  public static ForgeConfigSpec.IntValue            ring_drop_weight;

  public static ForgeConfigSpec.IntValue common_ring_weight;
  public static ForgeConfigSpec.IntValue   good_ring_weight;
  public static ForgeConfigSpec.IntValue   rare_ring_weight;
  public static ForgeConfigSpec.IntValue unique_ring_weight;

  public static ForgeConfigSpec.IntValue portal_spawn_time;
  public static ForgeConfigSpec.DoubleValue unknown_dimension_tree_spawn_chance;

  public Values(final ForgeConfigSpec.Builder builder){

    builder.comment("Here you can configure how unidentified items drop from enemy mobs.\n"+
                    "To disable this completely, disable the Identifier machine.");
    builder.push("Unidentified Items Config");
      builder.push("Mob Drop Settings");
        builder.push("Overworld Mobs");
          builder.push("Undead");
            MOBS.ZOMBIE.build(builder);
            MOBS.ZOMBIE_VILLAGER.build(builder);
            MOBS.HUSK.build(builder);
            MOBS.SKELETON.build(builder);
            MOBS.STRAY.build(builder);
            MOBS.DROWNED.build(builder);
            MOBS.PHANTOM.build(builder);
            MOBS.SKELETON_HORSE.build(builder);
          builder.pop();
          builder.push("Illagers");
            MOBS.PILLAGER.build(builder);
            MOBS.VINDICATOR.build(builder);
            MOBS.EVOKER.build(builder);
            MOBS.VEX.build(builder);
            MOBS.RAVAGER.build(builder);
            MOBS.ILLUSIONER.build(builder); // Unused mob. Only spawns if a player uses the /summon command.
          builder.pop();
          MOBS.SPIDER.build(builder);
          MOBS.CAVE_SPIDER.build(builder);
          MOBS.CREEPER.build(builder);
          MOBS.WITCH.build(builder);
          MOBS.GUARDIAN.build(builder);
          MOBS.ELDER_GUARDIAN.build(builder);
        builder.pop();
        builder.push("Nether Mobs");
          MOBS.ZOMBIE_PIGMAN.build(builder);
          MOBS.MAGMA_CUBE.build(builder);
          MOBS.GHAST.build(builder);
          MOBS.BLAZE.build(builder);
          MOBS.WITHER_SKELETON.build(builder);
        builder.pop();
        builder.push("End Mobs");
          MOBS.ENDERMAN.build(builder);
          MOBS.SHULKER.build(builder);
        builder.pop();
        builder.push("Boss Mobs");
          MOBS.END_DRAGON.build(builder);
          MOBS.WITHER.build(builder);
        builder.pop();
      builder.pop();
      builder.comment("If you do not have the Curios mod installed, then Ring Weight will not be counted with the other weights.");
      builder.push("Item Type Drop Weights");
          leather_armor_drop_weight = builder.defineInRange("Leather Armor",   DEFAULT_LEATHER_ARMOR_WEIGHT, 0, Integer.MAX_VALUE);
             gold_armor_drop_weight = builder.defineInRange("Gold Armor",      DEFAULT_GOLD_ARMOR_WEIGHT,    0, Integer.MAX_VALUE);
        chainmail_armor_drop_weight = builder.defineInRange("Chainmail Armor", DEFAULT_IRON_ARMOR_WEIGHT,    0, Integer.MAX_VALUE);
             iron_armor_drop_weight = builder.defineInRange("Iron Armor",      DEFAULT_IRON_ARMOR_WEIGHT,    0, Integer.MAX_VALUE);
          diamond_armor_drop_weight = builder.defineInRange("Diamond Armor",   DEFAULT_DIAMOND_ARMOR_WEIGHT, 0, Integer.MAX_VALUE);
                   ring_drop_weight = builder.defineInRange("Rings",           DEFAULT_RING_WEIGHT,          0, Integer.MAX_VALUE);
      builder.pop();
      builder.comment("When an Unidentified Ring is inserted into the Identifier,\n"+
                      "these weight values determine what kind of Ring is produced.");
      builder.push("Ring Type Weights");
        common_ring_weight = builder.defineInRange("Common Rings", DEFAULT_COMMON_RING_WEIGHT, 0, Integer.MAX_VALUE);
          good_ring_weight = builder.defineInRange("Good Rings",   DEFAULT_GOOD_RING_WEIGHT,   0, Integer.MAX_VALUE);
          rare_ring_weight = builder.defineInRange("Rare Rings",   DEFAULT_RARE_RING_WEIGHT,   0, Integer.MAX_VALUE);
        unique_ring_weight = builder.defineInRange("Unique Rings", DEFAULT_UNIQUE_RING_WEIGHT, 0, Integer.MAX_VALUE);
      builder.pop();
    builder.pop();
    
    builder.push("Other Values");
    portal_spawn_time = builder.defineInRange("Portal Spawn Time (in seconds)", DEFAULT_PORTAL_SPAWN_TIME, 5, 3600);
    
    unknown_dimension_tree_spawn_chance = builder.comment(
      "This float value determines the chance a weird tree will spawn for each chunk\nin the Unknown Dimension.")
      .defineInRange("Weird Tree Spawn Chance", DEFAULT_UNKNOWN_TREE_SPAWN_CHANCE, Float.MIN_NORMAL, 1.0f);
    builder.pop();
  }

}
