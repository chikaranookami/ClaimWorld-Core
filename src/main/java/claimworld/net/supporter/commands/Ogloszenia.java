package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.announcers.JoinAnnouncer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ogloszenia {
    public Ogloszenia() {
        new CommandBase("ogloszenia", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                new JoinAnnouncer().render((Player) sender);
                return true;
            }

            @Override
            public String getUsage() {
                return "/ogloszenia";
            }
        }.setPermission("claimworld.player");
    }
}
