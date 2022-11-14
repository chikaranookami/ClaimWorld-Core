package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerBedLeaveEvent implements Listener {

    @EventHandler
    public void playerBedLeaveEvent(org.bukkit.event.player.PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getTime() != 0) return;

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            TaskManager.getInstance().tryFinishTask(player, new Task("Przespij 4 noce.", "counter", 4));
        });
    }
}
