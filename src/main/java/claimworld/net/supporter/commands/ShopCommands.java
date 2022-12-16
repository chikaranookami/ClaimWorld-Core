package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.GeyserUtils;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;

public class ShopCommands {

    private final GeyserUtils geyserUtils = new GeyserUtils();

    private final String shopLink = "https://shop.claimworld.net/";

    private ItemStack getBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        String vipLink = "https://claimworld.net/sklep";

        BaseComponent[] shopComponent = new ComponentBuilder()
                .append("§cSklep\n")
                .append("§8§o> Kliknij tutaj")
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, shopLink))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(shopLink)))
                .append("\n\n§cWiecej o rangach\n")
                .append("§8§o> Kliknij tutaj")
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
                if (geyserUtils.isPlayerFromGeyser(player.getUniqueId())) {
                    player.sendMessage(getUserPrefix() + "Adres sklepu: " + shopLink);
                } else {
                    player.openBook(getBook());
                }
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
