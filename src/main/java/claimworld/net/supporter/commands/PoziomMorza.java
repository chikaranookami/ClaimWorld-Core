package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class PoziomMorza {
    public PoziomMorza() {
        new CommandBase("poziommorza", 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Bukkit.dispatchCommand(sender, "poziommorza");
                return true;
            }

            @Override
            public String getUsage() {
                return "/poziommorza";
            }
        }.setPermission("claimworld.player");
    }
}
