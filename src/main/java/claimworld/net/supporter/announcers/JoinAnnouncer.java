package claimworld.net.supporter.announcers;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.GeyserUtils;
import claimworld.net.supporter.utils.BonusManager;
import claimworld.net.supporter.tasks.TaskManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.bukkit.Bukkit.getScheduler;

public class JoinAnnouncer {

    private final GeyserUtils geyserUtils = new GeyserUtils();

    private final List<Sound> sounds = Collections.singletonList(Sound.ENTITY_PILLAGER_CELEBRATE);

    public String getAdminNote() {
        return "§8Zaczynamy powoli zmiany ekonomiczne, a pozniej przyjdzie czas na kolejne poziomy przepustki!";
    }

    public String getActiveBonuses() {
        List<String> keys = new ArrayList<>();

        for (Map.Entry<String, Boolean> entry : BonusManager.getInstance().getBonuses().entrySet()) {
            if (entry.getValue()) keys.add("\n§c-§8" + entry.getKey());
        }

        return String.join(" ", keys);
    }

    private ItemStack getBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        assert bookMeta != null;

        BaseComponent[] pageComponent = new ComponentBuilder()
                .append("§cNotka od admina\n")
                .append(getAdminNote())
                .create();
        bookMeta.spigot().addPage(pageComponent);

        bookMeta.spigot().addPage(TaskManager.getInstance().getActiveTaskComponent());

        pageComponent = new ComponentBuilder()
                .append("§cAktywne Bonusy")
                .append(getActiveBonuses())
                .create();
        bookMeta.spigot().addPage(pageComponent);

        bookMeta.setTitle("Witaj na CW!");
        bookMeta.setAuthor("Chikaraa");
        book.setItemMeta(bookMeta);

        return book;
    }

    public void render(Player player) {
        if (geyserUtils.isPlayerFromGeyser(player.getUniqueId())) {
            player.sendMessage("Notka od admina");
            player.sendMessage(getAdminNote());

            player.spigot().sendMessage(TaskManager.getInstance().getActiveTaskComponent());

            player.sendMessage("Aktywne Bonusy");
            player.sendMessage(getActiveBonuses());
            return;
        }

        player.openBook(getBook());

        AtomicInteger i = new AtomicInteger();
        getScheduler().scheduleSyncRepeatingTask(Supporter.getPlugin(), () -> {
            if (i.get() >= sounds.size()) {
                return;
            }

            player.playSound(player, sounds.get(i.get()), 1.0f, 1.0f);

            i.getAndIncrement();
        }, 4L, 10L);
    }
}
