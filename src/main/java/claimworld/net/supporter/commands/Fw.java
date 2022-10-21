package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

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
                fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.RED).flicker(true).withColor(Color.WHITE).build());
                firework.setFireworkMeta(fireworkMeta);

                firework.detonate();
                return true;
            }

            @Override
            public String getUsage() {
                return "/fw";
            }
        }.enableDelay(10).setPermission("claimworld.mvp");
    }
}
