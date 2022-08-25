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

public class PlayerInteractEntityEvent implements Listener {

    private final List<EntityType> entityTypes = Arrays.asList(EntityType.COW, EntityType.SHEEP, EntityType.PIG, EntityType.CHICKEN);

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
                player.playSound(player, Sound.ITEM_ARMOR_EQUIP_ELYTRA, 1.0f, 1.0f);

                return;
            }

            Vector direction = player.getLocation().getDirection();

            for (Entity entity : player.getPassengers()) {
                World world = entity.getWorld();

                player.removePassenger(entity);
                player.playSound(player, Sound.BLOCK_HONEY_BLOCK_PLACE, 1.0f, 1.0f);

                entity.setVelocity(direction.multiply(new Random().nextInt(1) + 2));

                Bukkit.getScheduler().runTaskLaterAsynchronously(Supporter.getInstance(), () -> {
                    Location location = entity.getLocation().add(0, 1, 0);
                    world.spawnParticle(Particle.CLOUD, location, 1);
                    world.spawnParticle(Particle.ASH, location, 1);
                    world.spawnParticle(Particle.WHITE_ASH, location, 1);
                }, 8L);
            }

            return;
        }

        if (player.getPassengers().isEmpty()) return;

        for (Entity entity : player.getPassengers()) {
            player.removePassenger(entity);
            player.playSound(player, Sound.ITEM_ARMOR_EQUIP_ELYTRA, 1.0f, 1.0f);
        }
    }
}
