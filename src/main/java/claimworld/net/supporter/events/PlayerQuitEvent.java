package claimworld.net.supporter.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class PlayerQuitEvent implements Listener {

    @EventHandler
    public void playerQuitEvent(org.bukkit.event.player.PlayerQuitEvent event) {
        event.setQuitMessage(colorize("&7" + event.getPlayer().getName() + " opuscil serwer"));
    }
}
