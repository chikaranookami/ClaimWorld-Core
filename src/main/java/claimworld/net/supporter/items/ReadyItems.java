package claimworld.net.supporter.items;

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

    public ItemStack getMenuItem() {
        return new CustomItem("&aMenu", Material.CLOCK, Collections.singletonList(colorize("&7&oPanel gracza.")), 1).getItem();
    }

    public ItemStack getKosaNaMoby() {
        return new CustomItem("&aK&bo&cs&da &eN&ea &aM&bo&cb&dy", Material.NETHERITE_HOE, 1, Collections.singletonList(colorize(getLore("custom"))), 15, Arrays.asList(Enchantment.DAMAGE_ARTHROPODS, Enchantment.FIRE_ASPECT, Enchantment.LOOT_BONUS_MOBS, Enchantment.MENDING, Enchantment.DAMAGE_ALL, Enchantment.SILK_TOUCH, Enchantment.DAMAGE_UNDEAD, Enchantment.DURABILITY), Arrays.asList(10, 2, 3, 1, 10, 1, 10, 3)).getItem();
    }

    public ReadyItems() {
        itemMap.put("Kox", new CustomItem("", Material.ENCHANTED_GOLDEN_APPLE, null));
        itemMap.put("Ruda_diamentu", new CustomItem("", Material.DIAMOND_ORE, null));
        itemMap.put("Sztabka_netherytu", new CustomItem("", Material.NETHERITE_INGOT, null));

        //itemMap.put("Prezent", new CustomItem("&dPrezent", Material.GHAST_TEAR, Collections.singletonList(colorize(getLore("rare"))), 13));
        //itemMap.put("Skarpeta", new CustomItem("&dSkarpeta", Material.GHAST_TEAR, Collections.singletonList(colorize(getLore("rare"))), 14));

        //itemMap.put("Halloweenowa_zupa", new CustomItem("&dHalloweenowa Zupa", Material.PUMPKIN_PIE, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.KNOCKBACK, 3));
        //itemMap.put("Halloweenowy_odbijacz", new CustomItem("&dHalloweenowy Odbijacz", Material.CARVED_PUMPKIN, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.THORNS, 8));

        itemMap.put("Niewidzialna_ramka", new CustomItem("&aNiewidzialna ramka", Material.ITEM_FRAME, Arrays.asList(colorize(getLore("uncommon")), "", colorize(getLore("oneTimeUse")))));

        itemMap.put("Ksiazka_unbreaking4", new CustomItem("Niezniszczalnosc IV", Material.ENCHANTED_BOOK, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.DURABILITY, 4));
        itemMap.put("Ksiazka_protection5", new CustomItem("Ochrona V", Material.ENCHANTED_BOOK, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.PROTECTION_ENVIRONMENTAL, 5));
        itemMap.put("Ksiazka_fortune4", new CustomItem("Szczescie IV", Material.ENCHANTED_BOOK, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.LOOT_BONUS_BLOCKS, 4));
        itemMap.put("Ksiazka_sharpness6", new CustomItem("Ostrosc VI", Material.ENCHANTED_BOOK, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.DAMAGE_ALL, 6));
        itemMap.put("Ksiazka_swiftsneak4", new CustomItem("Szybkie skradanie IV", Material.ENCHANTED_BOOK, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.SWIFT_SNEAK, 4));
        itemMap.put("Ksiazka_looting4", new CustomItem("Grabiez IV", Material.ENCHANTED_BOOK, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.LOOT_BONUS_MOBS, 4));
        itemMap.put("Ksiazka_thorns5", new CustomItem("Ciernie V", Material.ENCHANTED_BOOK, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.THORNS, 5));
        itemMap.put("Ksiazka_power6", new CustomItem("Moc VI", Material.ENCHANTED_BOOK, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.ARROW_DAMAGE, 6));
        itemMap.put("Ksiazka_fireaspect3", new CustomItem("Zaklety ogien III", Material.ENCHANTED_BOOK, 1, Collections.singletonList(colorize(getLore("rare"))), 0, Enchantment.FIRE_ASPECT, 3));

        itemMap.put("Jetpack", new CustomItem("&e&lJetpack", Material.CHAINMAIL_CHESTPLATE, 1, Collections.singletonList(colorize(getLore("legendary"))), 0, Enchantment.MENDING, 1));
        itemMap.put("Skrzynia_smoka", new CustomItem("&cSkrzynia Smoka", Material.CHEST_MINECART, Collections.singletonList(colorize(getLore("unique"))), 0));
        itemMap.put("Kupa", new CustomItem("&fKupa", Material.GHAST_TEAR, Collections.singletonList(colorize(getLore("common"))), 11));
        itemMap.put("$1", new CustomItem("&a$1", Material.GHAST_TEAR, Collections.singletonList(colorize(getLore("uncommon"))), 10));
        itemMap.put("Uniwersalny_bilet", new CustomItem("&fUniwersalny Bilet", Material.GHAST_TEAR, Collections.singletonList(colorize(getLore("common"))), 12));
        itemMap.put("Zwoj_ognia", new CustomItem("&cZwoj ognia", Material.PAPER, Arrays.asList(colorize(getLore("unique")), colorize(""), colorize("&7&oWskazana osoba plonie"), colorize("&7&oprzez 15 sekund i nie"), colorize("&7&omozna jej ugasic.")), 0));
    }
}
