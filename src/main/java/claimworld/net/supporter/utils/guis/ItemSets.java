package claimworld.net.supporter.utils.guis;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CustomHead;
import claimworld.net.supporter.utils.CustomItem;
import claimworld.net.supporter.utils.Ranks;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.logging.Level;

import static claimworld.net.supporter.Supporter.loadedWarehouse;
import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getScheduler;

public class ItemSets {
    
    private final HashMap<Integer, ItemStack> itemMap = new HashMap<>();
    private final ReadyItems readyItems = new ReadyItems();
    private final Ranks ranks = new Ranks();

    private ItemStack getRandomItemFromKit(int kit) {
        int random = new Random().nextInt(3);

        switch (kit) {
            case 0:
                if (random == 0) return new ItemStack(Material.STONE, 64);
                if (random == 1) return new ItemStack(Material.DIRT, 64);
                return new ItemStack(Material.DEEPSLATE, 64);
            case 1:
                if (random == 0) return new ItemStack(Material.DIORITE, 64);
                if (random == 1) return new ItemStack(Material.ANDESITE, 64);
                return new ItemStack(Material.GRANITE, 64);
        }

        return null;
    }

    public void fillWarehouse() {

    }
    
    public HashMap<Integer, ItemStack> initializeInventoryContent(Player player, String inventoryName) {
        if (inventoryName == null || inventoryName.isEmpty()) {
            getLogger().log(Level.INFO, "initializing error - inventoryName is empty or null");
            return null;
        }

        itemMap.clear();

        int random = new Random().nextInt(2);

        if (inventoryName.equals("Magazyn")) {
            if (!loadedWarehouse) return null;
            getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> new BukkitRunnable() {
                int i = 0;

                @Override
                public void run() {
                    if (i >= 8) {
                        cancel();
                        return;
                    }

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(colorize("&aLadowanie magazynu (" + i * 100 / 8 + "&a%)")));
                    itemMap.put(i, getRandomItemFromKit(random));

                    i += 1;
                }
            }.runTaskTimer(Supporter.getPlugin(), 0, 20L), 4L);

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(colorize("&aZakonczono ladowanie magazynu.")));

            return itemMap;
        }

        if (inventoryName.equals("Menu")) {
            itemMap.put(10, new CustomHead("&aInformacje", player, 1, Arrays.asList(
                    colorize("&fNick: " + player.getName() + "&f."),
                    colorize("&fRanga: " + ranks.getRankName(player) + "&f."),
                    colorize("&fWyróżnienie: " + ranks.getSpecialRankName(player)),
                    "",
                    colorize("&fDolaczono: " + new Date(player.getFirstPlayed()) + "&f."),
                    "",
                    colorize("&fOpoznienie: " + player.getPing() + "&fms.")
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
            return itemMap;
        }

        if (inventoryName.equals("Ulatwienia dostepu")) {
            itemMap.put(53, readyItems.get("Cofnij"));
            itemMap.put(10, new CustomItem("&aPoziom morza", Material.WATER_BUCKET, Collections.singletonList(colorize("&7&oSprawdz poziom morza."))).getItem());
            itemMap.put(11, new CustomItem("&aKosz", Material.HOPPER_MINECART, Collections.singletonList(colorize("&7&oLatwo usun przedmioty."))).getItem());
            return itemMap;
        }

        return null;
    }
}
