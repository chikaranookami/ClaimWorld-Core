package claimworld.net.supporter.utils.guis;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class ReadyItems {

    public ItemStack get(String name) {
        if (name.equals("Menu")) {
            ItemStack item = new ItemStack(Material.CLOCK);

            List<String> lore = new ArrayList<>();
            lore.add(colorize("&7&oPrywatny Zasobnik."));

            ItemMeta itemMeta = item.getItemMeta();
            assert itemMeta != null;

            itemMeta.setDisplayName(colorize("&aMenu"));
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);

            return item;
        }

        return null;
    }
}
