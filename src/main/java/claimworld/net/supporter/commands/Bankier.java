package claimworld.net.supporter.commands;

import claimworld.net.supporter.guis.Gui;
import claimworld.net.supporter.guis.GuiManager;
import claimworld.net.supporter.items.ReadyItems;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.WarehouseUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static org.bukkit.Bukkit.*;

public class Bankier {

    ReadyItems readyItems = ReadyItems.getInstance();

    private final List<String> lockedPlayers = new ArrayList<>();

    public Bankier() {
        new CommandBase("wymienexpa", 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                if (player == null) {
                    getLogger().log(Level.INFO, "Player is null");
                    return true;
                }

                player.closeInventory();

                int playerLevel = player.getLevel();
                if (playerLevel < 30) {
                    player.sendMessage(getUserPrefix() + "By to zrobic, potrzebujesz przynajmniej 30 poziomu doswiadczenia.");
                    return true;
                }

                player.setLevel(playerLevel - 30);

                player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.EXPERIENCE_BOTTLE, 140));

                return true;
            }

            @Override
            public String getUsage() {
                return "/wymienexpa <gracz>";
            }
        }.setPermission("claimworld.admin");

        new CommandBase("odbierzlokate", 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                if (player == null) {
                    getLogger().log(Level.INFO, "Player is null");
                    return true;
                }

                player.closeInventory();

                String playerName = player.getName();
                if (lockedPlayers.contains(playerName)) {
                    player.sendMessage(getUserPrefix() + "Nie mozesz uzyc tej funkcji wiecej, niz raz na restart.");
                    return true;
                }

                if (LocalTime.now().getHour() < 12) {
                    player.sendMessage(getUserPrefix() + "Ta funkcja jest dostepna od 12:00 do 0:00.");
                    return true;
                }

                lockedPlayers.add(playerName);

                new WarehouseUtils().addItemsSingle(playerName, Collections.singletonList(readyItems.get("$1")));

                return true;
            }

            @Override
            public String getUsage() {
                return "/odbierzlokate <player>";
            }
        }.setPermission("claimworld.admin");

        new CommandBase("bankier", 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                if (player == null) {
                    getLogger().log(Level.INFO, "Player is null");
                    return true;
                }

                new GuiManager(player, new Gui(null, 54, "Bankier"));

                return true;
            }

            @Override
            public String getUsage() {
                return "/bankier <player>";
            }
        }.setPermission("claimworld.admin");
    }
}
