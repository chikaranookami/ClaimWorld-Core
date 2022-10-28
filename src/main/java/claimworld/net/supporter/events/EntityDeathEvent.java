package claimworld.net.supporter.events;

import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityDeathEvent implements Listener {

    private final List<EntityType> entityTypes = new ArrayList<>();

    public EntityDeathEvent() {
        entityTypes.add(EntityType.ZOMBIE);
        entityTypes.add(EntityType.SKELETON);
        entityTypes.add(EntityType.WITHER_SKELETON);
    }

    @EventHandler
    public void entityDeathEvent(org.bukkit.event.entity.EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.CHICKEN) {
            Chicken chicken = (Chicken) event.getEntity();
            if (chicken.isAware()) return;
            event.getDrops().clear();
            event.setDroppedExp(0);
            return;
        }

        if (event.getEntityType() == EntityType.ZOMBIFIED_PIGLIN) {
            int random = new Random().nextInt(10);

            if (random > 0) return;

            event.getDrops().add(new ItemStack(Material.NETHER_WART));
            return;
        }

        if (entityTypes.contains(event.getEntityType())) {
            if (event.getDrops().isEmpty()) return;
            for (ItemStack item : event.getDrops()) {
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), item);
            }
        }
    }
}
