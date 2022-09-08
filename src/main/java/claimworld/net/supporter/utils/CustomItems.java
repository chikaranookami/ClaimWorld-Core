package claimworld.net.supporter.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomItems {

    public ItemStack getMoneyItem() {
        return moneyItem(1);
    }

    public ItemStack getMoneyItem(int amount) {
        return moneyItem(amount);
    }

    private ItemStack moneyItem(int amount) {
        ItemStack item = new ItemStack(Material.EMERALD, amount);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "$1");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Legendarna Waluta CW.");
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }
}
