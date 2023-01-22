package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.JetpackUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerSwapHandItemsEvent implements Listener {

    JetpackUtils jetpackUtils = JetpackUtils.getInstance();

    @EventHandler
    public void playerSwapHandItemsEvent(org.bukkit.event.player.PlayerSwapHandItemsEvent event) {
        String playerName = event.getPlayer().getName();
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            if (jetpackUtils.isJetpack(event.getOffHandItem())) {
                jetpackUtils.switchJetpack(playerName);
            } else {
                jetpackUtils.disableJetpack(playerName);
            }
        });
    }
}
