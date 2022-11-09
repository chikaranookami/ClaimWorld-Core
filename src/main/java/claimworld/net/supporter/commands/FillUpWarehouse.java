package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.MessageUtils;
import claimworld.net.supporter.utils.items.Locker;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static org.bukkit.Bukkit.getOnlinePlayers;

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
        new ItemStack(Material.ANDESITE, 64)
    );

    private List<ItemStack> getRandomItems() {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            items.add(blockSet.get(new Random().nextInt(blockSet.size())));
        }

        return items;
    }

    private List<ItemStack> getExperienceBottleItems() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.EXPERIENCE_BOTTLE, 5));
        return items;
    }

    private void renderItems(List<ItemStack> items) {
        //poprawione? sprawdzic dokladnie
        HashMap<String, List<ItemStack>> itemMap = Locker.getInstance().getLockerMap();

        for (Player player : getOnlinePlayers()) {
            player.sendMessage(MessageUtils.getUserPrefix() + "Otrzymales przedmiot. Wez go ze skrytki zanim zniknie!");

            String playerName = player.getName();
            List<ItemStack> fixedItems = new ArrayList<>(items);

            if (player.getOpenInventory().getTitle().equals("Skrytka " + playerName)) player.closeInventory();

            if (itemMap.get(playerName) == null) {
                itemMap.put(playerName, fixedItems);
                fixedItems.clear();
                continue;
            }

            itemMap.get(playerName).addAll(fixedItems);
        }
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

                renderItems(itemSets.get(value));

                return true;
            }

            @Override
            public String getUsage() {
                return usageName;
            }
        }.setPermission("claimworld.admin");
    }
}
