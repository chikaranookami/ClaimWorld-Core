package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class Teleporter {

    private Location location;

    public Teleporter() {
        new CommandBase("teleporter", 0, 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                if (arguments.length < 1) {
                    if (!(sender instanceof Player)) return false;

                    location = ((Player) sender).getLocation();

                    sender.sendMessage("nowy teleporter: " + location);

                    Bukkit.getConsoleSender().sendMessage(colorize(Messages.getBroadcastPrefix() + "&eTeleporter&f otrzymal nowe, aktywne koordynaty. Przejdz na &e/spawn&f, by go uzyc."));
                    return true;
                }

                Player player = Bukkit.getPlayer(arguments[0]);

                if (player == null) {
                    sender.sendMessage("nie ma takiego gracza?");
                    return true;
                }

                if (location == null) {
                    sender.sendMessage(player.getDisplayName() + " tried to use teleporter.");
                    player.sendMessage(Messages.getUserPrefix() + "Magiczny Portal jest obecnie wylaczony.");
                    return true;
                }

                player.teleport(location);
                player.sendMessage(Messages.getUserPrefix() + "Przeszedles przez Magiczny Portal.");
                sender.sendMessage("teleporter uzyty na " + player);

                return true;
            }

            @Override
            public String getUsage() {
                return "/teleporter <nick>";
            }
        }.setPermission("claimworld.mod");
    }
}
