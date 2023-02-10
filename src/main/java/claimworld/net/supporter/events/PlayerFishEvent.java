package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.tasks.TaskManager;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerFishEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();

    @EventHandler
    public void playerFishEvent(org.bukkit.event.player.PlayerFishEvent event) {
        Entity entity = event.getCaught();
        if (entity == null) return;

        event.getHook().setMaxWaitTime(500);

        if (!event.getHook().isInOpenWater()) return;
        if (event.getState() != org.bukkit.event.player.PlayerFishEvent.State.CAUGHT_FISH) return;

        Material material = ((Item) entity).getItemStack().getType();
        Player player = event.getPlayer();

        if (material == Material.BOW) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("fishOutBow")));
            return;
        }

        if (material != Material.STICK) return;
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("fishOutStick")));
    }
}
