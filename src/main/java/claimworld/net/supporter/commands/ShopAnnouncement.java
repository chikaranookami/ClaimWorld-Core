package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.ActiveBossBar;
import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandSender;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class ShopAnnouncement {
    public ShopAnnouncement() {
        new CommandBase("shopannouncement", 1, 99, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                BossBar bossBar = Bukkit.createBossBar(colorize("&e✦ " + String.join(" ", arguments) + " &e✦"), BarColor.YELLOW, BarStyle.SOLID);
                new ActiveBossBar(bossBar);
                return true;
            }

            @Override
            public String getUsage() {
                return "/shopannouncement <text>";
            }
        }.setPermission("claimworld.admin");
    }
}
