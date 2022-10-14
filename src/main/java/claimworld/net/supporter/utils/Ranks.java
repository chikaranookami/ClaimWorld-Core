package claimworld.net.supporter.utils;

import claimworld.net.supporter.utils.wip.ErrorMessages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getOnlinePlayers;

public class Ranks {
    public void updateAllScoreboardSpecialRank() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        assert manager != null;
        Scoreboard scoreboard = manager.getNewScoreboard();

        for (Player player : getOnlinePlayers()) {
            if (player.hasPermission("claimworld.admin")) {
                Objective specialRank = scoreboard.registerNewObjective("admin", "dummy", getSpecialRankName(player));
                specialRank.setDisplaySlot(DisplaySlot.BELOW_NAME);
            }
            if (player.hasPermission("claimworld.mod")) {
                Objective specialRank = scoreboard.registerNewObjective("mod", "dummy", getSpecialRankName(player));
                specialRank.setDisplaySlot(DisplaySlot.BELOW_NAME);
            }
            if (player.hasPermission("claimworld.mvp")) {
                Objective specialRank = scoreboard.registerNewObjective("mvp", "dummy", getSpecialRankName(player));
                specialRank.setDisplaySlot(DisplaySlot.BELOW_NAME);
            }
            if (player.hasPermission("claimworld.vip+")) {
                Objective specialRank = scoreboard.registerNewObjective("vip+", "dummy", getSpecialRankName(player));
                specialRank.setDisplaySlot(DisplaySlot.BELOW_NAME);
            }
            if (player.hasPermission("claimworld.vip")) {
                Objective specialRank = scoreboard.registerNewObjective("vip", "dummy", getSpecialRankName(player));
                specialRank.setDisplaySlot(DisplaySlot.BELOW_NAME);
            }
            if (player.hasPermission("claimworld.player")) {
                Objective specialRank = scoreboard.registerNewObjective("player", "dummy", getSpecialRankName(player));
                specialRank.setDisplaySlot(DisplaySlot.BELOW_NAME);
            }

            player.setScoreboard(scoreboard);
        }
    }

    public void updateRank(Player player) {
        player.setPlayerListName(getDesign(player));
        player.setDisplayName(getDesign(player));
    }

    public final String getSpecialRankName(Player player) {
        if (player.hasPermission("claimworld.specialrank.weteran")) {
            return colorize("&e&l❝ &eWeteran &e&l❞");
        }
        if (player.hasPermission("claimworld.specialrank.ksiadz")) {
            return colorize("&e&l♰ &eKsiadz &e&l♰");
        }
        if (player.hasPermission("claimworld.specialrank.bog")) {
            return colorize("&e&leψ &eBog &e&leψ");
        }
        if (player.hasPermission("claimworld.specialrank.szczalowy")) {
            return colorize("&e&l➹ &eSzczalowy &e&l➹");
        }
        if (player.hasPermission("claimworld.specialrank.shitmaster")) {
            return colorize("&e&l๑ &eShit Master &e&l๑");
        }
        else {
            return colorize("&7&oBrak.");
        }
    }

    public final String getRankName(String rank) {
        switch (rank) {
            case "admin":
                return colorize("&d[Admin]");
            case "moderator":
                return colorize("&a[Modek]");
            case "mvp":
                return colorize("&c[&c&lMVP&c]");
            case "vip+":
                return colorize("&e[&e&lVIP+&e]");
            case "vip":
                return colorize("&b[&b&lVIP&b]");
            case "player":
                return colorize("&7[Gracz]");
        }

        return null;
    }

    public final String getRankName(Player player) {
        if (player.hasPermission("claimworld.admin")) {
            return colorize("&dAdmin");
        }
        if (player.hasPermission("claimworld.mod")) {
            return colorize("&aModerator");
        }
        if (player.hasPermission("claimworld.mvp")) {
            return colorize("&c&lMVP");
        }
        if (player.hasPermission("claimworld.vip+")) {
            return colorize("&e&lVIP+");
        }
        if (player.hasPermission("claimworld.vip")) {
            return colorize("&b&lVIP");
        }
        if (player.hasPermission("claimworld.player")) {
            return colorize("&7Gracz");
        }
        else {
            return null;
        }
    }

    public final String getDesign(Player player) {
        if (player.hasPermission("claimworld.admin")) {
            return colorize(getRankName("admin") + "&r " + player.getName());
        }
        if (player.hasPermission("claimworld.mod")) {
            return colorize(getRankName("mod") + "&r " + player.getName());
        }
        if (player.hasPermission("claimworld.mvp")) {
            return colorize(getRankName("mvp") + "&r " + player.getName());
        }
        if (player.hasPermission("claimworld.vip+")) {
            return colorize(getRankName("vip+") + "&r " + player.getName());
        }
        if (player.hasPermission("claimworld.vip")) {
            return colorize(getRankName("vip") + "&r " + player.getName());
        }
        if (player.hasPermission("claimworld.player")) {
            return colorize(getRankName("player") + "&r " + player.getName());
        }
        else {
            return "";
        }
    }
}
