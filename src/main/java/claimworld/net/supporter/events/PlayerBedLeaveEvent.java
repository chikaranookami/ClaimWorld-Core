package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerBedLeaveEvent implements Listener {

    private final List<Player> delayedPlayers = new ArrayList<>();

    @EventHandler
    public void playerBedLeaveEvent(org.bukkit.event.player.PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getTime() != 0) return;
        if (delayedPlayers.contains(player)) return;
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            delayedPlayers.add(player);
            TaskManager.getInstance().tryFinishTask(player, new Task("Przespij 3 noce.", "counter", 3));

            getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                delayedPlayers.remove(player);
            }, 11000);
        });
    }
}
