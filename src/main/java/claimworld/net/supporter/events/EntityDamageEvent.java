package claimworld.net.supporter.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class EntityDamageEvent implements Listener {

    private void killPlayer(Player player) {
        player.setHealth(0);
        getLogger().log(Level.WARNING, "Player " + player.getDisplayName() + " has tried to cross the border.");
    }

    @EventHandler
    public void entityDamageEvent(org.bukkit.event.entity.EntityDamageEvent event) {
        if (!(event.getEntity().getType() == EntityType.PLAYER)) return;
        if (event.getEntity().getWorld().getWorldBorder().isInside(event.getEntity().getLocation())) return;
        if (!event.getCause().equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.SUFFOCATION)) return;

        killPlayer((Player) event.getEntity());
    }
}
