package claimworld.net.supporter.utils.battlepass;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static claimworld.net.supporter.utils.battlepass.BattlePassManager.mainObjectiveName;

public class SkillManager {
    private final HashMap<String, Skill> skillMap = new HashMap<>();

    public HashMap<String, Skill> getSkillMap() {
        return skillMap;
    }

    public ItemStack getSkillBook(Player player) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        assert bookMeta != null;

        ComponentBuilder builder;
        builder = new ComponentBuilder().append("§cUmiejetnosci\n").append("§8Najedz na umiejetnosc, by wyswietlic szczegoly.\n");

        for (Map.Entry<String, Skill> entry : skillMap.entrySet()) {
            if (canActivateSkill(player, entry.getKey())) {
                builder.append("\n§2§o+§8§o" + entry.getKey()).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(entry.getValue().getHelp())));
            } else {
                builder.append("\n§c§o-§8§o" + entry.getKey()).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(entry.getValue().getHelp())));
            }
        }

        bookMeta.spigot().setPages(builder.create());
        bookMeta.setTitle("Umiejetnosci");
        bookMeta.setAuthor("Chikaraa");
        book.setItemMeta(bookMeta);

        return book;
    }

    public void renderSkillEffect(Location location) {
        World world = location.getWorld();
        assert world != null;
        world.spawnParticle(Particle.SCULK_SOUL, location.add(0, 1, 0), 15);
        world.playSound(location, Sound.ENTITY_SHULKER_BULLET_HIT, 0.75f, 0.75f);
    }

    public boolean canActivateSkill(Player player, String skillName) {
        return player.getScoreboard().getObjective(mainObjectiveName).getScore(player.getName()).getScore() >= skillMap.get(skillName).getRequiredLevel();
    }

    public SkillManager() {
        List<Skill> skillList = new ArrayList<>();
        skillList.add(new Skill("Punkty bywalca", 30, "Szansa na dodatkowe punkty do Sklepu Punktowego przy wejsciu na serwer."));
        skillList.add(new Skill("Mob Killer", 60, "Szansa na zadanie podwojnych obrazen niemal wszystkim bytom."));
        skillList.add(new Skill("Twoj spawner", 90, "Mozliwosc aktualizacji moba w spawnerze za pomoca jaja spawnujacego."));

        for (Skill skill : skillList) {
            skillMap.put(skill.getName(), skill);
        }
    }
}
