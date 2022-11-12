package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;

public class PrivateMessage {
    public PrivateMessage() {
        new CommandBase("privatemessage", 2, 99, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                if (player == null) return false;
                if (arguments[1] == null) return false;

                List<String> fixedMessage = new ArrayList<>(Arrays.asList(arguments));
                fixedMessage.remove(0);

                player.sendMessage(getUserPrefix() + String.join(" ", fixedMessage));

                return true;
            }

            @Override
            public String getUsage() {
                return "/privatemessage <nick> <content>";
            }
        }.setPermission("claimworld.admin");
    }
}
