package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.FireworkUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class Fw {

    private final FireworkUtils fireworkUtils = new FireworkUtils();

    public Fw() {
        new CommandBase("fw", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Location location = ((Player) sender).getLocation();
                assert location.getWorld() != null;

                fireworkUtils.renderRandomFirework(location);

                return true;
            }

            @Override
            public String getUsage() {
                return "/fw";
            }
        }.enableDelay(3).setPermission("claimworld.mvp");
    }
}
