package claimworld.net.supporter.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getServer;

public class PlayerLoginEvent implements Listener {

    @EventHandler
    public void loginEvent(org.bukkit.event.player.PlayerLoginEvent event) {
        if (!(getServer().getOnlinePlayers().size() >= (getServer().getMaxPlayers() - 2))) return;

        Player player = event.getPlayer();

        if (player.hasPermission("claimworld.vip")) return;
        if (player.hasPermission("claimworld.mod")) return;

        event.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_FULL, "Ostatnie sloty sa zajete dla VIPow.");
    }
}
