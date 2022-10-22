package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        boostPointPrices.put("LoadedWarehouse", 20);
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

                if (value.equals("LoadedWarehouse")) {
                    if (loadedWarehouse) {
                        boosterAlreadyActive(value, player);
                        return true;
                    }

                    Bukkit.getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                        loadedWarehouse = true;
                        Bukkit.broadcastMessage(colorize(getBroadcastPrefix() + "Gracz&e " + player.getDisplayName() + " &frozdal wszystkim troche blokow!"));
                    }, 20L);

                    Bukkit.dispatchCommand(getConsoleSender(), "fillupwarehouse");
                    return true;
                }

                if (value.equals("MoreFromOres")) {
                    if (moreFromOres) {
                        boosterAlreadyActive(value, player);
                        return true;
                    }
                    Bukkit.getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                        moreFromOres = true;
                        Bukkit.broadcastMessage(colorize(getBroadcastPrefix() + "Gracz&e " + player.getDisplayName() + " &fwlasnie wlaczyl dodatkowe diamenty z rud do czasu kolejnego restartu serwera."));
                    }, 20L);
                    return true;
                }

                if (value.equals("DoubleXp")) {
                    if (doubleXp) {
                        boosterAlreadyActive(value, player);
                        return true;
                    }

                    Bukkit.getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                        doubleXp = true;
                        Bukkit.broadcastMessage(colorize(getBroadcastPrefix() + "Gracz&e " + player.getDisplayName() + " &fwlasnie dwukrotnie zwiekszyl zdobywane doswiadczenie do czasu kolejnego restartu serwera."));
                    }, 20L);
                    return true;
                }

                if (value.equals("TogglePhantoms")) {
                    if (togglePhantoms) {
                        boosterAlreadyActive(value, player);
                        return true;
                    }

                    Bukkit.getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                        togglePhantoms = true;
                        Bukkit.broadcastMessage(colorize(getBroadcastPrefix() + "Gracz&e " + player.getDisplayName() + "&f wlasnie wylaczyl pojawianie sie fantomow do czasu kolejnego restartu serwera."));
                    }, 20L);

                    Bukkit.dispatchCommand(getConsoleSender(), "gamerule doInsomnia false");
                    return true;
                }

                if (value.equals("ToggleEnd")) {
                    if (toggleEnd) {
                        boosterAlreadyActive(value, player);
                        return true;
                    }

                    Bukkit.getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                        toggleEnd = true;
                        Bukkit.broadcastMessage(colorize(getBroadcastPrefix() + "Gracz&e " + player.getDisplayName() + "&f wlasnie wlaczyl end do czasu kolejnego restartu serwera."));
                    }, 20L);
                    return true;
                }

                if (value.equals("PickupAll")) {
                    if (pickupAll) {
                        boosterAlreadyActive(value, player);
                        return true;
                    }

                    Bukkit.getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                        pickupAll = true;
                        Bukkit.broadcastMessage(colorize(getBroadcastPrefix() + "Gracz&e " + player.getDisplayName() + "&f wlasnie mozliwosc podnoszenia wszystkich bytow do czasu kolejnego restartu serwera."));
                    }, 20L);
                    return true;
                }

                if (value.equals("DoubledForce")) {
                    if (doubledForce) {
                        boosterAlreadyActive(value, player);
                        return true;
                    }

                    Bukkit.getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                        doubledForce = true;
                        Bukkit.broadcastMessage(colorize(getBroadcastPrefix() + "Gracz&e " + player.getDisplayName() + "&f wlasnie zwiekszyl sile rzucania bytami do czasu kolejnego restartu serwera."));
                    }, 20L);
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
