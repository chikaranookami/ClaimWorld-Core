package claimworld.net.supporter.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InventoryDragEvent implements Listener {

    @EventHandler
    public void inventoryDragEvent(org.bukkit.event.inventory.InventoryDragEvent event) {
        if (event.getInventory().equals(event.getWhoClicked().getInventory())) event.getWhoClicked().sendMessage("dragged inside own inventory");
    }
}
