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
                bookMeta.setPage(1, "Informacje\n\nW tym poradniku znajdziesz:§1\n- Komendy\n- Zmiany w rozgrywce\n\n §0Masz pytania? Smialo, kieruj je na Discorda.");
                bookMeta.setPage(2, "Komendy gracza\n§1/lwc§0 - zabawy z zabezpieczeniami skrzynek\n§1/msg§0 - prywatna wiadomosc");
                bookMeta.setPage(3, "Dodatkowe komendy VIP\n§1/playtime§0 - czas online\n§1/me§0 - opis postaci (RP)\n§1/skin§0 - zmien swojego skina\n§1/hat§0 - zaloz na glowe trzymany blok");
                bookMeta.setPage(4, "Dodatkowe komendy VIP+\n§1/sit§0,§1 /lay§0,§1 /crawl§0, §1/spin§0, §1/bellyflop§0 - animacje postaci\n\nVIP+ ma rowniez wszystkie komendy VIP.");
                bookMeta.setPage(5, "Dodatkowe komendy MVP+\n§1/pp§0 - konsola efektow\n§1/fw§0 - fajerwerk pod nogami\n\nMVP ma rowniez wszystkie komendy VIP+ i VIP.");
                bookMeta.setPage(6, "Wazniejsze zmiany w rozgrywce\n§1+§0 Skrzynie blokuja sie po postawieniu\n§1+§0 Wylaczono mocne maszyny z redstone\n§1+§0 Brodawki wypadaja z Zombie Piglinow\n§1+§0 Slabsze moby ze spawnerow");
                bookMeta.setPage(7, "§1+§0 System lokacji\n§1+§0 Dodatkowi Handlarze\n§1+§0 Niestandardowe przedmioty, w tym niestandardowa waluta\n§1+§0 Globalne wzmocnienia\n§1+§0 Nowe mechaniki\n§1+§0 Nieco inny spawn mobow\n§1+§0Wolniejsze, przerzucajace po 4 sztuki przedmiotow leje");
                bookMeta.setPage(8, "Projekt §1Claim World§0 stawia na dlugoterminowosc i z czasem zmian bedzie wiecej, jednak naszym celem jest utrzymanie rozgrywki w klimatach klasycznego Minecrafta tak bardzo, jak tylko sie da.");
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
