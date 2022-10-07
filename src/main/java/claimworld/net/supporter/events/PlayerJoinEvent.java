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
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class PlayerJoinEvent implements Listener {

    private final Ranks ranks = new Ranks();
    private final Scoreboard scoreboard = getScoreboardManager().getNewScoreboard();
    Team adminTeam = scoreboard.registerNewTeam("admin");
    Team modTeam = scoreboard.registerNewTeam("mod");
    Team mvpTeam = scoreboard.registerNewTeam("mvp");
    Team vippTeam = scoreboard.registerNewTeam("vip+");
    Team vipTeam = scoreboard.registerNewTeam("vip");
    Team playerTeam = scoreboard.registerNewTeam("player");

    @EventHandler
    public void joinEvent(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //set tablist
        player.setPlayerListHeader(colorize("\n &bClaimWorld&f.net \n"));
        //player.setPlayerListFooter("\n " + ChatColor.AQUA + "Magiczne Skrzynki \n" + ChatColor.WHITE + " Data: " + ChatColor.AQUA + "16.09.2022" + " \n\n " + ChatColor.AQUA + "Konkurs Talentow \n" + ChatColor.WHITE + " Data: " + ChatColor.AQUA + "17.09.2022" + " \n");

        //set menu item
        player.getInventory().setItem(17, new ReadyItems().get("Menu"));

        //set ranks
        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> ranks.updateRank(player), 4L);

        //set nametag
        //getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
            //Objective specialRank = scoreboard.registerNewObjective(player.getName(), "dummy", ranks.getSpecialRankName(player));
            //specialRank.setDisplaySlot(DisplaySlot.BELOW_NAME);

            //player.setScoreboard(scoreboard);
        //}, 10L);

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
