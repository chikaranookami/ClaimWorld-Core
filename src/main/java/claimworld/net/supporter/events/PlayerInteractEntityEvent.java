package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.guis.BonusManager;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.bukkit.Bukkit.*;
public class PlayerInteractEntityEvent implements Listener {

    BonusManager bonusManager = BonusManager.getInstance();

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

    @EventHandler
    public void playerInteractEntityEvent(org.bukkit.event.player.PlayerInteractEntityEvent event) {
        if (!event.getHand().equals(EquipmentSlot.HAND)) return;

        Player player = event.getPlayer();

        if (!entityTypes.contains(event.getRightClicked().getType())) {
            if (!bonusManager.getBonuses().get("Podnoszenie+")) return;
            if (blockedEntityTypes.contains(event.getRightClicked().getType())) return;
            if (fixedEntityTypes.contains(event.getRightClicked().getType())) return;
            if (!event.getRightClicked().hasGravity()) return;
        }

        if (!player.getInventory().getItemInMainHand().getType().isAir()) return;
        if (delayedPlayers.contains(player)) return;

        if (player.isSneaking()) {
            if (event.getRightClicked().getType() == EntityType.HORSE) event.setCancelled(true);
            if (event.getRightClicked().getType() == EntityType.SKELETON_HORSE) event.setCancelled(true);
            if (event.getRightClicked().getType() == EntityType.ZOMBIE_HORSE) event.setCancelled(true);

            if (player.getPassengers().isEmpty()) {
                Entity entity = event.getRightClicked();

                player.addPassenger(entity);
                player.playSound(player, Sound.ITEM_ARMOR_EQUIP_ELYTRA, 0.7f, 1.0f);

                delayedPlayers.add(player);
                removeDelayedPlayer(player);

                return;
            }

            Vector direction = player.getLocation().getDirection();

            for (Entity entity : player.getPassengers()) {
                player.removePassenger(entity);
                player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 0.5f, 1.0f);

                int force = new Random().nextInt(15);
                double number;

                if (force == 0) {
                    number = 1.5;
                } else {
                    number = 1.25;
                }
                if (bonusManager.getBonuses().get("Rzucanie+")) number++;

                entity.setVelocity(direction.multiply(number));

                delayedPlayers.add(player);
                removeDelayedPlayer(player);

                showParticles(entity);
            }

            return;
        }

        if (player.getPassengers().isEmpty()) return;

        for (Entity entity : player.getPassengers()) {
            player.removePassenger(entity);
            player.playSound(player, Sound.ITEM_ARMOR_EQUIP_ELYTRA, 0.7f, 1.0f);

            delayedPlayers.add(player);
            removeDelayedPlayer(player);
        }
    }
}
