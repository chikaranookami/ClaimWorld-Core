package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.announcers.JoinAnnouncer;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;

public class Ogloszenia {
    public Ogloszenia() {
        new CommandBase("ogloszenia", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                new JoinAnnouncer((Player) sender);
                return true;
            }

            @Override
            public String getUsage() {
                return "/ogloszenia";
            }
        }.setPermission("claimworld.player");
    }
}
