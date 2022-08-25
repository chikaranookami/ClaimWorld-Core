package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockBreakEvent implements Listener {

    @EventHandler
    public void blockBreakEvent(org.bukkit.event.block.BlockBreakEvent event) {
        if (!Supporter.DoubleXp) return;
        if (event.getExpToDrop() <= 0) return;

        event.setExpToDrop(event.getExpToDrop() * 2);
    }
}
