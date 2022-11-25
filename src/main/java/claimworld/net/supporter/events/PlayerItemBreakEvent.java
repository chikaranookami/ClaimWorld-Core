package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerItemBreakEvent implements Listener {

    @EventHandler
    public void playerItemBreakEvent(org.bukkit.event.player.PlayerItemBreakEvent event) {
        Player player = event.getPlayer();
        Material material = event.getBrokenItem().getType();

        if (material == Material.NETHERITE_SWORD) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
                TaskManager.getInstance().tryFinishTask(event.getPlayer(), new Task("Zuzyj netherytowy miecz.", "", 0));
            });
            return;
        }

        if (material != Material.DIAMOND_PICKAXE) return;

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            TaskManager.getInstance().tryFinishTask(player, new Task("Zuzyj diamentowy kilof.", "", 0));
        });
    }
}
