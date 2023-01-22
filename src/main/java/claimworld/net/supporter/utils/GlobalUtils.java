package claimworld.net.supporter.utils;

import claimworld.net.supporter.items.ReadyItems;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class GlobalUtils {

    ReadyItems readyItems = ReadyItems.getInstance();

    private static GlobalUtils instance = null;

    private final List<ItemStack> items = new ArrayList<>();

    public static GlobalUtils getInstance() {
        if (instance == null) instance = new GlobalUtils();
        return instance;
    }

    private final List<String> playersWithFreeChest = new ArrayList<>();

    public GlobalUtils() {
        items.add(readyItems.get("Skrzynia_smoka"));
        items.add(readyItems.get("Skrzynia_smoka"));
    }

    public List<String> getPlayersWithFreeChest() {
        return playersWithFreeChest;
    }

    public void addFreeChest(Player player) {
        String playerName = player.getName();
        playersWithFreeChest.add(playerName);
        new WarehouseUtils().addItemsSingle(playerName, items);

        getLogger().log(Level.INFO, "2x free chest has been added to inventory of " + playerName);
    }
}
