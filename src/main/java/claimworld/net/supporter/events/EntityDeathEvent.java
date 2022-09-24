package claimworld.net.supporter.events;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class EntityDeathEvent implements Listener {

    @EventHandler
    public void entityDeathEvent(org.bukkit.event.entity.EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.ZOMBIFIED_PIGLIN) return;
        int random = new Random().nextInt(10);

        if (random > 0) return;

        event.getDrops().add(new ItemStack(Material.NETHER_WART));
    }
}
