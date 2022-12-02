package claimworld.net.supporter.utils;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.items.Locker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getScheduler;

public class WarehouseUtils {
    public void tryCloseInventory(Player player) {
        if (player.getOpenInventory().getTitle().equals("Skrytka " + player.getName())) player.closeInventory();
    }

    public void addItemsGlobal(List<ItemStack> items) {
        for (Player player : getOnlinePlayers()) {
            addItemsSingle(player, items);
        }
    }

    public void addItemsSingle(Player player, List<ItemStack> items) {
        addItemsSingle(player, items, false);
    }

    public void addItemsSingle(Player player, List<ItemStack> items, boolean silent) {
        tryCloseInventory(player);

        if (!silent) player.sendMessage(MessageUtils.getUserPrefix() + "Otrzymales przedmiot. Wez go ze skrytki zanim zniknie!");

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            String playerName = player.getName();
            List<ItemStack> fixedItems = new ArrayList<>(items);
            HashMap<String, List<ItemStack>> itemMap = Locker.getInstance().getLockerMap();

            if (itemMap.get(playerName) != null) itemMap.get(playerName).addAll(fixedItems);
            itemMap.putIfAbsent(playerName, fixedItems);
        });
    }
}
