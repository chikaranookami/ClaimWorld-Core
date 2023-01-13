package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.GoalUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.logging.Level;

import static claimworld.net.supporter.utils.MessageUtils.getBroadcastPrefix;
import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;
import static org.bukkit.Bukkit.getConsoleSender;

public class Goal {

    GoalUtils goalUtils = GoalUtils.getInstance();

    private void increaseGoal(List<Integer> goals, int currentGoal, FileConfiguration configuration) {
        for (int goal : goals) {
            if (!(currentGoal < goal)) continue;
            configuration.set("goals.active_goal", goal);
            Supporter.getPlugin().saveConfig();
            break;
        }
    }

    private void renderRewardByGoal(int currentGoal) {
        switch (currentGoal) {
            case 250:
                dispatchCommand(getConsoleSender(), "gamerule playersSleepingPercentage 50");
                broadcastMessage(colorize(getBroadcastPrefix() + "Osiagnieto kolejny cel w sklepie. W nagrode &eprocent osob, potrzebnych do przespania nocy zostal zmniejszony&f."));
                break;
            case 500:
                dispatchCommand(getConsoleSender(), "worldborder set 8000");
                broadcastMessage(colorize(getBroadcastPrefix() + "Osiagnieto kolejny cel w sklepie. W nagrode &epowiekszona zostala granica swiata&f."));
                break;
            case 750:
                dispatchCommand(getConsoleSender(), "gamerule randomTickSpeed 5");
                broadcastMessage(colorize(getBroadcastPrefix() + "Osiagnieto kolejny cel w sklepie. W nagrode &ezwiekszony zostal randomTickSpeed&f, dzieki czemu na przyklad uprawy beda rosly szybciej."));
                break;
            case 1000:
                dispatchCommand(getConsoleSender(), "worldborder set 10000");
                broadcastMessage(colorize(getBroadcastPrefix() + "Osiagnieto kolejny cel w sklepie. W nagrode &epowiekszona zostala granica swiata&f."));
                break;
            case 1250:
                broadcastMessage(colorize(getBroadcastPrefix() + "Osiagnieto kolejny cel w sklepie. W nagrode &ezwiekszono szybkosc lowienia&f."));
                break;
            case 1750:
                broadcastMessage(colorize(getBroadcastPrefix() + "Osiagnieto kolejny cel w sklepie. W nagrode &eniedlugo pojawia sie efekty krwawienia.&f."));
                break;
            case 2150:
                broadcastMessage(colorize(getBroadcastPrefix() + "Osiagnieto kolejny cel w sklepie. W nagrode &eniedlugo pojawi sie kalendarz z bonusami za logowanie.&f."));
                break;
            case 2500:
                broadcastMessage(colorize(getBroadcastPrefix() + "Osiagnieto kolejny cel w sklepie. W nagrode &eniedlugo dodany zostanie Zwoj Ognia.&f."));
                break;
            case 3100:
                broadcastMessage(colorize(getBroadcastPrefix() + "Osiagnieto kolejny cel w sklepie. W nagrode &eniedlugo kupa bedzie troche bardziej smierdziec...&f."));
                break;
            case 4000:
                broadcastMessage(colorize(getBroadcastPrefix() + "Osiagnieto kolejny cel w sklepie. W nagrode &eniedlugo dodany zostanie plecak&f."));
                break;
            case 9999:
                getLogger().log(Level.WARNING, "wplacone zostalo wiecej kasy, niz ustawionych celow");
        }
    }

    public Goal() {
        new CommandBase("goal", 2, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                String task = arguments[0];
                String value = arguments[1];
                FileConfiguration config = Supporter.getPlugin().getConfig();

                if (value.isEmpty()) return false;

                if (task.equals("update")) {
                    int number = Integer.parseInt(value);

                    if (number == 0) return false;

                    int newValue = config.getInt("goals.total");
                    newValue = newValue + number;

                    config.set("goals.total", newValue);
                    Supporter.getPlugin().saveConfig();

                    getScheduler().runTaskLater(Supporter.getPlugin(), () -> dispatchCommand(sender, "goal tryBonus 0"), 30L);

                    broadcastMessage(colorize(getBroadcastPrefix() + "Postep obecnego celu w sklepie wynosi teraz &e" + goalUtils.getPercentAmount() + "%&f. Brakuje jeszcze &e " + goalUtils.getMissingAmount() + "zl&f."));
                    return true;
                }

                if (task.equals("tryBonus")) {
                    int currentValue = config.getInt("goals.total");
                    int currentGoal = config.getInt("goals.active_goal");
                    List<Integer> goals = config.getIntegerList("goals.list");

                    if (currentGoal == 0) {
                        currentGoal = 250;
                        config.set("goals.active_goal", 250);
                        Supporter.getPlugin().saveConfig();
                    }

                    if (currentValue >= currentGoal) {
                        increaseGoal(goals, currentGoal, config);
                        renderRewardByGoal(currentGoal);
                        return true;
                    }
                }

                return false;
            }

            @Override
            public String getUsage() {
                return "/goal <task> <value>";
            }
        }.setPermission("claimworld.admin");
    }
}
