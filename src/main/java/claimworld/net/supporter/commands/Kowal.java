package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.items.ReadyItems;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static org.bukkit.Bukkit.getScheduler;

public class Kowal {

    private final List<ItemStack> lockedItems = new ArrayList<>();

    private ItemStack getBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        String enchantText = "Niebawem bede mial taka mozliwosc.";
        String doubleText = "Wez do glownej reki przedmiot, ktory chcesz ulepszyc, a do drugiej Uniwersalny Bilet. Ulepszam wiekszosc niestandardowych przedmiotow.";

        BaseComponent[] shopComponent = new ComponentBuilder()
                .append("§cKowal\n\n")
                .append("§8§o> Chce wzmocnic swoje ulepszenie.")
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(enchantText)))
                .append("\n\n")
                .append("§8§n>§8§o Chce podwoic swoj przedmiot.")
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(doubleText)))
                .create();

        assert bookMeta != null;
        bookMeta.spigot().addPage(shopComponent);

        bookMeta.setTitle("Kowal");
        bookMeta.setAuthor("Chikaraa");
        book.setItemMeta(bookMeta);

        return book;
    }

    public Kowal() {
        ReadyItems items = new ReadyItems();
        lockedItems.add(items.get("$1"));
        lockedItems.add(items.get("Uniwersalny_bilet"));

        new CommandBase("kowal", 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                if (player == null) return false;

                PlayerInventory inventory = player.getInventory();
                ItemStack item = inventory.getItemInMainHand();
                ItemStack offItem = inventory.getItemInOffHand();

                if (item.getType().isAir() || item.getItemMeta() == null || item.getItemMeta().getLore() == null || item.getItemMeta().getLore().isEmpty() || lockedItems.contains(item) || offItem.getItemMeta() == null || (!(offItem.getItemMeta().getCustomModelData() == 12))) {
                    player.openBook(getBook());
                    return true;
                }

                inventory.getItemInOffHand().setAmount(inventory.getItemInOffHand().getAmount() - 1);
                inventory.getItemInMainHand().setAmount(item.getAmount() - 1);

                int chance = new Random().nextInt(10);
                World world = player.getWorld();
                Location location = player.getLocation();

                world.playSound(location, Sound.BLOCK_ANVIL_USE, 0.75f, 0.75f);

                getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                    if (chance > 0) {
                        world.playSound(location, Sound.BLOCK_ANVIL_DESTROY, 0.75f, 0.75f);
                        player.sendMessage(getUserPrefix() + "Nie udalo sie - Kowal spalil Twoj przedmiot.");
                    } else {
                        world.dropItemNaturally(player.getLocation(), item);
                        world.dropItemNaturally(player.getLocation(), item);
                        world.playSound(location, Sound.BLOCK_ANVIL_PLACE, 0.75f, 0.75f);
                        player.sendMessage(getUserPrefix() + "Sukces! Kowal podwoil Twoj przedmiot.");

                        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
                            TaskManager.getInstance().tryFinishTask(player, new Task("Podwoj przedmiot.", "", 0));
                        });
                    }
                }, 30L);

                return true;
            }

            @Override
            public String getUsage() {
                return "/kowal <player>";
            }
        }.setPermission("claimworld.admin");
    }
}
