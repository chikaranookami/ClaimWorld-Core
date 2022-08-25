package claimworld.net.supporter.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class AsyncPlayerChatEvent implements Listener {

    @EventHandler
    public void asyncPlayerChatEvent(org.bukkit.event.player.AsyncPlayerChatEvent event) {
        if (!event.isAsynchronous()) getLogger().log(Level.WARNING, "tried to use NOT async chat event");

        event.setFormat(event.getPlayer().getDisplayName() + ChatColor.GRAY + ": " + ChatColor.RESET + event.getMessage());
    }
}
