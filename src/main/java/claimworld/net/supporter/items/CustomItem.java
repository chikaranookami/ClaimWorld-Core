package claimworld.net.supporter.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class CustomItem {
    public ItemStack getItem() {return item;}

    private final ItemStack item;

    public CustomItem(String name, Material material, List<String> lore) {
        this(name, material, 1, lore, 0, new ArrayList<>(), Collections.singletonList(0));
    }

    public CustomItem(String name, Material material, List<String> lore, int customModelData) {
        this(name, material, 1, lore, customModelData, new ArrayList<>(), Collections.singletonList(0));
    }

    public CustomItem(String name, Material material, int amount, List<String> lore, int customModelData, Enchantment enchantment, int enchantmentLevel) {
        this(name, material, amount, lore, customModelData, Collections.singletonList(enchantment), Collections.singletonList(enchantmentLevel));
    }

    public CustomItem(String name, Material material, int amount, List<String> lore, int customModelData, List<Enchantment> enchantments, List<Integer> enchantmentLevels) {
        ItemStack item = new ItemStack(material);

        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;

        itemMeta.setDisplayName(colorize(name));

        if (enchantments != null && !enchantments.isEmpty()) {
            for (Enchantment enchantment : enchantments) {
                int enchantmentLevel = enchantmentLevels.get(enchantments.indexOf(enchantment));

                if (item.getType() == Material.ENCHANTED_BOOK) {
                    ((EnchantmentStorageMeta) itemMeta).addStoredEnchant(enchantment, enchantmentLevel, true);
                } else {
                    itemMeta.addEnchant(enchantment, enchantmentLevel, true);
                }
            }
        }

        /*
        if (enchantment != null) {
            if (item.getType() == Material.ENCHANTED_BOOK) {
                ((EnchantmentStorageMeta) itemMeta).addStoredEnchant(enchantment, enchantmentLevel, true);
            } else {
                itemMeta.addEnchant(enchantment, enchantmentLevel, true);
            }
        }
        */

        if (customModelData > 0) itemMeta.setCustomModelData(customModelData);

        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);
        item.setAmount(amount);

        this.item = item;
    }
}
