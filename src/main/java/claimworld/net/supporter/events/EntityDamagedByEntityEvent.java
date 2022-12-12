package claimworld.net.supporter.events;

import claimworld.net.supporter.utils.battlepass.SkillManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

import static org.bukkit.Bukkit.getScheduler;

public class EntityDamagedByEntityEvent implements Listener {

    @EventHandler
    public void entityDamagedByEntityEvent(EntityDamageByEntityEvent event) {
        EntityType entityType = event.getDamager().getType();
        if (entityType == EntityType.SNOWMAN) {
            event.setDamage(event.getDamage() * 6);
            return;
        }

        if (entityType != EntityType.PLAYER) return;

        Player player = (Player) event.getDamager();
        SkillManager skillManager = new SkillManager();

        if (!skillManager.canActivateSkill(player, "Mob Killer")) return;
        if (new Random().nextInt(8) != 0) return;

        event.setDamage(event.getDamage() * 2);
        skillManager.renderSkillEffect(event.getEntity().getLocation());
    }
}
