package claimworld.net.supporter.events;

import claimworld.net.supporter.utils.JetpackUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerSwapHandItemsEvent implements Listener {

    JetpackUtils jetpackUtils = JetpackUtils.getInstance();

    @EventHandler
    public void playerSwapHandItemsEvent(org.bukkit.event.player.PlayerSwapHandItemsEvent event) {
        String playerName = event.getPlayer().getName();

        if (jetpackUtils.isJetpack(event.getOffHandItem())) {
            jetpackUtils.switchJetpack(playerName);
            return;
        }

        if (jetpackUtils.isJetpack(event.getMainHandItem())) {
            jetpackUtils.disableJetpack(playerName);
            return;
        }
    }
}
