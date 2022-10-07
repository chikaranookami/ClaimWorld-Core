package claimworld.net.supporter.utils.wip;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

import static org.bukkit.Bukkit.*;

public class Goal {

    private void increaseGoal(List<Integer> goals, int currentGoal, FileConfiguration configuration) {
        for (int goal : goals) {
            if (!(currentGoal < goal)) continue;
            configuration.set("goals.active_goal", goal);
            break;
        }
    }

    public Goal() {
        new CommandBase("goal", 2, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                String task = arguments[0];
                String value = arguments[1];
                FileConfiguration config = Supporter.getPlugin().getConfig();

                if (value.isEmpty()) {
                    sender.sendMessage("Value not set. Use: " + getUsage());
                    return true;
                }

                if (task.equals("update")) {
                    int number = Integer.parseInt(value);

                    if (number == 0) {
                        sender.sendMessage("invalid value - converted integer is " + number);
                        return true;
                    }

                    int newValue = config.getInt("goals.total");
                    newValue = newValue + number;

                    config.set("goals.total", newValue);

                    getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                        dispatchCommand(sender, "goal tryBonus 0");
                    }, 30L);

                    Supporter.getPlugin().saveConfig();

                    return true;
                }

                if (task.equals("tryBonus")) {
                    int currentValue = config.getInt("goals.total");
                    int currentGoal = config.getInt("goals.active_goal");
                    List<Integer> goals = config.getIntegerList("goals.list");

                    if (currentGoal == 0) {
                        currentGoal = 250;
                        config.set("goals.active_goal", 250);
                    }

                    if (currentValue >= currentGoal) {
                        increaseGoal(goals, currentGoal, config);

                        switch (currentGoal) {
                            case 250:
                                dispatchCommand(sender, "say osiagnieto cel 250");
                                break;
                            case 500:
                                dispatchCommand(sender, "say osiagnieto cel 500");
                                break;
                            case 750:
                                dispatchCommand(sender, "say osiagnieto cel 750");
                                break;
                            case 1000:
                                dispatchCommand(sender, "say osiagnieto cel 1000");
                        }
                    }

                    Supporter.getPlugin().saveConfig();

                    return true;
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
