package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.announcers.ActiveBossBar;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getScheduler;

public class ShopAnnouncement {

    TaskManager taskManager = TaskManager.getInstance();

    public ShopAnnouncement() {
        new CommandBase("shopannouncement", 1, 99, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                BossBar bossBar = Bukkit.createBossBar(colorize("&e✦ " + String.join(" ", arguments) + " &e✦"), BarColor.YELLOW, BarStyle.SOLID);
                ActiveBossBar activeBossBar = new ActiveBossBar();
                activeBossBar.render(bossBar);

                getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
                    for (Player player : getOnlinePlayers()) {
                        taskManager.tryFinishTask(player, taskManager.getTaskMap().get("beOnlineWhenPlayerBuysFromShop"));
                    }
                });
                return true;
            }

            @Override
            public String getUsage() {
                return "/shopannouncement <text>";
            }
        }.setPermission("claimworld.admin");
    }
}
