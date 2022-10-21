package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.Messages;
import claimworld.net.supporter.utils.guis.StoredInventories;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FillUpWarehouse {

    private final List<ItemStack> randomItems = new ArrayList<>();

    private List<ItemStack> getRandomItems() {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            items.add(randomItems.get(new Random().nextInt(randomItems.size())));
        }

        return items;
    }

    public FillUpWarehouse() {
        randomItems.add(new ItemStack(Material.STONE, 64));
        randomItems.add(new ItemStack(Material.COBBLESTONE, 64));
        randomItems.add(new ItemStack(Material.DIRT, 64));
        randomItems.add(new ItemStack(Material.SAND, 64));
        randomItems.add(new ItemStack(Material.DEEPSLATE, 64));
        randomItems.add(new ItemStack(Material.TERRACOTTA, 64));

        new CommandBase("fillupwarehouse", false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                StoredInventories storedInventories = new StoredInventories();

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(Messages.getUserPrefix() + "W Twojej Skrytce cos jest. Odbierz to, zanim zniknie!");

                    if (storedInventories.getStoredItems().get(player.getName()) == null) {
                        storedInventories.updateStoredItems(player.getName(), getRandomItems());
                        continue;
                    }

                    storedInventories.getStoredItems().get(player.getName()).addAll(getRandomItems());
                }

                return true;
            }

            @Override
            public String getUsage() {
                return "/fillupwarehouse";
            }
        }.setPermission("claimworld.admin");
    }
}
