package claimworld.net.supporter.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ProjectileHitEvent implements Listener {

    @EventHandler
    public void projectileHitEvent(org.bukkit.event.entity.ProjectileHitEvent event) {
        Entity victim = event.getHitEntity();
        if (!(victim instanceof Player)) return;

        Entity attacker = (Entity) event.getEntity().getShooter();
        if (!(attacker instanceof Skeleton)) return;
        if (!attacker.isVisualFire()) return;

        ((Player) victim).damage(12);
    }
}
