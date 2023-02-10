package claimworld.net.supporter.items;

import claimworld.net.supporter.utils.ChestCounterUtils;
import claimworld.net.supporter.items.CustomHead;
import claimworld.net.supporter.items.CustomItem;
import claimworld.net.supporter.items.Locker;
import claimworld.net.supporter.items.ReadyItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.logging.Level;

import static claimworld.net.supporter.utils.AttributesManager.*;
import static claimworld.net.supporter.utils.MessageUtils.getAttributeIcon;
import static claimworld.net.supporter.utils.MessageUtils.getBattlepassIcon;
import static claimworld.net.supporter.utils.StringUtils.colorize;
import static claimworld.net.supporter.battlepass.BattlePassManager.mainObjectiveName;
import static org.bukkit.Bukkit.getLogger;

public class ItemSets {

    ReadyItems readyItems = ReadyItems.getInstance();
    
    private final HashMap<Integer, ItemStack> itemMap = new HashMap<>();

    private final ItemStack cofnij = new CustomItem("&fCofnij", Material.ARROW, Collections.singletonList(colorize("&7&oPoprzednie menu."))).getItem();

    public HashMap<Integer, ItemStack> initializeInventoryContent(Player player, String inventoryName) {
        if (inventoryName == null || inventoryName.isEmpty()) {
            getLogger().log(Level.INFO, "initializing error - inventoryName is empty or null");
            return null;
        }

        itemMap.clear();

        if (inventoryName.equals("Menu")) {
            String rankName = "";
            String playerName = player.getName();
            Scoreboard scoreboard = player.getScoreboard();
            Team team = player.getScoreboard().getEntryTeam(playerName);
            if (team == null) {
                rankName = colorize("Gracz");
            } else {
                rankName = team.getPrefix();
            }

            itemMap.put(10, new CustomHead("&aInformacje", player, 1, Arrays.asList(
                    colorize("&7Nick: &a" + playerName),
                    colorize("&7Ranga: " + rankName),
                    "",
                    colorize("&7Atrybut HP: &a" + scoreboard.getObjective(hpAttributeObjectiveName).getScore(playerName).getScore() + getAttributeIcon()),
                    colorize("&7Atrybut DMG: &a" + scoreboard.getObjective(damageAttributeObjectiveName).getScore(playerName).getScore() + getAttributeIcon()),
                    colorize("&7Przepustka: &a" + scoreboard.getObjective(mainObjectiveName).getScore(playerName).getScore() + getBattlepassIcon()),
                    "",
                    colorize("&7Otworzone Skrzynki: &a" + new ChestCounterUtils().get(player)),
                    "",
                    colorize("&7Ping: &a" + player.getPing() + "ms"),
                    "",
                    colorize("&7Dolaczono: "),
                    colorize("&a" + new Date(player.getFirstPlayed()))
            )).getItem());
            itemMap.put(12, new CustomItem("&aTeleportacja", Material.COMPASS, Collections.singletonList(colorize("&7&oPrzenies sie szybko!"))).getItem());
            itemMap.put(13, new CustomItem("&aPunkty", Material.EXPERIENCE_BOTTLE, Collections.singletonList(colorize("&7&oZarzadzaj swoimi punktami!"))).getItem());
            itemMap.put(14, new CustomItem("&aPrzepustka", Material.IRON_INGOT, Collections.singletonList(colorize("&7&oOdblokuj nowe rzeczy!"))).getItem());
            //itemMap.put(15, new CustomItem("&bPrzepustka Premium ✦", Material.DIAMOND, Collections.singletonList(colorize("&7&oJeszcze wiecje zabawy!"))).getItem());
            itemMap.put(16, new CustomItem("&bPanel VIP", Material.ENCHANTING_TABLE, Collections.singletonList(colorize("&7&oWszystko w jednym miejscu!"))).getItem());
            itemMap.put(28, new CustomItem("&fOgloszenia", Material.BOOK, Collections.singletonList(colorize("&7&oNajnowsze informacje."))).getItem());
            itemMap.put(37, new CustomItem("&fPomoc", Material.CAMPFIRE, Collections.singletonList(colorize("&7&oCentrum pomocy."))).getItem());
            itemMap.put(43, new CustomItem("&fUlatwienia dostepu", Material.NAME_TAG, Collections.singletonList(colorize("&7&oDodatkowe ustawienia."))).getItem());
            itemMap.put(21, new CustomItem("&aSkrytka", Material.ENDER_CHEST, Collections.singletonList(colorize("&7&oTwoje dodatkowe przedmioty."))).getItem());
            itemMap.put(22, new CustomItem("&aUmiejetnosci", Material.ENDER_EYE, Collections.singletonList(colorize("&7&oTwoje umiejetnosci."))).getItem());
            itemMap.put(23, new CustomItem("&aZadania", Material.WRITABLE_BOOK, Collections.singletonList(colorize("&7&oAktywne zadania."))).getItem());
            return itemMap;
        }

        if (inventoryName.equals("Mechanik")) {
            itemMap.put(10, new CustomItem("&fElytra", Material.ELYTRA, Collections.singletonList(colorize("&7&oCena: &a$128")), 0).getItem());
            itemMap.put(12, new CustomItem("&fJetpack", Material.CHAINMAIL_CHESTPLATE, Collections.singletonList(colorize("&7&oCena: &a$128")), 0).getItem());
            return itemMap;
        }

        if (inventoryName.equals("Bankier")) {
            itemMap.put(10, new CustomItem("&fWymien expa", Material.EXPERIENCE_BOTTLE, Arrays.asList(colorize("&7&oZamien swoj 30 level"), colorize("&7&ona 140 butelek expa.")), 0).getItem());
            itemMap.put(12, new CustomItem("&fOdbierz lokate", Material.EMERALD, Arrays.asList(colorize("&7&oOdbierz swoja lokate."), colorize("&7&oDostepne miedzy 12:00, a 0:00.")), 0).getItem());
            return itemMap;
        }

        if (inventoryName.equals("Teleportacja")) {
            String lore = colorize("&7&oUzyj, by sie przeteleportowac.");
            itemMap.put(53, cofnij);
            itemMap.put(10, new CustomItem("&fUstaw dom", Material.WHITE_BED, Collections.singletonList(colorize("&7&oUzyj, by ustawic dom."))).getItem());
            itemMap.put(12, new CustomItem("&aDom", Material.LIME_BED, Collections.singletonList(lore)).getItem());
            itemMap.put(13, new CustomItem("&aSpawn (Nowy)", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(14, new CustomItem("&aAutostrada Polnocna", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(15, new CustomItem("&aAutostrada Poludniowa", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(16, new CustomItem("&aWyspa Centralna", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(21, new CustomItem("&aLasy Wschodnie", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(22, new CustomItem("&aNadmorska Szlachta", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(23, new CustomItem("&aMonopoly", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(24, new CustomItem("&aWioska", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(25, new CustomItem("&aLasy Polnocne", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(30, new CustomItem("&aNowy Rzym", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            return itemMap;
        }

        if (inventoryName.equals("Panel VIP")) {
            itemMap.put(53, cofnij);
            //vip
            itemMap.put(0, new CustomItem("&fUsiadz sobie", Material.OAK_STAIRS, Collections.singletonList(colorize("&7&oPo prostu siadasz."))).getItem());
            itemMap.put(1, new CustomItem("&fZaloz czapke", Material.ACACIA_LEAVES, Collections.singletonList(colorize("&7&oZaloz trzymany blok na glowe."))).getItem());
            itemMap.put(2, new CustomItem("&f/skin", Material.NAME_TAG, Collections.singletonList(colorize("&7&oZarzadzaj swoim skinem."))).getItem());
            itemMap.put(3, new CustomItem("&f/me <tresc>", Material.TRIPWIRE_HOOK, Collections.singletonList(colorize("&7&oClaim World RP? Hmm?"))).getItem());
            itemMap.put(4, new CustomItem("&f/playtime <nick>", Material.WRITABLE_BOOK, Collections.singletonList(colorize("&7&oSprawdz czyjes godziny."))).getItem());
            //mvp
            itemMap.put(18, new CustomItem("&fPoloz sie", Material.RED_BED, Collections.singletonList(colorize("&7&oPo prostu sie kladziesz."))).getItem());
            itemMap.put(19, new CustomItem("&fPoloz sie na brzuchu", Material.STONE, Collections.singletonList(colorize("&7&oBELLY FLOP!"))).getItem());
            itemMap.put(20, new CustomItem("&fCzolgaj sie", Material.OAK_TRAPDOOR, Collections.singletonList(colorize("&7&oZaczynasz sie czolgac."))).getItem());
            itemMap.put(21, new CustomItem("&fObracanko", Material.GLASS_PANE, Collections.singletonList(colorize("&7&oZaczynasz sie obracac."))).getItem());
            itemMap.put(22, new CustomItem("&fKonsola efektow", Material.BEACON, Collections.singletonList(colorize("&7&oPodrasuj swoj wyglad."))).getItem());
            itemMap.put(23, new CustomItem("&fMaly fajerwerk", Material.FIREWORK_STAR, Collections.singletonList(colorize("&7&oOdpal fajerwerka pod nogami."))).getItem());
            itemMap.put(24, new CustomItem("&fSeria fajerwerkow", Material.FIREWORK_ROCKET, Collections.singletonList(colorize("&7&oTwoj maly pokaz."))).getItem());
            itemMap.put(25, new CustomItem("&f5% Na Bonus", Material.NETHER_STAR, Arrays.asList(colorize("&7&oSprobuj zarzucic wszystkim"), colorize("&7&ograczom online po kilka"), colorize("&7&ododatkowych Skrzyn Smoka."), "", colorize("&7&oDostepne miedzy 12:00, a 00:00."))).getItem());
            return itemMap;
        }

        if (inventoryName.equals("Ulatwienia dostepu")) {
            itemMap.put(53, cofnij);
            itemMap.put(10, new CustomItem("&aPoziom morza", Material.WATER_BUCKET, Collections.singletonList(colorize("&7&oSprawdz poziom morza."))).getItem());
            itemMap.put(11, new CustomItem("&aKosz", Material.HOPPER_MINECART, Collections.singletonList(colorize("&7&oLatwo usun przedmioty."))).getItem());
            itemMap.put(12, new CustomItem("&aRzut kostka", Material.REDSTONE_LAMP, Collections.singletonList(colorize("&7&oWyrzuc liczbe oczek."))).getItem());
            return itemMap;
        }

        if (inventoryName.equals("Skrytka " + player.getName())) {
            List<ItemStack> items = Locker.getInstance().getLockerMap().get(player.getName());

            if (items == null) {
                itemMap.put(0, new ItemStack(Material.AIR));
                return itemMap;
            }

            for (int i = 0; i < items.size(); i++) {
                itemMap.put(i, items.get(i));
            }

            return itemMap;
        }

        return null;
    }
}
