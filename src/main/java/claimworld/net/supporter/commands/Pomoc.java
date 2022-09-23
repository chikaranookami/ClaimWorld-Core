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
                bookMeta.setPage(1, "Informacje§8\n- Zasady oraz wytyczne znajduja sie na Discordzie\n- Komendy znajduja sie na kolejnej stronie");
                bookMeta.setPage(2, "Podstawowe Komendy§8\n/lwc - zabezpieczenia skrzynek\n/msg - prywatna wiadomosc");
                bookMeta.setPage(3, "Dodatkowe komendy VIP§8\n/playtime - czas online\n/me - opis postaci (RP)\n/skin - zmien swojego skina\n/hat - zaloz na glowe trzymany blok");
                bookMeta.setPage(4, "Dodatkowe komendy VIP+§8\n/sit, /lay, /crawl, /spin, /bellyflop - animacje\n\nVIP+ ma rowniez wszystkie komendy VIP.");
                bookMeta.setPage(5, "Dodatkowe komendy MVP+§8\n/pp - konsola efektow\n/fw - fajerwerk\n\nMVP ma rowniez wszystkie komendy VIP+ i VIP.");
                book.setItemMeta(bookMeta);

                Player player = (Player) sender;
                player.openBook(book);
                return true;
            }

            @Override
            public String getUsage() {
                return "/pomoc";
            }
        }.setPermission("claimworld.player");
    }
}
