package claimworld.net.supporter.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class CustomItems {
    private final String commonItemLore = colorize("&7&oPowszechny przedmiot [&f&o■&7&o■■■■]");
    private final String uncommonItemLore = colorize("&7&oNiepowszechny przedmiot [&a&o■■&7&o■■■]");
    private final String rareItemLore = colorize("&7&oRzadki przedmiot [&d&o■■■&7&o■■]");
    private final String uniqueItemLore = colorize("&7&oWyjatkowy przedmiot [&c&o■■■■&7&o■]");
    private final String legendaryItemLore = colorize("&7&oLegendarny przedmiot [&e&o■■■■■&7&o]");
    private final String customItemLore = colorize("&7&oUnikalny przedmiot [&a&l■&b&l■&c&l■&d&l■&e&l■&7&o]");

    private ItemStack playerHead(int amount, Player player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        assert skullMeta != null;

        skullMeta.setOwningPlayer(player);
        item.setItemMeta(skullMeta);

        return item;
    }

    private ItemStack invisibleItemFrameItem(int amount) {
        ItemStack item = new ItemStack(Material.ITEM_FRAME, amount);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;

        itemMeta.setDisplayName(colorize("&aNiewidzialna Ramka"));

        List<String> lore = new ArrayList<>();
        lore.add(uncommonItemLore);
        lore.add("");
        lore.add(colorize("&7&oMozna uzyc tylko raz."));
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack shitItem(int amount) {
        ItemStack item  = new ItemStack(Material.MUSHROOM_STEM, amount);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        meta.setDisplayName(colorize("&fKupa"));

        List<String> lore = new ArrayList<>();
        lore.add(commonItemLore);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack globalTicketItem(int amount) {
        ItemStack item  = new ItemStack(Material.PAPER, amount);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        meta.setDisplayName(colorize("&fUniwersalny Bilet"));

        List<String> lore = new ArrayList<>();
        lore.add(commonItemLore);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack moneyItem(int amount) {
        ItemStack item = new ItemStack(Material.EMERALD, amount);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        meta.setDisplayName(colorize("&a$1"));

        List<String> lore = new ArrayList<>();
        lore.add(uncommonItemLore);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack getPlayerSkull(Player player) {
        return getPlayerSkull(player, 1);
    }

    public ItemStack getPlayerSkull(Player player, int amount) {
        return playerHead(amount, player);
    }

    public ItemStack getInvisibleItemFrameItem() {
        return getInvisibleItemFrameItem(1);
    }

    public ItemStack getInvisibleItemFrameItem(int amount) {
        return invisibleItemFrameItem(amount);
    }

    public ItemStack getMoneyItem() {
        return getMoneyItem(1);
    }

    public ItemStack getMoneyItem(int amount) {
        return moneyItem(amount);
    }

    public ItemStack getShitItem() {
        return getShitItem(1);
    }

    public ItemStack getShitItem(int amount) {
        return shitItem(amount);
    }

    public ItemStack getGlobalTicketItem() {
        return getGlobalTicketItem(1);
    }

    public ItemStack getGlobalTicketItem(int amount) {
        return globalTicketItem(amount);
    }
}
