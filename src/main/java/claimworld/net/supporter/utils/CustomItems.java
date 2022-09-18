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

    public ItemStack getShitItem() {
        return getShitItem(1);
    }

    public ItemStack getShitItem(int amount) {
        return getShitItem(amount);
    }

    public ItemStack getGlobalTicketItem() {
        return getGlobalTicketItem(1);
    }

    public ItemStack getGlobalTicketItem(int amount) {
        return getGlobalTicketItem(amount);
    }

    private ItemStack shitItem(int amount) {
        ItemStack item  = new ItemStack(Material.MUSHROOM_STEM, amount);

        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "Kupa");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Powszechny Skladnik " + ChatColor.GRAY + "[▪▪▪▪▪]");
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack globalTicketItem(int amount) {
        ItemStack item  = new ItemStack(Material.PAPER, amount);

        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "Uniwersalny Bilet");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Powszechny Skladnik " + ChatColor.GRAY + "[▪▪▪▪▪]");
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack moneyItem(int amount) {
        ItemStack item = new ItemStack(Material.EMERALD, amount);

        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "$1");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Legendarna Waluta CW.");
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }
}
