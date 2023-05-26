package addsynth.overpoweredmod.game;

import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.machines.gem_converter.CycleGemConverterMessage;
import addsynth.overpoweredmod.machines.laser.network_messages.LaserClientSyncMessage;
import addsynth.overpoweredmod.machines.laser.network_messages.SetLaserDistanceMessage;
import addsynth.overpoweredmod.machines.plasma_generator.SetOutputThresholdMessage;
import addsynth.overpoweredmod.machines.portal.control_panel.GeneratePortalMessage;
import addsynth.overpoweredmod.machines.portal.control_panel.SyncPortalDataMessage;
import addsynth.overpoweredmod.machines.suspension_bridge.RotateBridgeMessage;
import addsynth.overpoweredmod.machines.suspension_bridge.SyncClientBridgeMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

// http://mcforge.readthedocs.io/en/latest/networking/simpleimpl/

public final class NetworkHandler {

  private static final String PROTOCAL_VERSION = "1";

  public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
    new ResourceLocation(OverpoweredTechnology.MOD_ID, "main_network_channel"),
    () -> PROTOCAL_VERSION, PROTOCAL_VERSION::equals, PROTOCAL_VERSION::equals
  );

  public static final void registerMessages(){
    INSTANCE.registerMessage(0,
      CycleGemConverterMessage.class,
      CycleGemConverterMessage::encode,
      CycleGemConverterMessage::decode,
      CycleGemConverterMessage::handle
    );
    INSTANCE.registerMessage(1,
      SetLaserDistanceMessage.class,
      SetLaserDistanceMessage::encode,
      SetLaserDistanceMessage::decode,
      SetLaserDistanceMessage::handle
    );
    INSTANCE.registerMessage(2,
      LaserClientSyncMessage.class,
      LaserClientSyncMessage::encode,
      LaserClientSyncMessage::decode,
      LaserClientSyncMessage::handle
    );
    INSTANCE.registerMessage(3,
      GeneratePortalMessage.class,
      GeneratePortalMessage::encode,
      GeneratePortalMessage::decode,
      GeneratePortalMessage::handle
    );
    INSTANCE.registerMessage(4,
      SyncPortalDataMessage.class,
      SyncPortalDataMessage::encode,
      SyncPortalDataMessage::decode,
      SyncPortalDataMessage::handle
    );
    INSTANCE.registerMessage(6,
      SyncClientBridgeMessage.class,
      SyncClientBridgeMessage::encode,
      SyncClientBridgeMessage::decode,
      SyncClientBridgeMessage::handle
    );

    INSTANCE.registerMessage(7,
      RotateBridgeMessage.class,
      RotateBridgeMessage::encode,
      RotateBridgeMessage::decode,
      RotateBridgeMessage::handle
    );
    
    INSTANCE.registerMessage(8,
      SetOutputThresholdMessage.class,
      SetOutputThresholdMessage::encode,
      SetOutputThresholdMessage::decode,
      SetOutputThresholdMessage::handle
    );
  }

}
