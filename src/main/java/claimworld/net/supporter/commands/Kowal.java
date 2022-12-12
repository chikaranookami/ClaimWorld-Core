package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.WarehouseUtils;
import claimworld.net.supporter.utils.items.ReadyItems;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getScheduler;

public class Kowal {

    TaskManager taskManager = TaskManager.getInstance();
    ReadyItems readyItems = ReadyItems.getInstance();

    //stary spawn
    private final Location kowalLocation = new Location(Bukkit.getWorld("1"), 264, 63, 405);

    private ItemStack getBook(Player player) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        String playerName = player.getName();

        BaseComponent[] shopComponent = new ComponentBuilder()
                .append("§cKowal\n")
                .append("§8Ulepsza / podwaja Twoje przedmioty (33% szans).\n\nJesli mu sie nie uda, Twoje przedmioty zostana zniszczone.\n\n")
                .append("§c§n> §8§oChce wzmocnic swoj przedmiot.")
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Wez do glownej reki przedmiot, ktory chcesz ulepszyc, a do drugiej wybrane ulepszenie.")))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kowalexecute upgrade " + playerName))
                .append("\n\n")
                .append("§c§n> §8§oChce podwoic swoj przedmiot.")
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Wez do glownej reki przedmiot, ktory chcesz podwoic, a do drugiej Uniwersalny Bilet.")))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kowalexecute clone " + playerName))
                .create();

        assert bookMeta != null;
        bookMeta.spigot().addPage(shopComponent);

        bookMeta.setTitle("Kowal");
        bookMeta.setAuthor("Chikaraa");
        book.setItemMeta(bookMeta);

        return book;
    }

    private List<ItemStack> getItemList(ItemStack item, int amount) {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            items.add(item);
        }
        return items;
    }

    private void playEnchantingEffect(Location location, World world) {
        world.playSound(location, Sound.BLOCK_ANVIL_USE, 0.75f, 0.75f);
    }

    private void renderBlacksmithResult(Player player, Location location, World world, PlayerInventory inventory, ItemStack item, ItemStack offItem, String task, Enchantment enchantment, int enchantmentLevel, boolean successful) {
        getScheduler().runTask(Supporter.getPlugin(), () -> {
            playEnchantingEffect(location, world);

            inventory.getItemInOffHand().setAmount(offItem.getAmount() - 1);
            inventory.getItemInMainHand().setAmount(item.getAmount() - 1);
        });

        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
            Map<String, Task> taskMap = taskManager.getTaskMap();

            if (!successful) {
                getScheduler().runTask(Supporter.getPlugin(), () -> world.playSound(location, Sound.BLOCK_ANVIL_DESTROY, 0.75f, 0.75f));

                player.sendMessage(getUserPrefix() + "Nie udalo sie - Kowal spalil Twoje przedmioty.");
                taskManager.tryFinishTask(player, taskMap.get("destroyItemsAtBlacksmith"));

                return;
            }

            getScheduler().runTask(Supporter.getPlugin(), () -> {
                item.setAmount(1);
                world.playSound(location, Sound.BLOCK_ANVIL_PLACE, 0.75f, 0.75f);
            });

            String questName;
            int finalItemAmount;

            if (task.equals("clone")) {
                finalItemAmount = 2;
                questName = "doubleItemAtBlacksmith";
            } else {
                finalItemAmount = 1;
                questName = "upgradeItemAtBlacksmith";

                getScheduler().runTask(Supporter.getPlugin(), () -> {
                    item.addUnsafeEnchantment(enchantment, enchantmentLevel);

                    ItemMeta itemMeta = item.getItemMeta();
                    if (item.hasItemMeta() && itemMeta.getLore() == null) {
                        List<String> newItemLore = new ArrayList<>();
                        newItemLore.add(colorize(readyItems.getLore("rare")));

                        itemMeta.setLore(newItemLore);
                        item.setItemMeta(itemMeta);
                    }
                });
            }

            player.sendMessage(getUserPrefix() + "Sukces! Kowal wykonal Twoje zlecenie!");

            new WarehouseUtils().addItemsSingle(player, getItemList(item, finalItemAmount));
            taskManager.tryFinishTask(player, taskMap.get(questName));

        }, 30L);
    }

    public Kowal() {
        new CommandBase("kowal", 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                if (player == null) return false;

                player.openBook(getBook(player));

                return true;
            }

            @Override
            public String getUsage() {
                return "/kowal <player>";
            }
        }.setPermission("claimworld.admin");

        new CommandBase("kowalexecute", 2, true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
                    Player player = Bukkit.getPlayer(arguments[1]);
                    if (player == null) {
                        sender.sendMessage("player is null - aborting...");
                        return;
                    }

                    String task = arguments[0];
                    if (!task.equals("clone") && !task.equals("upgrade")) {
                        player.sendMessage("Blad (kod 0). Zglos to na discorda!");
                        return;
                    }

                    if (kowalLocation.distance(player.getLocation()) >= 4) {
                        player.sendMessage("Blad (kod 1). Zglos to na discorda!");
                        return;
                    }

                    PlayerInventory inventory = player.getInventory();
                    ItemStack item = inventory.getItemInMainHand().clone();
                    ItemStack offItem = inventory.getItemInOffHand().clone();

                    if (item.getType().isAir() || offItem.getType().isAir()) {
                        player.sendMessage(getUserPrefix() + "Ustaw przedmioty w odpowiedni sposob, by moc je ulepszyc.");
                        return;
                    }

                    if (item.isSimilar(readyItems.get("$1")) || item.isSimilar(readyItems.get("Uniwersalny_bilet"))) {
                        player.sendMessage(getUserPrefix() + "Kowal nie obsluguje tego przedmiotu.");
                        return;
                    }

                    World world = player.getWorld();
                    Location location = player.getLocation();

                    if (task.equals("clone")) {
                        if (!offItem.isSimilar(readyItems.get("Uniwersalny_bilet"))) {
                            player.sendMessage(getUserPrefix() + "Nie wykryto Uniwersalnego Biletu.");
                            return;
                        }

                        List<String> itemLore = item.getItemMeta().getLore();
                        if (itemLore == null || itemLore.isEmpty()) {
                            player.sendMessage(getUserPrefix() + "Mozesz podwajac tylko niestandardowe przedmioty.");
                            return;
                        }

                        renderBlacksmithResult(player, location, world, inventory, item, offItem, task, null, 0, new Random().nextInt(5) < 2);
                        return;
                    }

                    ItemMeta offItemMeta = offItem.getItemMeta();
                    if (offItem.getType() != Material.ENCHANTED_BOOK || offItemMeta == null) {
                        player.sendMessage(getUserPrefix() + "W drugiej rece umiesc ulepszenie, ktore chcesz zaaplikowac.");
                        return;
                    }

                    EnchantmentStorageMeta bookStorageMeta = (EnchantmentStorageMeta) offItemMeta;

                    Map<Enchantment, Integer> bookEnchantments = bookStorageMeta.getStoredEnchants();
                    for (Enchantment bookEnchantment : bookEnchantments.keySet()) {
                        if (bookEnchantments.get(bookEnchantment) <= bookEnchantment.getMaxLevel()) {
                            player.sendMessage(getUserPrefix() + "Kowal zajmuje sie tylko niestandardowymi ulepszeniami..");
                            return;
                        }

                        if (!bookEnchantment.canEnchantItem(item)) {
                            player.sendMessage(getUserPrefix() + "Nie mozesz zaplikowac takiego ulepszenia na ten przedmiot.");
                            return;
                        }

                        for (Enchantment itemEnchantment : item.getEnchantments().keySet()) {
                            if (!bookEnchantment.conflictsWith(itemEnchantment) || (bookEnchantment == itemEnchantment && item.getEnchantmentLevel(itemEnchantment) != bookEnchantments.get(bookEnchantment))) continue;

                            player.sendMessage(getUserPrefix() + "Wybrane ulepszenia koliduja ze soba.");
                            return;
                        }

                        renderBlacksmithResult(player, location, world, inventory, item, offItem, task, bookEnchantment, bookEnchantments.get(bookEnchantment), new Random().nextInt(3) == 0);
                    }
                });

                return true;
            }

            @Override
            public String getUsage() {
                return "/kowalexecute <player>";
            }
        }.setPermission("claimworld.player");
    }
}
