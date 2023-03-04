package addsynth.core.gameplay.registers;

import addsynth.core.ADDSynthCore;
import addsynth.core.config.Features;
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

    ADDSynthCore.init_config();
    
    if(Features.caution_block.get()){ game.register(Core.caution_block); }
    if(Features.music_box.get()){     game.register(Core.music_box);     }
    if(Features.team_manager.get()){  game.register(Core.team_manager);  }
    
    if(Features.trophies()){
      if(Features.bronze_trophy.get()){   game.register(Trophy.bronze);   }
      if(Features.silver_trophy.get()){   game.register(Trophy.silver);   }
      if(Features.gold_trophy.get()){     game.register(Trophy.gold);     }
      if(Features.platinum_trophy.get()){ game.register(Trophy.platinum); }
    }
    // game.register(Core.test_block);
    
    ADDSynthCore.log.info("Done registering blocks.");
  }

  @SubscribeEvent
  public static final void register_items(final RegistryEvent.Register<Item> event){
    ADDSynthCore.log.info("Begin registering items...");

    final IForgeRegistry<Item> game = event.getRegistry();

    if(Features.caution_block.get()){
      game.register(RegistryUtil.getItemBlock(Core.caution_block));
    }
    if(Features.music_box.get()){
      game.register(RegistryUtil.getItemBlock(Core.music_box));
      if(Features.music_sheet.get()){
        game.register(Core.music_sheet);
      }
    }
    if(Features.team_manager.get()){
      game.register(RegistryUtil.getItemBlock(Core.team_manager));
    }
    
    if(Features.trophies()){
      game.register(Trophy.trophy_base);
      if(Features.bronze_trophy.get()){   game.register(Trophy.BRONZE.item_block);   }
      if(Features.silver_trophy.get()){   game.register(Trophy.SILVER.item_block);   }
      if(Features.gold_trophy.get()){     game.register(Trophy.GOLD.item_block);     }
      if(Features.platinum_trophy.get()){ game.register(Trophy.PLATINUM.item_block); }
    }
    
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
