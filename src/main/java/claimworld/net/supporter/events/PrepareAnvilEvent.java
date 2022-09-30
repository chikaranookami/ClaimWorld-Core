package claimworld.net.supporter.events;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class PrepareAnvilEvent implements Listener {

    @EventHandler
    public void prepareAnvilEvent(org.bukkit.event.inventory.PrepareAnvilEvent event) {
        if (event.getInventory().getItem(0) == null || event.getInventory().getItem(1) == null) return;
        if (event.getInventory().getItem(1).getType() != Material.ENCHANTED_BOOK) return;
        if (event.getInventory().getItem(0).getType() == Material.ENCHANTED_BOOK) return;

        ItemStack finalTool = new ItemStack(event.getInventory().getItem(0));
        EnchantmentStorageMeta bookEnchantments = (EnchantmentStorageMeta) event.getInventory().getItem(1).getItemMeta();

        //tymczasowa surowica, lepsza bylaby mapa albo cos
        if (!bookEnchantments.hasStoredEnchant(Enchantment.DURABILITY)) return;
        if (bookEnchantments.getStoredEnchantLevel(Enchantment.DURABILITY) < 4) return;

        finalTool.addUnsafeEnchantments(bookEnchantments.getStoredEnchants());

        event.setResult(finalTool);
    }
}
