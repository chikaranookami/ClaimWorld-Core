package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.tasks.TaskManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class CraftItemEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();

    @EventHandler
    public void craftItemEvent(org.bukkit.event.inventory.CraftItemEvent event) {
        Material material = event.getRecipe().getResult().getType();

        if (material == Material.GRINDSTONE) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask((Player) event.getWhoClicked(), taskManager.getTaskMap().get("craftGrindstone")));
            return;
        }

        if (material != Material.BEACON) return;

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask((Player) event.getWhoClicked(), taskManager.getTaskMap().get("craftBeacon")));
    }
}
