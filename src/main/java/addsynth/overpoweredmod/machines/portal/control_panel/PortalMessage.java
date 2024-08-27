package addsynth.overpoweredmod.machines.portal.control_panel;

import addsynth.core.util.color.ColorCode;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;

public enum PortalMessage {

  NO_DATA_CABLE         (ColorCode.ERROR, "gui.overpowered.portal_message.no_data_cable"),
  REQUIRE_PORTAL_FRAMES (ColorCode.ERROR, "gui.overpowered.portal_message.need_portal_frames"),
  TOO_MANY_PORTAL_FRAMES(ColorCode.ERROR, "gui.overpowered.portal_message.too_many_portal_frames"),
  PORTAL_NOT_CONSTRUCTED(ColorCode.ERROR, "gui.overpowered.portal_message.invalid_construction"),
  OBSTRUCTED            (ColorCode.ERROR, "gui.overpowered.portal_message.obstructed"),
  OFF                   (null,            "gui.overpowered.portal_message.off"),
  INCORRECT_ITEMS       (ColorCode.ERROR, "gui.overpowered.portal_message.incorrect_items"),
  NEEDS_ENERGY          (ColorCode.ERROR, "gui.overpowered.portal_message.needs_energy"),
  CREATIVE_MODE         (null,            "gui.overpowered.portal_message.creative_mode"),
  PORTAL_READY          (ColorCode.GOOD,  "gui.overpowered.portal_message.ready");

  private final TranslatableComponent message;
  private final String formatting_code;

  private PortalMessage(final ChatFormatting code, final String translation_key){
    this.message = new TranslatableComponent(translation_key);
    this.formatting_code = code != null ? code.toString() : "";
  }

  public final String getMessage(){
    return formatting_code + message;
  }

}
