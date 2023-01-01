package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.MessageUtils;
import claimworld.net.supporter.tasks.TaskManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getScheduler;

public class Teleporter {

    TaskManager taskManager = TaskManager.getInstance();

    private Location location;

    public Teleporter() {
        new CommandBase("teleporter", 0, 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                if (arguments.length < 1) {
                    if (!(sender instanceof Player)) return false;

                    location = ((Player) sender).getLocation();

                    Bukkit.broadcastMessage(colorize(MessageUtils.getBroadcastPrefix() + "&eTeleporter&f otrzymal nowe, aktywne koordynaty. Przejdz na &e/spawn&f, by go uzyc."));
                    return true;
                }

                Player player = Bukkit.getPlayer(arguments[0]);

                if (player == null) {
                    sender.sendMessage("nie ma takiego gracza?");
                    return true;
                }

                if (location == null) {
                    sender.sendMessage(player.getDisplayName() + " tried to use teleporter.");
                    player.sendMessage(MessageUtils.getUserPrefix() + "Magiczny Portal jest obecnie wylaczony.");
                    return true;
                }

                player.teleport(location);

                player.sendMessage(MessageUtils.getUserPrefix() + "Przeszedles przez Magiczny Portal.");
                sender.sendMessage("teleporter uzyty na " + player);

                getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("useActiveTeleporter")));
                return true;
            }

            @Override
            public String getUsage() {
                return "/teleporter <nick>";
            }
        }.setPermission("claimworld.mod");
    }
}
