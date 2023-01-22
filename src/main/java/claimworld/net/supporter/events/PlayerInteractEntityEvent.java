package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.items.ReadyItems;
import claimworld.net.supporter.utils.BonusManager;
import claimworld.net.supporter.tasks.TaskManager;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.bukkit.Bukkit.*;
public class PlayerInteractEntityEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();
    BonusManager bonusManager = BonusManager.getInstance();
    ReadyItems readyItems = ReadyItems.getInstance();

    private final List<EntityType> entityTypes = Arrays.asList(EntityType.COW, EntityType.SHEEP, EntityType.PIG, EntityType.CHICKEN);
    private final List<EntityType> blockedEntityTypes = Arrays.asList(EntityType.ENDER_DRAGON, EntityType.ENDER_CRYSTAL);
    private final List<EntityType> fixedEntityTypes = Arrays.asList(EntityType.BEE, EntityType.ALLAY);
    private final List<Player> delayedPlayers = new ArrayList<>();

    private void showParticles(Entity entity) {
        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                if (i >= 3) {
                    cancel();
                    return;
                }

                if (!entity.isOnGround()) entity.getWorld().spawnParticle(Particle.CLOUD, entity.getLocation(), 1, 0, 0, 0, 0);

                i += 1;
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 5L), 5L);
    }

    private void removeDelayedPlayer(Player player) {
        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
            delayedPlayers.remove(player);
        }, 10L);
    }

    private void useFireScroll(Entity entity) {
        entity.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, entity.getLocation().add(1, 0, 1), 5, 0.1, 0.1, 0.1, 0.1);

        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                if (i >= 60) {
                    cancel();
                    return;
                }

                entity.setFireTicks(20);
                i++;
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 5L), 5L);
    }

    @EventHandler
    public void playerInteractEntityEvent(org.bukkit.event.player.PlayerInteractEntityEvent event) {
        if (!event.getHand().equals(EquipmentSlot.HAND)) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        Entity entity = event.getRightClicked();

        if (item.isSimilar(readyItems.get("Zwoj_ognia"))) {
            if (item.getType().isAir()) return;
            item.setAmount(item.getAmount() - 1);
            useFireScroll(entity);
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("useFireScroll")));
            return;
        }

        EntityType entityType = event.getRightClicked().getType();

        if (!entityTypes.contains(entityType)) {
            if (!bonusManager.getBonuses().get("Podnoszenie+")) return;
            if (blockedEntityTypes.contains(entityType)) return;
            if (fixedEntityTypes.contains(entityType)) return;
            if (!entity.hasGravity()) return;
        }

        if (delayedPlayers.contains(player)) return;

        if (player.isSneaking()) {
            if (entityType == EntityType.HORSE) event.setCancelled(true);
            if (entityType == EntityType.SKELETON_HORSE) event.setCancelled(true);
            if (entityType == EntityType.ZOMBIE_HORSE) event.setCancelled(true);

            if (player.getPassengers().isEmpty()) {
                player.addPassenger(entity);
                player.playSound(player, Sound.ITEM_ARMOR_EQUIP_ELYTRA, 0.7f, 1.0f);

                if (entityType == EntityType.CREEPER) getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("pickupCreeper")));

                delayedPlayers.add(player);
                removeDelayedPlayer(player);

                return;
            }

            Vector direction = player.getLocation().getDirection();

            for (Entity passenger : player.getPassengers()) {
                player.removePassenger(passenger);
                player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 0.5f, 1.0f);

                int force = new Random().nextInt(15);
                double number;

                if (force == 0) {
                    number = 1.5;
                } else {
                    number = 1.25;
                }
                if (bonusManager.getBonuses().get("Rzucanie+")) number++;

                passenger.setVelocity(direction.multiply(number));

                delayedPlayers.add(player);
                removeDelayedPlayer(player);

                showParticles(passenger);
            }

            return;
        }

        if (player.getPassengers().isEmpty()) return;

        for (Entity passenger : player.getPassengers()) {
            player.removePassenger(passenger);
            player.playSound(player, Sound.ITEM_ARMOR_EQUIP_ELYTRA, 0.7f, 1.0f);

            delayedPlayers.add(player);
            removeDelayedPlayer(player);
        }
    }
}
