package claimworld.net.supporter.utils;

import claimworld.net.supporter.utils.wip.ErrorMessages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class Ranks {
    public void updateRank(Player player) {
        String newPlayerListName = getDesign(player);

        if (newPlayerListName == null) {
            ErrorMessages errorMessages = new ErrorMessages();
            player.sendMessage(errorMessages.getUserResponse(0));
            Bukkit.getConsoleSender().sendMessage(errorMessages.getByCode(0));
            return;
        }

        player.setPlayerListName(getDesign(player));
        player.setDisplayName(getDesign(player));
    }

    public final String getRankName(Player player) {
        if (player.hasPermission("claimworld.admin")) {
            return colorize("&dAdmin");
        }
        if (player.hasPermission("claimworld.mod")) {
            return colorize("&aModerator");
        }
        if (player.hasPermission("claimworld.mvp")) {
            return colorize("&cMVP");
        }
        if (player.hasPermission("claimworld.vip+")) {
            return colorize("&eVIP+");
        }
        if (player.hasPermission("claimworld.vip")) {
            return colorize("&bVIP");
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
            return colorize("&d[ADM]&r " + player.getName());
        }
        if (player.hasPermission("claimworld.mod")) {
            return colorize("&a[MOD]&r " + player.getName());
        }
        if (player.hasPermission("claimworld.mvp")) {
            return colorize("&c[MVP]&r " + player.getName());
        }
        if (player.hasPermission("claimworld.vip+")) {
            return colorize("&e[VIP+]&r " + player.getName());
        }
        if (player.hasPermission("claimworld.vip")) {
            return colorize("&b[VIP]&r " + player.getName());
        }
        if (player.hasPermission("claimworld.player")) {
            return colorize("&7[GRA]&r " + player.getName());
        }
        else {
            return null;
        }
    }
}
