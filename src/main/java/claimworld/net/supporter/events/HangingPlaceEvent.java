package claimworld.net.supporter.events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class HangingPlaceEvent implements Listener {

    @EventHandler
    public void hangingPlaceEvent(org.bukkit.event.hanging.HangingPlaceEvent event) {
        ItemStack itemStack = event.getItemStack();
        assert itemStack != null;

        if (!itemStack.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Niewidzialna Ramka")) return;

        ((ItemFrame) event.getEntity()).setVisible(false);
    }
}
