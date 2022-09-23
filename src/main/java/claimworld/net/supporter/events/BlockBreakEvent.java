package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static org.bukkit.Bukkit.getScheduler;

public class BlockBreakEvent implements Listener {

    @EventHandler
    public void blockBreakEvent(org.bukkit.event.block.BlockBreakEvent event) {
        if (Supporter.doubleXp) {
            if (event.getExpToDrop() <= 0 )return;
            event.setExpToDrop(event.getExpToDrop() * 2);
        }

        if (Supporter.moreFromOres) {
            Block block = event.getBlock();

            if (!(block.getType() == Material.DIAMOND_ORE || block.getType() == Material.DEEPSLATE_DIAMOND_ORE)) return;
            if (event.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.SILK_TOUCH) != 0) return;

            World world = event.getPlayer().getWorld();
            Location location = event.getBlock().getLocation();

            world.dropItem(location, new ItemStack(Material.DIAMOND));
            world.playSound(location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.7f, 0.75f);
            world.spawnParticle(Particle.EXPLOSION_NORMAL, location, 1);

            if (new Random().nextInt(5) != 0) return;

            getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                world.dropItem(location, new ItemStack(Material.DIAMOND));
                world.playSound(location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.7f, 0.75f);
                world.spawnParticle(Particle.EXPLOSION_NORMAL, location, 1);
            }, 4L);

        }
    }
}
