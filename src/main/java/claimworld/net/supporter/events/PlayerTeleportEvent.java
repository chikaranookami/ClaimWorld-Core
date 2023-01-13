package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.tasks.Task;
import claimworld.net.supporter.tasks.TaskManager;
import claimworld.net.supporter.utils.JetpackUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerTeleportEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();
    Map<String, Task> taskMap = taskManager.getTaskMap();
    JetpackUtils jetpackUtils = JetpackUtils.getInstance();

    @EventHandler
    public void playerTeleportEvent(org.bukkit.event.player.PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        jetpackUtils.disableJetpack(player.getName());

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("teleportYourself")));

        org.bukkit.event.player.PlayerTeleportEvent.TeleportCause teleportCause = event.getCause();
        Location to = event.getTo();
        assert to != null;

        if (teleportCause == org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT) {
            if (to.distance(event.getFrom()) < 100) return;
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("useChorus")));
            return;
        }

        if (!(teleportCause == org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) return;

        if (!(player.getWorld().getEnvironment() == World.Environment.THE_END)) return;
        if (player.getWorld().getWorldBorder().isInside(event.getTo())) return;

        event.setCancelled(true);
    }
}
