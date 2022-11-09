package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.announcers.JoinAnnouncer;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;

public class Ogloszenia {

    private ItemStack getBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta bookMeta = (BookMeta)book.getItemMeta();

        assert bookMeta != null;
        bookMeta.setAuthor("Chikaraa");
        bookMeta.setTitle("Ogloszenia");

        ArrayList<String> pages = new ArrayList<>();
        pages.add("§c§lOkres Halloween\n\n§c§l- §rWiekszy drop ze szkieletow, zombie i witherowych szkieletow\n§c§l- §rTylko Emeraldowy i Fantomowy Handlarz z dodatkowymi ofertami\n§c§l- §rWieczory z Globalnymi Boosterami!\n§c§l- §rHalloweenowe dekoracje\n§c§l- §rNiespodzianka!");

        bookMeta.setPages(pages);
        book.setItemMeta(bookMeta);

        return book;
    }

    public Ogloszenia() {
        new CommandBase("ogloszenia", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                new JoinAnnouncer((Player) sender);
                return true;
            }

            @Override
            public String getUsage() {
                return "/ogloszenia";
            }
        }.setPermission("claimworld.player");
    }
}
