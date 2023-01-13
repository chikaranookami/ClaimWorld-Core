package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.*;
import claimworld.net.supporter.announcers.JoinAnnouncer;
import claimworld.net.supporter.items.ReadyItems;
import claimworld.net.supporter.battlepass.BattlePassManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;

import java.util.*;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class PlayerJoinEvent implements Listener {

    ReadyItems readyItems = ReadyItems.getInstance();
    BonusManager bonusManager = BonusManager.getInstance();
    GlobalUtils globalUtils = GlobalUtils.getInstance();

    private final GeyserUtils geyserUtils = new GeyserUtils();
    private final AttributesManager attributesManager = new AttributesManager();

    private void renderCustomJoinBroadcast(Player player, String playerName) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            if (player.hasPermission("claimworld.mvp")) {
                broadcastMessage(colorize("&c> &c&lMVP&c " + playerName + " wlasnie wszeld na serwer, POGGERS!"));
                return;
            }

            if (player.hasPermission("claimworld.vip")) {
                broadcastMessage(colorize("&b> &b&lVIP&b " + playerName + " wlasnie wszeld na serwer, pogU!"));
                return;
            }

            broadcastMessage(colorize("&f> " + playerName + " wlasnie wszeld na serwer, witamy!"));
        });
    }

    @EventHandler
    public void joinEvent(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        renderCustomJoinBroadcast(player, playerName);

        event.setJoinMessage("");

        //fix rare jetpack bug
        if (player.isFlying() && !player.hasPermission("claimworld.admin")) player.setFlying(false);

        //set menu item
        player.getInventory().setItem(17, readyItems.getMenuItem());

        //attributes
        attributesManager.updatePlayerHealth(player);

        //mvp join bonus
        if (player.hasPermission("claimworld.mvp")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 1, false, false, true));
        }

        //announcer
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
            if (geyserUtils.isPlayerFromGeyser(player.getUniqueId())) {
                player.sendMessage(getUserPrefix() + "\nClaim World dziala na Java Edition, wiec rozgrywka zostala odpowiednio dostosowana do Twojego wydania.\n");
            } else {
                new JoinAnnouncer().render(player);
            }
        }, 10L);

        //checking compatibility between permissions and displayed team & fix jetpack bug
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

        //manage global free chest
        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
            List<String> playersWithFreeChest = globalUtils.getPlayersWithFreeChest();
            if (bonusManager.getBonuses().get("Skrzynka") && !playersWithFreeChest.contains(playerName)) {
                playersWithFreeChest.add(playerName);
                globalUtils.addFreeChest(player);
            }
        }, 100L);
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> attributesManager.tryUpdateStats(player), 300L);
    }
}
