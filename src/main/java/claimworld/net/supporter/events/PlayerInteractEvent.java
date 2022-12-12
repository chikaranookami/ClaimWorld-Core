package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.battlepass.SkillManager;
import claimworld.net.supporter.utils.items.ReadyItems;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.*;

public class PlayerInteractEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();
    ReadyItems readyItems = ReadyItems.getInstance();

    private final List<Material> allowedSpawnEggs = new ArrayList<>();
    private final List<Player> delayedPlayers = new ArrayList<>();
    private final List<ItemStack> chests = new ArrayList<>();

    public PlayerInteractEvent() {
        allowedSpawnEggs.add(Material.ZOMBIE_SPAWN_EGG);
        allowedSpawnEggs.add(Material.SKELETON_SPAWN_EGG);
        allowedSpawnEggs.add(Material.WITCH_SPAWN_EGG);
        allowedSpawnEggs.add(Material.CREEPER_SPAWN_EGG);

        ReadyItems readyItems = ReadyItems.getInstance();
        chests.add(readyItems.get("Skrzynia_smoka"));
        chests.add(readyItems.get("Prezent"));
    }

    @EventHandler
    public void playerInteractEvent(org.bukkit.event.player.PlayerInteractEvent event) {
        if (event.getHand() == null) return;
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (event.getClickedBlock() == null) return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null) return;

        Map<String, Task> taskMap = taskManager.getTaskMap();

        if (chests.contains(item) || item.isSimilar(readyItems.get("Prezent"))) {
            if (delayedPlayers.contains(player)) return;
            delayedPlayers.add(player);

            event.setCancelled(true);
            player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);

            long delay = 100;

            if (item.equals(readyItems.get("Skrzynia_smoka"))) {
                dispatchCommand(getConsoleSender(), "openchest Skrzynia_smoka " + player.getName());
                getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("openDragonChest")));
            }

            if (item.isSimilar(readyItems.get("Prezent"))) {
                delay = 10;
                dispatchCommand(getConsoleSender(), "openchest Prezent " + player.getName());
            }

            item.setAmount(item.getAmount() - 1);

            getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> delayedPlayers.remove(player), delay);

            return;
        }

        if (!allowedSpawnEggs.contains(event.getMaterial())) return;
        if (event.getClickedBlock().getLocation().distance(player.getLocation()) > 10) return;

        Material itemType = item.getType();
        if (itemType == Material.WITCH_SPAWN_EGG) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("spawnWitch")));
            return;
        }

        if (event.getClickedBlock().getType() != Material.SPAWNER) return;

        SkillManager skillManager = new SkillManager();
        if (!skillManager.canActivateSkill(player, "Twoj spawner")) {
            event.setCancelled(true);
        } else {
            player.getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
            skillManager.renderSkillEffect(event.getClickedBlock().getLocation());

            if (itemType != Material.ZOMBIE_SPAWN_EGG) return;

            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("setSpawnerToZombie")));
        }
    }
}
