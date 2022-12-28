package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.*;
import claimworld.net.supporter.utils.announcers.JoinAnnouncer;
import claimworld.net.supporter.utils.items.Locker;
import claimworld.net.supporter.utils.items.ReadyItems;
import claimworld.net.supporter.utils.battlepass.BattlePassManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
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

    @EventHandler
    public void joinEvent(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        event.setJoinMessage(colorize("&f> " + playerName + " wlasnie wszeld na serwer, witamy!"));

        //set menu item
        player.getInventory().setItem(17, ReadyItems.getInstance().getMenuItem());

        //attributes
        attributesManager.updatePlayerHealth(player);

        //mvp join bonus
        if (player.hasPermission("claimworld.mvp")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 1, false, false, true));
        }

        //announcer + skin clear
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
            if (geyserUtils.isPlayerFromGeyser(player.getUniqueId())) {
                player.sendMessage(getUserPrefix() + "\nClaim World dziala na Java Edition, wiec rozgrywka zostala odpowiednio dostosowana do Twojego wydania.\n");
            } else {
                new JoinAnnouncer().render(player);

                dispatchCommand(getConsoleSender(), "skin clear " + player.getName());
            }
        }, 10L);

        //checking compatibility between permissions and displayed team
        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
            Team team = player.getScoreboard().getEntryTeam(playerName);
            if (team == null) return;
            if (player.hasPermission("claimworld.vip") || player.hasPermission("claimworld.mod")) return;
            team.removeEntry(playerName);
        }, 20L);

        //setlist + skin update
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
            player.setPlayerListHeader(colorize("\n&a❤ Claim&fWorld&a.net\n"));
            BattlePassManager.getInstance().updateTablistFooter(player);

            dispatchCommand(getConsoleSender(), "skin update " + player.getName());
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
