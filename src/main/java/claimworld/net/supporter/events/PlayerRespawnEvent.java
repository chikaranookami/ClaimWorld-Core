package claimworld.net.supporter.events;

import claimworld.net.supporter.items.ReadyItems;
import claimworld.net.supporter.utils.AttributesManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerRespawnEvent implements Listener {

    private final AttributesManager attributesManager = new AttributesManager();

    @EventHandler
    public void playerRespawnEvent(org.bukkit.event.player.PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        player.getInventory().setItem(17, ReadyItems.getInstance().getMenuItem());
        attributesManager.updatePlayerHealth(player);
    }
}
