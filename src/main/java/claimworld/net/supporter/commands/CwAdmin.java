package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.items.CustomItem;
import claimworld.net.supporter.items.Locker;
import claimworld.net.supporter.items.ReadyItems;
import claimworld.net.supporter.tasks.TaskManager;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.logging.Level;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class CwAdmin {

    ReadyItems readyItems = ReadyItems.getInstance();

    private int snowAmount = 0;
    private final List<String> blockedPlayers = new ArrayList<>();

    private void renderGhastScream() {
        for (Player onlinePlayer : getOnlinePlayers()) {
            onlinePlayer.playSound(onlinePlayer, Sound.ENTITY_GHAST_SCREAM, 1.2f, 1.2f);
            onlinePlayer.playSound(onlinePlayer, Sound.ENTITY_GHAST_HURT, 1.2f, 1.2f);
            onlinePlayer.playSound(onlinePlayer, Sound.ENTITY_GHAST_SCREAM, 1.2f, 1.2f);
            onlinePlayer.playSound(onlinePlayer, Sound.ENTITY_GHAST_HURT, 1.2f, 1.2f);
        }
    }

    public CwAdmin() {
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

        new CommandBase("cwadmin", 2, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                String action = arguments[1];

                if (player == null) {
                    sender.sendMessage("Player is null");
                    return true;
                }

                if (action.equals("renderNewTasks")) {
                    boolean value = TaskManager.getInstance().renderNewTasks();
                    sender.sendMessage("rendering status: " + value);
                    return true;
                }

                if (action.equals("forceNewTasks")) {
                    boolean value = TaskManager.getInstance().renderNewTasks(true);
                    sender.sendMessage("force rendering status: " + value);
                    return true;
                }

                /*
                if (action.equals("playGhastScream")) {
                    getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                        renderGhastScream();
                    }, 10L);
                    sender.sendMessage("ghast scream has been played");
                    return true;
                }
                 */

                if (action.equals("giveCustomItems")) {
                    Location location = player.getLocation();

                    for (Map.Entry<String, CustomItem> entry : readyItems.getItemMap().entrySet()) {
                        ItemStack itemStack = entry.getValue().getItem();
                        Item item = location.getWorld().dropItem(location, itemStack);
                        item.setOwner(player.getUniqueId());
                    }

                    return true;
                }

                if (action.equals("giveKosa")) {
                    Location location = player.getLocation();
                    Item item = location.getWorld().dropItem(location, readyItems.getKosaNaMoby());
                    item.setOwner(player.getUniqueId());
                    return true;
                }

                Locker locker = Locker.getInstance();
                HashMap<String, List<ItemStack>> items = locker.getLockerMap();

                if (action.equals("checkStored")) {
                    for (Map.Entry<String, List<ItemStack>> entry : items.entrySet()) {
                        boolean isNull = false;
                        if (entry.getKey() == null) isNull = true;
                        sender.sendMessage(colorize("&7&oSI " + entry.getKey() + " (isEmpty: " + entry.getKey().isEmpty() +  ", isNull: " + isNull + ", length: " + entry.getKey().length() + ": " + entry.getValue()));
                    }

                    return true;
                }

                return false;
            }

            @Override
            public String getUsage() {
                return "/cwadmin <nick> <action>";
            }
        }.setPermission("claimworld.admin");
    }
}
