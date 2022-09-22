package claimworld.net.supporter.utils.guis;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class ItemSets {
    
    private final HashMap<Integer, ItemStack> itemMap = new HashMap<>();
    
    public HashMap<Integer, ItemStack> initializeInventoryContent(String inventoryName) {
        if (inventoryName == null || inventoryName.isEmpty()) {
            getLogger().log(Level.INFO, "initializing error - inventoryName is empty or null");
            return null;
        }

        itemMap.clear();

        if (inventoryName.equals("Zasobnik")) {
            itemMap.put(0, new ItemStack(Material.STONE));
            itemMap.put(1, new ItemStack(Material.DIRT));
            itemMap.put(2, new ItemStack(Material.COBBLED_DEEPSLATE));
            itemMap.put(3, new ItemStack(Material.COBBLESTONE));
            itemMap.put(4, new ItemStack(Material.WOODEN_SHOVEL));
        }

        return itemMap;
    }
}
