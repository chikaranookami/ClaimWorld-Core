package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetLokacja {
    public SetLokacja() {
        new CommandBase("setlokacja", 1, true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                FileConfiguration config = Supporter.getPlugin().getConfig();
                Player player = (Player) sender;
                Location location = player.getLocation();
                String locationName = arguments[0];

                config.set("locations." + locationName + ".x", location.getX());
                config.set("locations." + locationName + ".y", location.getY());
                config.set("locations." + locationName + ".z", location.getZ());
                assert location.getWorld() != null;
                config.set("locations." + locationName +  ".world", location.getWorld().getName());
                config.set("locations." + locationName + ".yaw", location.getYaw());
                config.set("locations." + locationName + ".pitch", location.getPitch());

                player.sendMessage("ustawiono lokacje " + locationName + " na wartosc: " + location);

                Supporter.getPlugin().saveConfig();
                return true;
            }

            @Override
            public String getUsage() {
                return "/setkacja <nazwa>";
            }
        }.setPermission("claimworld.admin");
    }
}
