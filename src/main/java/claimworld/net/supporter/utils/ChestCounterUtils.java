package claimworld.net.supporter.utils;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getScoreboardManager;

public class ChestCounterUtils {

    private final Objective objective = getScoreboardManager().getMainScoreboard().getObjective("otworzoneSkrzynkiCounter");

    public int get(Player player) {
        return objective.getScore(player.getName()).getScore();
    }

    public void addOne(Player player) {
        String playerName = player.getName();

        Score score = objective.getScore(playerName);
        score.setScore(score.getScore() + 1);

        getLogger().log(Level.INFO, "updated chest counter of player " + playerName + " to value " + score.getScore());
    }
}
