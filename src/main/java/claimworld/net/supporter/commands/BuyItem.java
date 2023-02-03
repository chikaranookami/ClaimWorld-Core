package claimworld.net.supporter.commands;

import claimworld.net.supporter.items.ReadyItems;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.WarehouseUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.logging.Level;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static org.bukkit.Bukkit.getLogger;

public class BuyItem {

    ReadyItems readyItems = ReadyItems.getInstance();

    public BuyItem() {
        new CommandBase("buyitem", 2, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                if (player == null) {
                    getLogger().log(Level.INFO, "Player is null");
                    return true;
                }

                String itemName = arguments[1];
                if (readyItems.get(itemName) == null) {
                    getLogger().log(Level.INFO, "Invalid item name.");
                    return true;
                }

                Inventory inventory = player.getInventory();
                ItemStack priceItem = readyItems.get("$1");
                int priceAmount = 128;

                player.closeInventory();

                if (!inventory.containsAtLeast(priceItem, priceAmount)) {
                    player.sendMessage(getUserPrefix() + "Brak wymaganych przedmiotow w ekwipunku.");
                } else {
                    inventory.removeItem(readyItems.get("$1", priceAmount));

                    new WarehouseUtils().addItemsSingle(player.getName(), Collections.singletonList(readyItems.get(itemName)));
                }

                return true;
            }

            @Override
            public String getUsage() {
                return "/buyitem <player> <item>";
            }
        }.setPermission("claimworld.admin");
    }
}
