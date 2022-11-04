package claimworld.net.supporter.events;

import claimworld.net.supporter.utils.items.ReadyItems;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HangingPlaceEvent implements Listener {

    @EventHandler
    public void hangingPlaceEvent(org.bukkit.event.hanging.HangingPlaceEvent event) {
        if (event.getItemStack() == null) return;
        if (event.getItemStack().getType() != Material.ITEM_FRAME) return;
        if (event.getItemStack().getItemMeta().getLore() == null) return;
        if (!event.getItemStack().getItemMeta().getLore().equals(new ReadyItems().get("Niewidzialna ramka").getItemMeta().getLore())) return;

        ((ItemFrame) event.getEntity()).setVisible(false);
    }
}
