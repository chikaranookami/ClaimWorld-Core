package claimworld.net.supporter.timers;

import claimworld.net.supporter.Supporter;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class AutoMessages {
    public AutoMessages() {
        org.bukkit.boss.BossBar vipBossbar = Bukkit.createBossBar(colorize("Nadchodzaca przepustka i jeszcze wiecej mozliwosci! &c/vip"), BarColor.RED, BarStyle.SOLID);
        org.bukkit.boss.BossBar progressBossbar = Bukkit.createBossBar(colorize(""), BarColor.GREEN, BarStyle.SOLID);

        getServer().getConsoleSender().sendMessage("Przygotowywanie bossbarow...");

        vipBossbar.setProgress(0.0);

        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
            for (Player player : getOnlinePlayers()) {
                vipBossbar.addPlayer(player);
            }

            getServer().getConsoleSender().sendMessage("Wyswietlanie bossbara #1...");

            new BukkitRunnable() {
                double d = 0.0;

                @Override
                public void run() {
                    if (d >= 1.0) {
                        vipBossbar.removeAll();
                        cancel();
                        return;
                    }

                    vipBossbar.setProgress(d);
                    d += 0.01;
                }
            }.runTaskTimer(Supporter.getPlugin(), 20L, 4L);

            getServer().getConsoleSender().sendMessage("Zakonczono wyswietlanie bossbara #1.");
        }, 144000L);
    }
}
