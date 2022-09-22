package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.guis.Gui;
import claimworld.net.supporter.utils.guis.GuiManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class InventoryClickEvent implements Listener {

    @EventHandler
    public void inventoryClickEvent(org.bukkit.event.inventory.InventoryClickEvent event) {
        if (event.getSlot() == 17 && event.getClickedInventory() == event.getWhoClicked().getInventory()) {
            event.setCancelled(true);
            getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                new GuiManager((Player) event.getWhoClicked(), new Gui(null, 54, "Zasobnik"));
            }, 1L);
        }
    }
}
