package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.GlobalUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static org.bukkit.Bukkit.*;

public class GlobalFreeChest {

    GlobalUtils globalUtils = GlobalUtils.getInstance();

    public GlobalFreeChest() {
        new CommandBase("globalfreechest", false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {

                    List<String> playersWithFreeChest = globalUtils.getPlayersWithFreeChest();

                    for (Player player : getOnlinePlayers()) {
                        String playerName = player.getName();
                        if (playersWithFreeChest.contains(playerName)) continue;

                        globalUtils.addFreeChest(player);
                    }
                });
                return true;
            }

            @Override
            public String getUsage() {
                return "/globalfreechest";
            }
        }.setPermission("claimworld.admin");
    }
}
