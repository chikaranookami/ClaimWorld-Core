package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.ErrorMessages;
import claimworld.net.supporter.utils.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {

    private final Ranks ranks = new Ranks();

    @EventHandler
    public void joinEvent(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //set tablist
        player.setPlayerListHeader("\n " + ChatColor.AQUA + "ClaimWorld" + ChatColor.WHITE + ".net" + " \n");
        player.setPlayerListFooter(ChatColor.AQUA + "\n Event Rzut Owca \n" + ChatColor.WHITE + " Data: " + ChatColor.AQUA + "03.09.2022 " + ChatColor.AQUA + "\n\n Duze Powiekszenie Mapy \n" + ChatColor.WHITE + " Data: " + ChatColor.AQUA + "09.09.2022 \n");

        //set ranks
        Bukkit.getScheduler().runTaskLaterAsynchronously(Supporter.getInstance(), () ->{
            String newPlayerListName = ranks.getDesign(player);

            if (newPlayerListName == null) {
                ErrorMessages errorMessages = new ErrorMessages();
                player.sendMessage(errorMessages.getUserResponse(0));
                Bukkit.getConsoleSender().sendMessage(errorMessages.getByCode(0));
                return;
            }

            player.setPlayerListName(ranks.getDesign(player));
            player.setDisplayName(ranks.getDesign(player));
        }, 4L);

        //end available?
        if (Supporter.ToggleEnd) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "End jest obecnie wlaczony. Korzystaj, poki mozesz!");
            return;
        }

        //end location fix
        if (player.getWorld().getEnvironment() == World.Environment.THE_END) {
            if (player.getLocation().getY() < -128) {
                player.setHealth(0);
                player.sendMessage(ChatColor.GRAY + "End jest obecnie wylaczony, a Ty byles gleboko w voidzie, wiec zginales.");
                return;
            }

            Bukkit.dispatchCommand(player, "spawn");
            player.sendMessage(ChatColor.GRAY + "End jest obecnie wylaczony. Przeteleportowano na spawn.");
        }
    }
}
