package claimworld.net.supporter.utils;

import claimworld.net.supporter.Supporter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getScheduler;

public class ActiveEventAnnouncer {

    private final List<Sound> sounds = Arrays.asList(Sound.UI_TOAST_CHALLENGE_COMPLETE, Sound.ENTITY_VILLAGER_CELEBRATE, Sound.ENTITY_EVOKER_CELEBRATE, Sound.ENTITY_PILLAGER_CELEBRATE, Sound.ENTITY_WITCH_CELEBRATE);

    public ActiveEventAnnouncer(Player player,String name, String date) {
        player.sendTitle(
                colorize("&c" + name + "!"),
                colorize("&fOkres: &c" + date + "&f, Szczegoly: &c/wydarzenie"),
                20, 80, 20
        );

        AtomicInteger i = new AtomicInteger();

        getScheduler().scheduleSyncRepeatingTask(Supporter.getPlugin(), () -> {
            if (i.get() >= sounds.size()) {
                return;
            }

            player.playSound(player, sounds.get(i.get()), 1.0f, 1.0f);

            i.getAndIncrement();
        }, 4L, 10L);
    }
}
