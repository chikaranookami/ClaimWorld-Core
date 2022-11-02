package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CustomHead;
import claimworld.net.supporter.utils.CustomItem;
import claimworld.net.supporter.utils.Messages;
import claimworld.net.supporter.utils.guis.ReadyItems;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.*;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class EntitySpawnEvent implements Listener {

    private Player player;
    private final ReadyItems readyItems = new ReadyItems();
    private final HashMap<Integer, String> traderName = new HashMap<>();

    private MerchantRecipe getCustomRecipe(ItemStack item, int maxUses, ItemStack ingredient1, ItemStack ingredient2) {
        MerchantRecipe recipe = new MerchantRecipe(item, 0, maxUses, true, 1, 1, 0, 0);
        recipe.addIngredient(ingredient1);
        if (ingredient2 != null) {
            recipe.addIngredient(ingredient2);
        }
        return recipe;
    }

    private List<MerchantRecipe> getRecipeSets(int set) {
        List<MerchantRecipe> updatedRecipes = new ArrayList<>();

        ItemStack dolar = readyItems.get("$1");

        //updatedRecipes.add(getCustomRecipe(new ItemStack(Material.BAT_SPAWN_EGG), 8, new ItemStack(Material.JACK_O_LANTERN), null));

        if (set == 0) {
            ItemStack ramka = readyItems.get("Niewidzialna ramka");
            ItemStack dolar2x = readyItems.get("$1", 2);
            ItemStack unbreaking4 = readyItems.get("Ksiazka unbreaking4");
            ItemStack glowa = new CustomHead("&fGlowa " + player.getName(), player, 1, Collections.singletonList(colorize(readyItems.getLore("common")))).getItem();
            //ItemStack halloweenowyOdbijacz = readyItems.get("Halloweenowy odbijacz");

            //Emeraldowy Handlarz
            updatedRecipes.add(getCustomRecipe(ramka, 2, new ItemStack(Material.EMERALD, 24), new ItemStack(Material.ITEM_FRAME)));
            updatedRecipes.add(getCustomRecipe(glowa, 1, new ItemStack(Material.EMERALD, 32), null));
            updatedRecipes.add(getCustomRecipe(new ItemStack(Material.FERN), 2, new ItemStack(Material.EMERALD, 16), null));
            updatedRecipes.add(getCustomRecipe(new ItemStack(Material.BAT_SPAWN_EGG), 1, new ItemStack(Material.EMERALD, 32), null));
            updatedRecipes.add(getCustomRecipe(dolar, 2, new ItemStack(Material.EMERALD, 64), null));
            updatedRecipes.add(getCustomRecipe(unbreaking4, 1, new ItemStack(Material.EMERALD, 16), dolar2x));
            //updatedRecipes.add(getCustomRecipe(halloweenowyOdbijacz, 2, new ItemStack(Material.JACK_O_LANTERN, 16), null));
        }

        if (set == 1) {
            ItemStack bilet = readyItems.get("Uniwersalny bilet");
            ItemStack dolar64x = readyItems.get("$1", 64);
            ItemStack halloweenowaZupa = readyItems.get("Halloweenowa zupa");

            //Fantomowy Handlarz
            updatedRecipes.add(getCustomRecipe(new ItemStack(Material.FIREWORK_ROCKET), 8, new ItemStack(Material.PHANTOM_MEMBRANE, 16), null));
            updatedRecipes.add(getCustomRecipe(bilet, 2, new ItemStack(Material.PHANTOM_MEMBRANE, 16), null));
            updatedRecipes.add(getCustomRecipe(dolar, 2, new ItemStack(Material.PHANTOM_MEMBRANE, 32), null));
            updatedRecipes.add(getCustomRecipe(new ItemStack(Material.ELYTRA), 1, new ItemStack(Material.PHANTOM_MEMBRANE, 64), dolar64x));
            updatedRecipes.add(getCustomRecipe(new ItemStack(Material.END_ROD), 4, new ItemStack(Material.PHANTOM_MEMBRANE, 16), null));
            //updatedRecipes.add(getCustomRecipe(halloweenowaZupa, 4, new ItemStack(Material.JACK_O_LANTERN, 4), null));

        }

        return updatedRecipes;
    }

    private final List<EntityType> entityTypes = new ArrayList<>();

    public EntitySpawnEvent() {
        traderName.put(0, colorize("&a&lEmeraldowy Handlarz"));
        traderName.put(1, colorize("&d&lFantomowy Handlarz"));
        entityTypes.add(EntityType.ZOMBIE);
        entityTypes.add(EntityType.SKELETON);
        entityTypes.add(EntityType.WITHER_SKELETON);
    }

    @EventHandler
    public void entitySpawnEvent(org.bukkit.event.entity.EntitySpawnEvent event) {
        if (getOnlinePlayers().size() < 1) return;

        if (entityTypes.contains(event.getEntityType())) {
            ((LivingEntity) event.getEntity()).setCanPickupItems(false);
        }

        if (event.getEntityType().equals(EntityType.WANDERING_TRADER)) {
            getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                int chance = new Random().nextInt(2);

                for (Player onlinePlayer : getOnlinePlayers()) {
                    player = onlinePlayer;
                    break;
                }

                WanderingTrader trader = (WanderingTrader) event.getEntity();
                trader.setCustomNameVisible(true);
                trader.setDespawnDelay(12000);
                trader.setRecipes(getRecipeSets(chance));
                trader.setCustomName(traderName.get(chance));

                broadcastMessage(colorize(Messages.getBroadcastPrefix() + traderName.get(chance) + "&f wlasnie pojawil sie na koordynatach &ex" + Math.round(trader.getLocation().getX()) + " &foraz &ez" + Math.round(trader.getLocation().getZ()) + "&f."));
            }, 30L);
        }
    }
}
