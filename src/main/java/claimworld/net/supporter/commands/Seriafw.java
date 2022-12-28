package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.wip.FireworkUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.bukkit.Bukkit.getScheduler;

public class Seriafw {

    private final FireworkUtils fireworkUtils = new FireworkUtils();

    public Seriafw() {
        new CommandBase("seriafw", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Location location = ((Player) sender).getLocation();
                assert location.getWorld() != null;

                AtomicInteger counter = new AtomicInteger();
                int random = new Random().nextInt(4) + 4;

                getScheduler().scheduleSyncRepeatingTask(Supporter.getPlugin(), () -> {
                    if (!(counter.get() < 24)) {
                        return;
                    }

                    Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
                    FireworkMeta fireworkMeta = firework.getFireworkMeta();

                    fireworkMeta.setPower(random);
                    fireworkMeta.addEffect(fireworkUtils.getRandomEffect());
                    firework.setFireworkMeta(fireworkMeta);

                    firework.setLife(random);
                    firework.setMaxLife(45);

                    counter.getAndIncrement();

                }, 0L, random);

                return true;
            }

            @Override
            public String getUsage() {
                return "/seriafw";
            }
        }.enableDelay(7).setPermission("claimworld.mvp");

        new CommandBase("nowyrok", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Location location = ((Player) sender).getLocation();
                assert location.getWorld() != null;

                AtomicInteger counter = new AtomicInteger();
                int random = new Random().nextInt(8) + 4;

                getScheduler().scheduleSyncRepeatingTask(Supporter.getPlugin(), () -> {
                    if (!(counter.get() < 60)) {
                        return;
                    }

                    Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
                    FireworkMeta fireworkMeta = firework.getFireworkMeta();

                    fireworkMeta.setPower(random);
                    fireworkMeta.addEffect(fireworkUtils.getRandomEffect());
                    firework.setFireworkMeta(fireworkMeta);

                    firework.setLife(random);
                    firework.setMaxLife(45);

                    counter.getAndIncrement();

                }, 0L, random);

                return true;
            }

            @Override
            public String getUsage() {
                return "/nowyrok";
            }
        }.enableDelay(30).setPermission("claimworld.player");
    }

}
