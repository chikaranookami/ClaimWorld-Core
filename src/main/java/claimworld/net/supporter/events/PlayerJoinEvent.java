package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.MessageUtils;
import claimworld.net.supporter.utils.items.ReadyItems;
import claimworld.net.supporter.utils.battlepass.BattlePassManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
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
        player.getInventory().setItem(17, new ReadyItems().get("Menu"));

        //mvp join bonus
        if (player.hasPermission("claimworld.mvp")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 1, true, false, false));
        }

        //end location fix
        if (player.getWorld().getEnvironment() == World.Environment.THE_END) {
            if (player.getLocation().getY() < -128) {
                player.setHealth(0);
                player.sendMessage(MessageUtils.getUserPrefix() + "End jest obecnie wylaczony, a Ty byles gleboko w voidzie, wiec zginales.");
                return;
            }

            Bukkit.dispatchCommand(player, "spawn");
            player.sendMessage(MessageUtils.getUserPrefix() + "End jest obecnie wylaczony. Przeteleportowano na spawn.");
        }

        //checking compatibility between permissions and displayed team
        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
            Team team = player.getScoreboard().getEntryTeam(playerName);
            if (team == null) return;
            if (player.hasPermission("claimworld.vip")) return;
            player.getScoreboard().getEntryTeam(playerName).removeEntry(playerName);
        }, 20L);

        //setlist
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
            player.setPlayerListHeader(colorize("\n&a❤ Claim&fWorld&a.net\n"));
            BattlePassManager.getInstance().updateTablistFooter(player);
        }, 30L);
    }
}
