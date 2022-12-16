package claimworld.net.supporter.utils;

import claimworld.net.supporter.Supporter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import static claimworld.net.supporter.utils.MessageUtils.getAttributeIcon;
import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static org.bukkit.Bukkit.getScheduler;

public class AttributesManager {

    private final GeyserUtils geyserUtils = new GeyserUtils();

    public static final String attributesObjectiveName = "atrybuty";
    public static final String hpAttributeObjectiveName = "atrybutHp";
    public static final String damageAttributeObjectiveName = "atrybutDamage";

    private BaseComponent[] getComponent(Scoreboard scoreboard  , String playerName) {
      return new ComponentBuilder()
                .append("§cWybierz atrybut " + getAttributeIcon() + "\n")
                .append("§8Masz wolne punkty atrybutow do rozdania.\n\n")
                .append("§c§n> §8§oZdrowie (obecnie " + scoreboard.getObjective(hpAttributeObjectiveName).getScore(playerName).getScore() + " pkt).")
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Zwieksza Twoje zdrowie o 1 punkt.")))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/updateattribute " + hpAttributeObjectiveName))
                .append("\n\n")
                .append("§c§n> §8§oObrazenia (obecnie " + scoreboard.getObjective(damageAttributeObjectiveName).getScore(playerName).getScore() + " pkt).")
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Zwieksza Twoje obrazenia o 1 punkt")))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/updateattribute " + damageAttributeObjectiveName))
                .create();
    }

    private ItemStack getBook(Player player) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        assert bookMeta != null;

        bookMeta.spigot().addPage(getComponent(player.getScoreboard(), player.getName()));
        bookMeta.setTitle("Wybierz atrybut");
        bookMeta.setAuthor("Chikaraa");

        book.setItemMeta(bookMeta);

        return book;
    }

    public void updatePlayerHealth(Player player) {
        Objective objective = player.getScoreboard().getObjective(hpAttributeObjectiveName);
        if (objective == null) return;

        int amplifier = objective.getScore(player.getName()).getScore();

        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
            if (amplifier != 0) player.setHealthScale(20 + amplifier * 2);
        }, 1L);
    }

    public double getAdditionalDamage(Player player) {
        return player.getScoreboard().getObjective(damageAttributeObjectiveName).getScore(player.getName()).getScore();
    }

    public boolean tryUpdateStats(Player player) {
        int score = player.getScoreboard().getObjective(attributesObjectiveName).getScore(player.getName()).getScore();
        if (!(score > 0)) return false;

        if (geyserUtils.isPlayerFromGeyser(player.getUniqueId())) {
            player.spigot().sendMessage(getComponent(player.getScoreboard(), player.getName()));
        } else {
            player.openBook(getBook(player));
            player.sendMessage(getUserPrefix() + "Mozesz otworzyc te ksiazke w dowolnej chwili na spawnie.");
        }

        return true;
    }
}
