package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;

import static org.bukkit.Bukkit.getScheduler;

public class SignChangeEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();

    @EventHandler
    public void signChangeEvent(org.bukkit.event.block.SignChangeEvent event) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            Player player = event.getPlayer();
            Location location = player.getLocation();
            Bukkit.getConsoleSender().sendMessage("Player " + player.getDisplayName() + " napisal na tabliczce (lokalizacja " + location.getX() + ", " + location.getY() + ", " + location.getZ() + ", " + player.getWorld().getName() + ": " + String.join(" ", event.getLines()));

            String content = event.getLine(0);
            if (content == null) return;
            if (!content.contains("<3")) return;

            taskManager.tryFinishTask(player, taskManager.getTaskMap().get("writeHeartOnSign"));
        });
    }
}
