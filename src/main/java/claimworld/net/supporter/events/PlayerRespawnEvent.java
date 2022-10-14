package claimworld.net.supporter.events;

import claimworld.net.supporter.utils.guis.ReadyItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PlayerRespawnEvent implements Listener {

    @EventHandler
    public void playerRespawnEvent(org.bukkit.event.player.PlayerRespawnEvent event) {
        ItemStack menu = new ReadyItems().get("Menu");

        event.getPlayer().getInventory().setItem(17, menu);
    }
}
