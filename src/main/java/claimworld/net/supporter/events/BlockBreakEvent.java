package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.BonusManager;
import claimworld.net.supporter.utils.items.ReadyItems;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.bukkit.Bukkit.getScheduler;

public class BlockBreakEvent implements Listener {

    ReadyItems readyItems = ReadyItems.getInstance();
    TaskManager taskManager = TaskManager.getInstance();

    private final List<Material> decemberBonusMaterials = new ArrayList<>();

    private void dropItem(World world, Location location) {
        world.dropItem(location, new ItemStack(Material.DIAMOND));
        world.playSound(location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.7f, 0.75f);
        world.spawnParticle(Particle.EXPLOSION_NORMAL, location, 1);
    }

    public BlockBreakEvent() {
        //enable at 6, 24, 25, 26 and 31 of december
        decemberBonusMaterials.add(Material.DIAMOND_ORE);
        decemberBonusMaterials.add(Material.DEEPSLATE_DIAMOND_ORE);
        decemberBonusMaterials.add(Material.EMERALD_ORE);
        decemberBonusMaterials.add(Material.DEEPSLATE_EMERALD_ORE);
        decemberBonusMaterials.add(Material.ANCIENT_DEBRIS);
    }

    @EventHandler
    public void blockBreakEvent(org.bukkit.event.block.BlockBreakEvent event) {
        if (!(event.getExpToDrop() > 0 )) return;

        BonusManager bonusManager = BonusManager.getInstance();
        if (bonusManager.getBonuses().get("DoubleXP")) {
            event.setExpToDrop(event.getExpToDrop() * 2);
        }

        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.SILK_TOUCH) != 0) return;

        World world = player.getWorld();
        Location location = event.getBlock().getLocation();
        Material material = event.getBlock().getType();
        //enable at 6, 24, 25, 26 and 31 of december
        if (decemberBonusMaterials.contains(material)) {
            if (new Random().nextInt(12) == 0) world.dropItem(location, readyItems.get("Prezent"));
        }

        Map<String, Task> taskMap = taskManager.getTaskMap();

        if (material == Material.DEEPSLATE_EMERALD_ORE || material == Material.EMERALD_ORE) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("breakEmeralds")));
            return;
        }

        if (material == Material.DIAMOND_ORE || material == Material.DEEPSLATE_DIAMOND_ORE) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("breakDiamonds")));

            if (!bonusManager.getBonuses().get("Diaxy+")) return;

            dropItem(world, location);

            int random = new Random().nextInt(5);
            if (random > 0) return;

            getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                dropItem(world, location);
            }, 4L);
        }
    }
}