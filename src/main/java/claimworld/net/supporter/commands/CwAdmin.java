package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.items.CustomItem;
import claimworld.net.supporter.utils.items.Locker;
import claimworld.net.supporter.utils.items.ReadyItems;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class CwAdmin {

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

                if (action.equals("snowAnimation")) {
                    String playerName = player.getName();
                    if (blockedPlayers.contains(playerName)) {
                        player.sendMessage(getUserPrefix() + "Druga warstwa sniegu? Wut?.");
                        Bukkit.dispatchCommand(getConsoleSender(), "adminvote User " + playerName + " AddPoints 3");
                        return true;
                    }

                    blockedPlayers.add(playerName);
                    new BukkitRunnable() {
                        final AtomicInteger counter = new AtomicInteger();

                        @Override
                        public void run() {
                            int radius = 2;
                            for (int x = radius; x >= -radius; x--) {
                                for (int z = radius; z >= -radius; z--) {
                                    int finalX = x;
                                    int finalZ = z;
                                    getScheduler().runTaskLater(Supporter.getPlugin(), () ->
                                                    player.getWorld().spawnParticle(
                                                            Particle.SNOWFLAKE,
                                                            player.getLocation().add(finalX, 6, finalZ),
                                                            2, 0, 0, 0, 0
                                                    )
                                            , new Random().nextInt(20));
                                }
                            }

                            counter.getAndIncrement();
                            if (counter.get() >= 5400 || !player.isOnline()) {
                                blockedPlayers.remove(playerName);
                                cancel();
                            }
                        }
                    }.runTaskTimerAsynchronously(Supporter.getPlugin(), 0, 10);
                    player.sendMessage(getUserPrefix() + "Wlaczono snieg. Wyjdz, by wylaczyc go szybciej.");
                    return true;
                }

                if (action.equals("largeSnowAnimation")) {
                    if (snowAmount >= 3) {
                        player.sendMessage(getUserPrefix() + "Spokojnie, daj chmurom odpoczac.");
                        Bukkit.dispatchCommand(getConsoleSender(), "adminvote User " + player.getName() + " AddPoints 20");
                        return true;
                    }

                    snowAmount++;
                    new BukkitRunnable() {
                        final AtomicInteger counter = new AtomicInteger();

                        @Override
                        public void run() {
                            int radius = 48;
                            for (int x = radius; x >= -radius; x--) {
                                for (int z = radius; z >= -radius; z--) {
                                    if (new Random().nextBoolean()) continue;

                                    int finalX = x;
                                    int finalZ = z;

                                    getScheduler().runTaskLater(Supporter.getPlugin(), () ->
                                            player.getWorld().spawnParticle(
                                                    Particle.SNOWFLAKE,
                                                    player.getLocation().add(finalX, 7, finalZ),
                                                    1, 0, 0, 0, 0
                                            )
                                    , new Random().nextInt(20 * 2));
                                }
                            }

                            counter.getAndIncrement();
                            if (counter.get() >= 1350 || !player.isOnline()) {
                                snowAmount--;
                                cancel();
                            }
                        }
                    }.runTaskTimerAsynchronously(Supporter.getPlugin(), 0, 40);
                    player.sendMessage(getUserPrefix() + "Wlaczono snieg. Wyjdz, by wylaczyc go szybciej.");
                    return true;
                }

                if (action.equals("forceNewTasks")) {
                    boolean value = TaskManager.getInstance().renderNewTasks(true);
                    sender.sendMessage("force rendering status: " + value);
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
                    Location location = player.getLocation();

                    for (Map.Entry<String, CustomItem> entry : ReadyItems.getInstance().getItemMap().entrySet()) {
                        ItemStack itemStack = entry.getValue().getItem();
                        Item item = location.getWorld().dropItem(location, itemStack);
                        item.setOwner(player.getUniqueId());
                    }

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
