package claimworld.net.supporter.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class CustomItem {
    public ItemStack getItem() {return item;}

    private final ItemStack item;

    public CustomItem(String name, Material material, List<String> lore) {
        this(name, material, 1, lore, 0, null, 0);
    }

    public CustomItem(String name, Material material, List<String> lore, int customModelData) {
        this(name, material, 1, lore, customModelData, null, 0);
    }

    public CustomItem(String name, Material material, int amount, List<String> lore, int customModelData, Enchantment enchantment, int enchantmentLevel) {
        ItemStack item = new ItemStack(material, amount);

        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;

        //TU JEST BLAD
        itemMeta.setDisplayName(colorize(name));

        if (enchantment != null) ((EnchantmentStorageMeta) itemMeta).addStoredEnchant(enchantment, enchantmentLevel, true);

        if (customModelData > 0) itemMeta.setCustomModelData(customModelData);

        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        this.item = item;
    }
}
