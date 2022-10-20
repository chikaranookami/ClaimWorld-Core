package claimworld.net.supporter.timers;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.GoalUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicReference;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class AutoMessages {

    long time = 144000;

    private void prepareBossBarTask(BossBar bossBar, int id) {
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
            bossBar.setProgress(0.0);

            for (Player player : getOnlinePlayers()) {
                bossBar.addPlayer(player);
            }

            AtomicReference<Double> d = new AtomicReference<>(0.0);

            getScheduler().scheduleSyncRepeatingTask(Supporter.getPlugin(), () -> {
                if (d.get() >= 1.0) {
                    bossBar.removeAll();
                    return;
                }

                bossBar.setProgress(d.get());
                d.updateAndGet(v -> (v + 0.01));
            }, 20L, 4L);
        }, id * time);
    }

    public AutoMessages() {
        getServer().getConsoleSender().sendMessage("Przygotowywanie bossbarow...");

        org.bukkit.boss.BossBar vipBossbar = Bukkit.createBossBar(colorize("Nadchodzaca &cprzepustka bojowa&f i jeszcze wiecej mozliwosci! &c/vip"), BarColor.RED, BarStyle.SOLID);

        prepareBossBarTask(vipBossbar, 1);

        FileConfiguration config = Supporter.getPlugin().getConfig();
        int currentValue = config.getInt("goals.total");
        int currentGoal = config.getInt("goals.active_goal");
        org.bukkit.boss.BossBar shopGoalBossbar = Bukkit.createBossBar(colorize("Cel w Sklepie: &b" + new GoalUtils().getReward(currentGoal) + "&f. Postep: &b" + currentValue + "&f/&b" + currentGoal), BarColor.BLUE, BarStyle.SOLID);

        prepareBossBarTask(shopGoalBossbar, 2);
    }
}
