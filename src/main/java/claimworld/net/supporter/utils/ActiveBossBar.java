package claimworld.net.supporter.utils;

import claimworld.net.supporter.Supporter;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicReference;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getScheduler;

public class ActiveBossBar {
    public void render(BossBar bossBar) {
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
    }
}
