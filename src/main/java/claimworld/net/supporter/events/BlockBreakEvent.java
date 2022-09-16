package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.MagicChests;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockBreakEvent implements Listener {

    @EventHandler
    public void blockBreakEvent(org.bukkit.event.block.BlockBreakEvent event) {
        if (event.getBlock().getLocation() == new MagicChests().getLocation()) return;

        if (!Supporter.doubleXp) return;
        if (event.getExpToDrop() <= 0 )return;

        event.setExpToDrop(event.getExpToDrop() * 2);
    }
}
