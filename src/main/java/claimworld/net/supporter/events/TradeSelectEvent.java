package claimworld.net.supporter.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class TradeSelectEvent implements Listener {

    @EventHandler
    public void tradeSelectEvent(org.bukkit.event.inventory.TradeSelectEvent event) {
        Player player = (Player) event.getWhoClicked();
        Location location = player.getLocation();

        getLogger().log(Level.INFO, String.valueOf(event.getResult()));

        getLogger().log(Level.INFO, "[CW SUPPORTER] Villager press trade -> User: " + player.getDisplayName() + ", Location: " + location.getX() + "x, " + location.getY() + "y, " + location.getZ() + "z, world " + location.getWorld());
    }
}