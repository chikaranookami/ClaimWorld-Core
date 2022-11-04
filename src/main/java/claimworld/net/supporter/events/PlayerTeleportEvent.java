package claimworld.net.supporter.events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerTeleportEvent implements Listener {

    @EventHandler
    public void playerTeleportEvent(org.bukkit.event.player.PlayerTeleportEvent event) {
        if (!(event.getCause() == org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) return;
        if (event.getTo() == null) return;

        Player player = event.getPlayer();

        if (!(player.getWorld().getEnvironment() == World.Environment.THE_END)) return;
        if (player.getWorld().getWorldBorder().isInside(event.getTo())) return;

        event.setCancelled(true);
    }
}
