package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static claimworld.net.supporter.events.PlayerLoginEvent.reservedSlots;
import static org.bukkit.Bukkit.getScheduler;
import static org.bukkit.Bukkit.getServer;

public class ServerListPingEvent implements Listener {

    private final int maxPlayers = getServer().getMaxPlayers();

    @EventHandler
    public void serverListPingEvent(org.bukkit.event.server.ServerListPingEvent event) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            int availableSlots = maxPlayers - reservedSlots;
            if (event.getNumPlayers() < availableSlots) return;

            event.setMaxPlayers(availableSlots);
        });
    }
}
