package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.StreamerUtils;
import claimworld.net.supporter.tasks.TaskManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Team;

import java.util.logging.Level;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class AsyncPlayerChatEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();
    StreamerUtils streamerUtils = StreamerUtils.getInstance();

    private void renderFilteredMessage(Player player, String message) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            for (String word : streamerUtils.getForbiddenWords()) {
                if (!message.contains(word)) continue;
                player.sendMessage(getUserPrefix() + "Wykryto niefajne okreslenie. Zablokowano wyslanie wiadomosci.");
                return;
            }

            String playerName = player.getName();
            Team team = player.getScoreboard().getEntryTeam(player.getName());

            if (team == null || team.getPrefix().isEmpty()) {
                broadcastMessage(playerName + ChatColor.GRAY + ": " + ChatColor.RESET + message);
            } else {
                broadcastMessage(team.getPrefix() + ChatColor.RESET + playerName + ChatColor.GRAY + ": " + ChatColor.RESET + colorize(message));
            }
        });
    }

    @EventHandler
    public void asyncPlayerChatEvent(org.bukkit.event.player.AsyncPlayerChatEvent event) {
        if (!event.isAsynchronous()) getLogger().log(Level.WARNING, "tried to use NOT async chat event");
        event.setCancelled(true);

        Player player = event.getPlayer();
        String message = event.getMessage();

        renderFilteredMessage(player, message);

        if (!message.contains("zagadka") && !message.contains("Zagadka")) return;
        taskManager.tryFinishTask(player, taskManager.getTaskMap().get("writeZagadka"));
    }
}
