package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.PrivateChestsUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;

public class Protection {

    PrivateChestsUtils privateChestsUtils = PrivateChestsUtils.getInstance();

    public Protection() {
        new CommandBase("dajdostep", 1, true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player owner = (Player) sender;
                Player player = Bukkit.getPlayer(arguments[0]);
                if (player == null) {
                    owner.sendMessage(getUserPrefix() + getUsage());
                    return true;
                }

                Block block = owner.getTargetBlock(null, 10);
                if (block.getType() != Material.CHEST) {
                    owner.sendMessage(getUserPrefix() + "Musisz patrzec na wybrana skrzynie.");
                    return true;
                }

                privateChestsUtils.updateState(owner, ((TileState) block.getState()), "addToProtection", player.getUniqueId());
                return true;
            }

            @Override
            public String getUsage() {
                return "/dajdostep <nick>";
            }
        }.setPermission("claimworld.mod");

        new CommandBase("usundostep", 1, true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player owner = (Player) sender;
                Player player = Bukkit.getPlayer(arguments[0]);
                if (player == null) {
                    owner.sendMessage(getUserPrefix() + getUsage());
                    return true;
                }

                Block block = owner.getTargetBlock(null, 10);
                if (block.getType() != Material.CHEST) {
                    owner.sendMessage(getUserPrefix() + "Musisz patrzec na wybrana skrzynie.");
                    return true;
                }

                privateChestsUtils.updateState(owner, ((TileState) block.getState()), "removeFromProtection", player.getUniqueId());
                return true;
            }

            @Override
            public String getUsage() {
                return "/dajdostep <nick>";
            }
        }.setPermission("claimworld.mod");
    }
}
