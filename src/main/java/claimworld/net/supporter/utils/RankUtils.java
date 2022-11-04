package claimworld.net.supporter.utils;

import org.bukkit.entity.Player;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class RankUtils {
    public void updateRank(Player player) {
        player.setPlayerListName(getDesign(player));
        player.setDisplayName(getDesign(player));
    }

    public final String getSpecialRankName(String value) {
        switch (value) {
            case "weteran":
                return colorize("&e&l❝ &eWeteran &e&l❞");
            case "ksiadz":
                return colorize("&e&l♰ &eKsiadz &e&l♰");
            case "bog":
                return colorize("&e&leψ &eBog &e&leψ");
            case "szczalowy":
                return colorize("&e&l➹ &eSzczalowy &e&l➹");
            case "shitmaster":
                return colorize("&e&l๑ &eShit Master &e&l๑");
        }

        return "";
    }

    public final String getSpecialRankName(Player player) {
        if (player.hasPermission("claimworld.specialrank.weteran")) {
            return getSpecialRankName("weteran");
        }
        if (player.hasPermission("claimworld.specialrank.ksiadz")) {
            return getSpecialRankName("ksiadz");
        }
        if (player.hasPermission("claimworld.specialrank.bog")) {
            return getSpecialRankName("bog");
        }
        if (player.hasPermission("claimworld.specialrank.szczalowy")) {
            return getSpecialRankName("szczalowy");
        }
        if (player.hasPermission("claimworld.specialrank.shitmaster")) {
            return getSpecialRankName("shitmaster");
        }
        else {
            return getSpecialRankName("");
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
            return colorize(getRankName("moderator") + "&r " + player.getName());
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
