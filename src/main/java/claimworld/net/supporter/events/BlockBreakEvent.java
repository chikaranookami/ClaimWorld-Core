package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static org.bukkit.Bukkit.getScheduler;

public class BlockBreakEvent implements Listener {

    private void dropItem(World world, Location location) {
        world.dropItem(location, new ItemStack(Material.DIAMOND));
        world.playSound(location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.7f, 0.75f);
        world.spawnParticle(Particle.EXPLOSION_NORMAL, location, 1);
    }

    @EventHandler
    public void blockBreakEvent(org.bukkit.event.block.BlockBreakEvent event) {
        if (Supporter.doubleXp) {
            if (event.getExpToDrop() <= 0 )return;
            event.setExpToDrop(event.getExpToDrop() * 2);
        }

        if (Supporter.moreFromOres) {
            if (!(event.getBlock().getType() == Material.DIAMOND_ORE || event.getBlock().getType() == Material.DEEPSLATE_DIAMOND_ORE)) return;
            if (event.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.SILK_TOUCH) != 0) return;

            dropItem(event.getPlayer().getWorld(), event.getBlock().getLocation());

            if (new Random().nextInt(5) != 0) return;

            getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                dropItem(event.getPlayer().getWorld(), event.getBlock().getLocation());
            }, 4L);

        }
    }
}
