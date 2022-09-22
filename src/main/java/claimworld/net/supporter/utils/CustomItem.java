package claimworld.net.supporter.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class CustomItem {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    private ItemStack item;
    private String name;
    private Material material;
    private final List<String> lore;

    public CustomItem(String name, Material material, List<String> lore) {
        this(name, material, 1, lore);
    }

    public CustomItem(String name, Material material, int amount, List<String> lore) {
        this.name = name;
        this.material = material;
        this.item = new ItemStack(material, amount);

        for (String string : lore) {
            colorize(string);
        }
        this.lore = lore;

        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;

        itemMeta.setDisplayName(colorize(name));
        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);
    }
}
