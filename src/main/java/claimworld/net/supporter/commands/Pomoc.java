package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
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
                pages.add("Komendy 8");
                pages.add("Komendy 9");
                pages.add("Komendy 10");
                pages.add("Komendy 11");

                bookMeta.setPages(pages);
                bookMeta.setPage(1, "§8Informacje\n\nW tym poradniku znajdziesz m.in:§c\n- Komendy\n- Zmiany w rozgrywce\n\n §8Masz pytania? Smialo, kieruj je na Discorda.");
                bookMeta.setPage(2, "§8Komendy\n\n§c/msg§8 - pw\n§c/ogloszenia§8 - bonusy, zadania, inne\n§c/cdefault <nick>§8 - dostep do wszystkich stawianych blokow\n§c/cmodify <nick>§8 - dostep do pojedynczego bloku");
                bookMeta.setPage(3, "§8Komendy VIP\n\n§c/playtime§8 - czas online\n§c/me§8 - opis postaci (RP)\n§c/skin§8 - zmien swojego skina\n§c/hat§8 - zaloz na glowe trzymany blok");
                bookMeta.setPage(4, "§8Komendy VIP+\n\n§c/sit§8,§c /lay§8,§c /crawl§8, §c/spin§8, §c/bellyflop§8 - animacje postaci\n\nVIP+ ma rowniez wszystkie komendy VIP.");
                bookMeta.setPage(5, "§8Komendy MVP+\n\n§c/pp§8 - konsola efektow\n§c/fw§8 - fajerwerk pod nogami\n§c/seriafw§8 - seria fajerwerkow w niebo\n\nMVP ma rowniez wszystkie komendy VIP+ i VIP.");
                bookMeta.setPage(6, "§8Zmiany w rozgrywce\n\n§c+§8 Skrzynie blokuja sie po postawieniu\n§c+§8 Limit sily na redstone\n§c+§8 Brodawki z Piglinow\n§c+§8 Moby bez AI ze spawnerow\n§c+§8 Wiekszosc bytow mozna podnosic przy pomocy SHIFT+PPM\n§c+§8 Dodatkowi Handlarze");
                bookMeta.setPage(7, "§c+§8 System teleportacji\n§c+§8 Niestandardowe przedmioty\n§c+§8 Globalne wzmocnienia\n§c+§8 Nowe mechaniki\n§c+§8 Inny spawn mobow, leje\n§c+§8 Limit bytow na chunk\n§c+§8 Niektore przedmioty znikaja szybciej\n§c+§8 Battlepass, Zadania\n§c+§8 Umiejetnosci");
                bookMeta.setPage(8, "§8Odgorne zezwolenia na wykorzystywanie bledow\n\n§c+§8 Wychodzenie na sufit w netherze\n\n\nWykorzystywanie bledow, ktore nie daja przewagi w rozgrywce §njest dozwolone§8.");
                bookMeta.setPage(9, "§8Zarezerwowane wzory sztandardow\n\n§c+§8 Wszystkie zajete wzory sztandardow dostepne sa do wgladu w specjalnym miejscu obok spawnu (patrz mapa).");
                bookMeta.setPage(10, "§8Progi zakupow w sklepie\n\n§c+§8 Za osiagniecie okreslonego progu przewidziane sa specjalne bonusy, o ktorych gracze zostana poinformowani po osiagnieciu wybranego celu.");
                bookMeta.setPage(11, "§8Farmy\n\n§c+§8 Zloto [-128 253 -84 Nether]\n§c+§8 Blazy [-334 72 -3 Nether]\n§c+§8 Owce [656 -703 Overworld]\n§c+§8 Kaktusy [409 289 Overworld]\n§c+§8 Trzcina [1096 -185 Overworld]\n§c+§8 Wiesniaki [310 -1979 Overworld]");
                bookMeta.setPage(12, "§8Projekt §cClaim World§8 stawia na dlugoterminowosc.\n\nW przyszlosci zmian bedzie wiecej, jednak naszym celem jest utrzymanie rozgrywki w klimatach klasycznego Minecrafta tak bardzo, jak tylko sie da.");
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
