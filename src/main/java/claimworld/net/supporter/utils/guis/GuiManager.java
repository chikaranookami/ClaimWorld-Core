package claimworld.net.supporter.utils.guis;

import claimworld.net.supporter.Supporter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

import static org.bukkit.Bukkit.getScheduler;

public class GuiManager {
    public GuiManager(Player player, Gui gui) {
        Inventory inventory = gui.getInventory();

        player.openInventory(gui.getInventory());

        long delay = 0;

        for (Map.Entry<Integer, ItemStack> entry : new ItemSets().initializeInventoryContent(player, gui.getName()).entrySet()) {
            getScheduler().runTaskLater(Supporter.getPlugin(), () -> {inventory.setItem(entry.getKey(), entry.getValue());}, delay++);
        }
    }
}
