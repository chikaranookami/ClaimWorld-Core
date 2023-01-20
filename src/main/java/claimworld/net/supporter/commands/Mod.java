package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class Mod {

    private final List<String> helpMessages = new ArrayList<>();

    private ItemStack getBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta bookMeta = (BookMeta)book.getItemMeta();

        assert bookMeta != null;
        bookMeta.setAuthor("Chikaraa");
        bookMeta.setTitle("Pomoc");

        bookMeta.setPages(helpMessages);
        book.setItemMeta(bookMeta);
        return book;
    }

    public Mod() {
        helpMessages.add("§8Informacje\n\n§c+§8 Nie szukamy cheatow / oszustw na sile.\n\n§c+§8 Dzialamy dopiero, gdy ktos poprosi lub gdy zauwazymy cos przy okazji.\n\n§c+§8 Korzystamy z kanalu administracyjnego na dc.");
        helpMessages.add("§8Komendy\n\n§c+§8 Komendy macie dostepne pod slashem (/ na czacie i naciskajac TAB).\n\n\n§c+§8 Na razie nie do wszystkich macie dostep, ale z czasem zyskacie full permisje.");
        
        new CommandBase("mod", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                ((Player) sender).openBook(getBook());
                return true;
            }

            @Override
            public String getUsage() {
                return "/mod";
            }
        }.setPermission("claimworld.mod");
    }
}
