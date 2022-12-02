package claimworld.net.supporter.timers;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.WarehouseUtils;
import claimworld.net.supporter.utils.battlepass.BattlePassManager;
import claimworld.net.supporter.utils.items.ReadyItems;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static org.bukkit.Bukkit.*;

public class WinterEventTimers {

    private Player getPlayer() {
        Optional<? extends Player> optionalPlayer = getOnlinePlayers().stream().findFirst();
        return optionalPlayer.orElse(null);
    }

    private void renderSnow(Player player) {
        new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                int radius = 2;
                for (int x = radius; x >= -radius; x--) {
                    for (int z = radius; z >= -radius; z--) {
                        int finalX = x;
                        int finalZ = z;
                        getScheduler().runTaskLater(Supporter.getPlugin(), () ->
                                        player.getWorld().spawnParticle(
                                                Particle.SNOWFLAKE,
                                                player.getLocation().add(finalX, 5, finalZ),
                                                2, 0, 0, 0, 0
                                        )
                                , new Random().nextInt(20));
                    }
                }

                counter.getAndIncrement();
                if (counter.get() >= 16 || !player.isOnline()) {
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(Supporter.getPlugin(), 0, 10);
    }

    private void playSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
    }

    public WinterEventTimers() {
        getServer().getConsoleSender().sendMessage("Przygotowywanie swiatecznych timerow...");

        //1h
        long prezentTime = 72000;
        getScheduler().runTaskTimerAsynchronously(Supporter.getPlugin(), () -> {
            Player player = getPlayer();
            if (player == null) return;

            new WarehouseUtils().addItemsSingle(player, Collections.singletonList(ReadyItems.getInstance().get("Prezent")));
            renderSnow(player);

            getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                TaskManager.getInstance().tryFinishTask(player, new Task("Otrzymaj Prezent od Swietego Mikolaja, po prostu grajac.", "", 0));
                player.sendMessage(getUserPrefix() + "Otrzymales prezent od Swietego Mikolaja!");
            }, 10L);

            getScheduler().runTask(Supporter.getPlugin(), () -> playSound(player));
        }, 0, prezentTime);

        //30m
        long snowTime = 36000;
        getScheduler().runTaskTimerAsynchronously(Supporter.getPlugin(), () -> {
            if (getOnlinePlayers().size() < 2) return;
            if (new Random().nextInt(10) != 0) return;

            Player player = getPlayer();

            getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                player.sendMessage(getUserPrefix() + "Otrzymales Swiateczne Blogoslawienstwo.");

                TaskManager.getInstance().tryFinishTask(player, new Task("Otrzymaj Swiateczne Blogoslawienstwo, po prostu grajac.", "", 0));
                new WarehouseUtils().addItemsSingle(player, Collections.singletonList(ReadyItems.getInstance().get("Prezent")));
                renderSnow(player);

                getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                    if (new Random().nextInt(8) != 0) return;

                    BattlePassManager.getInstance().updatePoints(player, 1, false);
                    player.sendMessage(getUserPrefix() + "Wow, Mikolaj przyniosl Ci tez awans na kolejny poziom przepustki!");
                }, 10L);

                getScheduler().runTask(Supporter.getPlugin(), () -> playSound(player));
            }, 24L);
        }, snowTime, snowTime);
    }
}
