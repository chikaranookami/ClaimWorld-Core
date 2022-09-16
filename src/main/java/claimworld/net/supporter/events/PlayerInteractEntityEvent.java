package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static claimworld.net.supporter.Supporter.doubledForce;
import static claimworld.net.supporter.Supporter.pickupAll;
import static org.bukkit.Bukkit.getScheduler;

public class PlayerInteractEntityEvent implements Listener {

    private final List<EntityType> entityTypes = Arrays.asList(EntityType.COW, EntityType.SHEEP, EntityType.PIG, EntityType.CHICKEN);
    private final List<EntityType> blockedEntityTypes = Arrays.asList(EntityType.ENDER_DRAGON, EntityType.ENDER_CRYSTAL, EntityType.AREA_EFFECT_CLOUD, EntityType.DRAGON_FIREBALL, EntityType.ENDER_SIGNAL, EntityType.EXPERIENCE_ORB, EntityType.LIGHTNING, EntityType.SMALL_FIREBALL);
    private final List<Player> delayedPlayers = new ArrayList<>();

    private int showParticlesTask;

    @EventHandler
    public void playerInteractEntityEvent(org.bukkit.event.player.PlayerInteractEntityEvent event) {
        if (!event.getHand().equals(EquipmentSlot.HAND)) return;
        if (!entityTypes.contains(event.getRightClicked().getType())) {
            if (!pickupAll) return;
            if (blockedEntityTypes.contains(event.getRightClicked().getType())) return;
            if (!event.getRightClicked().hasGravity()) return;
        }

        Player player = event.getPlayer();

        if (!player.getInventory().getItemInMainHand().getType().isAir()) return;
        if (delayedPlayers.contains(player)) return;

        if (player.isSneaking()) {
            if (player.getPassengers().isEmpty()) {
                Entity entity = event.getRightClicked();

                if (getScheduler().isCurrentlyRunning(showParticlesTask)) {
                    getScheduler().cancelTask(showParticlesTask);
                }

                player.addPassenger(entity);
                player.playSound(player, Sound.ITEM_ARMOR_EQUIP_ELYTRA, 0.7f, 1.0f);

                delayedPlayers.add(player);
                getScheduler().runTaskLaterAsynchronously(Supporter.getInstance(), () -> {
                    delayedPlayers.remove(player);
                }, 15L);

                return;
            }

            Vector direction = player.getLocation().getDirection();

            for (Entity entity : player.getPassengers()) {
                World world = entity.getWorld();

                player.removePassenger(entity);
                player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 0.5f, 1.0f);

                int force = new Random().nextInt(10);
                double number;

                if (force == 0) {
                    number = 1.75;
                } else {
                    number = 1;
                }
                if (doubledForce) number++;

                entity.setVelocity(direction.multiply(number));

                delayedPlayers.add(player);
                getScheduler().runTaskLaterAsynchronously(Supporter.getInstance(), () -> {
                    delayedPlayers.remove(player);
                }, 15L);

                showParticlesTask = getScheduler().scheduleSyncRepeatingTask(Supporter.getInstance(), () -> {
                    if (!entity.isOnGround() && !entity.isDead() && !entity.isInWater() && !getScheduler().isCurrentlyRunning(showParticlesTask)) {
                        Location location = entity.getLocation().add(0, 1, 0);

                        world.spawnParticle(Particle.CLOUD, location, 1, 0, 0, 0, 0);
                        world.spawnParticle(Particle.ASH, location, 1, 0, 0, 0, 0);
                        world.spawnParticle(Particle.WHITE_ASH, location, 1, 0, 0, 0, 0);
                    } else {
                        getScheduler().cancelTask(showParticlesTask);
                    }
                }, 2L, 2L);

                getScheduler().runTaskLaterAsynchronously(Supporter.getInstance(), () -> {
                    if (!getScheduler().isCurrentlyRunning(showParticlesTask)) return;

                    getScheduler().cancelTask(showParticlesTask);
                }, 100L);
            }

            return;
        }

        if (player.getPassengers().isEmpty()) return;

        for (Entity entity : player.getPassengers()) {
            player.removePassenger(entity);
            player.playSound(player, Sound.ITEM_ARMOR_EQUIP_ELYTRA, 0.7f, 1.0f);

            delayedPlayers.add(player);
            getScheduler().runTaskLaterAsynchronously(Supporter.getInstance(), () -> {
                delayedPlayers.remove(player);
            }, 15L);
        }
    }
}
