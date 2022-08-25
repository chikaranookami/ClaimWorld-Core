package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.Random;

public class ThrowEntity {
    public ThrowEntity() {
        new CommandBase("testthrow", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = (Player) sender;
                BukkitScheduler scheduler = Bukkit.getScheduler();
                Sheep entity = (Sheep) player.getWorld().spawnEntity(player.getLocation(), EntityType.SHEEP);

                scheduler.runTaskLater(Supporter.getInstance(), () -> {
                    player.addPassenger(entity);
                }, 10L);

                scheduler.runTaskLater(Supporter.getInstance(), () -> {
                    Vector direction = player.getLocation().getDirection();

                    player.removePassenger(entity);

                    entity.setVelocity(direction.multiply(new Random().nextInt(2) + 1));
                }, 20L);

                return true;
            }

            @Override
            public String getUsage() {
                return "/testthrow";
            }
        }.setPermission("claimworld.admin");
    }
}
