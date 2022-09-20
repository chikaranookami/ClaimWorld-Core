package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ShopAnnouncement {
    public ShopAnnouncement() {
        new CommandBase("shopannouncement", 1, 99, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Bukkit.broadcastMessage(Messages.getShopAnnouncementPrefix() + String.join(" ", arguments));
                return true;
            }

            @Override
            public String getUsage() {
                return "/shopannouncement <text>";
            }
        }.setPermission("claimworld.admin");
    }
}
