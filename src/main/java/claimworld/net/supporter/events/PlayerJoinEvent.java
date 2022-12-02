package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.announcers.JoinAnnouncer;
import claimworld.net.supporter.utils.items.ReadyItems;
import claimworld.net.supporter.utils.battlepass.BattlePassManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getScheduler;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void joinEvent(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        player.setDisplayName(playerName);

        //set menu item
        player.getInventory().setItem(17, ReadyItems.getInstance().get("Menu"));

        //mvp join bonus
        if (player.hasPermission("claimworld.mvp")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 1, true, false, false));
        }

        //announcer
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> new JoinAnnouncer(player), 10L);

        //enable at 6, 24, 25, 26 and 31 of december
        //getScheduler().runTaskLater(Supporter.getPlugin(), () -> player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 432000, 1, true, false, false)), 10L);

        //checking compatibility between permissions and displayed team
        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
            Team team = player.getScoreboard().getEntryTeam(playerName);
            if (team == null) return;
            if (player.hasPermission("claimworld.vip") || player.hasPermission("claimworld.mod")) return;
            team.removeEntry(playerName);
        }, 20L);

        //setlist
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
            player.setPlayerListHeader(colorize("\n&a❤ Claim&fWorld&a.net\n"));
            BattlePassManager.getInstance().updateTablistFooter(player);
        }, 30L);
    }
}
