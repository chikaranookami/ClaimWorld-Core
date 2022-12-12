package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getScheduler;

public class TradeSelectEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();

    @EventHandler
    public void tradeSelectEvent(org.bukkit.event.inventory.TradeSelectEvent event) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            Player player = (Player) event.getWhoClicked();
            getLogger().log(Level.INFO, "Villager press -> User: " + player.getDisplayName() + ", Location: " + player.getLocation());

            taskManager.tryFinishTask(player, taskManager.getTaskMap().get("pressTrader"));
        });
    }
}