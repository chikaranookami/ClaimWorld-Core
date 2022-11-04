package claimworld.net.supporter.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class TradeSelectEvent implements Listener {

    @EventHandler
    public void tradeSelectEvent(org.bukkit.event.inventory.TradeSelectEvent event) {
        Player player = (Player) event.getWhoClicked();
        getLogger().log(Level.INFO, "Villager press -> User: " + player.getDisplayName() + ", Location: " + player.getLocation());
    }
}