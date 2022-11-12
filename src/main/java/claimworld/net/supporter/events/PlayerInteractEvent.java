package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.battlepass.Skills;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerInteractEvent implements Listener {

    List<Material> allowedSpawnEggs = new ArrayList<>();

    public PlayerInteractEvent() {
        allowedSpawnEggs.add(Material.ZOMBIE_SPAWN_EGG);
        allowedSpawnEggs.add(Material.SKELETON_SPAWN_EGG);
        allowedSpawnEggs.add(Material.WITCH_SPAWN_EGG);
        allowedSpawnEggs.add(Material.CREEPER_SPAWN_EGG);
    }

    @EventHandler
    public void playerInteractEvent(org.bukkit.event.player.PlayerInteractEvent event) {
        if (event.getHand() == null) return;
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;
        if (!allowedSpawnEggs.contains(event.getMaterial())) return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null) return;

        if (!(event.getClickedBlock().getLocation().distance(player.getLocation()) < 7)) return;
        if (event.getClickedBlock().getType() != Material.SPAWNER) return;

        Material itemType = item.getType();
        if (itemType == Material.WITCH_SPAWN_EGG) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
                TaskManager.getInstance().tryFinishTask(player, new Task("Zresp wiedzme.", "", 0));
            });
            return;
        }

        Skills skills = new Skills();

        if (!skills.canActivateSkill(player, "Twoj spawner")) {
            event.setCancelled(true);
        } else {
            player.getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
            skills.renderSkillEffect(event.getClickedBlock().getLocation());

            if (itemType != Material.ZOMBIE_SPAWN_EGG) return;

            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
                TaskManager.getInstance().tryFinishTask(player, new Task("Ustaw zombie w spawnerze.", "", 0));
            });
        }
    }
}
