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

    ReadyItems readyItems = ReadyItems.getInstance();
    TaskManager taskManager = TaskManager.getInstance();

    @EventHandler
    public void playerFishEvent(org.bukkit.event.player.PlayerFishEvent event) {
        if (new GoalUtils().getShorterFishing()) event.getHook().setMaxWaitTime(500);

        if (!event.getHook().isInOpenWater()) return;
        if (event.getState() != org.bukkit.event.player.PlayerFishEvent.State.CAUGHT_FISH) return;

        Player player = event.getPlayer();
        Entity entity = event.getCaught();
        assert entity != null;
        if (new Random().nextInt(80) == 1) player.getWorld().dropItem(entity.getLocation(), readyItems.get("Prezent"));

        if (!(((Item) entity).getItemStack().getType() == Material.STICK)) return;

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(event.getPlayer(), taskManager.getTaskMap().get("fishOutStick")));
    }
}
