package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.guis.BonusManager;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static org.bukkit.Bukkit.getScheduler;

public class BlockBreakEvent implements Listener {

    BonusManager bonusManager = BonusManager.getInstance();

    private void dropItem(World world, Location location) {
        world.dropItem(location, new ItemStack(Material.DIAMOND));
        world.playSound(location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.7f, 0.75f);
        world.spawnParticle(Particle.EXPLOSION_NORMAL, location, 1);
    }

    @EventHandler
    public void blockBreakEvent(org.bukkit.event.block.BlockBreakEvent event) {
        if (!(event.getExpToDrop() > 0 )) return;

        if (bonusManager.getBonuses().get("DoubleXP")) {
            event.setExpToDrop(event.getExpToDrop() * 2);
        }

        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.SILK_TOUCH) != 0) return;

        if (event.getBlock().getType() == Material.DEEPSLATE_EMERALD_ORE || event.getBlock().getType() == Material.EMERALD_ORE) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
                TaskManager.getInstance().tryFinishTask(player, new Task("Rozkop 6 emeraldow.", "counter", 6));
            });
            return;
        }

        if (event.getBlock().getType() == Material.DIAMOND_ORE || event.getBlock().getType() == Material.DEEPSLATE_DIAMOND_ORE) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
                TaskManager.getInstance().tryFinishTask(player, new Task("Rozkop stack diaxow.", "counter", 64));
            });

            if (!bonusManager.getBonuses().get("Diaxy+")) return;

            dropItem(player.getWorld(), event.getBlock().getLocation());

            int random = new Random().nextInt(5);
            if (random > 0) return;

            getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                dropItem(player.getWorld(), event.getBlock().getLocation());
            }, 4L);
        }
    }
}