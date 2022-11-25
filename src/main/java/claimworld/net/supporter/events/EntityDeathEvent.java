package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bukkit.Bukkit.getScheduler;

public class EntityDeathEvent implements Listener {

    @EventHandler
    public void entityDeathEvent(org.bukkit.event.entity.EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player == null) return;

        EntityType entityType = event.getEntityType();
        Entity entity = event.getEntity();

        if (entity instanceof Monster) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> TaskManager.getInstance().tryFinishTask(player, new Task("Zabij 208 pelnych zycia potworow.", "counter", 208)));
        }

        if (entityType == EntityType.ZOMBIFIED_PIGLIN) {
            if (new Random().nextInt(10) > 0) return;

            event.getDrops().add(new ItemStack(Material.NETHER_WART));

            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> TaskManager.getInstance().tryFinishTask(player, new Task("Dropnij 32 brodawki z piglinow.", "counter", 32)));
            return;
        }

        if (entityType == EntityType.IRON_GOLEM) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> TaskManager.getInstance().tryFinishTask(player, new Task("Zabij 24 zelazne golemy.", "counter", 24)));
            return;
        }

        if (entityType == EntityType.WARDEN) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> TaskManager.getInstance().tryFinishTask(player, new Task("Pokonaj Wardena.", "", 0)));
            return;
        }

        if (entityType == EntityType.ENDER_DRAGON) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> TaskManager.getInstance().tryFinishTask(player, new Task("Pokonaj Smoka.", "", 0)));
            return;
        }

        if (entityType == EntityType.CHICKEN) {
            if (((Chicken) entity).isAware()) return;

            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }
}
