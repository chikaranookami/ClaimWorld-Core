package claimworld.net.supporter.timers;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.announcers.ActiveBossBar;
import claimworld.net.supporter.utils.GoalUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Random;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class AutoMessageTimer {

    GoalUtils goalUtils = GoalUtils.getInstance();

    public AutoMessageTimer() {
        getServer().getConsoleSender().sendMessage("Przygotowywanie bossbarow...");

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () ->{
            org.bukkit.boss.BossBar vipBossbar = Bukkit.createBossBar(colorize("Podoba Ci sie projekt? Chcesz, by sie rozwijal? Kup &c/vip&f"), BarColor.RED, BarStyle.SOLID);
            org.bukkit.boss.BossBar shopGoalBossbar = Bukkit.createBossBar(colorize("Cel w Sklepie: &e" + goalUtils.getCurrentReward() + "&f. Brakuje: &e" + goalUtils.getMissingAmount() + "zl"), BarColor.YELLOW, BarStyle.SOLID);
            ActiveBossBar activeBossBar = new ActiveBossBar();

            //1h
            long time = 144000;

            if (new Random().nextBoolean()) {
                getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> activeBossBar.render(vipBossbar), time);
            } else {
                getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> activeBossBar.render(vipBossbar), time * 3);
            }

            getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> activeBossBar.render(shopGoalBossbar), time * 2);
        });
    }
}
