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

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerInteractEntityEvent implements Listener {

    private final List<EntityType> entityTypes = Arrays.asList(EntityType.COW, EntityType.SHEEP, EntityType.PIG, EntityType.CHICKEN);

    private int showParticlesTask;

    @EventHandler
    public void playerInteractEntityEvent(org.bukkit.event.player.PlayerInteractEntityEvent event) {
        if (!event.getHand().equals(EquipmentSlot.HAND)) return;
        if (!entityTypes.contains(event.getRightClicked().getType())) return;

        Player player = event.getPlayer();

        if (!player.getInventory().getItemInMainHand().getType().isAir()) return;

        if (player.isSneaking()) {
            if (player.getPassengers().isEmpty()) {
                Entity entity = event.getRightClicked();

                player.addPassenger(entity);
                player.playSound(player, Sound.ITEM_ARMOR_EQUIP_ELYTRA, 0.7f, 1.0f);

                return;
            }

            Vector direction = player.getLocation().getDirection();

            for (Entity entity : player.getPassengers()) {
                World world = entity.getWorld();

                player.removePassenger(entity);
                player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 0.5f, 1.0f);

                int force = new Random().nextInt(10);

                if (force == 0) {
                    entity.setVelocity(direction.multiply(1.5));
                }

                if (force > 0) {
                    entity.setVelocity(direction.multiply(1));
                }

                if (player.isSneaking()) {
                    entity.setVelocity(direction.multiply(1.5));
                }

                showParticlesTask = getScheduler().scheduleSyncRepeatingTask(Supporter.getInstance(), () -> {
                    if (!entity.isOnGround()) {
                        Location location = entity.getLocation().add(0, 1, 0);

                        world.spawnParticle(Particle.CLOUD, location, 1, 0, 0, 0, 0);
                        world.spawnParticle(Particle.ASH, location, 1, 0, 0, 0, 0);
                        world.spawnParticle(Particle.WHITE_ASH, location, 1, 0, 0, 0, 0);
                    } else {
                        getScheduler().cancelTask(showParticlesTask);
                    }
                }, 2L, 2L);
            }

            return;
        }

        if (player.getPassengers().isEmpty()) return;

        for (Entity entity : player.getPassengers()) {
            player.removePassenger(entity);
            player.playSound(player, Sound.ITEM_ARMOR_EQUIP_ELYTRA, 0.7f, 1.0f);
        }
    }
}
