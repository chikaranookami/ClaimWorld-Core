package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.battlepass.BattlePassManager;
import claimworld.net.supporter.battlepass.SkillManager;
import claimworld.net.supporter.guis.Gui;
import claimworld.net.supporter.guis.GuiManager;
import claimworld.net.supporter.items.Locker;
import claimworld.net.supporter.items.ReadyItems;
import claimworld.net.supporter.tasks.TaskManager;
import claimworld.net.supporter.utils.JetpackUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.Bukkit.*;

public class InventoryClickEvent implements Listener {

    Locker locker = Locker.getInstance();
    TaskManager taskManager = TaskManager.getInstance();
    JetpackUtils jetpackUtils = JetpackUtils.getInstance();

    private final List<String> fixedEquipments = new ArrayList<>();

    public InventoryClickEvent() {
        fixedEquipments.add("Kosz");
        fixedEquipments.add("NPC Equipment");
    }

    @EventHandler
    public void inventoryClickEvent(org.bukkit.event.inventory.InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) return;

        int slot = event.getSlot();
        Player player = (Player) event.getWhoClicked();
        String playerName = player.getName();
        ItemStack item = event.getCurrentItem();
        Inventory playerInventory = player.getInventory();

        //shulker inventory == player inventory - sprawdzone, tak jest
        if (clickedInventory == playerInventory) {
            if (event.getAction() == InventoryAction.HOTBAR_SWAP) {
                if (!jetpackUtils.isJetpack(clickedInventory.getItem(40))) return;
                event.setCancelled(true);
                return;
            }

            if (slot == 17) {
                event.setCancelled(true);
                getScheduler().runTaskLater(Supporter.getPlugin(), () -> new GuiManager(player, new Gui(null, 54, "Menu")), 1L);
                return;
            }

            if (slot == 40) {
                if (!jetpackUtils.isJetpack(item)) return;
                if (!player.isFlying() && !jetpackUtils.hasJetpack(playerName)) return;
                event.setCancelled(true);
                return;
            }
        }

        Inventory inventory = event.getInventory();

        if (inventory.getType() != InventoryType.CHEST) return;
        if (inventory.getLocation() != null) return;
        if (inventory.getSize() < 9) return;
        if (fixedEquipments.contains(event.getView().getTitle())) return;

        event.setCancelled(true);

        if (!(event.isLeftClick())) return;

        String title = event.getView().getTitle();

        if (title.equals("Skrytka " + playerName)) {
            if (item == null) return;
            if (item.getType().isAir()) return;
            if (playerInventory.firstEmpty() == -1) return;
            if (event.getClickedInventory() == playerInventory) return;

            HashMap<String, List<ItemStack>> lockerMap = locker.getLockerMap();
            if (lockerMap.get(playerName).size() < 1) return;
            if (lockerMap.get(playerName) == null) return;

            inventory.removeItem(item);
            playerInventory.addItem(item);
            return;
        }

