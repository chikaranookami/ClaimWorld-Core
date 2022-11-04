package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.guis.BonusManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static claimworld.net.supporter.utils.MessageUtils.getBroadcastPrefix;
import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class Cws {
    BonusManager bonusManager = BonusManager.getInstance();

    private void boosterAlreadyActive(String variableName, Player player) {
        Bukkit.dispatchCommand(getConsoleSender(), "adminvote User " + player.getName() + " AddPoints " + bonusManager.getPointPrices().get(variableName));
        getConsoleSender().sendMessage(variableName + " is already true");
        player.sendMessage(getUserPrefix() + "Ktos juz aktywowal ten bonus. Punkty zostaly zwrocone.");
    }

    public Cws() {
        new CommandBase("cws", 1, 3, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[1]);

                if (player == null) return false;

                String value = arguments[0];
                HashMap<String, Boolean> bonuses = bonusManager.getBonuses();

                if (!bonuses.containsKey(value)) return false;

                if (bonuses.get(value)) {
                    boosterAlreadyActive(value, player);
                    return true;
                }

                bonuses.put(value, true);

                HashMap<String, String> bonusCommands = bonusManager.getBonusCommands();
                if (bonusCommands.containsKey(value)) dispatchCommand(getConsoleSender(), bonusCommands.get(value));

                broadcastMessage(colorize(getBroadcastPrefix() + player.getDisplayName() + " &f" + bonusManager.getBonusMessages().get(value) + "!"));

                return true;
            }

            @Override
            public String getUsage() {
                return "/cws <nazwa>";
            }
            }.setPermission("claimworld.admin");
    }
}
