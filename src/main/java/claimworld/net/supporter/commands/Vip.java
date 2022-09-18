package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

public class Vip {
    public Vip() {
        new CommandBase("vip", false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                TextComponent component = new TextComponent();
                component.setText(Messages.getUserPrefix() + "Kliknij tutaj, aby uzyskac wiecej informacji.");
                component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://claimworld.net/sklep"));
                sender.spigot().sendMessage(component);
                return true;
            }

            @Override
            public String getUsage() {
                return "/vip";
            }
        }.setPermission("claimworld.player");
    }
}
