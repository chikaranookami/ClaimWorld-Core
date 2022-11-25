package claimworld.net.supporter.utils.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class ReadyItems {

    private static ReadyItems instance;

    public static ReadyItems getInstance() {
        if (instance == null) instance = new ReadyItems();
        return instance;
    }

    private final HashMap<String, CustomItem> itemMap = new HashMap<>();

    public HashMap<String, CustomItem> getItemMap() {
        return itemMap;
    }

    public String getLore(String name) {
        switch (name) {
            case "common":
                return "&7&oJakosc przedmiotu [&f&o■&7&o■■■■]";
            case "uncommon":
                return "&7&oJakosc przedmiotu [&a&o■■&7&o■■■]";
            case "rare":
                return "&7&oJakosc przedmiotu [&d&o■■■&7&o■■]";
            case "unique":
                return "&7&oJakosc przedmiotu [&c&o■■■■&7&o■]";
            case "legendary":
                return "&7&oJakosc przedmiotu [&e&o■■■■■&7&o]";
            case "custom":
                return "&7&oJakosc przedmiotu [&a&l■&b&l■&c&l■&d&l■&e&l■&7&o]";
            case "oneTimeUse":
                return "&7&oMozna uzyc tylko raz.";
        }

        return null;
    }

    public ItemStack get(String name, int amount) {
        ItemStack itemStack = itemMap.get(name).getItem().clone();
        itemStack.setAmount(amount);
        return itemStack;
    }

    public ItemStack get(String name) {
        ItemStack itemStack = itemMap.get(name).getItem().clone();
        itemStack.setAmount(1);
        return itemStack;
    }

    public ReadyItems() {
        itemMap.put("Cofnij", new CustomItem("&fCofnij", Material.ARROW, Collections.singletonList(colorize("&7&oPoprzednie menu."))));
        itemMap.put("Menu", new CustomItem("&aMenu", Material.CLOCK, Collections.singletonList(colorize("&7&oPanel gracza.")), 1));

        itemMap.put("Kox", new CustomItem("", Material.ENCHANTED_GOLDEN_APPLE, null));
        itemMap.put("Ruda_diamentu", new CustomItem("", Material.DIAMOND_ORE, null));
        itemMap.put("Sztabka_netherytu", new CustomItem("", Material.NETHERITE_INGOT, null));

        //itemMap.put("Halloweenowa_zupa", new CustomItem("&dHalloweenowa Zupa", Material.PUMPKIN_PIE, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.KNOCKBACK, 3));
        //itemMap.put("Halloweenowy_odbijacz", new CustomItem("&dHalloweenowy Odbijacz", Material.CARVED_PUMPKIN, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.THORNS, 8));

        itemMap.put("Ksiazka_unbreaking4", new CustomItem("", Material.ENCHANTED_BOOK, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.DURABILITY, 4));
        itemMap.put("Ksiazka_protection5", new CustomItem("", Material.ENCHANTED_BOOK, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.PROTECTION_ENVIRONMENTAL, 5));
        itemMap.put("Ksiazka_fortune4", new CustomItem("", Material.ENCHANTED_BOOK, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.LOOT_BONUS_BLOCKS, 4));
        itemMap.put("Ksiazka_sharpness6", new CustomItem("", Material.ENCHANTED_BOOK, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.DAMAGE_ALL, 6));
        itemMap.put("Ksiazka_swiftsneak4", new CustomItem("", Material.ENCHANTED_BOOK, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.SWIFT_SNEAK, 4));

        itemMap.put("Skrzynia_smoka", new CustomItem("&cSkrzynia Smoka", Material.CHEST_MINECART, Collections.singletonList(colorize(getLore("unique"))), 0));
        itemMap.put("Niewidzialna_ramka", new CustomItem("&aNiewidzialna ramka", Material.ITEM_FRAME, Arrays.asList(colorize(getLore("uncommon")), "", colorize(getLore("oneTimeUse")))));
        itemMap.put("Kupa", new CustomItem("&fKupa", Material.GHAST_TEAR, Collections.singletonList(colorize(getLore("common"))), 11));
        itemMap.put("$1", new CustomItem("&a$1", Material.GHAST_TEAR, Collections.singletonList(colorize(getLore("uncommon"))), 10));
        itemMap.put("Uniwersalny_bilet", new CustomItem("&fUniwersalny Bilet", Material.GHAST_TEAR, Collections.singletonList(colorize(getLore("common"))), 12));
    }
}
