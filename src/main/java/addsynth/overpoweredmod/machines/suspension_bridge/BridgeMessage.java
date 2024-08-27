package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.core.util.color.ColorCode;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

public enum BridgeMessage {

  PENDING       (ColorCode.ERROR, "gui.overpowered.bridge_message.checking",      false),
  INVALID_BRIDGE(ColorCode.ERROR, "gui.overpowered.bridge_message.invalid",       false),
  INVALID_SHAPE (ColorCode.ERROR, "gui.overpowered.bridge_message.invalid_shape", false),
  OKAY          (ColorCode.GOOD,  "gui.overpowered.bridge_message.okay",          true),
  OBSTRUCTED    (ColorCode.ERROR, "gui.overpowered.bridge_message.obstructed",    false),
  NO_BRIDGE     (null,            "gui.overpowered.bridge_message.no_bridge",     false),
  NO_LENS       (ColorCode.ERROR, "gui.overpowered.bridge_message.no_lens",       false),
  OFF           (null,            "gui.overpowered.bridge_message.off",           true),
  ACTIVE        (ColorCode.GOOD,  "gui.overpowered.bridge_message.active",        true);

  private final MutableComponent bridge_message;
  private final ChatFormatting color;
  private final boolean valid;
  
  private BridgeMessage(final ChatFormatting code, final String translation_key, final boolean valid){
    this.bridge_message = new TranslatableComponent(translation_key);
    this.color = code;
    this.valid = valid;
  }

  public final Component getMessage(){
    return color != null ? bridge_message.withStyle(color) : bridge_message;
  }

  public final boolean is_valid(){
    return valid;
  }

}
