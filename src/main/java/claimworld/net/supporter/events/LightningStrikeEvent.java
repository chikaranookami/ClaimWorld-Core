package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class LightningStrikeEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();

    @EventHandler
    public void lightningStrikeEvent(org.bukkit.event.weather.LightningStrikeEvent event) {
        for (Entity entity : event.getLightning().getNearbyEntities(2, 2, 2)) {
            if (!(entity instanceof Player)) continue;
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask((Player) entity, taskManager.getTaskMap().get("getHitByLightningStrike")));
        }
    }
}
