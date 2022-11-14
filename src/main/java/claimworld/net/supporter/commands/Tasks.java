package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.command.CommandSender;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class Tasks {
    public Tasks() {
        new CommandBase("rendernewtasks", false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                TaskManager.getInstance().renderNewTasks();
                getLogger().log(Level.INFO, "trying to render a new set of tasks");
                return true;
            }

            @Override
            public String getUsage() {
                return "/rendernewtasks";
            }
        }.setPermission("claimworld.admin");
    }
}
