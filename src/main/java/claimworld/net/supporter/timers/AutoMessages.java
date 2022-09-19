package claimworld.net.supporter.timers;

import claimworld.net.supporter.Supporter;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getServer;

public class AutoMessages {
    public AutoMessages() {
        org.bukkit.boss.BossBar bossBar = Bukkit.createBossBar(colorize("Nowe mozliwosci, drugie zycie! Wiecej pod &b/vip"), BarColor.BLUE, BarStyle.SOLID);
        BukkitScheduler scheduler = Bukkit.getScheduler();

        getServer().getConsoleSender().sendMessage("Przygotowywanie bossbarow...");

        bossBar.setProgress(0.0);

        scheduler.runTaskLater(Supporter.getPlugin(), () -> {
            for (Player player : getOnlinePlayers()) {
                bossBar.addPlayer(player);
            }

            getServer().getConsoleSender().sendMessage("Wyswietlanie bossbara #1...");

            new BukkitRunnable() {
                double d = 0.0;

                @Override
                public void run() {
                    if (d >= 1.0) {
                        bossBar.removeAll();
                        cancel();
                        return;
                    }

                    bossBar.setProgress(d);
                    d += 0.01;
                }
            }.runTaskTimer(Supporter.getPlugin(), 20L, 4L);

            getServer().getConsoleSender().sendMessage("Zakonczono wyswietlanie bossbara #1.");
        }, 144000L);
    }
}
