package addsynth.core.gameplay.registers;

import addsynth.core.ADDSynthCore;
import addsynth.core.game.registry.RegistryUtil;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.Trophy;
import addsynth.core.gameplay.blocks.CautionBlock;
import addsynth.core.gameplay.blocks.TrophyBlock;
import addsynth.core.gameplay.items.CoreItem;
import addsynth.core.gameplay.music_box.MusicBox;
import addsynth.core.gameplay.music_box.MusicSheet;
import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.gameplay.reference.Names;
import addsynth.core.gameplay.team_manager.TeamManagerBlock;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = ADDSynthCore.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class CoreRegister {

  @SubscribeEvent
  public static final void register_blocks(final RegistryEvent.Register<Block> event){
    ADDSynthCore.log.info("Begin registering blocks...");

    final IForgeRegistry<Block> game = event.getRegistry();

    game.register(new CautionBlock());
    game.register(new MusicBox());
    game.register(new TeamManagerBlock());
    
    game.register(new TrophyBlock(Names.BRONZE_TROPHY));
    game.register(new TrophyBlock(Names.SILVER_TROPHY));
    game.register(new TrophyBlock(Names.GOLD_TROPHY));
    game.register(new TrophyBlock(Names.PLATINUM_TROPHY));
    
    // game.register(new TestBlock());
    
    ADDSynthCore.log.info("Done registering blocks.");
  }

  @SubscribeEvent
  public static final void register_items(final RegistryEvent.Register<Item> event){
    ADDSynthCore.log.info("Begin registering items...");

    final IForgeRegistry<Item> game = event.getRegistry();

    game.register(RegistryUtil.createItemBlock(Core.caution_block, ADDSynthCore.creative_tab));
    game.register(RegistryUtil.createItemBlock(Core.music_box,     ADDSynthCore.creative_tab));
    game.register(new MusicSheet());
    game.register(RegistryUtil.createItemBlock(Core.team_manager,  ADDSynthCore.creative_tab));
    
    game.register(new CoreItem(Names.TROPHY_BASE));
    game.register(RegistryUtil.createItemBlock(Trophy.bronze,   ADDSynthCore.creative_tab));
    game.register(RegistryUtil.createItemBlock(Trophy.silver,   ADDSynthCore.creative_tab));
    game.register(RegistryUtil.createItemBlock(Trophy.gold,     ADDSynthCore.creative_tab));
    game.register(RegistryUtil.createItemBlock(Trophy.platinum, ADDSynthCore.creative_tab));
    
    // game.register(RegistryUtil.createItemBlock(Core.test_block, ADDSynthCore.creative_tab));

    ADDSynthCore.log.info("Done registering items.");
  }

  @SubscribeEvent
  public static final void register_tileentities(final RegistryEvent.Register<BlockEntityType<?>> event){
    final IForgeRegistry<BlockEntityType<?>> game = event.getRegistry();
    game.register(RegistryUtil.createBlockEntityType(TileMusicBox::new, Core.music_box));
  }

  @SubscribeEvent
  public static final void registerContainers(final RegistryEvent.Register<MenuType<?>> event){
    final IForgeRegistry<MenuType<?>> game = event.getRegistry();
  }
    
}
