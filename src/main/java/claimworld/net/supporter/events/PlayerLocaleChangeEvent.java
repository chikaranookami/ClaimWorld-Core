package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Locale;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerLocaleChangeEvent implements Listener {

    @EventHandler
    public void playerLocaleChangeEvent(org.bukkit.event.player.PlayerLocaleChangeEvent event) {
        if ((!event.getLocale().equals(Locale.JAPANESE.getLanguage()))) return;

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> TaskManager.getInstance().tryFinishTask(event.getPlayer(), new Task("Poucz sie kanji.", "", 0)));
    }
}
