package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.tasks.Task;
import claimworld.net.supporter.tasks.TaskManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerItemBreakEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();

    @EventHandler
    public void playerItemBreakEvent(org.bukkit.event.player.PlayerItemBreakEvent event) {
        Player player = event.getPlayer();
        Material material = event.getBrokenItem().getType();
        Map<String, Task> taskMap = taskManager.getTaskMap();

        if (material == Material.STONE_SWORD) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(event.getPlayer(), taskMap.get("destroySword")));
            return;
        }

        if (material != Material.DIAMOND_PICKAXE) return;

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("destroyPickaxe")));
    }
}
