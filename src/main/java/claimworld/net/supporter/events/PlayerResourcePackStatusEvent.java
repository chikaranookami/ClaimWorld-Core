package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.announcers.JoinAnnouncer;
import claimworld.net.supporter.utils.battlepass.Skills;
import claimworld.net.supporter.utils.items.Locker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static org.bukkit.Bukkit.*;

public class PlayerResourcePackStatusEvent implements Listener {

    HashMap<String, List<ItemStack>> storedItems = Locker.getInstance().getLockerMap();
    private final List<String> lockedPlayerList = new ArrayList<>();

    @EventHandler
    public void playerResourcePackStatusEvent(org.bukkit.event.player.PlayerResourcePackStatusEvent event) {
        if (event.getStatus() == org.bukkit.event.player.PlayerResourcePackStatusEvent.Status.ACCEPTED) return;

        Player player = event.getPlayer();

        new JoinAnnouncer(player);

        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
            String playerName = player.getName();
            Skills skills = new Skills();

            if (skills.canActivateSkill(player, "Punkty bywalca")) {
                if (!lockedPlayerList.contains(playerName)) {
                    lockedPlayerList.add(playerName);
                    dispatchCommand(getConsoleSender(), "adminvote User " + playerName + " AddPoints 1");
                    player.sendMessage(getUserPrefix() + "Otrzymales dodatkowy punkt za logowanie.");
                    skills.renderSkillEffect(player.getLocation());
                }
            }

            if (storedItems.get(playerName) == null) return;
            if (storedItems.get(playerName).size() < 1) return;

            player.sendMessage(getUserPrefix() + "W Twojej Skrytce cos jest. Odbierz to, zanim zniknie!");
        }, 100L);
    }
}
