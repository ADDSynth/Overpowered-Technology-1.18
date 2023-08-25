package addsynth.core.gameplay.registers;

import addsynth.core.ADDSynthCore;
import addsynth.core.game.RegistryUtil;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.Trophy;
import addsynth.core.gameplay.reference.Names;
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

    game.register(Core.caution_block);
    game.register(Core.music_box);
    game.register(Core.team_manager);
    
    game.register(Trophy.bronze);
    game.register(Trophy.silver);
    game.register(Trophy.gold);
    game.register(Trophy.platinum);
    
    // game.register(Core.test_block);
    
    ADDSynthCore.log.info("Done registering blocks.");
  }

  @SubscribeEvent
  public static final void register_items(final RegistryEvent.Register<Item> event){
    ADDSynthCore.log.info("Begin registering items...");

    final IForgeRegistry<Item> game = event.getRegistry();

    game.register(RegistryUtil.getItemBlock(Core.caution_block));
    game.register(RegistryUtil.getItemBlock(Core.music_box));
    game.register(Core.music_sheet);
    game.register(RegistryUtil.getItemBlock(Core.team_manager));
    
    game.register(Trophy.trophy_base);
    game.register(Trophy.BRONZE.item_block);
    game.register(Trophy.SILVER.item_block);
    game.register(Trophy.GOLD.item_block);
    game.register(Trophy.PLATINUM.item_block);
    
    // game.register(RegistryUtil.getItemBlock(Core.test_block));

    ADDSynthCore.log.info("Done registering items.");
  }

  @SubscribeEvent
  public static final void register_tileentities(final RegistryEvent.Register<BlockEntityType<?>> event){
    final IForgeRegistry<BlockEntityType<?>> game = event.getRegistry();
    RegistryUtil.register(game, Tiles.MUSIC_BOX, Names.MUSIC_BOX);
  }

  @SubscribeEvent
  public static final void registerContainers(final RegistryEvent.Register<MenuType<?>> event){
    final IForgeRegistry<MenuType<?>> game = event.getRegistry();
  }
    
}
