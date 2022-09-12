package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static claimworld.net.supporter.Supporter.*;

public class Cws {
    public Cws() {

        final String usage = "/cws TogglePhantoms/DoubleXp/ToggleEnd";

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

                if (arguments[0].equals("DoubleXp")) {
                    if (Supporter.doubleXp) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminvote User " + player.getName() + " AddPoints 30");

                        sender.sendMessage("DoubleXp is already true");
                        player.sendMessage(ChatColor.GRAY + "Ktos juz wlaczyl podwojne doswiadczenie. Punkty zostaly zwrocone.");
                    }

                    if (!Supporter.doubleXp) {
                        Supporter.doubleXp = true;

                        Bukkit.getScheduler().runTaskLaterAsynchronously(getInstance(), () -> {
                            sender.sendMessage("DoubleXp has been set to " + Supporter.doubleXp);
                            Bukkit.broadcastMessage(ChatColor.AQUA + "> Gracz " + ChatColor.WHITE + player.getDisplayName() + ChatColor.AQUA + " wlasnie dwukrotnie zwiekszyl zdobywane doswiadczenie do czasu kolejnego restartu serwera.");
                        }, 20L);
                    }

                    return true;
                }

                if (arguments[0].equals("TogglePhantoms")) {
                    if (togglePhantoms) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminvote User " + player.getName() + " AddPoints 15");

                        sender.sendMessage("TogglePhantoms is already true");
                        player.sendMessage(ChatColor.GRAY + "Ktos juz wylaczyl fantomy. Punkty zostaly zwrocone.");
                    }

                    if (!togglePhantoms) {
                        togglePhantoms = true;
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doInsomnia false");

                        Bukkit.getScheduler().runTaskLaterAsynchronously(getInstance(), () -> {
                            sender.sendMessage("TogglePhantoms has been set to " + togglePhantoms);
                            Bukkit.broadcastMessage(ChatColor.AQUA + "> Gracz " + ChatColor.WHITE + player.getDisplayName() + ChatColor.AQUA + " wlasnie wylaczyl pojawianie sie fantomow do czasu kolejnego restartu serwera.");
                        }, 20L);
                    }

                    return true;
                }

                if (arguments[0].equals("ToggleEnd")) {
                    if (Supporter.toggleEnd) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminvote User " + player.getName() + " AddPoints 30");

                        sender.sendMessage("ToggleEnd is already true");
                        player.sendMessage(ChatColor.GRAY + "Ktos juz wlaczyl end. Punkty zostaly zwrocone.");
                    }

                    if (!Supporter.toggleEnd) {
                        toggleEnd = true;

                        Bukkit.getScheduler().runTaskLaterAsynchronously(getInstance(), () -> {
                            sender.sendMessage("toggleEnd has been set to " + Supporter.toggleEnd);
                            Bukkit.broadcastMessage(ChatColor.AQUA + "> Gracz " + ChatColor.WHITE + player.getDisplayName() + ChatColor.AQUA + " wlasnie wlaczyl end do czasu nastepnego kolejnego serwera.");
                        }, 20L);
                    }

                    return true;
                }

                if (arguments[0].equals("PickupAll")) {
                    if (pickupAll) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminvote User " + player.getName() + " AddPoints 30");

                        sender.sendMessage("pickupAll is already true");
                        player.sendMessage(ChatColor.GRAY + "Ktos juz wlaczyl mozliwosc podnoszenia wszystkiego. Punkty zostaly zwrocone.");
                    }

                    if (!pickupAll) {
                        pickupAll = true;

                        Bukkit.getScheduler().runTaskLaterAsynchronously(getInstance(), () -> {
                            sender.sendMessage("pickupAll has been set to " + pickupAll);
                            Bukkit.broadcastMessage(ChatColor.AQUA + "> Gracz " + ChatColor.WHITE + player.getDisplayName() + ChatColor.AQUA + " wlasnie mozliwosc podnoszenia wszystkich bytow do czasu kolejnego restartu serwera.");
                        }, 20L);
                    }

                    return true;
                }

                if (arguments[0].equals("DoubledForce")) {
                    if (doubledForce) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adminvote User " + player.getName() + " AddPoints 30");

                        sender.sendMessage("doubledForce is already true");
                        player.sendMessage(ChatColor.GRAY + "Ktos juz zwiekszyl sile rzucania bytami. Punkty zostaly zwrocone.");
                    }

                    if (!doubledForce) {
                        doubledForce = true;

                        Bukkit.getScheduler().runTaskLaterAsynchronously(getInstance(), () -> {
                            sender.sendMessage("doubledForce has been set to " + doubledForce);
                            Bukkit.broadcastMessage(ChatColor.AQUA + "> Gracz " + ChatColor.WHITE + player.getDisplayName() + ChatColor.AQUA + " wlasnie zwiekszyl sile rzucania bytami do czasu kolejnego restartu serwera.");
                        }, 20L);
                    }

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
