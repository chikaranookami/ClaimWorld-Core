package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.battlepass.Skills;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
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
        if (event.getDamager().getType() != EntityType.PLAYER) return;

        Player player = (Player) event.getDamager();
        Skills skills = new Skills();

        if (!skills.canActivateSkill(player, "Mob Killer")) return;
        if (new Random().nextInt(8) != 0) return;

        event.setDamage(event.getDamage() * 2);
        skills.renderSkillEffect(event.getEntity().getLocation());

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            TaskManager.getInstance().tryFinishTask(player, new Task("Aktywuj Mob Killera 32 razy.", "counter", 32));
        });
    }
}
