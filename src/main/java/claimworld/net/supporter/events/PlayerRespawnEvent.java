package claimworld.net.supporter.events;

import claimworld.net.supporter.utils.items.ReadyItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerRespawnEvent implements Listener {

    @EventHandler
    public void playerRespawnEvent(org.bukkit.event.player.PlayerRespawnEvent event) {
        event.getPlayer().getInventory().setItem(17, ReadyItems.getInstance().get("Menu"));
    }
}
