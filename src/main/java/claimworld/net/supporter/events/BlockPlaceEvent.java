package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.tasks.TaskManager;
import claimworld.net.supporter.utils.PrivateChestsUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getScheduler;

public class BlockPlaceEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();
    PrivateChestsUtils privateChestsUtils = PrivateChestsUtils.getInstance();

    @EventHandler
    public void blockPlaceEvent(org.bukkit.event.block.BlockPlaceEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        Player player = event.getPlayer();

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("placeAnything")));

        if (material == Material.REINFORCED_DEEPSLATE) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("placeReinforcedDeepslate")));
            return;
        }

        if (material != Material.CHEST) return;
        if (!(block.getState() instanceof TileState)) return;

        privateChestsUtils.updateState(player, (TileState) block.getState(), "create");
    }
}
