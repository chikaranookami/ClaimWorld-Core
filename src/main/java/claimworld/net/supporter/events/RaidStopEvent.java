package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class RaidStopEvent implements Listener {

    @EventHandler
    public void raidStopEvent(org.bukkit.event.raid.RaidStopEvent event) {
        if (event.getReason() != org.bukkit.event.raid.RaidStopEvent.Reason.TIMEOUT) return;

        for (Player player : event.getWorld().getPlayers()) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> TaskManager.getInstance().tryFinishTask(player, new Task("Przeczekaj atak na wioske.", "", 0)));
        }
    }
}
