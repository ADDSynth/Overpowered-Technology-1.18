package addsynth.core;

import java.io.File;
import addsynth.core.compat.Compatibility;
import addsynth.core.compat.EMCValue;
import addsynth.core.game.registry.RegistryUtil;
import addsynth.core.gameplay.Config;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.commands.ADDSynthCommands;
import addsynth.core.gameplay.team_manager.data.CriteriaData;
import addsynth.core.gameplay.team_manager.data.TeamData;
import addsynth.core.recipe.FurnaceRecipes;
import addsynth.core.util.CommonUtil;
import addsynth.core.util.constants.DevStage;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Mod(value = ADDSynthCore.MOD_ID)
public final class ADDSynthCore {

  public static final String MOD_ID = "addsynthcore";
  public static final String NAME = "ADDSynthCore";
  public static final String VERSION = "1.0";
  public static final String VERSION_DATE = "February 2, 2024";
  public static final DevStage DEV_STAGE = DevStage.STABLE;

  public static final Logger log = LogManager.getLogger(NAME);

  public static final CreativeModeTab creative_tab = new CreativeModeTab("addsynthcore"){
    @Override
    public final ItemStack makeIcon(){
      return new ItemStack(RegistryUtil.getItemBlock(Core.caution_block), 1);
    }
  };

  public ADDSynthCore(){
    ADDSynthCore.log.info("Begin constructing ADDSynthCore class object...");

    final FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
    final IEventBus bus = context.getModEventBus();
    bus.addListener(ADDSynthCore::main_setup);
    bus.addListener(ADDSynthCore::client_setup);
    MinecraftForge.EVENT_BUS.addListener(ADDSynthCore::onServerStarting);
    MinecraftForge.EVENT_BUS.addListener(ADDSynthCore::onServerStarted);
    MinecraftForge.EVENT_BUS.addListener(ADDSynthCore::registerCommands);

    init_config();

    MinecraftForge.EVENT_BUS.addListener(TeamData::serverTick);
    MinecraftForge.EVENT_BUS.addListener(ADDSynthCommands::tick);
    
    ADDSynthCore.log.info("Done constructing ADDSynthCore class object.");
  }

  private static final void init_config(){
    new File(FMLPaths.CONFIGDIR.get().toString(), NAME).mkdir();

    final ModLoadingContext context = ModLoadingContext.get();
    context.registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC,         NAME+File.separator+"main.toml");
  }

  private static final void main_setup(final FMLCommonSetupEvent event){
    log.info("Begin ADDSynthCore main setup...");
    CommonUtil.displayModInfo(log, NAME, "ADDSynth", VERSION, DEV_STAGE, VERSION_DATE);
  
    Debug.debug();
    if(Config.debug_mod_detection.get()){
      event.enqueueWork(Compatibility::debug);
    }
    NetworkHandler.registerMessages();
    MinecraftForge.EVENT_BUS.addListener((TagsUpdatedEvent tag_event) -> Debug.dump_tags());
    
    FurnaceRecipes.INSTANCE.register();

    log.info("Finished ADDSynthCore main setup.");
  }

  private static final void client_setup(final FMLClientSetupEvent event){
    CriteriaData.calculate();
  }

  public static void onServerStarting(final ServerStartingEvent event){
    // TODO: I can customize recipes here?
    // TODO: change the recipes of the Trophies to use ingots instead of metal plates, if no plates exist.
    // And if I can do that, go ahead and reimplement the ability to disable the Trophy base item.
  }

  public static void onServerStarted(final ServerStartedEvent event){
    if(Compatibility.PROJECT_E.loaded){
      EMCValue.check_internal_emc_values();
      if(DEV_STAGE.isDevelopment){
        EMCValue.check_items(MOD_ID);
      }
    }
  }

  public static final void registerCommands(final RegisterCommandsEvent event){
    ADDSynthCommands.register(event.getDispatcher());
  }

}
