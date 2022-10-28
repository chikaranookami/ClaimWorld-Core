package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static claimworld.net.supporter.Supporter.*;
import static claimworld.net.supporter.utils.Messages.getBroadcastPrefix;
import static claimworld.net.supporter.utils.Messages.getUserPrefix;
import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getConsoleSender;

public class Cws {
    private final String usage = "/cws TogglePhantoms/DoubleXp/ToggleEnd/MoreFromOres/LoadedWarehouse/PickupAll/DoubledForce";

    private final HashMap<String, Integer> boostPointPrices = new HashMap<>();

    private void boosterAlreadyActive(String variableName, Player player) {
        Bukkit.dispatchCommand(getConsoleSender(), "adminvote User " + player.getName() + " AddPoints " + boostPointPrices.get(variableName));
        getConsoleSender().sendMessage(variableName + " is already true");
        player.sendMessage(getUserPrefix() + "Ktos juz aktywowal ten bonus. Punkty zostaly zwrocone.");
    }

    public Cws() {
        boostPointPrices.put("BlocksWarehouse", 20);
        boostPointPrices.put("MoreFromOres", 30);
        boostPointPrices.put("DoubleXp", 30);
        boostPointPrices.put("TogglePhantoms", 15);
        boostPointPrices.put("ToggleEnd", 20);
        boostPointPrices.put("PickupAll", 30);
        boostPointPrices.put("DoubledForce", 30);

        new CommandBase("cws", 0, 3, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                if (arguments.length <= 0) {
                    sender.sendMessage(usage);
                    return true;
                }

                Player player = Bukkit.getPlayer(arguments[1]);

                if (player == null) {
                    sender.sendMessage("player hasn't been set or his nickname is invalid - aborting");
                    return true;
                }

                String value = arguments[0];

                if (value.equals("BlocksWarehouse")) {
                    if (loadedWarehouse) {
                        boosterAlreadyActive(value, player);
                        return true;
                    }
                    loadedWarehouse = true;
                    Bukkit.broadcastMessage(colorize(getBroadcastPrefix() + player.getDisplayName() + " &frozdal troche blokow!"));
                    Bukkit.dispatchCommand(getConsoleSender(), "fillupwarehouse blocks");
                    return true;
                }

                if (value.equals("XpBottleWarehouse")) {
                    Bukkit.broadcastMessage(colorize(getBroadcastPrefix() + player.getDisplayName() + " &frozdal troche expa!"));
                    Bukkit.dispatchCommand(getConsoleSender(), "fillupwarehouse xpBottle");
                    return true;
                }

                if (value.equals("MoreFromOres")) {
                    if (moreFromOres) {
                        boosterAlreadyActive(value, player);
                        return true;
                    }
                    moreFromOres = true;
                    Bukkit.broadcastMessage(colorize(getBroadcastPrefix() + player.getDisplayName() + " &fwlaczyl dodatkowe diamenty z rud!"));
                    return true;
                }

                if (value.equals("DoubleXp")) {
                    if (doubleXp) {
                        boosterAlreadyActive(value, player);
                        return true;
                    }
                    doubleXp = true;
                    Bukkit.broadcastMessage(colorize(getBroadcastPrefix() + player.getDisplayName() + " &f2x zwiekszyl zdobywane doswiadczenie!"));
                    return true;
                }

                if (value.equals("TogglePhantoms")) {
                    if (togglePhantoms) {
                        boosterAlreadyActive(value, player);
                        return true;
                    }
                    togglePhantoms = true;
                    Bukkit.broadcastMessage(colorize(getBroadcastPrefix() + player.getDisplayName() + " &fwylaczyl fantomy!"));
                    Bukkit.dispatchCommand(getConsoleSender(), "gamerule doInsomnia false");
                    return true;
                }

                if (value.equals("ToggleEnd")) {
                    if (toggleEnd) {
                        boosterAlreadyActive(value, player);
                        return true;
                    }
                    toggleEnd = true;
                    Bukkit.broadcastMessage(colorize(getBroadcastPrefix() + player.getDisplayName() + " &fwlaczyl end!"));
                    return true;
                }

                if (value.equals("PickupAll")) {
                    if (pickupAll) {
                        boosterAlreadyActive(value, player);
                        return true;
                    }
                    pickupAll = true;
                    Bukkit.broadcastMessage(colorize(getBroadcastPrefix() + player.getDisplayName() + " &fwlaczyl podnoszenie wiekszosci bytow!"));
                    return true;
                }

                if (value.equals("DoubledForce")) {
                    if (doubledForce) {
                        boosterAlreadyActive(value, player);
                        return true;
                    }
                    doubledForce = true;
                    Bukkit.broadcastMessage(colorize(getBroadcastPrefix() + player.getDisplayName() + " &fzwiekszyl sile rzucania bytami!"));
                    return true;
                }

                sender.sendMessage("?");
                return true;
            }

            @Override
            public String getUsage() {
                return usage;
            }
            }.setPermission("claimworld.admin");
    }
}
