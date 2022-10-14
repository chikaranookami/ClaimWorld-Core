package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.Messages;
import claimworld.net.supporter.utils.Ranks;
import claimworld.net.supporter.utils.guis.ReadyItems;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class PlayerJoinEvent implements Listener {

    private final Ranks ranks = new Ranks();
    private final Scoreboard scoreboard = getScoreboardManager().getNewScoreboard();
    private final Team adminTeam = scoreboard.registerNewTeam("admin");
    private final Team modTeam = scoreboard.registerNewTeam("mod");
    private final Team mvpTeam = scoreboard.registerNewTeam("mvp");
    private final Team vippTeam = scoreboard.registerNewTeam("vip+");
    private final Team vipTeam = scoreboard.registerNewTeam("vip");
    private final Team playerTeam = scoreboard.registerNewTeam("player");

    private void updatePlayerNametag(Player player) {
        if (player.hasPermission("claimworld.admin")) {
            adminTeam.addEntry(player.getName());
            return;
        }
        if (player.hasPermission("claimworld.mod")) {
            modTeam.addEntry(player.getName());
            return;
        }
        if (player.hasPermission("claimworld.mvp")) {
            mvpTeam.addEntry(player.getName());
            return;
        }
        if (player.hasPermission("claimworld.vip+")) {
            vippTeam.addEntry(player.getName());
            return;
        }
        if (player.hasPermission("claimworld.vip")) {
            vipTeam.addEntry(player.getName());
            return;
        }
        if (player.hasPermission("claimworld.player")) {
            playerTeam.addEntry(player.getName());
        }
    }

    public PlayerJoinEvent() {
        adminTeam.setPrefix(colorize(ranks.getRankName("admin") + " "));
        modTeam.setPrefix(colorize(ranks.getRankName("mod") + " "));
        mvpTeam.setPrefix(colorize(ranks.getRankName("mvp") + " "));
        vippTeam.setPrefix(colorize(ranks.getRankName("vip+") + " "));
        vipTeam.setPrefix(colorize(ranks.getRankName("vip") + " "));
        playerTeam.setPrefix(colorize(ranks.getRankName("player") + " "));

        playerTeam.setCanSeeFriendlyInvisibles(false);
        adminTeam.setCanSeeFriendlyInvisibles(false);
        vipTeam.setCanSeeFriendlyInvisibles(false);
        vippTeam.setCanSeeFriendlyInvisibles(false);
        mvpTeam.setCanSeeFriendlyInvisibles(false);
        modTeam.setCanSeeFriendlyInvisibles(false);

        playerTeam.setAllowFriendlyFire(true);
        vipTeam.setAllowFriendlyFire(true);
        vippTeam.setAllowFriendlyFire(true);
        mvpTeam.setAllowFriendlyFire(true);
        modTeam.setAllowFriendlyFire(true);
        adminTeam.setAllowFriendlyFire(true);
    }

    @EventHandler
    public void joinEvent(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //set tablist
        player.setPlayerListHeader(colorize("\n &bClaimWorld&f.net \n"));
        //player.setPlayerListFooter("\n " + ChatColor.AQUA + "Magiczne Skrzynki \n" + ChatColor.WHITE + " Data: " + ChatColor.AQUA + "16.09.2022" + " \n\n " + ChatColor.AQUA + "Konkurs Talentow \n" + ChatColor.WHITE + " Data: " + ChatColor.AQUA + "17.09.2022" + " \n");

        //set menu item
        ItemStack menu = new ReadyItems().get("Menu");

        player.getInventory().setItem(17, menu);

        //set ranks
        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> ranks.updateRank(player), 10L);

        //set nametag
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
            player.setScoreboard(scoreboard);
            updatePlayerNametag(player);
        }, 10L);

        //end available?
        if (Supporter.toggleEnd) {
            player.sendMessage(Messages.getUserPrefix() + "End jest obecnie wlaczony. Korzystaj, poki mozesz!");
            return;
        }

        //end location fix
        if (player.getWorld().getEnvironment() == World.Environment.THE_END) {
            if (player.getLocation().getY() < -128) {
                player.setHealth(0);
                player.sendMessage(Messages.getUserPrefix() + "End jest obecnie wylaczony, a Ty byles gleboko w voidzie, wiec zginales.");
                return;
            }

            Bukkit.dispatchCommand(player, "spawn");
            player.sendMessage(Messages.getUserPrefix() + "End jest obecnie wylaczony. Przeteleportowano na spawn.");
        }
    }
}
