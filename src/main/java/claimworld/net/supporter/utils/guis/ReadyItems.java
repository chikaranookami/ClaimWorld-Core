package claimworld.net.supporter.utils.guis;

import claimworld.net.supporter.utils.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class ReadyItems {

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

    public ItemStack get(String name) {
        if (name.equals("Cofnij")) {
            return new CustomItem("&fCofnij", Material.ARROW, Collections.singletonList(colorize("&7&oPoprzednie menu."))).getItem();
        }

        if (name.equals("Menu")) {
            return new CustomItem("&aMenu", Material.CLOCK, Collections.singletonList(colorize("&7&oPanel gracza."))).getItem();
        }

        if (name.equals("Niewidzialna ramka")) {
            return new CustomItem("&aNiewidzialna ramka", Material.ITEM_FRAME, Arrays.asList(colorize(getLore("uncommon")), "", colorize(getLore("oneTimeUse")))).getItem();
        }

        if (name.equals("Kupa")) {
            return new CustomItem("&fKupa", Material.MUSHROOM_STEM, Collections.singletonList(colorize(getLore("common")))).getItem();
        }

        if (name.equals("$1")) {
            return new CustomItem("&a$1", Material.EMERALD, Collections.singletonList(colorize(getLore("uncommon")))).getItem();
        }

        if (name.equals("Uniwersalny Bilet")) {
            return new CustomItem("&fUniwersalny Bilet", Material.PAPER, Collections.singletonList(colorize(getLore("common")))).getItem();
        }

        return null;
    }
}
