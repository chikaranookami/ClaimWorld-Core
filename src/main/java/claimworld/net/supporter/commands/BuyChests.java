package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.WarehouseUtils;
import claimworld.net.supporter.utils.items.ReadyItems;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BuyChests {
    public BuyChests() {
        new CommandBase("buychests", false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                List<ItemStack> itemStackList = new ArrayList<>();
                itemStackList.add(ReadyItems.getInstance().get("Skrzynia_smoka"));

                new WarehouseUtils().addItemsGlobal(itemStackList);
                return true;
            }

            @Override
            public String getUsage() {
                return "/buychests";
            }
        }.setPermission("claimworld.admin");
    }
}
