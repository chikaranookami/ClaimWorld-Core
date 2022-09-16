package claimworld.net.supporter.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MultipleHandles {
    public void giveOrDropItems(Player player, ItemStack[] items) {
        Inventory inventory = player.getInventory();
        Location location = player.getLocation();

        for (ItemStack item : items) {
            if (inventory.firstEmpty() == -1) {
                location.getWorld().dropItem(location, item);
            } else {
                inventory.addItem(item);
            }
        }
    }
}
