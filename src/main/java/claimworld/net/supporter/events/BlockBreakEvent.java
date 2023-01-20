package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.BonusManager;
import claimworld.net.supporter.tasks.Task;
import claimworld.net.supporter.tasks.TaskManager;
import claimworld.net.supporter.utils.PrivateChestsUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.Map;
import java.util.Random;

import static org.bukkit.Bukkit.getScheduler;

public class BlockBreakEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();
    BonusManager bonusManager = BonusManager.getInstance();
    PrivateChestsUtils privateChestsUtils = PrivateChestsUtils.getInstance();

    private void dropItem(World world, Location location) {
        world.dropItem(location, new ItemStack(Material.DIAMOND));
        world.playSound(location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.75f, 0.75f);
        world.spawnParticle(Particle.EXPLOSION_NORMAL, location, 1);
    }

    private void handleBonus(Player player, Block block) {
        World world = player.getWorld();
        Location location = block.getLocation();

        getScheduler().runTask(Supporter.getPlugin(), () -> dropItem(world, location));

        int random = new Random().nextInt(5);
        if (random > 0) return;

        getScheduler().runTaskLater(Supporter.getPlugin(), () -> dropItem(world, location), 4L);
    }

    @EventHandler
    public void blockBreakEvent(org.bukkit.event.block.BlockBreakEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        Player player = event.getPlayer();

        /*
        if (material == Material.CHEST) {
            if (!(block.getState() instanceof TileState)) return;

            PersistentDataContainer container = ((TileState) block.getState()).getPersistentDataContainer();
            if (!privateChestsUtils.hasProtection(container)) return;
            if (privateChestsUtils.hasAccess(player, container)) return;

            event.setCancelled(true);
            return;
        }
         */

        if (event.getExpToDrop() > 0 ) {
            if (bonusManager.getBonuses().get("DoubleXP")) {
                event.setExpToDrop(event.getExpToDrop() * 2);
            }

            if (player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.SILK_TOUCH) != 0) return;

            Map<String, Task> taskMap = taskManager.getTaskMap();

            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("breakAnything")));

            if (material == Material.ANCIENT_DEBRIS) {
                getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("breakDebris")));
                return;
            }

            if (material == Material.DEEPSLATE_EMERALD_ORE || material == Material.EMERALD_ORE) {
                getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("breakEmeralds")));
                return;
            }

            if (material == Material.DIAMOND_ORE || material == Material.DEEPSLATE_DIAMOND_ORE) {
                getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
                    taskManager.tryFinishTask(player, taskMap.get("breakDiamonds"));
                    if (!bonusManager.getBonuses().get("Diaxy+")) return;
                    handleBonus(player, block);
                });
            }
        }
    }
}