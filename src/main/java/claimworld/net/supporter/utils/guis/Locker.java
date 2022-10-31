package claimworld.net.supporter.utils.guis;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class Locker {
    private static Locker instance = null;
    private final HashMap<String, List<ItemStack>> lockerMap = new HashMap<>();

    public HashMap<String, List<ItemStack>> getLockerMap() {
        return lockerMap;
    }

    public void updateStoredItems(String playerName, List<ItemStack> items) {
        lockerMap.remove(playerName);
        lockerMap.put(playerName, items);
    }

    public static Locker getInstance() {
        if (instance == null) instance = new Locker();
        return instance;
    }
}
