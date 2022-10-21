package claimworld.net.supporter.events;

import claimworld.net.supporter.utils.guis.StoredInventories;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class InventoryCloseEvent implements Listener {

    @EventHandler
    public void inventoryCloseEvent(org.bukkit.event.inventory.InventoryCloseEvent event) {
        if (!event.getView().getTitle().equals("Schowek " + event.getPlayer().getName())) return;

        getLogger().log(Level.INFO, "Saving stored contents of " + event.getPlayer().getName() + "...");
        new StoredInventories().updateStoredItems(event.getPlayer().getName(), Arrays.asList(event.getInventory().getContents()));
    }
}
