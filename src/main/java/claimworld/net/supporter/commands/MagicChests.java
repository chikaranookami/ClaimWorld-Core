package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MagicChests {

    private int amount;

    public MagicChests() {
        new CommandBase("magicchests", 2, 3) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                String action = arguments[0];
                Player player = Bukkit.getPlayer(arguments[1]);

                if (player == null) return false;

                if (action.equals("give")) {
                    if (!arguments[2].isEmpty()) amount = Integer.parseInt(arguments[2]);
                    if (amount < 1) amount = 1;

                    ItemStack item = new claimworld.net.supporter.utils.MagicChests().getItem(amount);

                    if (player.getInventory().firstEmpty() == -1) {
                        assert player.getLocation().getWorld() != null;
                        player.getLocation().getWorld().dropItem(player.getLocation(), item);
                    } else {
                        player.getInventory().addItem(item);
                    }

                    return true;
                }

                return false;
            }

            @Override
            public String getUsage() {
                return "/magicchests <action> <nickname> <amount>";
            }
        }.setPermission("claimworld.admin");
    }
}
