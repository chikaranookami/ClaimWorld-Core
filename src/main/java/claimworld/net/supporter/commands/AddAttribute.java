package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static claimworld.net.supporter.utils.battlepass.BattlePassManager.attributesObjectiveName;
import static org.bukkit.Bukkit.*;

public class AddAttribute {
    public AddAttribute() {
        new CommandBase("addattribute", 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = getPlayer(arguments[0]);
                if (player == null) return false;

                dispatchCommand(getConsoleSender(), "scoreboard players add " + player.getName() + " " + attributesObjectiveName + " 1");
                dispatchCommand(getConsoleSender(), "privateMessage " + player.getName() + " Otrzymales Atrybut. Za jego pomoca juz niebawem bedziesz w stanie rozwinac swoja postac.");
                return true;
            }

            @Override
            public String getUsage() {
                return "/addattribute <nick>";
            }
        }.setPermission("claimworld.admin");
    }
}
