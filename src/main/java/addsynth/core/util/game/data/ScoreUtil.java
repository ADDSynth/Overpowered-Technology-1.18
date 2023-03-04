package addsynth.core.util.game.data;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;

public final class ScoreUtil {

  public static final Score getScore(final MinecraftServer server, final String player_name, final String objective_name){
    final Scoreboard scoreboard = server.getScoreboard();
    final Objective objective = scoreboard.getObjective(objective_name);
    final Score score = scoreboard.getOrCreatePlayerScore(player_name, objective);
    return score;
  }

}
