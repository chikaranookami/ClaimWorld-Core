package claimworld.net.supporter.utils.guis;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class StoredInventories {
    public static final HashMap<String, List<ItemStack>> storedItems = new HashMap<>();

    public HashMap<String, List<ItemStack>> getStoredItems() {
        return storedItems;
    }

    public void updateStoredItems(String playerName, List<ItemStack> items) {
        storedItems.remove(playerName);
        storedItems.put(playerName, items);
    }
}
