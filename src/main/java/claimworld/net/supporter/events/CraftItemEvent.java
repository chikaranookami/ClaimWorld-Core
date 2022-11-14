package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class CraftItemEvent implements Listener {

    @EventHandler
    public void craftItemEvent(org.bukkit.event.inventory.CraftItemEvent event) {
        if (event.getRecipe().getResult().getType() != Material.BEACON) return;

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            TaskManager.getInstance().tryFinishTask((Player) event.getWhoClicked(), new Task("Zrob beacona.", "", 0));
        });
    }
}
