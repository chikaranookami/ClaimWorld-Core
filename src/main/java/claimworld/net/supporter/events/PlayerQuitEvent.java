package claimworld.net.supporter.events;

import claimworld.net.supporter.utils.JetpackUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class PlayerQuitEvent implements Listener {

    JetpackUtils jetpackUtils = JetpackUtils.getInstance();

    @EventHandler
    public void playerQuitEvent(org.bukkit.event.player.PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        event.setQuitMessage(colorize("&f> " + playerName + " opuscil serwer."));

        jetpackUtils.disableJetpack(playerName);
    }
}
