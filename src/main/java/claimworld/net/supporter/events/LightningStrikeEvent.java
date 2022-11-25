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

    @EventHandler
    public void lightningStrikeEvent(org.bukkit.event.weather.LightningStrikeEvent event) {
        if (event.getCause() != org.bukkit.event.weather.LightningStrikeEvent.Cause.COMMAND) return;

        for (Entity entity : event.getLightning().getNearbyEntities(5, 5, 5)) {
            if (!(entity instanceof Player)) continue;
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> TaskManager.getInstance().tryFinishTask((Player) entity, new Task("Oberwij piorunem od admina.", "", 0)));
        }
    }
}
