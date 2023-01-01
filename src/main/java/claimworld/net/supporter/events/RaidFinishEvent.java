package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.tasks.TaskManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class RaidFinishEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();

    @EventHandler
    public void raidFinishEvent(org.bukkit.event.raid.RaidFinishEvent event) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            for (Player player : event.getWinners()) {
                taskManager.tryFinishTask(player, taskManager.getTaskMap().get("winRaid"));
            }
        });
    }
}
