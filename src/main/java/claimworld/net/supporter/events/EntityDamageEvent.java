package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.tasks.TaskManager;
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
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("getHitByAnything")));

        if (player.getWorld().getWorldBorder().isInside(event.getEntity().getLocation())) return;

        if (event.getCause().equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.SUFFOCATION)) {
            killPlayer(player);
        }
    }
}
