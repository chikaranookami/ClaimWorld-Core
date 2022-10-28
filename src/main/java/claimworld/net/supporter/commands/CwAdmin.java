package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.Messages;
import claimworld.net.supporter.utils.Ranks;
import claimworld.net.supporter.utils.guis.ReadyItems;
import claimworld.net.supporter.utils.guis.StoredInventories;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getScheduler;

public class CwAdmin {
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
                    for (Player onlinePlayer : getOnlinePlayers()) {
                        onlinePlayer.playSound(onlinePlayer, Sound.ENTITY_GHAST_SCREAM, 1.2f, 1.2f);
                        onlinePlayer.playSound(onlinePlayer, Sound.ENTITY_GHAST_HURT, 1.2f, 1.2f);
                        onlinePlayer.playSound(onlinePlayer, Sound.ENTITY_GHAST_SCREAM, 1.2f, 1.2f);
                        onlinePlayer.playSound(onlinePlayer, Sound.ENTITY_GHAST_HURT, 1.2f, 1.2f);
                    }
                    sender.sendMessage("ghast scream has been played");
                    return true;
                }

                if (action.equals("updateRank")) {
                    String displayName = player.getDisplayName();

                    sender.sendMessage("Trying to update rank of " + displayName);

                    getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                        new Ranks().updateRank(player);

                        sender.sendMessage("Rank has been updated for " + displayName);
                    }, 40L);
                    return true;
                }

                if (action.equals("giveCustomItems")) {
                    assert player.getLocation().getWorld() != null;
                    ItemStack kupa = new ReadyItems().get("Kupa");
                    ItemStack dolar = new ReadyItems().get("$1");
                    ItemStack bilet = new ReadyItems().get("Uniwersalny bilet");
                    ItemStack ramka = new ReadyItems().get("Niewidzialna ramka");

                    World world = player.getLocation().getWorld();
                    Location location = player.getLocation();

                    world.dropItem(location, dolar);
                    world.dropItem(location, bilet);
                    world.dropItem(location, kupa);
                    world.dropItem(location, ramka);
                    return true;
                }

                StoredInventories storedInventories = new StoredInventories();
                HashMap<String, List<ItemStack>> items = storedInventories.getStoredItems();

                if (action.equals("manuallyAddToStored")) {
                    String message = "W Twojej Skrytce cos jest. Odbierz to, zanim zniknie!";

                    if (!(sender instanceof Player)) return true;

                    Player admin = (Player) sender;
                    ItemStack item = admin.getInventory().getItemInMainHand();

                    if (item.getType().isAir()) {
                        admin.sendMessage("pusta reka");
                        return true;
                    }

                    String playerName = player.getName();

                    if (items.get(playerName) == null) {
                        List<ItemStack> content = new ArrayList<>();
                        content.add(item);
                        storedInventories.updateStoredItems(playerName, content);
                    } else {
                        items.get(playerName).add(item);
                    }

                    player.sendMessage(Messages.getUserPrefix() + message);
                    return true;
                }

                if (action.equals("checkStored")) {
                    for (Map.Entry<String, List<ItemStack>> entry : items.entrySet()) {
                        sender.sendMessage(colorize("&7&oSI " + entry.getKey() + ": " + entry.getValue()));
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
