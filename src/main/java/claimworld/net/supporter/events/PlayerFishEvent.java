package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.GoalUtils;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerFishEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();

    @EventHandler
    public void playerFishEvent(org.bukkit.event.player.PlayerFishEvent event) {
        if (new GoalUtils().getShorterFishing()) event.getHook().setMaxWaitTime(500);

        if (!event.getHook().isInOpenWater()) return;
        if (event.getState() != org.bukkit.event.player.PlayerFishEvent.State.CAUGHT_FISH) return;

        assert event.getCaught() != null;
        if (!(((Item) event.getCaught()).getItemStack().getType() == Material.INK_SAC)) return;

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(event.getPlayer(), taskManager.getTaskMap().get("fishOutInkSac")));
    }
}
