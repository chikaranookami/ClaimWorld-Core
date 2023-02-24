package claimworld.net.supporter.events;

import claimworld.net.supporter.items.ReadyItems;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PrepareAnvilEvent implements Listener {

    ReadyItems readyItems = ReadyItems.getInstance();

    @EventHandler
    public void prepareAnvilEvent(org.bukkit.event.inventory.PrepareAnvilEvent event) {
        ItemStack item1 = event.getInventory().getItem(0);
        ItemStack item2 = event.getInventory().getItem(1);
        if ((item1 == null || !item1.isSimilar(readyItems.get("Jetpack"))) && (item2 == null || !item2.isSimilar(readyItems.get("Jetpack")))) return;

        event.setResult(new ItemStack(Material.AIR));
    }
}
