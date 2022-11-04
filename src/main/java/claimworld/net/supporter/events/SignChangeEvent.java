package claimworld.net.supporter.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;

public class SignChangeEvent implements Listener {

    @EventHandler
    public void signChangeEvent(org.bukkit.event.block.SignChangeEvent event) {
        Player player = event.getPlayer();
        Bukkit.getConsoleSender().sendMessage("Player " + player.getDisplayName() + " napisal na tabliczce (lokalizacja " + player.getLocation() + ": " + String.join(" ", event.getLines()));
    }
}
