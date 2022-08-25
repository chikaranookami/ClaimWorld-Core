package claimworld.net.supporter.timers;

import claimworld.net.supporter.Supporter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getServer;

public class AutoMessages {
    public AutoMessages() {
        org.bukkit.boss.BossBar bossBar1 = Bukkit.createBossBar("Nowe mozliwosci, drugie zycie! Wiecej pod " + ChatColor.AQUA + "/vip", BarColor.BLUE, BarStyle.SOLID);
        BukkitScheduler scheduler = Bukkit.getScheduler();

        getServer().getConsoleSender().sendMessage("Przygotowywanie bossbarow...");

        bossBar1.setProgress(0.0);

        scheduler.runTaskLater(Supporter.getInstance(), () -> {
            for (Player player : getOnlinePlayers()) {
                bossBar1.addPlayer(player);
            }

            getServer().getConsoleSender().sendMessage("Wyswietlanie bossbara ##1...");

            new BukkitRunnable() {
                double d = 0.0;

                @Override
                public void run() {
                    if (d >= 1.0) {
                        bossBar1.removeAll();
                        cancel();
                        return;
                    }

                    bossBar1.setProgress(d);
                    d += 0.01;
                }
            }.runTaskTimer(Supporter.getInstance(), 20L, 4L);

            getServer().getConsoleSender().sendMessage("Zakonczono wyswietlanie bossbara ##1.");
        }, 144000L);

        scheduler.runTaskLater(Supporter.getInstance(), () -> {
            for (Player player : getOnlinePlayers()) {
                bossBar1.addPlayer(player);
            }

            getServer().getConsoleSender().sendMessage("Wyswietlanie bossbara ##1...");

            new BukkitRunnable() {
                double d = 0.0;

                @Override
                public void run() {
                    if (d >= 1.0) {
                        bossBar1.removeAll();
                        cancel();
                        return;
                    }

                    bossBar1.setProgress(d);
                    d += 0.01;
                }
            }.runTaskTimer(Supporter.getInstance(), 20L, 4L);

            getServer().getConsoleSender().sendMessage("Zakonczono wyswietlanie bossbara ##1.");
        }, 288000L);
    }
}
