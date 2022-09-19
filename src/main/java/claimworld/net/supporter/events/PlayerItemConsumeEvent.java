package claimworld.net.supporter.events;

import claimworld.net.supporter.utils.CustomItems;
import claimworld.net.supporter.utils.Messages;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class PlayerItemConsumeEvent implements Listener {

    @EventHandler
    public void playerItemConsumeEvent(org.bukkit.event.player.PlayerItemConsumeEvent event) {
        int poopChance = new Random().nextInt(1000);

        if (poopChance > 18) return;

        int random = new Random().nextInt(5);

        Player player = event.getPlayer();
        Block block = player.getLocation().getBlock();
        Block block1 = player.getLocation().add(0, 1, 0).getBlock();
        World world = player.getWorld();
        Location location = player.getLocation();

        if (poopChance > 2) {
            world.playSound(location, Sound.ENTITY_SHEEP_AMBIENT, 0.5f, 2f);
            world.spawnParticle(Particle.SPELL, location, 10, 0.75, 0.75, 0.75, 0);
            world.spawnParticle(Particle.SLIME, location, 10, 0.75, 0.75, 0.75);
            player.sendMessage(Messages.getUserPrefix() + "Chyba zbiera Ci sie na cos ciezszego...");
        }

        if (poopChance <= 2) {
            if (block.getType() != Material.AIR) return;
            if (random == 1) if (block1.getType() != Material.AIR) return;

            block.setType(Material.DIRT);

            if (random == 1) {
                if (block1.getType() != Material.AIR) return;
                block1.setType(Material.DIRT);
            }

            world.dropItem(location, new CustomItems().getShitItem());

            world.playSound(location, Sound.ENTITY_SHEEP_AMBIENT, 0.6f, 2f);
            world.playSound(location, Sound.BLOCK_ROOTED_DIRT_PLACE, 0.85f, 2f);
            world.playSound(location, Sound.BLOCK_ROOTED_DIRT_BREAK, 0.6f, 2f);
            world.spawnParticle(Particle.SPELL, location, 20, 0.75, 0.75, 0.75, 0);
            world.spawnParticle(Particle.SLIME, location, 20, 0.75, 0.75, 0.75);

            Bukkit.broadcastMessage(colorize(Messages.getBroadcastPrefix() + "Gracz&e" + player.getDisplayName() + " &fwlasnie sie... zesral."));
        }
    }
}