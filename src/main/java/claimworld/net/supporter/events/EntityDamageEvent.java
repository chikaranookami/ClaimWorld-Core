package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getScheduler;

public class EntityDamageEvent implements Listener {

    private void killPlayer(Player player) {
        player.setHealth(0);
        getLogger().log(Level.WARNING, "Player " + player.getDisplayName() + " has tried to cross the border.");
    }

    @EventHandler
    public void entityDamageEvent(org.bukkit.event.entity.EntityDamageEvent event) {
        if (!(event.getEntity().getType() == EntityType.PLAYER)) return;
        if (event.getEntity().getWorld().getWorldBorder().isInside(event.getEntity().getLocation())) return;

        Player player = (Player) event.getEntity();
        org.bukkit.event.entity.EntityDamageEvent.DamageCause damageCause = event.getCause();

        if (damageCause.equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.STARVATION)) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> TaskManager.getInstance().tryFinishTask(player, new Task("Umrzyj z glodu.", "", 0)));
            return;
        }

        if (damageCause.equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.DRAGON_BREATH)) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> TaskManager.getInstance().tryFinishTask(player, new Task("Pooddychaj chwile smokiem.", "counter", 8)));
            return;
        }

        if (damageCause.equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.SUFFOCATION)) {
            killPlayer(player);
        }
    }
}
