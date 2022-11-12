package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerItemBreakEvent implements Listener {

    @EventHandler
    public void playerItemBreakEvent(org.bukkit.event.player.PlayerItemBreakEvent event) {
        if (event.getBrokenItem().getType() != Material.NETHERITE_SWORD) return;

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            TaskManager.getInstance().tryFinishTask(event.getPlayer(), new Task("Zniszcz netherytowy miecz.", "", 0));
        });
    }
}
