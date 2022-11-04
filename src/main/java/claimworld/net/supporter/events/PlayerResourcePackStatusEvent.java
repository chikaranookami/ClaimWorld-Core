package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.announcers.JoinAnnouncer;
import claimworld.net.supporter.utils.MessageUtils;
import claimworld.net.supporter.utils.items.Locker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerResourcePackStatusEvent implements Listener {

    HashMap<String, List<ItemStack>> storedItems = Locker.getInstance().getLockerMap();

    @EventHandler
    public void playerResourcePackStatusEvent(org.bukkit.event.player.PlayerResourcePackStatusEvent event) {
        if (event.getStatus() == org.bukkit.event.player.PlayerResourcePackStatusEvent.Status.ACCEPTED) return;

        Player player = event.getPlayer();

        new JoinAnnouncer(player);

        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
            String playerName = player.getName();

            if (storedItems.get(playerName) != null) {
                if (storedItems.get(playerName).size() < 1) return;

                player.sendMessage(MessageUtils.getUserPrefix() + "W Twojej Skrytce cos jest. Odbierz to, zanim zniknie!");
            }
        }, 100L);
    }
}
