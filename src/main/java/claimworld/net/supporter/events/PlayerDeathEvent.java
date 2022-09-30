package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathEvent implements Listener {
    @EventHandler
    public void playerDeathEvent(org.bukkit.event.entity.PlayerDeathEvent event) {
        event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());

        for (ItemStack item : event.getDrops()) {
            if (!item.hasItemMeta()) continue;
            if (!item.getItemMeta().hasCustomModelData()) continue;
            if (item.getItemMeta().getCustomModelData() != 1) continue;
            event.getDrops().remove(item);
            break;
        }

        if (!Supporter.doubleXp) return;

        int exp = event.getDroppedExp();

        if (exp <= 0) return;

        event.setDroppedExp(exp * 2);

    }
}
