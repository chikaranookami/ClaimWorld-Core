package claimworld.net.supporter.utils.guis;

import claimworld.net.supporter.utils.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class ReadyItems {

    private final String commonItemLore = "&7&oPowszechny przedmiot [&f&o■&7&o■■■■]";
    private final String uncommonItemLore = "&7&oNiepowszechny przedmiot [&a&o■■&7&o■■■]";
    private final String rareItemLore = "&7&oRzadki przedmiot [&d&o■■■&7&o■■]";
    private final String uniqueItemLore = "&7&oWyjatkowy przedmiot [&c&o■■■■&7&o■]";
    private final String legendaryItemLore = "&7&oLegendarny przedmiot [&e&o■■■■■&7&o]";
    private final String customItemLore = "&7&oUnikalny przedmiot [&a&l■&b&l■&c&l■&d&l■&e&l■&7&o]";
    private final String oneTimeUseLore = "&7&oMozna uzyc tylko raz.";

    public ItemStack get(String name) {
        if (name.equals("Menu")) {
            return new CustomItem("&aMenu", Material.CLOCK, Collections.singletonList("&7&oPrywatny Zasobnik")).getItem();
        }

        if (name.equals("Niewidzialna Ramka") {
            return new CustomItem("&aNiewidzialna Ramka", Material.ITEM_FRAME, new ArrayList<String>(commonItemLore, customItemLore)).getItem();
        }

        return null;
    }
}
