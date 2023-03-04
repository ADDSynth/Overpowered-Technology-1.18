package addsynth.overpoweredmod;

import java.io.File;
import addsynth.core.recipe.RecipeUtil;
import addsynth.core.util.CommonUtil;
import addsynth.core.util.constants.DevStage;
import addsynth.core.util.game.Game;
import addsynth.material.MaterialsUtil;
import addsynth.overpoweredmod.assets.CustomStats;
import addsynth.overpoweredmod.compatability.CompatabilityManager;
import addsynth.overpoweredmod.config.*;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.OverpoweredSavedData;
import addsynth.overpoweredmod.game.core.Laser;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.machines.Filters;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.GuiAdvancedOreRefinery;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.OreRefineryRecipes;
import addsynth.overpoweredmod.machines.crystal_matter_generator.GuiCrystalMatterGenerator;
import addsynth.overpoweredmod.machines.energy_extractor.GuiCrystalEnergyExtractor;
import addsynth.overpoweredmod.machines.fusion.chamber.GuiFusionChamber;
import addsynth.overpoweredmod.machines.gem_converter.GuiGemConverter;
import addsynth.overpoweredmod.machines.identifier.GuiIdentifier;
import addsynth.overpoweredmod.machines.inverter.GuiInverter;
import addsynth.overpoweredmod.machines.laser.machine.GuiLaserHousing;
import addsynth.overpoweredmod.machines.magic_infuser.GuiMagicInfuser;
import addsynth.overpoweredmod.machines.matter_compressor.GuiMatterCompressor;
import addsynth.overpoweredmod.machines.plasma_generator.GuiPlasmaGenerator;
import addsynth.overpoweredmod.machines.portal.control_panel.GuiPortalControlPanel;
import addsynth.overpoweredmod.machines.portal.frame.GuiPortalFrame;
import addsynth.overpoweredmod.machines.suspension_bridge.GuiEnergySuspensionBridge;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Mod(value = OverpoweredTechnology.MOD_ID)
public class OverpoweredTechnology {

  public static final String MOD_ID = "overpowered"; // FUTURE: version 1.6 will rename the modid to overpowered_technology. All assets must also be renamed.
  public static final String MOD_NAME = "Overpowered Technology";
  public static final String VERSION = "1.5";
  public static final String VERSION_DATE = "December 22, 2022";
  public static final DevStage DEV_STAGE = DevStage.STABLE;
    
  public static final Logger log = LogManager.getLogger(MOD_NAME);

  private static boolean config_loaded;

  public OverpoweredTechnology(){
    OverpoweredTechnology.log.info("Begin constructing "+OverpoweredTechnology.class.getSimpleName()+" class object...");
    final FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
    final IEventBus bus = context.getModEventBus();
    bus.addListener(OverpoweredTechnology::main_setup);
    bus.addListener(OverpoweredTechnology::client_setup);
    bus.addListener(CompatabilityManager::inter_mod_communications);
    MinecraftForge.EVENT_BUS.addListener(OverpoweredTechnology::serverStarted);
    init_config();
    OverpoweredTechnology.log.info("Done constructing "+OverpoweredTechnology.class.getSimpleName()+" class object.");
  }

