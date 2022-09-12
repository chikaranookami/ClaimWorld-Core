package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerChangedWorldEvent implements Listener {

    @EventHandler
    public void playerChangedWorldEvent(org.bukkit.event.player.PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        if (!(player.getWorld().getEnvironment() == World.Environment.THE_END)) return;
        if (Supporter.toggleEnd) return;

        Bukkit.dispatchCommand(player, "spawn");

        player.sendMessage(ChatColor.GRAY + "End niedostepny. Mozesz go wlaczyc w Sklepie Punktowym (/punkty).");
    }
}
