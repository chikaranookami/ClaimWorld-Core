package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.battlepass.SkillManager;
import claimworld.net.supporter.items.ReadyItems;
import claimworld.net.supporter.tasks.Task;
import claimworld.net.supporter.tasks.TaskManager;
import claimworld.net.supporter.utils.JetpackUtils;
import claimworld.net.supporter.utils.PrivateChestsUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.*;

public class PlayerInteractEvent implements Listener {

    ReadyItems readyItems = ReadyItems.getInstance();
    TaskManager taskManager = TaskManager.getInstance();
    JetpackUtils jetpackUtils = JetpackUtils.getInstance();
    PrivateChestsUtils privateChestsUtils = PrivateChestsUtils.getInstance();

    SkillManager skillManager = new SkillManager();

    private final List<Material> allowedSpawnEggs = new ArrayList<>();
    private final List<Player> delayedPlayers = new ArrayList<>();

    public PlayerInteractEvent() {
        allowedSpawnEggs.add(Material.ZOMBIE_SPAWN_EGG);
        allowedSpawnEggs.add(Material.SKELETON_SPAWN_EGG);
        allowedSpawnEggs.add(Material.WITCH_SPAWN_EGG);
        allowedSpawnEggs.add(Material.CREEPER_SPAWN_EGG);
    }

    private void useChest(Player player, ItemStack item, Map<String, Task> taskMap) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            if (delayedPlayers.contains(player)) return;
            delayedPlayers.add(player);

            getScheduler().runTask(Supporter.getPlugin(), () -> {
                item.setAmount(item.getAmount() - 1);
                player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
                dispatchCommand(getConsoleSender(), "openchest Skrzynia_smoka " + player.getName());
            });

            taskManager.tryFinishTask(player, taskMap.get("openDragonChest"));

            getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> delayedPlayers.remove(player), 100);
        });
    }

    @EventHandler
    public void playerInteractEvent(org.bukkit.event.player.PlayerInteractEvent event) {
        if (event.getHand() == null) return;

        ItemStack item = event.getItem();
        /*if (event.getHand().equals(EquipmentSlot.OFF_HAND)) {
            if (!jetpackUtils.isJetpack(item)) return;

            event.setCancelled(true);
            return;
        }*/
        
        if (item == null) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        Material blockType = block.getType();
        Player player = event.getPlayer();
        Material itemType = item.getType();
        Location location = block.getLocation();
        
        if (blockType == Material.BARRIER) {
            if (itemType != Material.NETHERITE_HOE) return;
            block.setType(Material.AIR);
            player.getWorld().dropItemNaturally(location, new ItemStack(Material.BARRIER));
            return;
        }

        /*
        if (block != null && block.getType() == Material.CHEST) {
            if (!(block.getState() instanceof TileState)) return;

            PersistentDataContainer container = ((TileState) block.getState()).getPersistentDataContainer();
            if (!privateChestsUtils.hasProtection(container)) return;
            if (privateChestsUtils.hasAccess(player, container)) return;

            event.setCancelled(true);
            return;
        }
         */

        Map<String, Task> taskMap = taskManager.getTaskMap();

        if (item.equals(readyItems.get("Skrzynia_smoka"))) {
            event.setCancelled(true);
            useChest(player, item, taskMap);
            return;
        }

        if (!allowedSpawnEggs.contains(event.getMaterial())) return;
        if (event.getClickedBlock().getLocation().distance(player.getLocation()) > 10) return;
        
        if (itemType == Material.WITCH_SPAWN_EGG) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("spawnWitch")));
            return;
        }

        if (blockType != Material.SPAWNER) return;

        if (!skillManager.canActivateSkill(player, "Twoj spawner")) {
            event.setCancelled(true);
            return;
        }
        
        skillManager.renderSkillEffect(location);
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> player.getInventory().getItemInMainHand().setAmount(item.getAmount() - 1), 1L);

        if (itemType == Material.ZOMBIE_SPAWN_EGG) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("setSpawnerToZombie")));
        }
    }
}
