package claimworld.net.supporter.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;

public class SignChangeEvent implements Listener {

    @EventHandler
    public void signChangeEvent(org.bukkit.event.block.SignChangeEvent event) {
        String[] lines = event.getLines();

        if (lines.length < 1) return;

        Bukkit.getConsoleSender().sendMessage("Player " + event.getPlayer().getDisplayName() + " napisal na tabliczce: " + Arrays.toString(lines) + " (ilosc znakow " + lines.length + ")");
    }
}
