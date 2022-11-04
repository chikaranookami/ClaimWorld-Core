package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.wip.ErrorMessages;
import claimworld.net.supporter.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class DajPunkty {

    public DajPunkty() {
        new CommandBase("dajpunkty", 2, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                ErrorMessages errorMessages = new ErrorMessages();
                Player player = Bukkit.getPlayer(arguments[0]);

                if (player == null) {
                    sender.sendMessage(errorMessages.getByCode(1));
                    return true;
                }

                int points = Integer.parseInt(arguments[1]);

                if (!(points > 0 && points < 10000)) {
                    sender.sendMessage(errorMessages.getByCode(2));
                    return true;
                }

                int total = 0;
                double multipler = 0;
                int random = new Random().nextInt(3);

                if (points < 10) {
                    if (player.hasPermission("claimworld.vip")) {
                        /* 33% szans */
                        if (random == 0) multipler = 1;
                    }

                    if (player.hasPermission("claimworld.vip+")) {
                        /* 66% szans */
                        if (random > 0) multipler = 1;
                    }

                    if (player.hasPermission("claimworld.mvp")) {
                        multipler = 1;
                    }

                    total = (int) (points + multipler);
                }

                if (points >= 10) {
                    if (player.hasPermission("claimworld.vip")) {
                        multipler = 1.2;
                    }

                    if (player.hasPermission("claimworld.vip+")) {
                        multipler = 1.5;
                    }

                    if (player.hasPermission("claimworld.mvp")) {
                        multipler = 2;
                    }

                    total = (int) (points * multipler);
                }

                Bukkit.dispatchCommand(sender, "adminvote User " + player.getName() + " AddPoints " + total);

                sender.sendMessage(ChatColor.GREEN + "Pomyslnie dodano graczowi " + player.getDisplayName() + ChatColor.GREEN + " " + points + ChatColor.GREEN + " punktow (multipler " + multipler + ChatColor.GREEN + ")");
                player.sendMessage(MessageUtils.getUserPrefix() + "Otrzymano " + total + " punktow.");

                return true;
            }

            @Override
            public String getUsage() {
                return "/dajpunkty <nick> <ilosc>";
            }
        }.setPermission("claimworld.admin");
    }
}
