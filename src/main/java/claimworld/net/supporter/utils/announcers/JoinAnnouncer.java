package claimworld.net.supporter.utils.announcers;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.RankUtils;
import claimworld.net.supporter.utils.guis.BonusManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getScheduler;

public class JoinAnnouncer {

    private final List<Sound> sounds = Collections.singletonList(Sound.ENTITY_PILLAGER_CELEBRATE);

    private String getActiveBonuses() {
        List<String> keys = new ArrayList<>();

        for (Map.Entry<String, Boolean> entry : BonusManager.getInstance().getBonuses().entrySet()) {
            if (entry.getValue()) keys.add("§c-§0" + entry.getKey());
        }

        return String.join(" ", keys);
    }

    private ItemStack getBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        BaseComponent[] shopComponent = new ComponentBuilder()
                .append("§cOstatnie Zmiany\n")
                .append("§0Glownie poprawki i optymalizacje.")
                .append("\n\n§cAktywne Bonusy\n")
                .append(getActiveBonuses())
                .create();

        assert bookMeta != null;
        bookMeta.spigot().addPage(shopComponent);

        bookMeta.setTitle("Witaj na CW!");
        bookMeta.setAuthor("Chikaraa");
        book.setItemMeta(bookMeta);

        return book;
    }

    public JoinAnnouncer(Player player) {
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
