package claimworld.net.supporter.utils.guis;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class GuiManager {
    public GuiManager(Player player, Gui gui) {
        Inventory inventory = gui.getInventory();

        player.openInventory(gui.getInventory());

        for (Map.Entry<Integer, ItemStack> entry : new ItemSets().initializeInventoryContent(player, gui.getName()).entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
    }
}