        if (title.equals("Menu")) {
            switch (slot) {
                case 37:
                    player.closeInventory();
                    getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(player, "pomoc"), 1L);
                    return;
                case 28:
                    player.closeInventory();
                    getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(player, "ogloszenia"), 1L);
                    return;
                case 13:
                    player.closeInventory();
                    getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(player, "vote gui"), 1L);
                    return;
                case 12:
                    new GuiManager(player, new Gui(null, 54, "Teleportacja"));
                    return;
                case 43:
                    new GuiManager(player, new Gui(null, 54, "Ulatwienia dostepu"));
                    return;
                case 21:
                    new GuiManager(player, new Gui(null, 54, "Skrytka " + player.getName()));
                    return;
                case 22:
                    player.closeInventory();
                    player.openBook(new SkillManager().getSkillBook(player));
                    return;
                case 23:
                    player.closeInventory();
                    getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(player, "zadania"), 1L);
                    return;
                case 14:
                    player.closeInventory();
                    player.openBook(new BattlePassManager().getBattlepassBook());
                    return;
                case 16:
                    if (player.hasPermission("claimworld.vip")) new GuiManager(player, new Gui(null, 54, "Panel VIP"));
                    return;
            }

            return;
        }

        if (title.equals("Mechanik")) {
            switch (slot) {
                case 10:
                    dispatchCommand(getConsoleSender(), "buyitem " + playerName + " Elytra");
                    return;
                case 12:
                    dispatchCommand(getConsoleSender(), "buyitem " + playerName + " Jetpack");
                    return;
            }
            return;
        }

        if (title.equals("Teleportacja") || title.equals("Ulatwienia dostepu") || title.equals("Panel VIP")) {
            if (slot == 53) {
                new GuiManager(player, new Gui(null, 54, "Menu"));
                return;
            }

            if (title.equals("Panel VIP")) {
                switch (slot) {
                    case 0:
                        player.closeInventory();
                        dispatchCommand(player, "sit");
                        return;
                    case 1:
                        player.closeInventory();
                        dispatchCommand(player, "hat");
                        return;
                    case 2:
                        player.closeInventory();
                        dispatchCommand(player, "skin");
                        return;
                    case 3:
                        player.closeInventory();
                        dispatchCommand(player, "me");
                        return;
                    case 4:
                        player.closeInventory();
                        dispatchCommand(player, "playtime");
                        return;
                    case 18:
                        player.closeInventory();
                        dispatchCommand(player, "lay");
                        return;
                    case 19:
                        player.closeInventory();
                        dispatchCommand(player, "bellyflop");
                        return;
                    case 20:
                        player.closeInventory();
                        dispatchCommand(player, "crawl");
                        return;
                    case 21:
                        player.closeInventory();
                        dispatchCommand(player, "spin");
                        return;
                    case 22:
                        player.closeInventory();
                        dispatchCommand(player, "pp");
                        return;
                    case 23:
                        player.closeInventory();
                        dispatchCommand(player, "fw");
                        return;
                    case 24:
                        player.closeInventory();
                        dispatchCommand(player, "seriafw");
                        return;
                    case 25:
                        player.closeInventory();
                        dispatchCommand(player, "chestbooster");
                        return;
                }

                return;
            }

            if (title.equals("Teleportacja")) {
                switch (slot) {
                    case 10:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(player, "sethome"), 1L);
                        return;
                    case 12:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(player, "home"), 1L);
                        return;
                    case 13:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(player, "spawn"), 1L);
                        return;
                    case 14:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(getConsoleSender(), "loadlokacja " + player.getName() + " autostrada_polnocna"), 1L);
                        return;
                    case 15:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(getConsoleSender(), "loadlokacja " + player.getName() + " autostrada_poludniowa"), 1L);
                        return;
                    case 16:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(getConsoleSender(), "loadlokacja " + player.getName() + " wyspa_centralna"), 1L);
                        return;
                    case 21:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(getConsoleSender(), "loadlokacja " + player.getName() + " lasy_wschodnie"), 1L);
                        return;
                    case 22:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(getConsoleSender(), "loadlokacja " + player.getName() + " nadmorska_szlachta"), 1L);
                        return;
                    case 23:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(getConsoleSender(), "loadlokacja " + player.getName() + " monopoly"), 1L);
                        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("singleTeleportYourself")));
                        return;
                    case 24:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(getConsoleSender(), "loadlokacja " + player.getName() + " wioska"), 1L);
                        return;
                    case 25:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(getConsoleSender(), "loadlokacja " + player.getName() + " lasy_polnocne"), 1L);
                        return;
                }

                return;
            }

            if (title.equals("Ulatwienia dostepu")) {
                switch (slot) {
                    case 10:
                        player.closeInventory();
                        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskManager.getTaskMap().get("checkSeaLevel")));
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(player, "depth"), 1L);
                        return;
                    case 11:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(player, "disposal"), 1L);
                        return;
                    case 12:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> Bukkit.dispatchCommand(player, "dice"), 1L);
                }
            }
        }
    }
}
