package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.wip.FireworkUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class Fw {
    public Fw() {
        new CommandBase("fw", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Location location = ((Player) sender).getLocation();
                assert location.getWorld() != null;

                Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
                FireworkMeta fireworkMeta = firework.getFireworkMeta();

                fireworkMeta.setPower(3);
                fireworkMeta.addEffect(new FireworkUtils().getRandomEffect());
                firework.setFireworkMeta(fireworkMeta);

                firework.setLife(new Random().nextInt(5) + 1);
                return true;
            }

            @Override
            public String getUsage() {
                return "/fw";
            }
        }.enableDelay(3).setPermission("claimworld.mvp");
    }
}
