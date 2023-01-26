package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.items.ReadyItems;
import claimworld.net.supporter.tasks.TaskManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerAdvancementDoneEvent implements Listener {

    ReadyItems readyItems = ReadyItems.getInstance();
    TaskManager taskManager = TaskManager.getInstance();

    @EventHandler
    public void playerAdvancementDoneEvent(org.bukkit.event.player.PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("getAchievementDone")));

        player.getWorld().dropItemNaturally(player.getLocation(), readyItems.get("$1"));
    }
}
