package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.tasks.TaskManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Team;

import java.util.logging.Level;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getScheduler;

public class AsyncPlayerChatEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();

    @EventHandler
    public void asyncPlayerChatEvent(org.bukkit.event.player.AsyncPlayerChatEvent event) {
        if (!event.isAsynchronous()) getLogger().log(Level.WARNING, "tried to use NOT async chat event");

        Player player = event.getPlayer();
        Team team = event.getPlayer().getScoreboard().getEntryTeam(player.getName());
        String playerName = player.getName();
        String message = event.getMessage();

        if (team == null || team.getPrefix().isEmpty()) {
            event.setFormat(playerName + ChatColor.GRAY + ": " + ChatColor.RESET + message);
        } else {
            event.setFormat(team.getPrefix() + ChatColor.RESET + playerName + ChatColor.GRAY + ": " + ChatColor.RESET + colorize(message));
        }

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            if (!message.contains("zagadka") && !message.contains("Zagadka")) return;
            taskManager.tryFinishTask(player, taskManager.getTaskMap().get("writeZagadka"));
        });
    }
}
