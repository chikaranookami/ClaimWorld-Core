package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CwAdmin {
    public CwAdmin() {
        new CommandBase("cwadmin", 2, true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                String action = arguments[1];

                if (player == null) {
                    sender.sendMessage("Player is null");
                    return true;
                }

                if (action.equals("updateRank")) {
                    sender.sendMessage("Trying to update rank of " + player.getDisplayName());
                    new Ranks().updateRank(player);
                    return true;
                }

                return false;
            }

            @Override
            public String getUsage() {
                return "/cwadmin <nick> <action>";
            }
        }.setPermission("claimworld.mod");
    }
}
