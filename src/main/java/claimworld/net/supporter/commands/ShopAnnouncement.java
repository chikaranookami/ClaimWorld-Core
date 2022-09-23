package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class ShopAnnouncement {
    public ShopAnnouncement() {
        new CommandBase("shopannouncement", 1, 99, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Bukkit.broadcastMessage(colorize("&e[✦ SKLEP ✦]&f " + String.join(" ", arguments)));
                return true;
            }

            @Override
            public String getUsage() {
                return "/shopannouncement <text>";
            }
        }.setPermission("claimworld.admin");
    }
}
