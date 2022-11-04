package claimworld.net.supporter.events;

import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityDeathEvent implements Listener {

    @EventHandler
    public void entityDeathEvent(org.bukkit.event.entity.EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIFIED_PIGLIN) {
            if (new Random().nextInt(10) > 0) return;

            event.getDrops().add(new ItemStack(Material.NETHER_WART));
        }

        if (!(event.getEntityType() == EntityType.CHICKEN)) return;
        if (((Chicken) event.getEntity()).isAware()) return;

        event.getDrops().clear();
        event.setDroppedExp(0);
    }
}
