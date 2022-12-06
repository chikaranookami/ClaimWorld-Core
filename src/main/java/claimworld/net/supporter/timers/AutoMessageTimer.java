package claimworld.net.supporter.timers;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.announcers.ActiveBossBar;
import claimworld.net.supporter.utils.GoalUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.file.FileConfiguration;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class AutoMessageTimer {

    public AutoMessageTimer() {
        getServer().getConsoleSender().sendMessage("Przygotowywanie bossbarow...");

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () ->{
            FileConfiguration config = Supporter.getPlugin().getConfig();
            int currentValue = config.getInt("goals.total");
            int currentGoal = config.getInt("goals.active_goal");

            org.bukkit.boss.BossBar vipBossbar = Bukkit.createBossBar(colorize("Podoba Ci sie projekt? Chcesz, by sie rozwijal? Kup &c/vip&f"), BarColor.RED, BarStyle.SOLID);
            org.bukkit.boss.BossBar shopGoalBossbar = Bukkit.createBossBar(colorize("Cel w Sklepie: &b" + new GoalUtils().getReward(currentGoal) + "&f. Postep: &b" + currentValue + "&f/&b" + currentGoal), BarColor.BLUE, BarStyle.SOLID);
            ActiveBossBar activeBossBar = new ActiveBossBar();

            //2h
            long time = 144000;

            getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                activeBossBar.render(vipBossbar);
            }, time);
            getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                activeBossBar.render(shopGoalBossbar);
            }, time * 2);
        });
    }
}
