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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static org.bukkit.Bukkit.getScheduler;

public class HangingPlaceEvent implements Listener {

    @EventHandler
    public void hangingPlaceEvent(org.bukkit.event.hanging.HangingPlaceEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack == null) return;
        if (itemStack.getType() != Material.ITEM_FRAME) return;

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return;

        List<String> lore = itemMeta.getLore();
        if (lore == null) return;
        if (!lore.equals(ReadyItems.getInstance().get("Niewidzialna_ramka").getItemMeta().getLore())) return;

        ((ItemFrame) event.getEntity()).setVisible(false);

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            Player player = event.getPlayer();
            if (player == null) return;

            TaskManager.getInstance().tryFinishTask(player, new Task("Powies niewidzialna ramke.", "", 0));
        });
    }
}
