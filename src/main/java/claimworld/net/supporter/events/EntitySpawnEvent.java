package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CustomItems;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bukkit.Bukkit.*;

public class EntitySpawnEvent implements Listener {

    private Player player;
    private List<MerchantRecipe> updatedRecipes;

    @EventHandler
    public void entitySpawnEvent(org.bukkit.event.entity.EntitySpawnEvent event) {
        if (!event.getEntityType().equals(EntityType.WANDERING_TRADER)) return;

        getScheduler().runTaskLater(Supporter.getInstance(), () -> {
            if (!(new Random().nextInt(2) == 0)) return;

            for (Player onlinePlayer : getOnlinePlayers()) {
                player = onlinePlayer;
                break;
            }

            updatedRecipes = new ArrayList<>();

            //invisibleItemFrame
            ItemStack invisibleItemFrame = new ItemStack(Material.ITEM_FRAME, 1);
            ItemMeta invisibleItemFrameMeta = invisibleItemFrame.getItemMeta();
            assert invisibleItemFrameMeta != null;
            invisibleItemFrameMeta.setDisplayName(ChatColor.GREEN + "Niewidzialna Ramka");

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mozna uzyc tylko raz.");
            invisibleItemFrameMeta.setLore(lore);

            invisibleItemFrame.setItemMeta(invisibleItemFrameMeta);

            MerchantRecipe recipe1 = new MerchantRecipe(invisibleItemFrame, 0, 7, true, 1, 1, 32, 24);
            recipe1.addIngredient(new ItemStack(Material.EMERALD, 24));
            recipe1.addIngredient(new ItemStack(Material.ITEM_FRAME));
            updatedRecipes.add(recipe1);

            //random player skull
            ItemStack randomPlayerHead = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta skullMeta = (SkullMeta) randomPlayerHead.getItemMeta();
            assert skullMeta != null;
            skullMeta.setOwningPlayer(player);
            randomPlayerHead.setItemMeta(skullMeta);

            MerchantRecipe recipe2 = new MerchantRecipe(randomPlayerHead, 0, 1, true, 1, 1, 32, 16);
            recipe2.addIngredient(new ItemStack(Material.EMERALD, 32));
            updatedRecipes.add(recipe2);

            //fireworks for phantom membranes
            ItemStack firework = new ItemStack(Material.FIREWORK_ROCKET, 1);

            MerchantRecipe recipe6 = new MerchantRecipe(firework, 0, 8, true, 1, 1, 8, 32);
            recipe6.addIngredient(new ItemStack(Material.PHANTOM_MEMBRANE, 16));
            updatedRecipes.add(recipe6);

            int additionalChances = new Random().nextInt(5);

            //80% on fern
            if (additionalChances > 0) {
                ItemStack fern = new ItemStack(Material.FERN, 1);

                MerchantRecipe recipe3 = new MerchantRecipe(fern, 0, 4, true, 1, 1, 32, 16);
                recipe3.addIngredient(new ItemStack(Material.EMERALD, 12));
                updatedRecipes.add(recipe3);
            }

            //60% on money for phantom membrane
            if (additionalChances > 1) {
                ItemStack money = new CustomItems().getMoneyItem();

                MerchantRecipe recipe4 = new MerchantRecipe(money, 0, 2, true, 1, 1, 8, 32);
                recipe4.addIngredient(new ItemStack(Material.PHANTOM_MEMBRANE, 64));
                updatedRecipes.add(recipe4);
            }

            //40% on money
            if (additionalChances > 2) {
                ItemStack money = new CustomItems().getMoneyItem();

                MerchantRecipe recipe5 = new MerchantRecipe(money, 0, 1, true, 1, 1, 8, 32);
                recipe5.addIngredient(new ItemStack(Material.EMERALD, 64));
                updatedRecipes.add(recipe5);
            }

            //40% on new common item
            if (additionalChances > 2) {
                ItemStack universalTicket = new CustomItems().getGlobalTicketItem();

                MerchantRecipe recipe7 = new MerchantRecipe(universalTicket, 0, 8, true, 1, 1, 8, 32);
                recipe7.addIngredient(new ItemStack(Material.PHANTOM_MEMBRANE, 16));
                updatedRecipes.add(recipe7);
            }

            WanderingTrader trader = (WanderingTrader) event.getEntity();
            trader.setCustomName(ChatColor.GREEN + "" + ChatColor.BOLD + "Tajemniczy Handlarz");
            trader.setCustomNameVisible(true);
            trader.setDespawnDelay(6000);
            trader.setRecipes(updatedRecipes);

            Location location = trader.getLocation();
            broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Tajemniczy Handlarz" + ChatColor.RESET + "" + ChatColor.GREEN + " wlasnie pojawil sie na koordynatach x" + Math.round(location.getX()) + " oraz z" + Math.round(location.getZ()) + ".");
        }, 30L);
    }
}
