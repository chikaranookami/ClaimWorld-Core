package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class ShopCommands {
    private ItemStack getBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        String shopLink = "https://shop.claimworld.net/";
        String vipLink = "https://claimworld.net/sklep";

        BaseComponent[] shopComponent = new ComponentBuilder()
                .append("\n§1Sklep\n")
                .append("§0§o> Kliknij tutaj")
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, shopLink))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(shopLink)))
                .append("\n\n\n§1Rangi Premium\n")
                .append("§0§o> Kliknij tutaj")
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, vipLink))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(vipLink)))
                .create();

        assert bookMeta != null;
        bookMeta.spigot().addPage(shopComponent);

        bookMeta.setTitle("ClaimShop");
        bookMeta.setAuthor("Chikaraa");
        book.setItemMeta(bookMeta);

        return book;
    }

    public ShopCommands() {
        new CommandBase("vip", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = (Player) sender;
                player.openBook(getBook());
                return true;
            }

            @Override
            public String getUsage() {
                return "/vip";
            }
        }.setPermission("claimworld.player");

        new CommandBase("sklep", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = (Player) sender;
                player.openBook(getBook());
                return true;
            }

            @Override
            public String getUsage() {
                return "/sklep";
            }
        }.setPermission("claimworld.player");
    }
}
