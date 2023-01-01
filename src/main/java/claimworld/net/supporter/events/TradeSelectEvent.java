package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.tasks.TaskManager;
import org.bukkit.Location;
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
            Location location = player.getLocation();
            getLogger().log(Level.INFO, "Trade press -> " + player.getDisplayName() + " (" + Math.round(location.getX()) + "x, " + Math.round(location.getY()) + "y, " + Math.round(location.getZ()) + "z)");

            taskManager.tryFinishTask(player, taskManager.getTaskMap().get("pressTrader"));
        });
    }
}