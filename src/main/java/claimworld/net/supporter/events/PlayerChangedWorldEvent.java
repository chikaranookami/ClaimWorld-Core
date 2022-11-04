package claimworld.net.supporter.events;

import claimworld.net.supporter.utils.MessageUtils;
import claimworld.net.supporter.utils.guis.BonusManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerChangedWorldEvent implements Listener {

    BonusManager bonusManager = BonusManager.getInstance();

    @EventHandler
    public void playerChangedWorldEvent(org.bukkit.event.player.PlayerChangedWorldEvent event) {
        if (bonusManager.getBonuses().get("End")) return;

        Player player = event.getPlayer();

        if (!(player.getWorld().getEnvironment() == World.Environment.THE_END)) return;

        Bukkit.dispatchCommand(player, "spawn");

        player.sendMessage(MessageUtils.getUserPrefix() + "End niedostepny. Mozesz go wlaczyc w Sklepie Punktowym (/punkty).");
    }
}
