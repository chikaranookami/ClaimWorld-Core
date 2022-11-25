package claimworld.net.supporter.utils.announcers;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.guis.BonusManager;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static claimworld.net.supporter.utils.MessageUtils.getBattlepassIcon;
import static org.bukkit.Bukkit.getScheduler;

public class JoinAnnouncer {

    private final List<Sound> sounds = Collections.singletonList(Sound.ENTITY_PILLAGER_CELEBRATE);

    private String getActiveBonuses() {
        List<String> keys = new ArrayList<>();

        for (Map.Entry<String, Boolean> entry : BonusManager.getInstance().getBonuses().entrySet()) {
            if (entry.getValue()) keys.add("§c-§8" + entry.getKey() + "\n");
        }

        return String.join(" ", keys);
    }

    private ItemStack getBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        assert bookMeta != null;

        BaseComponent[] pageComponent = new ComponentBuilder()
                .append("§cNotka od admina\n")
                .append("§8Juz niebawem wracamy do standardowych mechanik, znanych Wam dobrze z vanilli. Wycisniemy z tej gry wszystko, co sie da!")
                .create();
        bookMeta.spigot().addPage(pageComponent);

        bookMeta.spigot().addPage(TaskManager.getInstance().getActiveTaskComponent());

        pageComponent = new ComponentBuilder()
                .append("§cAktywne Bonusy\n")
                .append(getActiveBonuses())
                .create();
        bookMeta.spigot().addPage(pageComponent);

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
