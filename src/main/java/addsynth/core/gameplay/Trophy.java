package addsynth.core.gameplay;

import addsynth.core.ADDSynthCore;
import addsynth.core.game.RegistryUtil;
import addsynth.core.gameplay.blocks.TrophyBlock;
import addsynth.core.gameplay.items.CoreItem;
import addsynth.core.gameplay.reference.Names;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public enum Trophy {

  BRONZE(Names.BRONZE_TROPHY),
  SILVER(Names.SILVER_TROPHY),
  GOLD(Names.GOLD_TROPHY),
  PLATINUM(Names.PLATINUM_TROPHY);

  private final TrophyBlock trophy;
  public final BlockItem item_block;
  
  public static final Item trophy_base = new CoreItem(Names.TROPHY_BASE);
  public static final TrophyBlock bronze   = BRONZE.trophy;
  public static final TrophyBlock silver   = SILVER.trophy;
  public static final TrophyBlock gold     = GOLD.trophy;
  public static final TrophyBlock platinum = PLATINUM.trophy;

  private Trophy(final ResourceLocation name){
    this.trophy = new TrophyBlock();
    this.trophy.setRegistryName(name);
    this.item_block = RegistryUtil.create_ItemBlock(this.trophy, ADDSynthCore.creative_tab, name);
  }

}
