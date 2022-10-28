package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.Messages;
import claimworld.net.supporter.utils.guis.StoredInventories;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class FillUpWarehouse {

    private final String blocksName = "blocks";
    private final String xpPotionName = "xpBottle";

    private final String usageName = "/fillupwarehouse <value>";

    private final HashMap<String, List<ItemStack>> itemSets = new HashMap<>();

    private List<ItemStack> getRandomItems() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.STONE, 64));
        items.add(new ItemStack(Material.COBBLESTONE, 64));
        items.add(new ItemStack(Material.DIRT, 64));
        items.add(new ItemStack(Material.SAND, 64));
        items.add(new ItemStack(Material.DEEPSLATE, 64));
        items.add(new ItemStack(Material.TERRACOTTA, 64));
        items.add(new ItemStack(Material.GRANITE, 64));
        items.add(new ItemStack(Material.DIORITE, 64));
        items.add(new ItemStack(Material.ANDESITE, 64));

        for (int i = 0; i < 4; i++) {
            items.add(items.get(new Random().nextInt(items.size())));
        }

        return items;
    }

    private List<ItemStack> getExperienceBottleItems() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.EXPERIENCE_BOTTLE));

        return items;
    }

    public FillUpWarehouse() {
        itemSets.put(blocksName, getRandomItems());
        itemSets.put(xpPotionName, getExperienceBottleItems());

        new CommandBase("fillupwarehouse", 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                StoredInventories storedInventories = new StoredInventories();

                String value = arguments[0];

                if (!(value.equals(blocksName) || value.equals(xpPotionName))) return false;

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(Messages.getUserPrefix() + "W Twojej Skrytce cos jest. Odbierz to, zanim zniknie!");

                    if (storedInventories.getStoredItems().get(player.getName()) == null) {
                        storedInventories.updateStoredItems(player.getName(), itemSets.get(value));
                        continue;
                    }

                    storedInventories.getStoredItems().get(player.getName()).addAll(itemSets.get(value));
                }

                return true;
            }

            @Override
            public String getUsage() {
                return usageName;
            }
        }.setPermission("claimworld.admin");
    }
}
