package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.guis.Gui;
import claimworld.net.supporter.utils.guis.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.logging.Level;

import static org.bukkit.Bukkit.*;

public class InventoryClickEvent implements Listener {

    @EventHandler
    public void inventoryClickEvent(org.bukkit.event.inventory.InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;

        int slot = event.getSlot();
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();

        if (slot == 17 && clickedInventory == player.getInventory()) {
            event.setCancelled(true);
            getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                new GuiManager(player, new Gui(null, 54, "Menu"));
            }, 1L);
            return;
        }

        //if (clickedInventory == player.getInventory()) return;

        Inventory inventory = event.getInventory();

        if (inventory.getType() != InventoryType.CHEST) return;
        if (inventory.getLocation() != null) return;
        if (inventory.getSize() < 9) return;
        if (event.getView().getTitle().equals("Kosz")) return;

        event.setCancelled(true);

        String title = event.getView().getTitle();

        if (title.equals("Menu")) {
            switch (slot) {
                case 28:
                    player.closeInventory();
                    getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                        Bukkit.dispatchCommand(player, "pomoc");
                    }, 1L);
                    return;
                case 13:
                    player.closeInventory();
                    getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                        Bukkit.dispatchCommand(player, "vote gui");
                    }, 1L);
                    return;
                case 12:
                    new GuiManager(player, new Gui(null, 54, "Teleportacja"));
                    return;
                case 37:
                    new GuiManager(player, new Gui(null, 54, "Ulatwienia dostepu"));
                    return;
            }
        }

        if (title.equals("Teleportacja") || title.equals("Ulatwienia dostepu")) {
            if (slot == 53) {
                new GuiManager(player, new Gui(null, 54, "Menu"));
                return;
            }

            if (title.equals("Teleportacja")) {
                switch (slot) {
                    case 10:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                            Bukkit.dispatchCommand(player, "sethome");
                        }, 1L);
                        return;
                    case 12:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                            Bukkit.dispatchCommand(player, "home");
                        }, 1L);
                        return;
                    case 13:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                            Bukkit.dispatchCommand(player, "spawn");
                        }, 1L);
                        return;
                    case 14:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                            Bukkit.dispatchCommand(getConsoleSender(), "loadlokacja " + player.getName() + " autostrada_polnocna");
                        }, 1L);
                        return;
                    case 15:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                            Bukkit.dispatchCommand(getConsoleSender(), "loadlokacja " + player.getName() + " autostrada_poludniowa");
                        }, 1L);
                        return;
                    case 16:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                            Bukkit.dispatchCommand(getConsoleSender(), "loadlokacja " + player.getName() + " centrum_publiczne");
                        }, 1L);
                        return;
                    case 21:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                            Bukkit.dispatchCommand(getConsoleSender(), "loadlokacja " + player.getName() + " lasy_wschodnie");
                        }, 1L);
                        return;
                    case 22:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                            Bukkit.dispatchCommand(getConsoleSender(), "loadlokacja " + player.getName() + " nadmorska_szlachta");
                        }, 1L);
                        return;
                }
            }

            if (title.equals("Ulatwienia dostepu")) {
                switch (slot) {
                    case 10:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                            Bukkit.dispatchCommand(player, "depth");
                        }, 1L);
                        return;
                    case 11:
                        player.closeInventory();
                        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                            Bukkit.dispatchCommand(player, "disposal");
                        }, 1L);
                }
            }
        }
    }
}
