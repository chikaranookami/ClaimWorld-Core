package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.WarehouseUtils;
import claimworld.net.supporter.utils.items.ReadyItems;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddToWarehouse {
    public AddToWarehouse() {
        new CommandBase("addtowarehouse", 2, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                if (arguments[1] == null) return false;

                Player player = Bukkit.getPlayer(arguments[0]);
                if (player == null) return false;

                List<String> fixedMessage = new ArrayList<>(Arrays.asList(arguments));
                fixedMessage.remove(0);

                ItemStack item = ReadyItems.getInstance().get(String.join(" ", fixedMessage)).clone();

                List<ItemStack> itemStackList = new ArrayList<>();
                itemStackList.add(item);

                new WarehouseUtils().addItemsSingle(player, itemStackList);

                return true;
            }

            @Override
            public String getUsage() {
                return "/addtowarehouse <nick> <itemName>";
            }
        }.setPermission("claimworld.admin");
    }
}
