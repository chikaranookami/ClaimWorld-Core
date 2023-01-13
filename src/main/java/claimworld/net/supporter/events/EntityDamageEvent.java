package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.tasks.TaskManager;
import claimworld.net.supporter.utils.JetpackUtils;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

import static org.bukkit.Bukkit.*;

public class EntityDamageEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();
    JetpackUtils jetpackUtils = JetpackUtils.getInstance();

    private void handleCrossBorderEvent(Player player, Location location, org.bukkit.event.entity.EntityDamageEvent.DamageCause cause) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            if (player.getWorld().getWorldBorder().isInside(location)) return;

            if (cause.equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.SUFFOCATION)) {
                getScheduler().runTask(Supporter.getPlugin(), () -> player.setHealth(0));

                getLogger().log(Level.WARNING, "Player " + player.getDisplayName() + " has tried to cross the border.");
            }
        });
    }

    @EventHandler
    public void entityDamageEvent(org.bukkit.event.entity.EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;

        Player player = (Player) event.getEntity();
        org.bukkit.event.entity.EntityDamageEvent.DamageCause cause = event.getCause();

        if (cause == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL && jetpackUtils.hasJetpack(player.getName())) {
            event.setCancelled(true);
            return;
        }

        handleCrossBorderEvent(player, player.getLocation(), cause);

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("getHitByAnything")));
    }
}
