package claimworld.net.supporter.events;

import claimworld.net.supporter.utils.MagicChests;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractEvent implements Listener {

    private final MagicChests magicChests = new MagicChests();

    @EventHandler
    public void playerInteractEvent(org.bukkit.event.player.PlayerInteractEvent event) {
        assert event.getHand() != null;
        assert event.getClickedBlock() != null;

        if (!event.getHand().equals(EquipmentSlot.HAND)) return;
        if (!(event.getClickedBlock().getLocation() == magicChests.getLocation())) return;

        Player player = event.getPlayer();
        ItemStack mainItem = player.getInventory().getItemInMainHand();

        assert  mainItem.getItemMeta() != null;

        if (!(mainItem.getType() == Material.ENDER_CHEST)) return;
        if (!mainItem.getItemMeta().getDisplayName().equals(magicChests.getChestName())) return;

        mainItem.setAmount(1);

        player.getInventory().remove(mainItem);
    }
}
