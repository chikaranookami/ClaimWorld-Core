package claimworld.net.supporter.utils.announcers;

import claimworld.net.supporter.Supporter;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicReference;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getScheduler;

public class ActiveBossBar {

    public void render(BossBar bossBar) {
        render(bossBar, 4);
    }

    public void render(BossBar bossBar, long period) {
        bossBar.setProgress(0.0);

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            for (Player player : getOnlinePlayers()) {
                bossBar.addPlayer(player);
            }
        });

        AtomicReference<Double> d = new AtomicReference<>(0.0);

        getScheduler().scheduleSyncRepeatingTask(Supporter.getPlugin(), () -> {
            if (d.get() >= 1.0) {
                bossBar.removeAll();
                return;
            }

            bossBar.setProgress(d.get());
            d.updateAndGet(v -> (v + 0.01));

        }, 20L, period);
    }
}
