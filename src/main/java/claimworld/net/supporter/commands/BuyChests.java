package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.WarehouseUtils;
import claimworld.net.supporter.items.ReadyItems;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BuyChests {
    public BuyChests() {
        new CommandBase("buychests", 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                int amount = Integer.parseInt(arguments[0]);
                if (amount == 0) amount = 1;

                List<ItemStack> itemStackList = new ArrayList<>();
                for (int i = 0; i < amount; i++) {
                    itemStackList.add(ReadyItems.getInstance().get("Skrzynia_smoka"));
                }

                new WarehouseUtils().addItemsGlobal(itemStackList);
                return true;
            }

            @Override
            public String getUsage() {
                return "/buychests <amount>";
            }
        }.setPermission("claimworld.admin");
    }
}
