package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

import static org.bukkit.Bukkit.*;

public class EntityDamageEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();

    private void killPlayer(Player player) {
        player.setHealth(0);
        getLogger().log(Level.WARNING, "Player " + player.getDisplayName() + " has tried to cross the border.");
    }

    @EventHandler
    public void entityDamageEvent(org.bukkit.event.entity.EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;

        Player player = (Player) event.getEntity();
        org.bukkit.event.entity.EntityDamageEvent.DamageCause damageCause = event.getCause();

        if (damageCause.equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("getHitByDragon")));
            return;
        }

        if (event.getEntity().getWorld().getWorldBorder().isInside(event.getEntity().getLocation())) return;

        if (damageCause.equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.SUFFOCATION)) {
            killPlayer(player);
        }
    }
}
