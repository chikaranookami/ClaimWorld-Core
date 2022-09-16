package claimworld.net.supporter.events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

public class PlayerItemConsumeEvent implements Listener {

    @EventHandler
    public void playerItemConsumeEvent(org.bukkit.event.player.PlayerItemConsumeEvent event) {
        //if (new Random().nextInt(1000) > 0) return;

        Player player = event.getPlayer();
        Block block = player.getLocation().add(0, 1, 0).getBlock();

        if (block.getType() != Material.AIR) return;

        block.setType(Material.DIRT);

        World world = player.getWorld();
        Location location = player.getLocation();

        world.playSound(location, Sound.ENTITY_SHEEP_AMBIENT, 0.5f, 2f);
        world.playSound(location, Sound.BLOCK_ROOTED_DIRT_PLACE, 0.75f, 2f);
        world.playSound(location, Sound.BLOCK_ROOTED_DIRT_BREAK, 0.5f,2f);
        world.spawnParticle(Particle.SPELL, location, 10, 0.1, 0.1, 0.1, 0);

        Bukkit.broadcastMessage(ChatColor.GREEN + "> Gracz " + player.getDisplayName() + " wlasnie zrobil... kupe.");
    }
}
