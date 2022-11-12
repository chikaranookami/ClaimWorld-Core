package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.MessageUtils;
import claimworld.net.supporter.utils.items.Locker;
import claimworld.net.supporter.utils.items.ReadyItems;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class CwAdmin {

    TaskManager taskManager = TaskManager.getInstance();

    private void renderGhastScream() {
        for (Player onlinePlayer : getOnlinePlayers()) {
            onlinePlayer.playSound(onlinePlayer, Sound.ENTITY_GHAST_SCREAM, 1.2f, 1.2f);
            onlinePlayer.playSound(onlinePlayer, Sound.ENTITY_GHAST_HURT, 1.2f, 1.2f);
            onlinePlayer.playSound(onlinePlayer, Sound.ENTITY_GHAST_SCREAM, 1.2f, 1.2f);
            onlinePlayer.playSound(onlinePlayer, Sound.ENTITY_GHAST_HURT, 1.2f, 1.2f);
        }
    }

    public CwAdmin() {
        new CommandBase("cwadmin", 2, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                String action = arguments[1];

                if (player == null) {
                    sender.sendMessage("Player is null");
                    return true;
                }

                if (action.equals("spawnBall")) {
                    Chicken chicken = (Chicken) player.getWorld().spawnEntity(player.getLocation(), EntityType.CHICKEN);
                    chicken.setAware(false);
                    sender.sendMessage("ball has been spawned at " + player.getLocation());
                    return true;
                }

                if (action.equals("playGhastScream")) {
                    getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                        renderGhastScream();
                    }, 10L);
                    sender.sendMessage("ghast scream has been played");
                    return true;
                }

                if (action.equals("giveCustomItems")) {
                    assert player.getLocation().getWorld() != null;
                    ItemStack kupa = new ReadyItems().get("Kupa");
                    ItemStack dolar = new ReadyItems().get("$1");
                    ItemStack bilet = new ReadyItems().get("Uniwersalny_bilet");
                    ItemStack ramka = new ReadyItems().get("Niewidzialna_ramka");

                    World world = player.getLocation().getWorld();
                    Location location = player.getLocation();

                    world.dropItem(location, dolar);
                    world.dropItem(location, bilet);
                    world.dropItem(location, kupa);
                    world.dropItem(location, ramka);
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
