package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerTeleportEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();

    @EventHandler
    public void playerTeleportEvent(org.bukkit.event.player.PlayerTeleportEvent event) {
        org.bukkit.event.player.PlayerTeleportEvent.TeleportCause teleportCause = event.getCause();
        Player player = event.getPlayer();
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
