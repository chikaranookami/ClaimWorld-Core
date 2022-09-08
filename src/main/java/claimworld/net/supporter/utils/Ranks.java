package claimworld.net.supporter.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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

    public final String getDesign(Player player) {
        if (player.hasPermission("claimworld.admin")) {
            return ChatColor.LIGHT_PURPLE + "[CEO]" + ChatColor.RESET + " " + player.getName();
        }
        if (player.hasPermission("claimworld.mod")) {
            return ChatColor.GREEN + "[MOD]" + ChatColor.RESET + " " + player.getName();
        }
        if (player.hasPermission("claimworld.mvp")) {
            return ChatColor.RED + "[MVP]" + ChatColor.RESET + " " + player.getName();
        }
        if (player.hasPermission("claimworld.vip+")) {
            return ChatColor.YELLOW + "[VIP+]" + ChatColor.RESET + " " + player.getName();
        }
        if (player.hasPermission("claimworld.vip")) {
            return ChatColor.AQUA + "[VIP]" + ChatColor.RESET + " " + player.getName();
        }
        if (player.hasPermission("claimworld.player")) {
            return ChatColor.GRAY + "[GRA]" + ChatColor.RESET + " " + player.getName();
        }
        else {
            return null;
        }
    }
}
