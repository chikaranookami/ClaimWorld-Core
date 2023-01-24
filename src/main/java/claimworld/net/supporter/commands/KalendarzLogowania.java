package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.logging.Level;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static org.bukkit.Bukkit.*;

public class KalendarzLogowania {

    private final String dailyObjectiveName = "dailyRewardCounter";

    public KalendarzLogowania() {
        new CommandBase("kalendarzupdate", 2, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                int dailyAmount = Integer.parseInt(arguments[1]);
                if (dailyAmount == 0) {
                    getLogger().log(Level.INFO, "dailyamount = 0 (kalendarzupdate)");
                    return true;
                }

                String playerName = arguments[0];
                if (!getOfflinePlayer(playerName).hasPlayedBefore()) {
                    getLogger().log(Level.INFO, "attempt to force a daily reward by " + playerName);
                    return true;
                }

                Scoreboard scoreboard = getServer().getScoreboardManager().getMainScoreboard();
                Score score = scoreboard.getObjective(dailyObjectiveName).getScore(playerName);

                int finalAmount = score.getScore() + 1;
                score.setScore(finalAmount);

                int modulo = finalAmount % 10;
                if (finalAmount > 9 && modulo == 0) {
                    dispatchCommand(getConsoleSender(), "buychests 1");
                } else {
                    dispatchCommand(getConsoleSender(), "addtowarehouse " + playerName + " Skrzynia_smoka");
                }

                Player player = Bukkit.getPlayer(playerName);
                if (player != null) player.sendMessage(getUserPrefix() + "Przyznano nagrode ze sklepu.");

                return true;
            }

            @Override
            public String getUsage() {
                return "/kalendarzupdate <amount>";
            }
        }.setPermission("claimworld.player");
    }
}
