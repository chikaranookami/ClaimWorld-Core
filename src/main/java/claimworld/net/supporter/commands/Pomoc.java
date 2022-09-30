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
                pages.add("Komendy 5");
                pages.add("Komendy 6");
                pages.add("Komendy 7");

                bookMeta.setPages(pages);
                bookMeta.setPage(1, "Informacje\n\nW tym poradniku znajdziesz:§8\n- Komendy\n- Zmiany w rozgrywce\n\n Masz pytania? Smialo, kieruj je na Discorda.");
                bookMeta.setPage(2, "Komendy gracza§8\n/lwc - zabawy z zabezpieczeniami skrzynek\n/msg - prywatna wiadomosc");
                bookMeta.setPage(3, "Dodatkowe komendy VIP§8\n/playtime - czas online\n/me - opis postaci (RP)\n/skin - zmien swojego skina\n/hat - zaloz na glowe trzymany blok");
                bookMeta.setPage(4, "Dodatkowe komendy VIP+§8\n/sit, /lay, /crawl, /spin, /bellyflop - animacje postaci\n\nVIP+ ma rowniez wszystkie komendy VIP.");
                bookMeta.setPage(5, "Dodatkowe komendy MVP+§8\n/pp - konsola efektow\n/fw - fajerwerk pod nogami\n\nMVP ma rowniez wszystkie komendy VIP+ i VIP.");
                bookMeta.setPage(6, "Wazniejsze zmiany w rozgrywce§8\n- Skrzynie blokuja sie po postawieniu\n- Wylaczono mocne maszyny z redstone\n- Brodawki wypadaja z Zombie Piglinow");
                bookMeta.setPage(7, "§8- Slabsze moby ze spawnerow\n- System lokacji\n- Dodatkowi Handlarze\n- Niestandardowe przedmioty, w tym niestandardowa waluta\n- Globalne wzmocnienia\n- Nowe mechaniki\n- Nieco inny spawn mobow");
                bookMeta.setPage(8, "§8Projekt §0Claim World§8 stawia na dlugoterminowosc i z czasem zmian bedzie wiecej, jednak naszym celem jest utrzymanie rozgrywki w klimatach klasycznego Minecrafta tak bardzo, jak tylko sie da.");
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
