package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.getScheduler;

public class PrepareAnvilEvent implements Listener {

    private final HashMap<Enchantment, Integer> betterEnchantments = new HashMap<>();

    public PrepareAnvilEvent() {
        betterEnchantments.put(Enchantment.DURABILITY, 4);
    }

    @EventHandler
    public void prepareAnvilEvent(org.bukkit.event.inventory.PrepareAnvilEvent event) {
        if (event.getInventory().getItem(0) == null || event.getInventory().getItem(1) == null) return;

        //hashmap?
        if (event.getInventory().getItem(1).getType() != Material.ENCHANTED_BOOK) return;
        if (event.getInventory().getItem(0).getType() == Material.ENCHANTED_BOOK) return;

        ItemStack finalTool = new ItemStack(event.getInventory().getItem(0));
        EnchantmentStorageMeta bookEnchantments = (EnchantmentStorageMeta) event.getInventory().getItem(1).getItemMeta();

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            for (Map.Entry<Enchantment, Integer> entry : betterEnchantments.entrySet()) {
                assert bookEnchantments != null;
                if (!bookEnchantments.hasStoredEnchant(entry.getKey())) return;
                if (bookEnchantments.getStoredEnchantLevel(entry.getKey()) < entry.getValue()) return;
            }
        });
        //if (!bookEnchantments.hasStoredEnchant(Enchantment.DURABILITY)) return;
        //if (bookEnchantments.getStoredEnchantLevel(Enchantment.DURABILITY) < 4) return;

        ItemStack result = event.getResult();
        if (result != null) {
            ItemMeta finalToolMeta = finalTool.getItemMeta();
            ItemMeta resultMeta = result.getItemMeta();
            assert resultMeta != null;
            assert finalToolMeta != null;

            finalToolMeta.setDisplayName(resultMeta.getDisplayName());
            finalTool.setItemMeta(finalToolMeta);
        }


        assert bookEnchantments != null;
        finalTool.addUnsafeEnchantments(bookEnchantments.getStoredEnchants());

        event.setResult(finalTool);
    }
}
