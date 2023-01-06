package claimworld.net.supporter.utils.wip;

import claimworld.net.supporter.utils.PrivateChestsUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;

public class BlockPlaceEvent implements Listener {

    @EventHandler
    public void blockPlaceEvent(org.bukkit.event.block.BlockPlaceEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        if (material != Material.CHEST && material != Material.SHULKER_BOX) return;
        if (!(block.getState() instanceof TileState)) return;

        TileState state = (TileState) block.getState();
        NamespacedKey key = new PrivateChestsUtils().getKey();

        Player player = event.getPlayer();
        PersistentDataContainer container = state.getPersistentDataContainer();
        container.set(key, PersistentDataType.STRING, "|" + player.getUniqueId());

        state.update();

        player.sendMessage(getUserPrefix() + "Utworzono zabezpieczona skrzynie.");
    }
}
