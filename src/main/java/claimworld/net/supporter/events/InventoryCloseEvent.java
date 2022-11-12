package claimworld.net.supporter.events;

import claimworld.net.supporter.utils.items.Locker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class InventoryCloseEvent implements Listener {

    @EventHandler
    public void inventoryCloseEvent(org.bukkit.event.inventory.InventoryCloseEvent event) {
        String playerName = event.getPlayer().getName();

        if (!event.getView().getTitle().equals("Skrytka " + playerName)) return;

        Locker locker = Locker.getInstance();
        HashMap<String, List<ItemStack>> lockerItems = locker.getLockerMap();

        if (lockerItems.get(playerName) == null) return;

        List<ItemStack> items = new ArrayList<>();

        if (lockerItems.get(playerName) == null) return;
        if (!(lockerItems.get(playerName).size() < 1)) {
            for (ItemStack item : event.getInventory().getContents()) {
                if (item == null) continue;
                if (item.getType().isAir()) continue;
                items.add(item);
            }
        }

        Locker.getInstance().updateStoredItems(playerName, items);
        getLogger().log(Level.INFO, "Saving stored contents of " + playerName + "...");
    }
}
