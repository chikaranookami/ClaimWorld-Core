package claimworld.net.supporter.events;

import claimworld.net.supporter.utils.items.CustomHead;
import claimworld.net.supporter.utils.MessageUtils;
import claimworld.net.supporter.utils.items.ReadyItems;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.*;
import java.util.logging.Level;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class EntitySpawnEvent implements Listener {

    private final List<Player> players = new ArrayList<>();
    private final ReadyItems readyItems = new ReadyItems();
    private final HashMap<Integer, String> traderName = new HashMap<>();

    private MerchantRecipe getCustomRecipe(ItemStack item, int maxUses, ItemStack ingredient1, ItemStack ingredient2) {
        MerchantRecipe recipe = new MerchantRecipe(item, 0, maxUses, true, 1, 1, 0, 0);
        recipe.addIngredient(ingredient1);
        if (ingredient2 != null) recipe.addIngredient(ingredient2);
        return recipe;
    }

    private List<MerchantRecipe> getRecipeSets(int set) {
        List<MerchantRecipe> updatedRecipes = new ArrayList<>();

        ItemStack dolar = readyItems.get("$1");
        ItemStack dolar64x = readyItems.get("$1", 64);
        //updatedRecipes.add(getCustomRecipe(new ItemStack(Material.BAT_SPAWN_EGG), 1, new ItemStack(Material.JACK_O_LANTERN), null));

        if (set == 0) {
            ItemStack ramka = readyItems.get("Niewidzialna_ramka");
            ItemStack dolar2x = readyItems.get("$1", 2);
            ItemStack unbreaking4 = readyItems.get("Ksiazka_unbreaking4");
            ItemStack glowa1 = new CustomHead("&fGlowa " + players.get(0).getName(), players.get(0), 1, Collections.singletonList(colorize(readyItems.getLore("common")))).getItem();
            ItemStack glowa2 = new CustomHead("&fGlowa " + players.get(1).getName(), players.get(1), 1, Collections.singletonList(colorize(readyItems.getLore("common")))).getItem();
            //ItemStack halloweenowyOdbijacz = readyItems.get("Halloweenowy odbijacz");

            //Emeraldowy Handlarz
            updatedRecipes.add(getCustomRecipe(ramka, 2, new ItemStack(Material.EMERALD, 24), new ItemStack(Material.ITEM_FRAME)));
            updatedRecipes.add(getCustomRecipe(glowa1, 1, new ItemStack(Material.EMERALD, 32), null));
            updatedRecipes.add(getCustomRecipe(glowa2, 1, new ItemStack(Material.EMERALD, 32), null));
            updatedRecipes.add(getCustomRecipe(new ItemStack(Material.ZOMBIE_SPAWN_EGG), 1, dolar64x, null));
            updatedRecipes.add(getCustomRecipe(new ItemStack(Material.CREEPER_SPAWN_EGG), 1, dolar64x, null));
            updatedRecipes.add(getCustomRecipe(dolar, 2, new ItemStack(Material.EMERALD, 64), null));
            updatedRecipes.add(getCustomRecipe(unbreaking4, 1, new ItemStack(Material.EMERALD, 16), dolar2x));
            //updatedRecipes.add(getCustomRecipe(halloweenowyOdbijacz, 2, new ItemStack(Material.JACK_O_LANTERN, 16), null));
        }

        if (set == 1) {
            ItemStack bilet = readyItems.get("Uniwersalny_bilet");
            ItemStack sharpnessBook = readyItems.get("Ksiazka_sharpness6");
            //ItemStack halloweenowaZupa = readyItems.get("Halloweenowa zupa");

            //Fantomowy Handlarz
            updatedRecipes.add(getCustomRecipe(new ItemStack(Material.FIREWORK_ROCKET), 8, new ItemStack(Material.PHANTOM_MEMBRANE, 16), null));
            updatedRecipes.add(getCustomRecipe(bilet, 2, new ItemStack(Material.PHANTOM_MEMBRANE, 16), null));
            updatedRecipes.add(getCustomRecipe(dolar, 2, new ItemStack(Material.PHANTOM_MEMBRANE, 32), null));
            updatedRecipes.add(getCustomRecipe(new ItemStack(Material.SKELETON_SPAWN_EGG), 1, dolar64x, null));
            updatedRecipes.add(getCustomRecipe(new ItemStack(Material.WITCH_SPAWN_EGG), 1, dolar64x, null));
            updatedRecipes.add(getCustomRecipe(sharpnessBook, 1, new ItemStack(Material.PHANTOM_MEMBRANE, 32), dolar64x));
            updatedRecipes.add(getCustomRecipe(new ItemStack(Material.ELYTRA), 1, new ItemStack(Material.PHANTOM_MEMBRANE, 64), dolar64x));
            //updatedRecipes.add(getCustomRecipe(halloweenowaZupa, 4, new ItemStack(Material.JACK_O_LANTERN, 4), null));

        }

        return updatedRecipes;
    }

    public EntitySpawnEvent() {
        traderName.put(0, colorize("&a&lEmeraldowy Handlarz"));
        traderName.put(1, colorize("&d&lFantomowy Handlarz"));
    }

    @EventHandler
    public void entitySpawnEvent(org.bukkit.event.entity.EntitySpawnEvent event) {
        if (event.getLocation().getChunk().getEntities().length > 125) {
            event.setCancelled(true);
            getLogger().log(Level.INFO, "cancelled spawn of " + event.getEntityType() + " at " + event.getLocation());
            return;
        }

        if (getOnlinePlayers().size() < 2) return;
        if (!(event.getEntityType().equals(EntityType.WANDERING_TRADER))) return;

        int chance = new Random().nextInt(4);
        if (chance > 1) return;

        for (Player onlinePlayer : getOnlinePlayers()) {
            players.add(onlinePlayer);
            if (!(players.size() < 2)) break;
        }

        WanderingTrader trader = (WanderingTrader) event.getEntity();
        trader.setCustomNameVisible(true);
        trader.setDespawnDelay(12000);
        trader.setRecipes(getRecipeSets(chance));
        trader.setCustomName(traderName.get(chance));

        broadcastMessage(colorize(MessageUtils.getBroadcastPrefix() + traderName.get(chance) + "&f wlasnie pojawil sie na koordynatach &ex" + Math.round(trader.getLocation().getX()) + " &foraz &ez" + Math.round(trader.getLocation().getZ()) + "&f."));
    }
}
