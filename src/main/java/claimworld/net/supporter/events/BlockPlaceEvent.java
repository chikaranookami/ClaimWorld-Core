package claimworld.net.supporter.events;

import claimworld.net.supporter.utils.PrivateChestsUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockPlaceEvent implements Listener {

    PrivateChestsUtils privateChestsUtils = PrivateChestsUtils.getInstance();

    @EventHandler
    public void blockPlaceEvent(org.bukkit.event.block.BlockPlaceEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        Material material = block.getType();

        if (material != Material.CHEST) return;
        if (!(block.getState() instanceof TileState)) return;

        privateChestsUtils.updateState(event.getPlayer(), (TileState) block.getState(), "create");
    }
}
