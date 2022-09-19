package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class LoadLokacja {
    public LoadLokacja() {
        new CommandBase("loadlokacja", 2, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                FileConfiguration config = Supporter.getPlugin().getConfig();
                Player player = Bukkit.getPlayer(arguments[0]);
                String locationName = arguments[1];

                if (player == null) {
                    sender.sendMessage("nie ma takiego gracza");
                    return true;
                }

                if (config.get("locations." + locationName) == null) {
                    sender.sendMessage("nie ma takiej lokacji");
                    return true;
                }

                World world = getServer().getWorld(String.valueOf(config.get("locations." + locationName + ".world")));

                if (world == null) {
                    sender.sendMessage("ten swiat juz nie istnieje");
                    return true;
                }

                Location location = new Location(world,
                        config.getInt("locations." + locationName + ".x"),
                        config.getInt("locations." + locationName + ".y"),
                        config.getInt("locations." + locationName + ".z"),
                        config.getInt("locations." + locationName + ".yaw"),
                        config.getInt("locations." + locationName + ".pitch")
                );

                player.teleport(location);

                return true;
            }

            @Override
            public String getUsage() {
                return "/loadlokacja <gracz> <nazwa>";
            }
        }.setPermission("claimworld.mod");
    }
}
