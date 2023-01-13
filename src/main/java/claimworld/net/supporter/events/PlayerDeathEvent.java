package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.BonusManager;
import claimworld.net.supporter.tasks.TaskManager;
import claimworld.net.supporter.utils.JetpackUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerDeathEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();
    BonusManager bonusManager = BonusManager.getInstance();
    JetpackUtils jetpackUtils = JetpackUtils.getInstance();

    @EventHandler
    public void playerDeathEvent(org.bukkit.event.entity.PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.getEntity().getWorld().strikeLightningEffect(player.getLocation());

        jetpackUtils.disableJetpack(player.getName());

        assert player.getLastDamageCause() != null;
        EntityDamageEvent.DamageCause damageCause = player.getLastDamageCause().getCause();
        if (damageCause == EntityDamageEvent.DamageCause.FIRE_TICK || damageCause == EntityDamageEvent.DamageCause.FIRE) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("dieDueToFire")));
            return;
        }
        if (damageCause == EntityDamageEvent.DamageCause.STARVATION) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("starveToDeath")));
            return;
        }

        if (!bonusManager.getBonuses().get("DoubleXP")) return;

        int exp = event.getDroppedExp();

        if (exp <= 0) return;

        event.setDroppedExp(exp * 2);

    }
}
