package addsynth.overpoweredmod.assets;

import addsynth.core.game.item.constants.ArmorMaterial;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.compatability.CompatabilityManager;
import addsynth.overpoweredmod.config.Features;
import addsynth.overpoweredmod.config.UnidentifiedItemDropConfig;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OverpoweredTechnology.MOD_ID)
public final class LootTables {

// https://mcforge.readthedocs.io/en/latest/items/loot_tables/
// https://minecraft.gamepedia.com/Loot_table
// https://minecraft.gamepedia.com/Status_effect#Luck
// https://github.com/Vazkii/Botania/blob/master/src/main/java/vazkii/botania/common/core/loot/LootHandler.java

  private static final LootPool build_loot_pool(final float drop_chance){
    final int   leather_weight = Values.leather_armor_drop_weight.get();
    final int      gold_weight = Values.gold_armor_drop_weight.get();
    final int chainmail_weight = Values.chainmail_armor_drop_weight.get();
    final int      iron_weight = Values.iron_armor_drop_weight.get();
    final int   diamond_weight = Values.diamond_armor_drop_weight.get();
    final LootPool.Builder loot = new LootPool.Builder();
    // Not how I would've designed it, but this is the best I can do.
    // As long as items of the same type have the same weight, and each type has
    // the same number of items, then they all have an equal chance of being picked.
    // For instance: [30, 18, 9, 9, 3, 4] is the same as [120, 72, 36, 36, 12, 16]
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.LEATHER.ordinal()][0]).setWeight(leather_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.LEATHER.ordinal()][1]).setWeight(leather_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.LEATHER.ordinal()][2]).setWeight(leather_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.LEATHER.ordinal()][3]).setWeight(leather_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.GOLD.ordinal()][0]).setWeight(gold_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.GOLD.ordinal()][1]).setWeight(gold_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.GOLD.ordinal()][2]).setWeight(gold_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.GOLD.ordinal()][3]).setWeight(gold_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.CHAINMAIL.ordinal()][0]).setWeight(chainmail_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.CHAINMAIL.ordinal()][1]).setWeight(chainmail_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.CHAINMAIL.ordinal()][2]).setWeight(chainmail_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.CHAINMAIL.ordinal()][3]).setWeight(chainmail_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.IRON.ordinal()][0]).setWeight(iron_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.IRON.ordinal()][1]).setWeight(iron_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.IRON.ordinal()][2]).setWeight(iron_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.IRON.ordinal()][3]).setWeight(iron_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.DIAMOND.ordinal()][0]).setWeight(diamond_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.DIAMOND.ordinal()][1]).setWeight(diamond_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.DIAMOND.ordinal()][2]).setWeight(diamond_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.DIAMOND.ordinal()][3]).setWeight(diamond_weight));
    if(CompatabilityManager.are_rings_enabled()){
      final int      ring_weight = Values.ring_drop_weight.get();
      loot.add(LootItem.lootTableItem(OverpoweredItems.ring_0.get()).setWeight(ring_weight));
      loot.add(LootItem.lootTableItem(OverpoweredItems.ring_1.get()).setWeight(ring_weight));
      loot.add(LootItem.lootTableItem(OverpoweredItems.ring_2.get()).setWeight(ring_weight));
      loot.add(LootItem.lootTableItem(OverpoweredItems.ring_3.get()).setWeight(ring_weight));
    }
    loot.when(LootItemKilledByPlayerCondition.killedByPlayer());
    loot.when(LootItemRandomChanceCondition.randomChance(drop_chance));
    loot.name("overpowered_custom_loot_table");
    return loot.build();
  }

  private static final boolean debug_loot_tables = false;
  private static final String prefix = "minecraft:entities/";
  
  @SubscribeEvent
  public static final void inject_loot(final LootTableLoadEvent event){
    if(Features.identifier.get()){
      final String name = event.getName().toString();
      if(name.startsWith(prefix)){
        if(debug_loot_tables){
          OverpoweredTechnology.log.info("Loading Loot Table: "+name);
        }
        final String mob = name.substring(prefix.length());
        if(mob.equals("zombie")         ){addDrops(Values.MOBS.ZOMBIE,          event); return;}
        if(mob.equals("zombie_villager")){addDrops(Values.MOBS.ZOMBIE_VILLAGER, event); return;}
        if(mob.equals("husk")           ){addDrops(Values.MOBS.HUSK,            event); return;}
        if(mob.equals("spider")         ){addDrops(Values.MOBS.SPIDER,          event); return;}
        if(mob.equals("cave_spider")    ){addDrops(Values.MOBS.CAVE_SPIDER,     event); return;}
        if(mob.equals("creeper")        ){addDrops(Values.MOBS.CREEPER,         event); return;}
        if(mob.equals("skeleton")       ){addDrops(Values.MOBS.SKELETON,        event); return;}
        if(mob.equals("zombie_pigman")  ){addDrops(Values.MOBS.ZOMBIE_PIGMAN,   event); return;}
        if(mob.equals("blaze")          ){addDrops(Values.MOBS.BLAZE,           event); return;}
        if(mob.equals("witch")          ){addDrops(Values.MOBS.WITCH,           event); return;}
        if(mob.equals("ghast")          ){addDrops(Values.MOBS.GHAST,           event); return;}
        if(mob.equals("enderman")       ){addDrops(Values.MOBS.ENDERMAN,        event); return;}
        if(mob.equals("stray")          ){addDrops(Values.MOBS.STRAY,           event); return;}
        if(mob.equals("guardian")       ){addDrops(Values.MOBS.GUARDIAN,        event); return;}
        if(mob.equals("elder_guardian") ){addDrops(Values.MOBS.ELDER_GUARDIAN,  event); return;}
        if(mob.equals("wither_skeleton")){addDrops(Values.MOBS.WITHER_SKELETON, event); return;}
        if(mob.equals("magma_cube")     ){addDrops(Values.MOBS.MAGMA_CUBE,      event); return;}
        if(mob.equals("shulker")        ){addDrops(Values.MOBS.SHULKER,         event); return;}
        if(mob.equals("vex")            ){addDrops(Values.MOBS.VEX,             event); return;}
        if(mob.equals("evoker")         ){addDrops(Values.MOBS.EVOKER,          event); return;}
        if(mob.equals("vindicator")     ){addDrops(Values.MOBS.VINDICATOR,      event); return;}
        if(mob.equals("illusioner")     ){addDrops(Values.MOBS.ILLUSIONER,      event); return;}
        if(mob.equals("drowned")        ){addDrops(Values.MOBS.DROWNED,         event); return;}
        if(mob.equals("phantom")        ){addDrops(Values.MOBS.PHANTOM,         event); return;}
        if(mob.equals("skeleton_horse") ){addDrops(Values.MOBS.SKELETON_HORSE,  event); return;}
        if(mob.equals("pillager")       ){addDrops(Values.MOBS.PILLAGER,        event); return;}
        if(mob.equals("ravager")        ){addDrops(Values.MOBS.RAVAGER,         event); return;}
        if(mob.equals("ender_dragon")   ){addDrops(Values.MOBS.END_DRAGON,      event); return;}
        if(mob.equals("wither")         ){addDrops(Values.MOBS.WITHER,          event); return;}
      }
    }
  }

  private static final void addDrops(final UnidentifiedItemDropConfig config, final LootTableLoadEvent event){
    if(config.drop.get()){
      event.getTable().addPool(build_loot_pool(config.chance.get().floatValue()));
      if(debug_loot_tables){
        OverpoweredTechnology.log.info("  Successfully injected custom loot pool into Loot Table for: "+config.mob_name);
      }
    }
  }

}
