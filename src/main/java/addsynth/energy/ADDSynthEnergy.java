package addsynth.energy;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import addsynth.core.compat.Compatibility;
import addsynth.core.compat.EMCValue;
import addsynth.core.game.RegistryUtil;
import addsynth.core.util.CommonUtil;
import addsynth.core.util.constants.DevStage;
import addsynth.energy.compat.ADDSynthEnergyCompat;
import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.NetworkHandler;
import addsynth.energy.gameplay.config.Config;
import addsynth.energy.gameplay.machines.circuit_fabricator.CircuitFabricatorGui;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipes;
import addsynth.energy.gameplay.machines.compressor.GuiCompressor;
import addsynth.energy.gameplay.machines.compressor.recipe.CompressorRecipes;
import addsynth.energy.gameplay.machines.electric_furnace.GuiElectricFurnace;
import addsynth.energy.gameplay.machines.energy_storage.GuiEnergyStorageContainer;
import addsynth.energy.gameplay.machines.generator.GuiGenerator;
import addsynth.energy.gameplay.machines.universal_energy_interface.GuiUniversalEnergyInterface;
import addsynth.energy.registers.Containers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(value = ADDSynthEnergy.MOD_ID)
public class ADDSynthEnergy {

  public static final String MOD_ID = "addsynth_energy";
  public static final String MOD_NAME = "ADDSynth Energy";
  public static final String VERSION = "1.0";
  public static final String VERSION_DATE = "December 22, 2022";
  public static final DevStage DEV_STAGE = DevStage.STABLE;
    
  public static final Logger log = LogManager.getLogger(MOD_NAME);

  public static final CreativeModeTab creative_tab = new CreativeModeTab("addsynth_energy"){
    @Override
    public final ItemStack makeIcon(){
      return new ItemStack(RegistryUtil.getItemBlock(EnergyBlocks.wire));
    }
  };

  public ADDSynthEnergy(){
    final FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
    final IEventBus bus = context.getModEventBus();
    bus.addListener(ADDSynthEnergy::main_setup);
    bus.addListener(ADDSynthEnergyCompat::sendIMCMessages);
    bus.addListener(ADDSynthEnergy::client_setup);
    MinecraftForge.EVENT_BUS.addListener(ADDSynthEnergy::onServerStarted);
    init_config();
  }

  private static final void init_config(){
    new File(FMLPaths.CONFIGDIR.get().toString(), MOD_NAME).mkdir();

    final ModLoadingContext context = ModLoadingContext.get();
    context.registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC,   MOD_NAME+File.separator+"main.toml");
  }

  private static final void main_setup(final FMLCommonSetupEvent event){
    CommonUtil.displayModInfo(log, MOD_NAME, "ADDSynth", VERSION, DEV_STAGE, VERSION_DATE);
    NetworkHandler.registerMessages();
    CompressorRecipes.INSTANCE.register();
    CircuitFabricatorRecipes.INSTANCE.register();
  }

  public static void onServerStarted(final ServerStartedEvent event){
    @SuppressWarnings("resource")
    final MinecraftServer server = event.getServer();
    
    // build recipe caches
    final RecipeManager recipe_manager = server.getRecipeManager();
    CompressorRecipes.INSTANCE.rebuild(recipe_manager);
    CircuitFabricatorRecipes.INSTANCE.rebuild(recipe_manager);
    
    if(Compatibility.PROJECT_E.loaded){
      if(DEV_STAGE.isDevelopment){
        EMCValue.check_items(MOD_ID);
      }
    }
  }

  private static final void client_setup(final FMLClientSetupEvent event){
    event.enqueueWork(ADDSynthEnergy::register_guis);
    set_block_render_types();
  }

  private static final void register_guis(){
    MenuScreens.register(Containers.GENERATOR.get(),                  GuiGenerator::new);
    MenuScreens.register(Containers.ENERGY_STORAGE_CONTAINER.get(),   GuiEnergyStorageContainer::new);
    MenuScreens.register(Containers.COMPRESSOR.get(),                 GuiCompressor::new);
    MenuScreens.register(Containers.ELECTRIC_FURNACE.get(),           GuiElectricFurnace::new);
    MenuScreens.register(Containers.CIRCUIT_FABRICATOR.get(),         CircuitFabricatorGui::new);
    MenuScreens.register(Containers.UNIVERSAL_ENERGY_INTERFACE.get(), GuiUniversalEnergyInterface::new);
  }

  private static final void set_block_render_types(){
    ItemBlockRenderTypes.setRenderLayer(EnergyBlocks.energy_storage.get(), RenderType.translucent());
  }

}
