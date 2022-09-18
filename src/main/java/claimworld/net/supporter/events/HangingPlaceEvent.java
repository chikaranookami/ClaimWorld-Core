package claimworld.net.supporter.events;

import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class HangingPlaceEvent implements Listener {

    @EventHandler
    public void hangingPlaceEvent(org.bukkit.event.hanging.HangingPlaceEvent event) {
        ItemStack itemStack = event.getItemStack();
        assert itemStack != null;
        assert itemStack.getItemMeta()!= null;
        if (!itemStack.getItemMeta().getDisplayName().equals(colorize("&aNiewidzialna Ramka"))) return;

        ((ItemFrame) event.getEntity()).setVisible(false);
    }
}
