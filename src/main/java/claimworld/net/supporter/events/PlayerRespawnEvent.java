package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.items.ReadyItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerRespawnEvent implements Listener {

    @EventHandler
    public void playerRespawnEvent(org.bukkit.event.player.PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        player.getInventory().setItem(17, ReadyItems.getInstance().get("Menu"));

        //enable at 6, 24, 25, 26 and 31 of december
        //player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 432000, 1, true, false, true));
        //player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 432000, 1, true, false, true));
    }
}
