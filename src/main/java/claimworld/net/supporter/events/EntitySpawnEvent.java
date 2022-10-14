package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CustomHead;
import claimworld.net.supporter.utils.CustomItem;
import claimworld.net.supporter.utils.Messages;
import claimworld.net.supporter.utils.guis.ReadyItems;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class EntitySpawnEvent implements Listener {

    private Player player;
    private final ReadyItems readyItems = new ReadyItems();

    private MerchantRecipe getCustomRecipe(ItemStack item, int maxUses, ItemStack ingredient1, ItemStack ingredient2) {
        MerchantRecipe recipe = new MerchantRecipe(item, 0, maxUses, true, 1, 1, 32, 24);
        recipe.addIngredient(ingredient1);
        if (ingredient2 != null) {
            recipe.addIngredient(ingredient2);
        }
        return recipe;
    }

    @EventHandler
    public void entitySpawnEvent(org.bukkit.event.entity.EntitySpawnEvent event) {
        if (!event.getEntityType().equals(EntityType.WANDERING_TRADER)) return;

        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
            int chance = new Random().nextInt(3);

            if (chance == 0) return;

            for (Player onlinePlayer : getOnlinePlayers()) {
                player = onlinePlayer;
                break;
            }

            List<MerchantRecipe> updatedRecipes = new ArrayList<>();
            WanderingTrader trader = (WanderingTrader) event.getEntity();
            String traderName = null;

            ItemStack dolar = readyItems.get("$1");

            //emerald trader
            if (chance == 1) {
                ItemStack ramka = readyItems.get("Niewidzialna ramka");
                ItemStack glowa = new CustomHead("&fGlowa " + player.getName(), player, 1, Collections.singletonList(colorize(readyItems.getLore("common")))).getItem();
                ItemStack dolar2x = readyItems.get("$1", 2);
                ItemStack unbreaking4 = new CustomItem("", Material.ENCHANTED_BOOK, 1, null, 0, Enchantment.DURABILITY, 4).getItem();

                updatedRecipes.add(getCustomRecipe(ramka, 2, new ItemStack(Material.EMERALD, 24), new ItemStack(Material.ITEM_FRAME)));
                updatedRecipes.add(getCustomRecipe(glowa, 1, new ItemStack(Material.EMERALD, 32), null));
                updatedRecipes.add(getCustomRecipe(new ItemStack(Material.FERN), 2, new ItemStack(Material.EMERALD, 16), null));
                updatedRecipes.add(getCustomRecipe(new ItemStack(Material.BAT_SPAWN_EGG), 1, new ItemStack(Material.EMERALD, 32), null));
                updatedRecipes.add(getCustomRecipe(dolar, 2, new ItemStack(Material.EMERALD, 64), null));
                updatedRecipes.add(getCustomRecipe(unbreaking4, 1, new ItemStack(Material.EMERALD, 16), dolar2x));
                traderName = colorize("&a&lEmeraldowy Handlarz");
            }

            //phantom trader
            if (chance == 2) {
                ItemStack bilet = readyItems.get("Uniwersalny bilet");
                ItemStack dolar48x = readyItems.get("$1", 48);

                updatedRecipes.add(getCustomRecipe(new ItemStack(Material.FIREWORK_ROCKET), 8, new ItemStack(Material.PHANTOM_MEMBRANE, 16), null));
                updatedRecipes.add(getCustomRecipe(bilet, 2, new ItemStack(Material.PHANTOM_MEMBRANE, 16), null));
                updatedRecipes.add(getCustomRecipe(dolar, 2, new ItemStack(Material.PHANTOM_MEMBRANE, 48), null));
                updatedRecipes.add(getCustomRecipe(new ItemStack(Material.ELYTRA), 1, new ItemStack(Material.PHANTOM_MEMBRANE, 64), dolar48x));
                traderName = colorize("&d&lFantomowy Handlarz");
            }

            trader.setCustomName(traderName);
            trader.setCustomNameVisible(true);
            trader.setDespawnDelay(6000);
            trader.setRecipes(updatedRecipes);

            broadcastMessage(colorize(Messages.getBroadcastPrefix() + traderName + "&f wlasnie pojawil sie na koordynatach &ex" + Math.round(trader.getLocation().getX()) + " &foraz &ez" + Math.round(trader.getLocation().getZ()) + "&f."));
        }, 30L);
    }
}
