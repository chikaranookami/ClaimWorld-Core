package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;

public class Pomoc {

    public Pomoc() {
        new CommandBase("pomoc", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
                BookMeta bookMeta = (BookMeta)book.getItemMeta();

                assert bookMeta != null;
                bookMeta.setAuthor("Chikaraa");
                bookMeta.setTitle("Pomoc");

                ArrayList<String> pages = new ArrayList<>();
                pages.add("Informacje");
                pages.add("Komendy 1");
                pages.add("Komendy 2");
                pages.add("Komendy 3");
                pages.add("Komendy 4");

                bookMeta.setPages(pages);
                bookMeta.setPage(1, "§3§lInformacje§r\n- Zasady oraz wytyczne znajduja sie na Discordzie\n- Komendy znajduja sie na kolejnej stronie");
                bookMeta.setPage(2, "§3§lPodstawowe Komendy§r\n/lwc - obsluga zabezpieczen\n/vote, /punkty - glosowanie i punkty\n/home, /sethome, /spawn - obsluga lokacji\n/msg - prywatna wiadomosc\n/warp lobby - lobby\n/voicechat - czat glosowy");
                bookMeta.setPage(3, "§3§lDodatkowe komendy VIP§r\n/playtime - czas online\n/me - opis postaci (RP)\n/skin - zmien swojego skina\n/hat - zaloz na glowe trzymany blok");
                bookMeta.setPage(4, "§3§lDodatkowe komendy VIP+§r\n/sit, /lay, /crawl, /spin, /bellyflop - animacje\n\nVIP+ ma rowniez wszystkie komendy VIP.");
                bookMeta.setPage(5, "§3§lDodatkowe komendy MVP+§r\n/pp - konsola efektow\n/fw - fajerwerk\n\nMVP ma rowniez wszystkie komendy VIP+ i VIP.");
                book.setItemMeta(bookMeta);

                Player player = (Player) sender;
                player.openBook(book);

                TextComponent message = new TextComponent(ChatColor.GREEN + "-> Kliknij tutaj, by przejsc do sklepu.");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://shop.claimworld.net"));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Dolacz do Hall Of Fame!")));
                player.spigot().sendMessage(message);
                return true;
            }

            @Override
            public String getUsage() {
                return "/pomoc";
            }
        }.setPermission("claimworld.player");
    }
}
