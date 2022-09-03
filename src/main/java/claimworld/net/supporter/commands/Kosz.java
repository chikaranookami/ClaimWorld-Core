package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Kosz {
    public Kosz() {
        new CommandBase("kosz", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Bukkit.dispatchCommand(sender, "disposal");
                return true;
            }

            @Override
            public String getUsage() {
                return "/kosz";
            }
        }.setPermission("claimworld.player");
    }
}
