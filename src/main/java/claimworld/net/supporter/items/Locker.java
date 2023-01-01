package claimworld.net.supporter.items;

import claimworld.net.supporter.Supporter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

import static org.bukkit.Bukkit.getScheduler;

public class Locker {
    private static Locker instance = null;
    private final HashMap<String, List<ItemStack>> lockerMap = new HashMap<>();

    public HashMap<String, List<ItemStack>> getLockerMap() {
        return lockerMap;
    }

    public void updateStoredItems(String playerName, List<ItemStack> items) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            lockerMap.remove(playerName);
            lockerMap.put(playerName, items);
        });
    }

    public static Locker getInstance() {
        if (instance == null) instance = new Locker();
        return instance;
    }
}
