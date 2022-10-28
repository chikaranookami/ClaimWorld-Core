package claimworld.net.supporter.utils.guis;

import claimworld.net.supporter.utils.CustomHead;
import claimworld.net.supporter.utils.CustomItem;
import claimworld.net.supporter.utils.Ranks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.logging.Level;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getLogger;

public class ItemSets {
    
    private final HashMap<Integer, ItemStack> itemMap = new HashMap<>();
    private final ReadyItems readyItems = new ReadyItems();
    private final Ranks ranks = new Ranks();
    
    public HashMap<Integer, ItemStack> initializeInventoryContent(Player player, String inventoryName) {
        if (inventoryName == null || inventoryName.isEmpty()) {
            getLogger().log(Level.INFO, "initializing error - inventoryName is empty or null");
            return null;
        }

        itemMap.clear();

        if (inventoryName.equals("Menu")) {
            itemMap.put(10, new CustomHead("&aInformacje", player, 1, Arrays.asList(
                    colorize("&fNick: " + player.getName() + "&f."),
                    colorize("&fRanga: " + ranks.getRankName(player) + "&f."),
                    colorize("&fWyróżnienie: " + ranks.getSpecialRankName(player)),
                    "",
                    colorize("&fDolaczono: " + new Date(player.getFirstPlayed()) + "&f."),
                    "",
                    colorize("&fPing: " + player.getPing() + "&fms.")
            )).getItem());
            itemMap.put(12, new CustomItem("&aTeleportacja", Material.COMPASS, Collections.singletonList(colorize("&7&oPrzenies sie szybko!"))).getItem());
            itemMap.put(13, new CustomItem("&aPunkty", Material.EXPERIENCE_BOTTLE, Collections.singletonList(colorize("&7&oZarzadzaj swoimi punktami!"))).getItem());
            itemMap.put(14, new CustomItem("&aPrzepustka", Material.IRON_INGOT, Arrays.asList(colorize("&7&o- Jeszcze wiecej opcji!"), colorize("&7&oDostepna juz niebawem!"))).getItem());
            itemMap.put(15, new CustomItem("&bPrzepustka Premium ✦", Material.DIAMOND, Arrays.asList(colorize("&7&o- Nowe, unikalne mozliwosci!"), colorize("&7&oDostepna juz niebawem!"))).getItem());
            itemMap.put(16, new CustomItem("&bPanel VIP", Material.ENCHANTING_TABLE, Arrays.asList(colorize("&7&oTajemnicze miejsce!"), colorize("&7&oDostepna juz niebawem!"))).getItem());
            itemMap.put(28, new CustomItem("&fOgnisko u Mariana", Material.CAMPFIRE, Collections.singletonList(colorize("&7&oCentrum pomocy."))).getItem());
            itemMap.put(37, new CustomItem("&fUlatwienia dostepu", Material.NAME_TAG, Collections.singletonList(colorize("&7&oDodatkowe ustawienia."))).getItem());
            itemMap.put(43, new CustomItem("&a#1 Eventowe Tournée", Material.BOOK, Arrays.asList(
                    colorize("&7&oSzczegoly na Discordzie."),"",
                    colorize("&a> Turniej Kosza 2v2"), colorize("&7&oData: 1.10.2022"), "",
                    colorize("&a> Turniej Zbijaka"), colorize("&7&oData: 8.10.2022"), "",
                    colorize("&a> Zabawa W Chowanego"), colorize("&7&oData: 15.10.2022"), "",
                    colorize("&a> Monopoly by CW"), colorize("&7&oData: 22.10.2022"), "",
                    colorize("&a> Konkurs Talentow"), colorize("&7&oData: 29.10.2022")
            )).getItem());
            itemMap.put(21, new CustomItem("&bSkrytka", Material.ENDER_CHEST, Collections.singletonList(colorize("&7&oTwoje dodatkowe przedmioty."))).getItem());
            return itemMap;
        }

        if (inventoryName.equals("Teleportacja")) {
            String lore = colorize("&7&oUzyj, by sie przeteleportowac.");

            itemMap.put(53, readyItems.get("Cofnij"));
            itemMap.put(10, new CustomItem("&fUstaw dom", Material.WHITE_BED, Collections.singletonList(colorize("&7&oUzyj, by ustawic dom."))).getItem());
            itemMap.put(12, new CustomItem("&aDom", Material.LIME_BED, Collections.singletonList(lore)).getItem());
            itemMap.put(13, new CustomItem("&aSpawn", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(14, new CustomItem("&aAutostrada Polnocna", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(15, new CustomItem("&aAutostrada Poludniowa", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(16, new CustomItem("&aCentrum Publiczne", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(21, new CustomItem("&aLasy Wschodnie", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(22, new CustomItem("&aNadmorska Szlachta", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(23, new CustomItem("&aMonopoly", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            return itemMap;
        }

        if (inventoryName.equals("Ulatwienia dostepu")) {
            itemMap.put(53, readyItems.get("Cofnij"));
            itemMap.put(10, new CustomItem("&aPoziom morza", Material.WATER_BUCKET, Collections.singletonList(colorize("&7&oSprawdz poziom morza."))).getItem());
            itemMap.put(11, new CustomItem("&aKosz", Material.HOPPER_MINECART, Collections.singletonList(colorize("&7&oLatwo usun przedmioty."))).getItem());
            itemMap.put(12, new CustomItem("&aRzut kostka", Material.REDSTONE_LAMP, Collections.singletonList(colorize("&7&oWyrzuc liczbe oczek."))).getItem());
            return itemMap;
        }

        if (inventoryName.equals("Skrytka " + player.getName())) {
            List<ItemStack> items = new StoredInventories().getStoredItems().get(player.getName());

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
