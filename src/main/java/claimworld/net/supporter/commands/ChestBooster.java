package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static claimworld.net.supporter.utils.MessageUtils.getBroadcastPrefix;
import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static org.bukkit.Bukkit.broadcastMessage;
import static org.bukkit.Bukkit.dispatchCommand;

public class ChestBooster {

    private final List<String> lockedPlayers = new ArrayList<>();

    public ChestBooster() {
        new CommandBase("chestbooster", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = (Player) sender;
                String playerName = player.getName();
                if (lockedPlayers.contains(playerName)) {
                    player.sendMessage(getUserPrefix() + "Nie mozesz uzyc tej funkcji wiecej, niz raz na restart.");
                    return true;
                }

                if (LocalTime.now().getHour() < 12) {
                    player.sendMessage(getUserPrefix() + "Ta funkcja jest dostepna od 12:00 do 0:00.");
                    return true;
                }

                lockedPlayers.add(playerName);

                int random = new Random().nextInt(15);
                if (random != 0) {
                    player.sendMessage(getUserPrefix() + "Niestety - nie udalo Ci sie wlaczyc dodatkowych skrzynek. Sprobuj ponownie po restarcie serwera.");
                    return true;
                }

                dispatchCommand(Bukkit.getConsoleSender(), "buychests 10");
                broadcastMessage(getBroadcastPrefix() + "Gracz " + playerName + " zarzucil dodatkowymi skrzynkami!");
                return true;
            }

            @Override
            public String getUsage() {
                return "/chestbooster";
            }
        }.setPermission("claimworld.mvp");
    }
}
