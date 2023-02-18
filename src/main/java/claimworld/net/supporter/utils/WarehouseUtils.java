package claimworld.net.supporter.utils;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.items.Locker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getScheduler;

public class WarehouseUtils {
    public void tryCloseInventory(Player player) {
        if (player.getOpenInventory().getTitle().equals("Skrytka " + player.getName())) getScheduler().runTask(Supporter.getPlugin(), player::closeInventory);
    }

    public void addItemsGlobal(List<ItemStack> items) {
        for (Player player : getOnlinePlayers()) {
            addItemsSingle(player.getName(), items);
        }
    }

    public void addItemsSingle(String playerName, List<ItemStack> items) {
        addItemsSingle(playerName, items, false);
    }

    public void addItemsSingle(String playerName, List<ItemStack> items, boolean silent) {
        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            tryCloseInventory(player);
            if (!silent) player.sendMessage(MessageUtils.getUserPrefix() + "Otrzymales przedmiot. Wez go ze skrytki zanim zniknie!");
        }

        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
            List<ItemStack> fixedItems = new ArrayList<>(items);
            HashMap<String, List<ItemStack>> itemMap = Locker.getInstance().getLockerMap();

            if (itemMap.get(playerName) != null) itemMap.get(playerName).addAll(fixedItems);
            itemMap.putIfAbsent(playerName, fixedItems);
        }, 10L);
    }
}
