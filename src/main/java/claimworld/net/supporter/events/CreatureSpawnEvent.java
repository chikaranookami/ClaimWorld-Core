package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.items.CustomHead;
import claimworld.net.supporter.utils.MessageUtils;
import claimworld.net.supporter.items.ReadyItems;
import claimworld.net.supporter.tasks.Task;
import claimworld.net.supporter.tasks.TaskManager;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.logging.Level;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class CreatureSpawnEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();
    ReadyItems readyItems = ReadyItems.getInstance();

    private final List<Player> players = new ArrayList<>();
    private final HashMap<Integer, String> traderName = new HashMap<>();
    private final List<org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason> checkedSpawnReasons = new ArrayList<>();

    private MerchantRecipe getCustomRecipe(ItemStack item, int maxUses, ItemStack ingredient1, ItemStack ingredient2) {
        MerchantRecipe recipe = new MerchantRecipe(item, 0, maxUses, true, 1, 1, 0, 0);
        recipe.addIngredient(ingredient1);
        if (ingredient2 != null) recipe.addIngredient(ingredient2);
        return recipe;
    }

    private List<MerchantRecipe> getRecipeSets(int set) {
        List<MerchantRecipe> updatedRecipes = new ArrayList<>();

        int random = new Random().nextInt(8);

        ItemStack jetpack = readyItems.get("Jetpack");
        ItemStack dolarek = readyItems.get("$1");
        ItemStack dolar64x = readyItems.get("$1", 64);
        ItemStack skrzyniaSmoka = readyItems.get("Skrzynia_smoka");
        ItemStack glowa1 = new CustomHead("&fGlowa " + players.get(0).getName(), players.get(0), 1, Collections.singletonList(colorize(readyItems.getLore("common")))).getItem();
        ItemStack glowa2 = new CustomHead("&fGlowa " + players.get(1).getName(), players.get(1), 1, Collections.singletonList(colorize(readyItems.getLore("common")))).getItem();
        //updatedRecipes.add(getCustomRecipe(new ItemStack(Material.BAT_SPAWN_EGG), 1, new ItemStack(Material.JACK_O_LANTERN), null));

        if (set == 0) {
            ItemStack ramka = readyItems.get("Niewidzialna_ramka");
            ItemStack unbreaking4 = readyItems.get("Ksiazka_unbreaking4");
            ItemStack protection5 = readyItems.get("Ksiazka_protection5");
            ItemStack fireaspect3 = readyItems.get("Ksiazka_fireaspect3");
            ItemStack power6 = readyItems.get("Ksiazka_power6");
            //ItemStack halloweenowyOdbijacz = readyItems.get("Halloweenowy odbijacz");

            //Emeraldowy Handlarz
            updatedRecipes.add(getCustomRecipe(ramka, 2, new ItemStack(Material.EMERALD, 24), new ItemStack(Material.ITEM_FRAME)));
            updatedRecipes.add(getCustomRecipe(glowa1, 1, new ItemStack(Material.EMERALD, 32), null));
            updatedRecipes.add(getCustomRecipe(glowa2, 1, new ItemStack(Material.EMERALD, 32), null));
            if (random > 1) {
                updatedRecipes.add(getCustomRecipe(new ItemStack(Material.ZOMBIE_SPAWN_EGG), 1, dolar64x, null));
                updatedRecipes.add(getCustomRecipe(new ItemStack(Material.CREEPER_SPAWN_EGG), 1, dolar64x, null));
            }
            if (random > 3) {
                updatedRecipes.add(getCustomRecipe(dolarek, 4, new ItemStack(Material.EMERALD, 64), null));
                updatedRecipes.add(getCustomRecipe(skrzyniaSmoka, 4, new ItemStack(Material.EMERALD, 32), null));
                updatedRecipes.add(getCustomRecipe(fireaspect3, 1, new ItemStack(Material.EMERALD, 16), dolar64x));
            }
            if (random > 5) {
                updatedRecipes.add(getCustomRecipe(unbreaking4, 1, new ItemStack(Material.EMERALD, 16), dolar64x));
                updatedRecipes.add(getCustomRecipe(protection5, 1, new ItemStack(Material.EMERALD, 16), dolar64x));
                updatedRecipes.add(getCustomRecipe(new ItemStack(Material.SPONGE, 4), 1, new ItemStack(Material.EMERALD, 16), null));
                updatedRecipes.add(getCustomRecipe(jetpack, 1, new ItemStack(Material.EMERALD, 16), dolar64x));
            }
            if (random > 6) {
                updatedRecipes.add(getCustomRecipe(new ItemStack(Material.ELYTRA), 1, new ItemStack(Material.EMERALD, 64), dolar64x));
                updatedRecipes.add(getCustomRecipe(power6, 1, new ItemStack(Material.EMERALD, 16), dolar64x));
            }
            //updatedRecipes.add(getCustomRecipe(halloweenowyOdbijacz, 2, new ItemStack(Material.JACK_O_LANTERN, 16), null));
        }

        if (set == 1) {
            ItemStack bilet = readyItems.get("Uniwersalny_bilet");
            ItemStack sharpnessBook = readyItems.get("Ksiazka_sharpness6");
            ItemStack looting4 = readyItems.get("Ksiazka_looting4");
            ItemStack thorns5 = readyItems.get("Ksiazka_thorns5");
            ItemStack zwojOgnia = readyItems.get("Zwoj_ognia");
            //ItemStack halloweenowaZupa = readyItems.get("Halloweenowa zupa");

            //Fantomowy Handlarz
            updatedRecipes.add(getCustomRecipe(new ItemStack(Material.FIREWORK_ROCKET), 8, new ItemStack(Material.PHANTOM_MEMBRANE, 16), null));
            updatedRecipes.add(getCustomRecipe(glowa1, 1, new ItemStack(Material.PHANTOM_MEMBRANE, 32), null));
            updatedRecipes.add(getCustomRecipe(glowa2, 1, new ItemStack(Material.PHANTOM_MEMBRANE, 32), null));
            if (random > 1) {
                updatedRecipes.add(getCustomRecipe(bilet, 2, new ItemStack(Material.PHANTOM_MEMBRANE, 16), null));
                updatedRecipes.add(getCustomRecipe(zwojOgnia, 1, new ItemStack(Material.PHANTOM_MEMBRANE, 16), null));
                updatedRecipes.add(getCustomRecipe(dolarek, 4, new ItemStack(Material.PHANTOM_MEMBRANE, 32), null));
            }
            if (random > 4) {
                updatedRecipes.add(getCustomRecipe(new ItemStack(Material.SKELETON_SPAWN_EGG), 1, dolar64x, null));
                updatedRecipes.add(getCustomRecipe(new ItemStack(Material.WITCH_SPAWN_EGG), 1, dolar64x, null));
                updatedRecipes.add(getCustomRecipe(looting4, 1, new ItemStack(Material.PHANTOM_MEMBRANE, 16), dolar64x));
            }
            if (random > 5) {
                updatedRecipes.add(getCustomRecipe(sharpnessBook, 1, new ItemStack(Material.PHANTOM_MEMBRANE, 16), dolar64x));
                updatedRecipes.add(getCustomRecipe(skrzyniaSmoka, 4, new ItemStack(Material.PHANTOM_MEMBRANE, 32), null));
                updatedRecipes.add(getCustomRecipe(thorns5, 1, new ItemStack(Material.PHANTOM_MEMBRANE, 16), dolar64x));
                updatedRecipes.add(getCustomRecipe(jetpack, 1, new ItemStack(Material.PHANTOM_MEMBRANE, 16), dolar64x));
            }
            if (random > 6) {
                updatedRecipes.add(getCustomRecipe(new ItemStack(Material.ELYTRA), 1, new ItemStack(Material.PHANTOM_MEMBRANE, 64), dolar64x));
                updatedRecipes.add(getCustomRecipe(new ItemStack(Material.SPONGE, 4), 1, new ItemStack(Material.PHANTOM_MEMBRANE, 16), null));
            }
            //updatedRecipes.add(getCustomRecipe(halloweenowaZupa, 4, new ItemStack(Material.JACK_O_LANTERN, 4), null));

        }

        return updatedRecipes;
    }

    public CreatureSpawnEvent() {
        traderName.put(0, colorize("&a&lEmeraldowy Handlarz"));
        traderName.put(1, colorize("&d&lFantomowy Handlarz"));

        checkedSpawnReasons.add(org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.BREEDING);
        checkedSpawnReasons.add(org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.EGG);
    }

    @EventHandler
    public void creatureSpawnEvent(org.bukkit.event.entity.CreatureSpawnEvent event) {
        org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason spawnReason = event.getSpawnReason();
        Map<String, Task> taskMap = taskManager.getTaskMap();

        if (spawnReason == org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.ENDER_PEARL) {
            for (Entity entity : event.getEntity().getNearbyEntities(3, 3, 3)) {
                if (!(entity instanceof Player)) continue;
                getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask((Player) entity, taskMap.get("stayCloseToSomeNewEndermite")));
            }
            return;
        }

        if (spawnReason == org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.CURED) {
            for (Entity entity : event.getEntity().getNearbyEntities(3, 3, 3)) {
                if (!(entity instanceof Player)) continue;
                getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask((Player) entity, taskMap.get("transformZombieToVillager")));
            }
            return;
        }

        if (checkedSpawnReasons.contains(spawnReason)) {
            if (event.getLocation().getChunk().getEntities().length <= 150) return;

            event.setCancelled(true);
            getLogger().log(Level.INFO, "cancelled spawn of " + event.getEntityType() + " at " + event.getLocation());
            return;
        }

        if (getOnlinePlayers().size() < 2) return;

        EntityType entityType = event.getEntityType();

        if (event.getEntity() instanceof Monster) {
            if (entityType == EntityType.CREEPER) return;

            int chance = new Random().nextInt(25);
            if (chance != 0) return;

            Monster monster = (Monster) event.getEntity();
            monster.setCanPickupItems(false);
            monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(monster.getHealth() * 4);
            monster.setSilent(true);
            monster.setVisualFire(true);
            monster.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 4));
            monster.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));

            return;
        }

        if (entityType.equals(EntityType.WANDERING_TRADER)) {
            int chance = new Random().nextInt(4);
            if (chance > 1) return;

            getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
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

                getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
                    for (Player player : getOnlinePlayers()) {
                        taskManager.tryFinishTask(player, taskMap.get("beOnlineWhenTraderSpawns"));
                    }
                });
            }, 20L);
        }
    }
}
