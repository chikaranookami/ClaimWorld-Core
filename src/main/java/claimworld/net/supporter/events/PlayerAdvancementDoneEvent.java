package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerAdvancementDoneEvent implements Listener {

    @EventHandler
    public void playerAdvancementDoneEvent(org.bukkit.event.player.PlayerAdvancementDoneEvent event) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> TaskManager.getInstance().tryFinishTask(event.getPlayer(), new Task("Ukoncz osiagniecie.", "", 0)));
    }
}
