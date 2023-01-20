package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.tasks.TaskManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerRiptideEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();

    @EventHandler
    public void playerRiptideEvent(org.bukkit.event.player.PlayerRiptideEvent event) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(event.getPlayer(), taskManager.getTaskMap().get("riptideYourself")));
    }
}
