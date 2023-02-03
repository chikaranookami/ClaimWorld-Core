package claimworld.net.supporter.commands;

import claimworld.net.supporter.items.ReadyItems;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.WarehouseUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static org.bukkit.Bukkit.*;

public class Bankier {

    ReadyItems readyItems = ReadyItems.getInstance();

    private final List<String> lockedPlayers = new ArrayList<>();

    public Bankier() {
        new CommandBase("bankier", 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                if (player == null) {
                    getLogger().log(Level.INFO, "Player is null");
                    return true;
                }

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

                new WarehouseUtils().addItemsSingle(playerName, Collections.singletonList(readyItems.get("$1")));

                return true;
            }

            @Override
            public String getUsage() {
                return "/bankier";
            }
        }.setPermission("claimworld.admin");
    }
}
