package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.ActiveEventAnnouncer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerResourcePackStatusEvent implements Listener {

    @EventHandler
    public void playerResourcePackStatusEvent(org.bukkit.event.player.PlayerResourcePackStatusEvent event) {
        if (event.getStatus() == org.bukkit.event.player.PlayerResourcePackStatusEvent.Status.ACCEPTED) return;

        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
            new ActiveEventAnnouncer(event.getPlayer(), "Halloweenowy Weekend", "28.10 - 31.10");
        }, 40L);
    }
}
