package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.Messages;
import claimworld.net.supporter.utils.Ranks;
import claimworld.net.supporter.utils.guis.Gui;
import claimworld.net.supporter.utils.guis.GuiManager;
import claimworld.net.supporter.utils.guis.ReadyItems;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Bukkit.getScheduler;

public class CwAdmin {
    public CwAdmin() {
        new CommandBase("cwadmin", 2, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                String action = arguments[1];

                if (player == null) {
                    sender.sendMessage("Player is null");
                    return true;
                }

                if (action.equals("openWarehouse")) {
                    new GuiManager(player, new Gui(null, 27, "Magazyn"));
                    return true;
                }

                if (action.equals("fillWarehouse")) {
                    getScheduler().runTaskLater(Supporter.getPlugin(), () -> {

                    }, 200L);
                    return true;
                }

                if (action.equals("spawnBall")) {
                    Chicken chicken = (Chicken) player.getWorld().spawnEntity(player.getLocation(), EntityType.CHICKEN);
                    chicken.setAware(false);
                    sender.sendMessage("ball has been spawned at " + player.getLocation());
                    return true;
                }

                if (action.equals("updateRank")) {
                    sender.sendMessage("Trying to update rank of " + player.getDisplayName());
                    getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                        new Ranks().updateRank(player);
                        sender.sendMessage("Rank has been updated for " + player.getDisplayName());
                    }, 40L);
                    return true;
                }

                if (action.equals("giveCustomItems")) {
                    assert player.getLocation().getWorld() != null;
                    ItemStack kupa = new ReadyItems().get("Kupa");
                    ItemStack dolar = new ReadyItems().get("$1");
                    ItemStack bilet = new ReadyItems().get("Uniwersalny bilet");
                    ItemStack ramka = new ReadyItems().get("Niewidzialna ramka");

                    player.getLocation().getWorld().dropItem(player.getLocation(), dolar);
                    player.getLocation().getWorld().dropItem(player.getLocation(), bilet);
                    player.getLocation().getWorld().dropItem(player.getLocation(), kupa);
                    player.getLocation().getWorld().dropItem(player.getLocation(), ramka);
                    return true;
                }

                return false;
            }

            @Override
            public String getUsage() {
                return "/cwadmin <nick> <action>";
            }
        }.setPermission("claimworld.mod");
    }
}
