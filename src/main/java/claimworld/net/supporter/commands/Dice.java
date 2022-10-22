package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class Dice {
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

                return true;
            }

            public String getUsage() {
                return "/dice";
            }
        }.enableDelay(15).setPermission("claimworld.player");
    }
}
