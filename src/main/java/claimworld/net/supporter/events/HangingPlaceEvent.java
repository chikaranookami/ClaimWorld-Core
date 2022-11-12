package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.items.ReadyItems;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class HangingPlaceEvent implements Listener {

    @EventHandler
    public void hangingPlaceEvent(org.bukkit.event.hanging.HangingPlaceEvent event) {
        if (event.getItemStack() == null) return;
        if (event.getItemStack().getType() != Material.ITEM_FRAME) return;
        if (event.getItemStack().getItemMeta() == null) return;
        if (event.getItemStack().getItemMeta().getLore() == null) return;
        if (!event.getItemStack().getItemMeta().getLore().equals(new ReadyItems().get("Niewidzialna_ramka").getItemMeta().getLore())) return;

        ((ItemFrame) event.getEntity()).setVisible(false);

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            Player player = event.getPlayer();
            if (player == null) return;

            TaskManager.getInstance().tryFinishTask(player, new Task("Powies niewidzialna ramke.", "", 0));
        });
    }
}
