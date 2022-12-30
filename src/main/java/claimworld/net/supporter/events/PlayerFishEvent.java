package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.GoalUtils;
import claimworld.net.supporter.utils.items.ReadyItems;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

import static org.bukkit.Bukkit.broadcastMessage;
import static org.bukkit.Bukkit.getScheduler;

public class PlayerFishEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();
    GoalUtils goalUtils = new GoalUtils();

    @EventHandler
    public void playerFishEvent(org.bukkit.event.player.PlayerFishEvent event) {
        if (goalUtils.getShorterFishing()) event.getHook().setMaxWaitTime(500);

        if (!event.getHook().isInOpenWater()) return;
        if (event.getState() != org.bukkit.event.player.PlayerFishEvent.State.CAUGHT_FISH) return;

        Entity entity = event.getCaught();
        assert entity != null;

        if (!(((Item) entity).getItemStack().getType() == Material.STICK)) return;

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(event.getPlayer(), taskManager.getTaskMap().get("fishOutStick")));
    }
}
