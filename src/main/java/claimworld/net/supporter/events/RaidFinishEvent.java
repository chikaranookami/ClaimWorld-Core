package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class RaidFinishEvent implements Listener {

    @EventHandler
    public void raidFinishEvent(org.bukkit.event.raid.RaidFinishEvent event) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () ->{
            for (Player player : event.getWinners()) {
                TaskManager.getInstance().tryFinishTask(player, new Task("Ukoncz raida.", "", 0));
            }
        });
    }
}
