package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.tasks.TaskManager;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;

public class Zadania {

    private ItemStack getBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta bookMeta = (BookMeta)book.getItemMeta();

        assert bookMeta != null;
        bookMeta.setAuthor("Chikaraa");
        bookMeta.setTitle("Zadania");

        ArrayList<BaseComponent[]> pages = new ArrayList<>();
        pages.add(TaskManager.getInstance().getActiveTaskComponent());

        bookMeta.spigot().setPages(pages);
        book.setItemMeta(bookMeta);

        return book;
    }

    public Zadania() {
        new CommandBase("zadania", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                ((Player) sender).openBook(getBook());
                return true;
            }

            @Override
            public String getUsage() {
                return "/zadania";
            }
        }.setPermission("claimworld.player");
    }
}
