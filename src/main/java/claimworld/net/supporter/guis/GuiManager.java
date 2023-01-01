package claimworld.net.supporter.guis;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GuiManager {
    public GuiManager(Player player, Gui gui) {
        Inventory inventory = gui.getInventory();

        player.openInventory(gui.getInventory());

        HashMap<Integer, ItemStack> items = new ItemSets().initializeInventoryContent(player, gui.getName());

        if (items == null) return;

        for (Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
            if (entry.getKey() > 53) break;
            inventory.setItem(entry.getKey(), entry.getValue());
        }
    }
}
