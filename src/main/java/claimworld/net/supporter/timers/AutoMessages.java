package claimworld.net.supporter.timers;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.GoalUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class AutoMessages {

    private void prepareBossBarTask(BossBar bossBar, int id) {
        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
            bossBar.setProgress(0.0);

            for (Player player : getOnlinePlayers()) {
                bossBar.addPlayer(player);
            }

            getServer().getConsoleSender().sendMessage("Wyswietlanie bossbara " + id);

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

            getServer().getConsoleSender().sendMessage("Zakonczono wyswietlanie bossbara " + id);
        }, id * 144000L);
    }

    public AutoMessages() {
        getServer().getConsoleSender().sendMessage("Przygotowywanie bossbarow...");

        org.bukkit.boss.BossBar vipBossbar = Bukkit.createBossBar(colorize("Nadchodzaca przepustka i jeszcze wiecej mozliwosci! &c/vip"), BarColor.RED, BarStyle.SOLID);

        prepareBossBarTask(vipBossbar, 1);

        FileConfiguration config = Supporter.getPlugin().getConfig();
        int currentValue = config.getInt("goals.total");
        int currentGoal = config.getInt("goals.active_goal");
        org.bukkit.boss.BossBar shopGoalBossbar = Bukkit.createBossBar(colorize("Cel w Sklepie: " + new GoalUtils().getReward(currentGoal) + ". Postep: &b" + currentValue + "&f/&b" + currentGoal), BarColor.BLUE, BarStyle.SOLID);

        prepareBossBarTask(shopGoalBossbar, 2);
    }
}
