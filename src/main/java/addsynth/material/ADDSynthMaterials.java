package addsynth.material;

import java.io.File;
import java.util.stream.Stream;
import addsynth.core.ADDSynthCore;
import addsynth.core.compat.Compatibility;
import addsynth.core.compat.EMCValue;
import addsynth.core.util.CommonUtil;
import addsynth.core.util.constants.DevStage;
import addsynth.material.compat.MaterialsCompat;
import addsynth.material.config.WorldgenConfig;
import addsynth.material.worldgen.OreGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.InterModComms.IMCMessage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = ADDSynthMaterials.MOD_ID)
public final class ADDSynthMaterials {

  public static final String MOD_ID = "addsynth_materials";
  public static final String MOD_NAME = "ADDSynth Materials";
  public static final String VERSION = "1.0";
  public static final String VERSION_DATE = ADDSynthCore.VERSION_DATE;
  public static final DevStage DEV_STAGE = ADDSynthCore.DEV_STAGE;

  public static final Logger log = LogManager.getLogger(MOD_NAME);

  public static final CreativeModeTab creative_tab = new CreativeModeTab(MOD_ID){
    @Override
    public final ItemStack makeIcon(){
      return new ItemStack(Material.SAPPHIRE.getGem());
    }
  };

  public ADDSynthMaterials(){
    final FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
    final IEventBus bus = context.getModEventBus();
    bus.addListener(ADDSynthMaterials::main_setup);
    bus.addListener(MaterialsCompat::sendIMCMessages);
    bus.addListener(ADDSynthMaterials::process_imc_messages);
    MinecraftForge.EVENT_BUS.addListener(ADDSynthMaterials::onServerStarted);
    MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ADDSynthMaterials::loadBiomes);
    MinecraftForge.EVENT_BUS.addGenericListener(Block.class, MaterialsRegister::onMissingBlockEntries);
    MinecraftForge.EVENT_BUS.addGenericListener(Item.class, MaterialsRegister::onMissingItemEntries);
    init_config();
  }

  private static final void init_config(){
    new File(FMLPaths.CONFIGDIR.get().toString(), MOD_NAME).mkdir();

    final ModLoadingContext context = ModLoadingContext.get();
    context.registerConfig(ModConfig.Type.COMMON, WorldgenConfig.CONFIG_SPEC, MOD_NAME+File.separator+"worldgen.toml");
  }

  private static final void main_setup(final FMLCommonSetupEvent event){
    // log.info("Begin ADDSynthMaterials main setup...");
    CommonUtil.displayModInfo(log, MOD_NAME, "ADDSynth", VERSION, DEV_STAGE, VERSION_DATE);
    // log.info("Finished ADDSynthMaterials main setup.");
  }

  public static void onServerStarted(final ServerStartedEvent event){
    if(Compatibility.PROJECT_E.loaded){
      if(DEV_STAGE.isDevelopment){
        EMCValue.check_items(MOD_ID);
      }
    }
  }

  public static final void loadBiomes(final BiomeLoadingEvent event){ // Just here temporarily
    OreGenerator.register(event.getCategory(), event.getGeneration());
  }

  private static final void process_imc_messages(final InterModProcessEvent event){
    final Stream<IMCMessage> message_stream = event.getIMCStream();
    message_stream.forEach(message -> {
      final String sender  = message.senderModId();
      final String type    = message.method();
      final Object payload = message.messageSupplier().get();
    });
  }

}
