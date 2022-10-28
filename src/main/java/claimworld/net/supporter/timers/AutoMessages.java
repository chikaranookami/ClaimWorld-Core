package claimworld.net.supporter.timers;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.ActiveBossBar;
import claimworld.net.supporter.utils.GoalUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.file.FileConfiguration;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class AutoMessages {

    final long time = 144000;

    public AutoMessages() {
        getServer().getConsoleSender().sendMessage("Przygotowywanie bossbarow...");

        FileConfiguration config = Supporter.getPlugin().getConfig();
        int currentValue = config.getInt("goals.total");
        int currentGoal = config.getInt("goals.active_goal");
        org.bukkit.boss.BossBar shopGoalBossbar = Bukkit.createBossBar(colorize("Cel w Sklepie: &b" + new GoalUtils().getReward(currentGoal) + "&f. Postep: &b" + currentValue + "&f/&b" + currentGoal), BarColor.BLUE, BarStyle.SOLID);
        org.bukkit.boss.BossBar vipBossbar = Bukkit.createBossBar(colorize("Nadchodzaca &cprzepustka bojowa&f i jeszcze wiecej mozliwosci! &c/vip"), BarColor.RED, BarStyle.SOLID);

        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {new ActiveBossBar(vipBossbar);}, time);
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {new ActiveBossBar(shopGoalBossbar);}, time * 2);
    }
}
