package claimworld.net.supporter.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireworkUtils {
    private FireworkEffect.Type getRandomType() {
        List<FireworkEffect.Type> list = new ArrayList<>();
        list.add(FireworkEffect.Type.BALL_LARGE);
        list.add(FireworkEffect.Type.BALL);
        list.add(FireworkEffect.Type.STAR);
        list.add(FireworkEffect.Type.CREEPER);
        list.add(FireworkEffect.Type.BURST);

        return list.get(new Random().nextInt(list.size()));
    }

    public FireworkEffect getRandomEffect() {
        return FireworkEffect.builder()
                .with(getRandomType())
                .withColor(Color.fromRGB(new Random().nextInt(255) + 1, new Random().nextInt(255) + 1, new Random().nextInt(255) + 1))
                .flicker(new Random().nextBoolean())
                .withColor(Color.WHITE).build();
    }

    public void renderRandomFirework(Location location) {
        renderRandomFirework(location, 3, 0);
    }

    public void renderRandomFirework(Location location, int maxLifeTime) {
        renderRandomFirework(location, 3, maxLifeTime);
    }

    public void renderRandomFirework(Location location, int power, int maxLifeTime) {
        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        fireworkMeta.setPower(power);
        fireworkMeta.addEffect(getRandomEffect());
        firework.setFireworkMeta(fireworkMeta);

        if (maxLifeTime <= 0) {
            firework.detonate();
            return;
        }

        firework.setLife(new Random().nextInt(8) + 4);
        firework.setMaxLife(maxLifeTime);
    }
}
