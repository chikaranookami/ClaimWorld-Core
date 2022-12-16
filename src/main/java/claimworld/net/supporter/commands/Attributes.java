package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.AttributesManager;
import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.logging.Level;

import static claimworld.net.supporter.utils.AttributesManager.*;
import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static org.bukkit.Bukkit.*;

public class Attributes {

    AttributesManager attributesManager = new AttributesManager();

    public Attributes() {
        new CommandBase("showattributebook", 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                if (player == null) {
                    sender.sendMessage("Player is null - aborting...");
                    return true;
                }

                if (!attributesManager.tryUpdateStats(player)) player.sendMessage(getUserPrefix() + "Nie masz wolnych punktow do rozdania.");

                return true;
            }

            @Override
            public String getUsage() {
                return "/showattributebook <player>";
            }
        }.setPermission("claimworld.player");

        new CommandBase("updateattribute", 1, true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                String value = arguments[0];
                if (!value.equals(hpAttributeObjectiveName) && !value.equals(damageAttributeObjectiveName)) return false;

                Player player = (Player) sender;
                String playerName = player.getName();
                Scoreboard scoreboard = player.getScoreboard();
                Score singleScore = scoreboard.getObjective(value).getScore(playerName);

                int updatedAmount = singleScore.getScore() + 1;
                singleScore.setScore(updatedAmount);

                Score scoreCounter = scoreboard.getObjective(attributesObjectiveName).getScore(playerName);
                int leftAmount = scoreCounter.getScore();
                if (leftAmount > 0) leftAmount--;
                scoreCounter.setScore(leftAmount);

                if (value.equals(hpAttributeObjectiveName)) attributesManager.updatePlayerHealth(player);

                sender.sendMessage(getUserPrefix() + "Zaktualizowano punkty atrybutow.");

                getLogger().log(Level.INFO, "Updated " + value + " for " + playerName + " to " + updatedAmount + ". (current scoreCounter is " + leftAmount + ")");

                return true;
            }

            @Override
            public String getUsage() {
                return "/updateattribute " + hpAttributeObjectiveName + "/" + damageAttributeObjectiveName;
            }
        }.setPermission("claimworld.player");

        new CommandBase("addattribute", 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = getPlayer(arguments[0]);
                if (player == null) return false;

                dispatchCommand(getConsoleSender(), "scoreboard players add " + player.getName() + " " + attributesObjectiveName + " 1");
                dispatchCommand(getConsoleSender(), "privateMessage " + player.getName() + " Otrzymales Atrybut. Za jego pomoca juz niebawem bedziesz w stanie rozwinac swoja postac.");
                return true;
            }

            @Override
            public String getUsage() {
                return "/addattribute <nick>";
            }
        }.setPermission("claimworld.admin");
    }
}
