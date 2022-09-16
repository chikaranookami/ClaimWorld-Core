package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.command.CommandSender;

public class MagicChests2 {
    public MagicChests2() {
        new CommandBase("openmagicchest", 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                int amount = Integer.parseInt(arguments[0]);

                if (amount <= 0) {
                    amount = 1;
                }

                new claimworld.net.supporter.utils.MagicChests2().openChest(amount);

                return true;
            }

            @Override
            public String getUsage() {
                return "/openmagicchest <amount>";
            }
        }.setPermission("claimworld.admin");
    }
}
