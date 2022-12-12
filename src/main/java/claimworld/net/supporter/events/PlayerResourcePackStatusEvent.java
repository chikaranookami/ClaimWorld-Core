package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.announcers.JoinAnnouncer;
import claimworld.net.supporter.utils.battlepass.SkillManager;
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

    private final List<String> lockedPlayerList = new ArrayList<>();

    @EventHandler
    public void playerResourcePackStatusEvent(org.bukkit.event.player.PlayerResourcePackStatusEvent event) {
        if (event.getStatus() == org.bukkit.event.player.PlayerResourcePackStatusEvent.Status.ACCEPTED) return;

        Player player = event.getPlayer();
        String playerName = player.getName();
        SkillManager skillManager = new SkillManager();

        if (!skillManager.canActivateSkill(player, "Punkty bywalca")) return;
        if (lockedPlayerList.contains(playerName)) return;
        lockedPlayerList.add(playerName);

        if (new Random().nextInt(2) != 0) return;

        getScheduler().runTask(Supporter.getPlugin(), () -> dispatchCommand(getConsoleSender(), "adminvote User " + playerName + " AddPoints 1"));
        player.sendMessage(getUserPrefix() + "Otrzymales dodatkowy punkt za logowanie.");
        skillManager.renderSkillEffect(player.getLocation());
    }
}
