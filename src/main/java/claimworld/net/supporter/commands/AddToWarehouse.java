package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.WarehouseUtils;
import claimworld.net.supporter.items.ReadyItems;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddToWarehouse {

    WarehouseUtils warehouseUtils = new WarehouseUtils();

    public AddToWarehouse() {
        new CommandBase("addtowarehouse", 2, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                List<String> fixedMessage = new ArrayList<>(Arrays.asList(arguments));
                fixedMessage.remove(0);

                ItemStack item = ReadyItems.getInstance().get(String.join(" ", fixedMessage)).clone();

                List<ItemStack> itemStackList = new ArrayList<>();
                itemStackList.add(item);

                warehouseUtils.addItemsSingle(arguments[0], itemStackList);

                return true;
            }

            @Override
            public String getUsage() {
                return "/addtowarehouse <nick> <itemName>";
            }
        }.setPermission("claimworld.admin");
    }
}
