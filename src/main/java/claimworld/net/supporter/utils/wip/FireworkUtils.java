package claimworld.net.supporter.utils.wip;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;

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
}