  public static final void init_config(){
    if(config_loaded == false){
      OverpoweredTechnology.log.info("Loading Configuration files...");
  
      new File(FMLPaths.CONFIGDIR.get().toString(), MOD_NAME).mkdir();

      final ModLoadingContext context = ModLoadingContext.get();
      // MAYBE: the two ways to get the mod context can probably be combined/merged, but I don't want to think about that right now.
      //        what exactly is the relationship of FMLJavaModLoadingContext and ModLoadingContext?
  
      context.registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC,        MOD_NAME+File.separator+"main.toml");
      context.registerConfig(ModConfig.Type.COMMON, Features.CONFIG_SPEC,      MOD_NAME+File.separator+"feature_disable.toml");
      context.registerConfig(ModConfig.Type.COMMON, MachineValues.CONFIG_SPEC, MOD_NAME+File.separator+"machine_values.toml");
      context.registerConfig(ModConfig.Type.COMMON, Values.CONFIG_SPEC,        MOD_NAME+File.separator+"values.toml");

      FMLJavaModLoadingContext.get().getModEventBus().addListener(OverpoweredTechnology::mod_config_event);

      config_loaded = true;
  
      OverpoweredTechnology.log.info("Done Loading Configuration files.");
    }
  }
  
  private static final void main_setup(final FMLCommonSetupEvent event){
    log.info("Begin "+MOD_NAME+" main setup...");
    
    CommonUtil.displayModInfo(log, MOD_NAME, "ADDSynth", VERSION, DEV_STAGE, VERSION_DATE);
    
    NetworkHandler.registerMessages();
    // WeirdDimension.register();
    RecipeUtil.registerResponder(OreRefineryRecipes::refresh_ore_refinery_recipes);
    RecipeUtil.registerResponder(Filters::regenerate_machine_filters);
    MaterialsUtil.registerResponder(OreRefineryRecipes::refresh_ore_refinery_recipes);
    MaterialsUtil.registerResponder(Filters::regenerate_machine_filters);
    
    // Register Stats
    // Can't add Overpowered Technology Name to stats because then the text overlaps the stat values.
    Game.registerCustomStat(CustomStats.GEMS_CONVERTED);
    Game.registerCustomStat(CustomStats.LASERS_FIRED);
    Game.registerCustomStat(CustomStats.ITEMS_IDENTIFIED);
    // Game.registerCustomStat(BLACK_HOLE_EVENTS);
    
    log.info("Finished "+MOD_NAME+" main setup.");
  }

  private static final void serverStarted(final ServerStartedEvent event){
    // load world saved data
    @SuppressWarnings("resource")
    final MinecraftServer server = event.getServer();
    OverpoweredSavedData.load(server);
  }

  private static final void client_setup(final FMLClientSetupEvent event){
    register_guis();
    set_block_render_types();
  }

  private static final void register_guis(){
    MenuScreens.register(Containers.CRYSTAL_ENERGY_EXTRACTOR,   GuiCrystalEnergyExtractor::new);
    MenuScreens.register(Containers.GEM_CONVERTER,              GuiGemConverter::new);
    MenuScreens.register(Containers.INVERTER,                   GuiInverter::new);
    MenuScreens.register(Containers.MAGIC_INFUSER,              GuiMagicInfuser::new);
    MenuScreens.register(Containers.IDENTIFIER,                 GuiIdentifier::new);
    MenuScreens.register(Containers.ENERGY_SUSPENSION_BRIDGE,   GuiEnergySuspensionBridge::new);
    MenuScreens.register(Containers.PORTAL_CONTROL_PANEL,       GuiPortalControlPanel::new);
    MenuScreens.register(Containers.PORTAL_FRAME,               GuiPortalFrame::new);
    MenuScreens.register(Containers.LASER_HOUSING,              GuiLaserHousing::new);
    MenuScreens.register(Containers.PLASMA_GENERATOR,           GuiPlasmaGenerator::new);
    MenuScreens.register(Containers.ADVANCED_ORE_REFINERY,      GuiAdvancedOreRefinery::new);
    MenuScreens.register(Containers.CRYSTAL_MATTER_GENERATOR,   GuiCrystalMatterGenerator::new);
    MenuScreens.register(Containers.FUSION_CHAMBER,             GuiFusionChamber::new);
    MenuScreens.register(Containers.MATTER_COMPRESSOR,          GuiMatterCompressor::new);
  }

  private static final void set_block_render_types(){
    final RenderType translucent = RenderType.translucent();
    ItemBlockRenderTypes.setRenderLayer(OverpoweredBlocks.null_block,  translucent);
    ItemBlockRenderTypes.setRenderLayer(OverpoweredBlocks.portal,      translucent);
    ItemBlockRenderTypes.setRenderLayer(Laser.WHITE.beam,   translucent);
    ItemBlockRenderTypes.setRenderLayer(Laser.RED.beam,     translucent);
    ItemBlockRenderTypes.setRenderLayer(Laser.ORANGE.beam,  translucent);
    ItemBlockRenderTypes.setRenderLayer(Laser.YELLOW.beam,  translucent);
    ItemBlockRenderTypes.setRenderLayer(Laser.GREEN.beam,   translucent);
    ItemBlockRenderTypes.setRenderLayer(Laser.CYAN.beam,    translucent);
    ItemBlockRenderTypes.setRenderLayer(Laser.BLUE.beam,    translucent);
    ItemBlockRenderTypes.setRenderLayer(Laser.MAGENTA.beam, translucent);
    ItemBlockRenderTypes.setRenderLayer(OverpoweredBlocks.white_energy_bridge,   translucent);
    ItemBlockRenderTypes.setRenderLayer(OverpoweredBlocks.red_energy_bridge,     translucent);
    ItemBlockRenderTypes.setRenderLayer(OverpoweredBlocks.orange_energy_bridge,  translucent);
    ItemBlockRenderTypes.setRenderLayer(OverpoweredBlocks.yellow_energy_bridge,  translucent);
    ItemBlockRenderTypes.setRenderLayer(OverpoweredBlocks.green_energy_bridge,   translucent);
    ItemBlockRenderTypes.setRenderLayer(OverpoweredBlocks.cyan_energy_bridge,    translucent);
    ItemBlockRenderTypes.setRenderLayer(OverpoweredBlocks.blue_energy_bridge,    translucent);
    ItemBlockRenderTypes.setRenderLayer(OverpoweredBlocks.magenta_energy_bridge, translucent);
    ItemBlockRenderTypes.setRenderLayer(OverpoweredBlocks.fusion_control_laser_beam, translucent);
  }

  public static final void mod_config_event(final ModConfigEvent event){
    event.getConfig().save();
  }

}
