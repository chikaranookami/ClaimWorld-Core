package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getScheduler;

public class Dice {
    TaskManager taskManager = TaskManager.getInstance();

    private ArrayList<Player> getNearbyPlayers(Player pl){
        ArrayList<Player> nearby = new ArrayList<>();
        double range = 50;

        for (Entity e : pl.getNearbyEntities(range, range, range)){
            if (e instanceof Player){
                nearby.add((Player) e);
            }
        }

        nearby.add(pl);

        return nearby;
    }

    public Dice() {
        new CommandBase("dice", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = (Player) sender;
                int number = new Random().nextInt(12) + 1;

                for (Player nearbyPlayer : getNearbyPlayers(player)) {
                    nearbyPlayer.sendMessage(colorize("&7[Rzut Kostka]&f Gracz &7" + player.getName() + " &fwlasnie wyrzucil liczbe &7" + number + "&f."));
                }

                if (number == 12) getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("throwNumberUsingDice")));

                return true;
            }

            public String getUsage() {
                return "/dice";
            }
        }.enableDelay(10).setPermission("claimworld.player");
    }
}
