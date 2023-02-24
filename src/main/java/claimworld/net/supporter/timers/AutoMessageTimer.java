package claimworld.net.supporter.timers;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.announcers.ActiveBossBar;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

import java.util.Random;
import java.util.logging.Level;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class AutoMessageTimer {

    public AutoMessageTimer() {
        getLogger().log(Level.INFO, "[" + getClass().getSimpleName() + "] Przygotowywanie...");

        if (new Random().nextInt(4) != 0) return;

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () ->{
            org.bukkit.boss.BossBar vipBossbar = Bukkit.createBossBar(colorize("Podoba Ci sie &cprojekt&f? Chcesz, by sie rozwijal? Kup &c/vip&f"), BarColor.RED, BarStyle.SOLID);
            org.bukkit.boss.BossBar shopGoalBossbar = Bukkit.createBossBar(colorize("&eDarmowe Skrzynie Smoka&f juz na &eShop.ClaimWorld.net"), BarColor.YELLOW, BarStyle.SOLID);
            ActiveBossBar activeBossBar = new ActiveBossBar();

            //1h
            long time = 144000;

            if (new Random().nextBoolean()) {
                getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> activeBossBar.render(vipBossbar), time);
            } else {
                getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> activeBossBar.render(vipBossbar), time * 5);
            }

            getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> activeBossBar.render(shopGoalBossbar), time * 3);
        });
    }
}
