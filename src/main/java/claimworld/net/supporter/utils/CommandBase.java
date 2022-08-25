package claimworld.net.supporter.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static claimworld.net.supporter.Supporter.getInstance;

public abstract class CommandBase extends BukkitCommand implements CommandExecutor {

    private List<String> delayedPlayers = null;
    private int delay = 2;
    private final int minArguments;
    private final int maxArguments;
    private final boolean playerOnly;

    public CommandBase(String command) {
        this(command, 0);
    }

    public CommandBase(String command, boolean playerOnly) {
        this(command, 0, playerOnly);
    }

    public CommandBase(String command, int requiredArguments) {
        this(command, requiredArguments, requiredArguments);
    }

    public CommandBase(String command, int minArguments, int maxArguments) {
        this(command, minArguments, maxArguments, false);
    }

    public CommandBase(String command, int requiredArguments, boolean playerOnly) {
        this(command, requiredArguments, requiredArguments, playerOnly);
    }

    public CommandBase(String command, int minArguments, int maxArguments, boolean playerOnly) {
        super(command);

        this.minArguments = minArguments;
        this.maxArguments = maxArguments;
        this.playerOnly = playerOnly;

        CommandMap commandMap = getCommandMap();
        if (commandMap != null) {
            commandMap.register(command, this);
        }
    }

    public CommandMap getCommandMap() {
        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);

                return (CommandMap) field.get(Bukkit.getPluginManager());
            }
        } catch(NoSuchFieldException | IllegalAccessException error) {
            error.printStackTrace();
        }

        return null;
    }

    public CommandBase enableDelay(int delay) {
        this.delay = delay;
        this.delayedPlayers = new ArrayList<>();
        return this;
    }

    public void removePlayer(Player player) {
        this.delayedPlayers.remove(player.getName());
    }

    public void sendUsage(CommandSender sender) {
        sender.sendMessage(getUsage());
    }

    public boolean execute(CommandSender sender, String alias, String[] arguments) {
        if (arguments.length < minArguments || (arguments.length > maxArguments && maxArguments != -1)) {
            sendUsage(sender);
            return true;
        }

        if (playerOnly && !(sender instanceof Player)) {
            sender.sendMessage("Only players can use that command.");
            return true;
        }

        String permission = getPermission();
        if (permission != null && !(sender.hasPermission(permission))) {
            sender.sendMessage("Brak uprawnien.");
            return true;
        }

        if (delayedPlayers != null && sender instanceof Player) {
            Player player = (Player) sender;
            if (delayedPlayers.contains(player.getName()) && !player.hasPermission("claimworld.mod")) {
                player.sendMessage("Musisz odczekac chwile, zanim bedziesz mogl uzyc tej komendy ponownie.");
                return true;
            }

            delayedPlayers.add(player.getName());
            Bukkit.getScheduler().runTaskLaterAsynchronously(getInstance(), () -> {
                delayedPlayers.remove(player.getName());
            }, 20L * delay);
        }

        if (!onCommand(sender, arguments)) {
            sendUsage(sender);
        }
        return true;
    }

    public boolean onCommand(CommandSender sender, Command command, String alias, String[] arguments) {
        this.onCommand(sender, arguments);
        return true;
    }

    public abstract boolean onCommand(CommandSender sender, String[] arguments);

    public abstract String getUsage();

}
