package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.FireworkUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

                    fireworkUtils.renderRandomFirework(location, 45);

                    counter.getAndIncrement();

                }, 0L, random);

                return true;
            }

            @Override
            public String getUsage() {
                return "/seriafw";
            }
        }.enableDelay(7).setPermission("claimworld.mvp");
    }

}
