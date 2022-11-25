package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.guis.BonusManager;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerDeathEvent implements Listener {

    BonusManager bonusManager = BonusManager.getInstance();

    @EventHandler
    public void playerDeathEvent(org.bukkit.event.entity.PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.getEntity().getWorld().strikeLightningEffect(player.getLocation());

        assert player.getLastDamageCause() != null;
        if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.STARVATION) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> TaskManager.getInstance().tryFinishTask(player, new Task("Umrzyj z glodu.", "", 0)));
            return;
        }

        if (!bonusManager.getBonuses().get("DoubleXP")) return;

        int exp = event.getDroppedExp();

        if (exp <= 0) return;

        event.setDroppedExp(exp * 2);

    }
}
