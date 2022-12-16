package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.GeyserUtils;
import claimworld.net.supporter.utils.GlobalUtils;
import claimworld.net.supporter.utils.announcers.JoinAnnouncer;
import claimworld.net.supporter.utils.BonusManager;
import claimworld.net.supporter.utils.items.Locker;
import claimworld.net.supporter.utils.items.ReadyItems;
import claimworld.net.supporter.utils.battlepass.BattlePassManager;
import claimworld.net.supporter.utils.AttributesManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.List;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getScheduler;

public class PlayerJoinEvent implements Listener {

    BonusManager bonusManager = BonusManager.getInstance();
    GlobalUtils globalUtils = GlobalUtils.getInstance();
    HashMap<String, List<ItemStack>> storedItems = Locker.getInstance().getLockerMap();

    private final GeyserUtils geyserUtils = new GeyserUtils();
    private final AttributesManager attributesManager = new AttributesManager();

    @EventHandler
    public void joinEvent(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        player.setDisplayName(playerName);

        //set menu item
        player.getInventory().setItem(17, ReadyItems.getInstance().get("Menu"));

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

        //enable at 6, 24, 25, 26 and 31 of december
        //getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
            //player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 432000, 1, true, false, true));
            //player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 432000, 1, true, false, true));
        //}, 10L);

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

        //check for any stored items
        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
            if (storedItems.get(playerName) == null) return;
            if (storedItems.get(playerName).size() < 1) return;

            player.sendMessage(getUserPrefix() + "W Twojej Skrytce cos jest. Odbierz to, zanim zniknie!");
        }, 40L);

        //manage global free chest
        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
            if (!bonusManager.getBonuses().get("Skrzynka")) return;

            List<String> playersWithFreeChest = globalUtils.getPlayersWithFreeChest();
            if (playersWithFreeChest.contains(playerName)) return;

            globalUtils.addFreeChest(player);
        }, 100L);

        getScheduler().runTaskLater(Supporter.getPlugin(), () -> attributesManager.tryUpdateStats(player), 300L);
    }
}
