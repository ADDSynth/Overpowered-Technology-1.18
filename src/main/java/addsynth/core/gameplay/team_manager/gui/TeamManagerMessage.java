package addsynth.core.gameplay.team_manager.gui;

import addsynth.core.util.color.ColorCode;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

public final class TeamManagerMessage {

  public static final MutableComponent must_specify_name     = new TranslatableComponent("gui.addsynthcore.team_manager.message.id_required").withStyle(ColorCode.ERROR);
  public static final MutableComponent cannot_contain_spaces = new TranslatableComponent("gui.addsynthcore.team_manager.message.no_spaces").withStyle(ColorCode.ERROR);
  public static final MutableComponent must_be_shorter       = new TranslatableComponent("gui.addsynthcore.team_manager.message.must_be_shorter_than_16_characters").withStyle(ColorCode.ERROR);
  public static final MutableComponent name_already_exists   = new TranslatableComponent("gui.addsynthcore.team_manager.message.name_already_exists").withStyle(ColorCode.WARNING);
  public static final MutableComponent must_specify_criteria = new TranslatableComponent("gui.addsynthcore.team_manager.message.criteria_required").withStyle(ColorCode.ERROR);
  public static final MutableComponent score_is_readonly     = new TranslatableComponent("gui.addsynthcore.team_manager.message.readonly_score");

}
