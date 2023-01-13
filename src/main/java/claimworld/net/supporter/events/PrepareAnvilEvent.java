package claimworld.net.supporter.events;

import claimworld.net.supporter.items.ReadyItems;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PrepareAnvilEvent implements Listener {

    ReadyItems readyItems = ReadyItems.getInstance();

    @EventHandler
    public void prepareAnvilEvent(org.bukkit.event.inventory.PrepareAnvilEvent event) {
        ItemStack item1 = event.getInventory().getItem(0);
        if (item1 == null) return;

        ItemStack item2 = event.getInventory().getItem(1);
        if (item2 == null) return;

        ItemMeta item1Meta = item1.getItemMeta();
        if (item1Meta == null) return;

        ItemMeta item2Meta = item2.getItemMeta();
        if (item2Meta == null) return;

        if (item1Meta.getDisplayName().equals(readyItems.get("Jetpack").getItemMeta().getDisplayName())) {
            event.setResult(new ItemStack(Material.AIR));
            return;
        }
        if (item2Meta.getDisplayName().equals(readyItems.get("Jetpack").getItemMeta().getDisplayName())) {
            event.setResult(new ItemStack(Material.AIR));
        }
    }
}
