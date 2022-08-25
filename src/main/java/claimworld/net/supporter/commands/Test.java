package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.command.CommandSender;

public class Test {
    public Test() {
        new CommandBase("test", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                return true;
            }

            @Override
            public String getUsage() {
                return "/test";
            }
        }.setPermission("claimworld.mod");
    }
}
