package claimworld.net.supporter.commands;

import claimworld.net.supporter.guis.Gui;
import claimworld.net.supporter.guis.GuiManager;
import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class OpenMenu {
    public OpenMenu() {
        new CommandBase("openmenu", 2, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                if (player == null) {
                    getLogger().log(Level.INFO, "Player is null");
                    return true;
                }

                new GuiManager(player, new Gui(null, 54, arguments[1]));

                return true;
            }

            @Override
            public String getUsage() {
                return "/openmenu <player> <menu>";
            }
        }.setPermission("claimworld.admin");
    }
}
