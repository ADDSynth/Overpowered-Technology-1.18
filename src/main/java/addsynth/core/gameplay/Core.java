package addsynth.core.gameplay;

import addsynth.core.game.blocks.TestBlock;
import addsynth.core.gameplay.blocks.CautionBlock;
import addsynth.core.gameplay.music_box.MusicBox;
import addsynth.core.gameplay.music_box.MusicSheet;
import addsynth.core.gameplay.team_manager.TeamManagerBlock;
import net.minecraft.world.level.block.Block;

public final class Core {

  // public static final TestBlock        test_block              = new TestBlock();

  public static final Block            caution_block           = new CautionBlock();
  public static final MusicBox         music_box               = new MusicBox();
  public static final MusicSheet       music_sheet             = new MusicSheet();
  public static final TeamManagerBlock team_manager            = new TeamManagerBlock();

}
