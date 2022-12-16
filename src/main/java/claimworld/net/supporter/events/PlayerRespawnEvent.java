package claimworld.net.supporter.events;

import claimworld.net.supporter.utils.items.ReadyItems;
import claimworld.net.supporter.utils.AttributesManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerRespawnEvent implements Listener {

    private final AttributesManager attributesManager = new AttributesManager();

    @EventHandler
    public void playerRespawnEvent(org.bukkit.event.player.PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        player.getInventory().setItem(17, ReadyItems.getInstance().get("Menu"));
        attributesManager.updatePlayerHealth(player);

        //enable at 6, 24, 25, 26 and 31 of december
        //player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 432000, 1, true, false, true));
        //player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 432000, 1, true, false, true));
    }
}
