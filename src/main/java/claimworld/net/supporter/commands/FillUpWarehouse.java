package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.WarehouseUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static org.bukkit.Bukkit.getScheduler;

public class FillUpWarehouse {

    private final String blocksName = "blocks";
    private final String xpPotionName = "xpBottle";
    private final String usageName = "/fillupwarehouse <value>";
    
    private final List<ItemStack> blockSet = Arrays.asList(
        new ItemStack(Material.STONE, 64),
        new ItemStack(Material.COBBLESTONE, 64),
        new ItemStack(Material.DIRT, 64),
        new ItemStack(Material.SAND, 64),
        new ItemStack(Material.DEEPSLATE, 64),
        new ItemStack(Material.TERRACOTTA, 64),
        new ItemStack(Material.GRANITE, 64),
        new ItemStack(Material.DIORITE, 64),
        new ItemStack(Material.WHITE_WOOL, 64),
        new ItemStack(Material.ANDESITE, 64),
        new ItemStack(Material.CLAY, 64),
        new ItemStack(Material.QUARTZ_BLOCK, 64),
        new ItemStack(Material.MOSSY_COBBLESTONE, 64),
        new ItemStack(Material.GLASS, 64),
        new ItemStack(Material.PURPUR_BLOCK, 64),
        new ItemStack(Material.GRAVEL, 64),
        new ItemStack(Material.GLASS, 64)
    );

    private List<ItemStack> getRandomItems() {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            items.add(blockSet.get(new Random().nextInt(blockSet.size())));
        }

        return items;
    }

    private List<ItemStack> getExperienceBottleItems() {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {

        });
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.EXPERIENCE_BOTTLE, 8));
        return items;
    }

    public FillUpWarehouse() {
        new CommandBase("fillupwarehouse", 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                HashMap<String, List<ItemStack>> itemSets = new HashMap<>();
                itemSets.put(blocksName, getRandomItems());
                itemSets.put(xpPotionName, getExperienceBottleItems());

                String value = arguments[0];
                if (!(itemSets.containsKey(value))) return false;

                new WarehouseUtils().addItemsGlobal(itemSets.get(value));

                return true;
            }

            @Override
            public String getUsage() {
                return usageName;
            }
        }.setPermission("claimworld.admin");
    }
}
