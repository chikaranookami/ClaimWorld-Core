package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Punkty {
    public Punkty() {
        new CommandBase("punkty", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Bukkit.dispatchCommand(sender, "vote shop");
                return true;
            }

            @Override
            public String getUsage() {
                return "/punkty";
            }
        }.setPermission("claimworld.player");
    }
}
