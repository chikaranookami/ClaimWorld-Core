package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.bukkit.Bukkit.getScheduler;

public class Seriafw {

    private FireworkEffect.Type getRandomType() {
        List<FireworkEffect.Type> list = new ArrayList<>();
        list.add(FireworkEffect.Type.BALL_LARGE);
        list.add(FireworkEffect.Type.BALL);
        list.add(FireworkEffect.Type.STAR);
        list.add(FireworkEffect.Type.CREEPER);
        list.add(FireworkEffect.Type.BURST);

        return list.get(new Random().nextInt(list.size()));
    }

    private FireworkEffect getRandomEffect() {
        return FireworkEffect.builder()
                .with(getRandomType())
                .withColor(Color.RED)
                .flicker(new Random().nextBoolean())
                .withColor(Color.WHITE).build();
    }

    public Seriafw() {
        new CommandBase("seriafw", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Location location = ((Player) sender).getLocation();
                assert location.getWorld() != null;

                AtomicInteger counter = new AtomicInteger();
                int random = new Random().nextInt(3) + 6;

                getScheduler().scheduleSyncRepeatingTask(Supporter.getPlugin(), () -> {
                    if (!(counter.get() < 10)) {
                        return;
                    }

                    Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
                    FireworkMeta fireworkMeta = firework.getFireworkMeta();

                    fireworkMeta.setPower(random);
                    fireworkMeta.addEffect(getRandomEffect());
                    firework.setFireworkMeta(fireworkMeta);

                    firework.setLife(random);
                    firework.setMaxLife(50);

                    counter.getAndIncrement();

                }, 10L, random * 2L);

                return true;
            }

            @Override
            public String getUsage() {
                return "/seriafw";
            }
        }.enableDelay(10).setPermission("claimworld.mvp");
    }

}
